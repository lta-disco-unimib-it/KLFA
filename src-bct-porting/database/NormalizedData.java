package database;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import flattener.writers.DBWriter;

public class NormalizedData {
	
	static int idNormalizedData;
	static String normalizedDataDefinition;
	
	//FK
	static int idBeginEndExecMethod = 0;
	static Integer idMethodCall = null;
	
	public NormalizedData(){
	}
	
	public static void insert(String methodName, String trace, int idBeginEndExecMethod) throws DataLayerException 
	{
		getMethodCall(idBeginEndExecMethod);
		try {
			
			/*
			System.out.println("INSERT INTO normalizeddata:");
			System.out.println("methodName: " + methodName);			
			System.out.println("idMethodCall: " + idMethodCall);
			System.out.println("trace: " + trace);
			*/
			
			PreparedStatement stmt = ConnectionDispenser.getConnection().prepareStatement(			
			"INSERT INTO normalizeddata (beginendexecmethod_idBeginEndExecMethod, methodCall_idMethodCall, normalizedDataDefinition) VALUES(?,?,?)");
			stmt.setInt(1, idBeginEndExecMethod);
			stmt.setObject(2, idMethodCall);
			stmt.setString(3, trace);
			stmt.execute();
			//System.out.println("NormalizedData.insert "+stmt);
			ResultSet rs = stmt.getGeneratedKeys();
			if (! rs.first()) throw new DataLayerException("Unable to insert NormalizedData for " + methodName); 
			idNormalizedData = rs.getInt(1); 

			//Log.getInstance().debug("Project " + name + " inserted");
			stmt.close();
			idMethodCall = null;
			
		} catch (SQLException e) {
			throw new DataLayerException(e.getMessage());
		} catch (DBException e) {
			throw new DataLayerException(e.getMessage());
		}
	}

	private static void getMethodCall(int idBeginEndExecMethod) throws DataLayerException {
		try {			
			PreparedStatement stmt = ConnectionDispenser.getConnection().prepareStatement(
				"SELECT idMethodCall FROM methodcall WHERE beginendexecmethod_idBeginEndExecMethod = ?");
			stmt.setInt(1, idBeginEndExecMethod);		
			
			ResultSet rs = stmt.executeQuery();
			if (rs.first()) {
				idMethodCall = rs.getInt("idMethodCall");							
				stmt.close();
			}
			else {
				int tmp = 0;
				stmt = ConnectionDispenser.getConnection().prepareStatement(
				"SELECT startMethod FROM beginendexecmethod WHERE idBeginEndExecMethod = ?");
				stmt.setInt(1, idBeginEndExecMethod);
				rs = stmt.executeQuery();
				if (rs.first()) {
					tmp = rs.getInt("startMethod");
					stmt.close();
			
					stmt = ConnectionDispenser.getConnection().prepareStatement(
					"SELECT idMethodCall FROM methodcall WHERE beginendexecmethod_idBeginEndExecMethod = ?");
					stmt.setInt(1, tmp);
					rs = stmt.executeQuery();
					if (rs.first()) {
						idMethodCall = rs.getInt("idMethodCall");
						stmt.close();
					} //else {
						  //throw new DataLayerException("Invalid idBeginEndExecMethod: " + idBeginEndExecMethod);
						  //stmt.close();
					//}
				} //else {
					//throw new DataLayerException("Invalid idBeginEndExecMethod: " + idBeginEndExecMethod);
					//stmt.close();	
				//}
			}
		} catch (SQLException e) {
			throw new DataLayerException(e.getMessage());
		} catch (DBException e) {
			throw new DataLayerException(e.getMessage());
		}
		
	}
}