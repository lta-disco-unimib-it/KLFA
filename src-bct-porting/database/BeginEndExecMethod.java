package database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BeginEndExecMethod {
	
	static int idBeginEndExecMethod;
	static int occurrence;
	static String beginEnd;
	static int startMethod;
	
	//FK
	static int idMethod = 0;
	static long idThread = 0;
	
	public BeginEndExecMethod(){
	}
	
	public static void insert(String methodSignature, String methodState, long threadId) throws DataLayerException {
		//System.out.println("insert "+methodSignature+" "+methodState);
		getStartMethod(methodState, threadId);
		getOccurence(threadId);
		getMethodId(methodSignature);
		
		try {
			//autoincrement database 
			PreparedStatement stmt = ConnectionDispenser.getConnection().prepareStatement(
			"INSERT INTO beginendexecmethod (Thread_idThread, Method_idMethod, occurrence, beginEnd, startMethod) VALUES(?,?,?,?,?)");
			stmt.setLong(1, threadId);
			stmt.setInt(2, idMethod);
			stmt.setInt(3, occurrence);
			stmt.setString(4, methodState);
			stmt.setInt(5, startMethod);
			stmt.execute();
			//System.out.println("Statement "+stmt.toString());
			ResultSet rs = stmt.getGeneratedKeys();
			if (! rs.first()) throw new DataLayerException("Unable to insert BeginEndExecMethod " + methodState); 
			idBeginEndExecMethod = rs.getInt(1); 

			//Log.getInstance().debug("Project " + name + " inserted");
			stmt.close();
			
		} catch (SQLException e) {
			throw new DataLayerException(e.getMessage());
		} catch (DBException e) {
			throw new DataLayerException(e.getMessage());
		}
		if(methodState == "B#") {
			Registry.getInstance(threadId).push(idBeginEndExecMethod);
			
		}
	}
	
	private static void getStartMethod(String methodState, long threadId) {
		if(methodState == "E#") {
			startMethod =Registry.getInstance(threadId).pop();
		}else startMethod = 0;
	}

	private static void getMethodId(String methodSignature) throws DataLayerException{
		try {
			PreparedStatement stmt = ConnectionDispenser.getConnection().prepareStatement(
				"SELECT idMethod FROM method WHERE methodDeclaration = ?");
			stmt.setString(1, methodSignature);
			ResultSet rs = stmt.executeQuery();
			if (rs.first()) {
				idMethod = rs.getInt("idMethod");
				stmt.close();
			}
			else {
				stmt.close();
				throw new DataLayerException("Invalid Method: " + methodSignature );
			}
		} catch (SQLException e) {
			throw new DataLayerException(e.getMessage());
		} catch (DBException e) {
			throw new DataLayerException(e.getMessage());
		}
		
	}

	private static void getOccurence(long threadId) throws DataLayerException {
		try {
			PreparedStatement stmt = ConnectionDispenser.getConnection().prepareStatement(
				"SELECT occurrence FROM beginendexecmethod, thread WHERE thread_idThread = idThread AND idThread = ?");
			stmt.setLong(1, threadId);
			ResultSet rs = stmt.executeQuery();
			
			if (rs.last()) {
				occurrence = rs.getInt("occurrence");
				occurrence++;
				stmt.close();
			} else {
				//System.out.println("-----------NEW THREAD---------------------");
				occurrence = 1;
				stmt.close();
			}
		} catch (SQLException e) {
			throw new DataLayerException(e.getMessage());
		} catch (DBException e) {
			throw new DataLayerException(e.getMessage());
		}
	}
	
	public static int getId() {
		return idBeginEndExecMethod;
	}
}
