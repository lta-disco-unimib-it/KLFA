package database;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import flattener.writers.DBWriter;

public class MethodCall {
	
	static int idMethodCall;
	static int occurence;
	
	//FK
	static int idMethod;
	static int idInteractionTrace;
	int idBeginEndExecMethod;
	
	public MethodCall(){
	}
	
	public static void insert(int idInteractionTrace, String methodName, int occurence, int idBeginEndExecMethod) throws DataLayerException 
	{
		getMethodId(methodName);
		
		try {
			PreparedStatement stmt = ConnectionDispenser.getConnection().prepareStatement(
			"INSERT INTO methodcall (method_idMethod, interactiontrace_idInteractionTrace, beginendexecmethod_idBeginEndExecMethod, occurrence) VALUES(?,?,?,?)");
			stmt.setInt(1, idMethod);
			stmt.setInt(2, idInteractionTrace);
			stmt.setInt(3, idBeginEndExecMethod);
			stmt.setInt(4, occurence);
			stmt.execute();
			
			ResultSet rs = stmt.getGeneratedKeys();
			if (! rs.first()) throw new DataLayerException("Unable to insert method call for " + methodName); 
			idMethodCall = rs.getInt(1); 

			//Log.getInstance().debug("Project " + name + " inserted");
			stmt.close();
			
		} catch (SQLException e) {
			throw new DataLayerException(e.getMessage());
		} catch (DBException e) {
			throw new DataLayerException(e.getMessage());
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