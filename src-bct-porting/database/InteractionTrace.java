package database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import traceReaders.raw.Token;

import flattener.writers.DBWriter;

public class InteractionTrace {
	
	static int idInteractionTrace;
	
	//FK
	static int idMethod;
	static int idThread = 0;
	
	public InteractionTrace(){
	}
	
	public static void insert(String methodName, Vector<Token> trace, String threadId) throws DataLayerException {
		
		getMethodId(methodName);
		
		try {
			PreparedStatement stmt = ConnectionDispenser.getConnection().prepareStatement(
			"INSERT INTO interactiontrace (method_idMethod, thread_idThread) VALUES(?,?)");
			stmt.setInt(1, idMethod);
			stmt.setString(2, threadId);
			stmt.execute();

			ResultSet rs = stmt.getGeneratedKeys();
			if (! rs.first()) throw new DataLayerException("Unable to insert interaction trace " + methodName); 
			idInteractionTrace = rs.getInt(1); 

			//Log.getInstance().debug("Project " + name + " inserted");
			stmt.close();
			
		} catch (SQLException e) {
			throw new DataLayerException(e.getMessage());
		} catch (DBException e) {
			throw new DataLayerException(e.getMessage());
		}
		 
		if(trace != null) {
			int occurrence = 0;
			for (int currentPosition = 0; currentPosition < trace.size(); currentPosition++) {	
				occurrence++;
				try {
					MethodCall.insert(idInteractionTrace, ((Token)trace.get(currentPosition)).getMethodName().substring(0, ((Token)trace.get(currentPosition)).getMethodName().length()-1), occurrence, ((Token)trace.get(currentPosition)).getId());
				} catch (DataLayerException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	private static void getMethodId(String methodName) throws DataLayerException {
		try {
			PreparedStatement stmt = ConnectionDispenser.getConnection().prepareStatement(
				"SELECT idMethod FROM method WHERE methodDeclaration = ?");
			stmt.setString(1, methodName);
			ResultSet rs = stmt.executeQuery();
			if (rs.first()) {
				idMethod = rs.getInt("idMethod");
				stmt.close();
			}
			else {
				stmt.close();
				throw new DataLayerException("Invalid Method: " + methodName );
			}
		} catch (SQLException e) {
			throw new DataLayerException(e.getMessage());
		} catch (DBException e) {
			throw new DataLayerException(e.getMessage());
		}	
	}	
}
