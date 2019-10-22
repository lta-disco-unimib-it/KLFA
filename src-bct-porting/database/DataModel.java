package database;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import flattener.writers.DBWriter;

public class DataModel {
	
	static int idDataModel;
	static String modelIN;
	static String modelOUT;
	
	//FK
	static int idMethod = 0;
	
	public DataModel(){
	}
	
	public static void insert(String methodSignature, String modelIN, String modelOUT) throws DataLayerException 
	{
		getMethodId(methodSignature);
		try {
			PreparedStatement stmt = ConnectionDispenser.getConnection().prepareStatement(
			"INSERT INTO datamodel (modelIN, modelOUT, method_idMethod) VALUES(?,?,?)");
			stmt.setString(1, modelIN);
			stmt.setString(2, modelOUT);
			stmt.setInt(3, idMethod);
			stmt.execute();

			ResultSet rs = stmt.getGeneratedKeys();
			if (! rs.first()) throw new DataLayerException("Unable to insert IO invariant declaration for " + methodSignature); 
			idDataModel = rs.getInt(1); 

			//Log.getInstance().debug("Project " + name + " inserted");
			stmt.close();
			
		} catch (SQLException e) {
			throw new DataLayerException(e.getMessage());
		} catch (DBException e) {
			throw new DataLayerException(e.getMessage());
		}
	}
	
	private static void getMethodId(String methodName) throws DataLayerException{
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
