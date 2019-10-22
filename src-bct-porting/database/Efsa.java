package database;

import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.rowset.serial.SerialBlob;

import flattener.writers.DBWriter;

public class Efsa {
	
	static int idEFSA;
	static Blob efsa;
	
	//FK
	static int idMethod = 0;
	
	public Efsa(){
	}
	
	public static void insert(int idMethod, SerialBlob efsa) throws DataLayerException {
		
		try {
			PreparedStatement stmt = ConnectionDispenser.getConnection().prepareStatement(
			"INSERT INTO efsa (efsa, method_idMethod) VALUES(?,?)");
			stmt.setBlob(1, efsa);
			stmt.setInt(2, idMethod);
			stmt.execute();
			
			ResultSet rs = stmt.getGeneratedKeys();
			if (! rs.first()) throw new DataLayerException("Unable to insert FSA model for " + idMethod); 
			idEFSA = rs.getInt(1); 

			//Log.getInstance().debug("Project " + name + " inserted");
			stmt.close();
			
		} catch (SQLException e) {
			throw new DataLayerException(e.getMessage());
		} catch (DBException e) {
			throw new DataLayerException(e.getMessage());
		}	
	}
}