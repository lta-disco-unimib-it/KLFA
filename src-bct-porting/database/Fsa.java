package database;

import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.rowset.serial.SerialBlob;

import flattener.writers.DBWriter;

public class Fsa {
	
	static int idFSA;
	static Blob fsa;
	
	//FK
	static int idMethod = 0;
	
	public Fsa(){
	}
	
	public static void insert(String methodSignature, Blob fsa) throws DataLayerException 
	{
		getMethodId(methodSignature);
		try {
			PreparedStatement stmt = ConnectionDispenser.getConnection().prepareStatement(
			"INSERT INTO fsa (fsa, method_idMethod) VALUES(?,?)");
			stmt.setBlob(1, fsa);
			stmt.setInt(2, idMethod);
			stmt.execute();
			
			ResultSet rs = stmt.getGeneratedKeys();
			if (! rs.first()) throw new DataLayerException("Unable to insert FSA model for " + methodSignature); 
			idFSA = rs.getInt(1); 

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
