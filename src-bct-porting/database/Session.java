package database;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import flattener.writers.DBWriter;



public class Session {
	
	static int idSession;
	static String dataSession;
	
	public Session(){
	}
	
	static void insert() throws DataLayerException 
	{
		try {
			PreparedStatement stmt = ConnectionDispenser.getConnection().prepareStatement(
			"INSERT INTO sesson (idSession, dataSession) VALUES(?,?)");
			stmt.setInt(1, idSession);
			stmt.setString(2, dataSession);
			stmt.execute();
			//ResultSet rs = stmt.getGeneratedKeys();
			//if (! rs.first()) throw new DataLayerException("Unable to insert Project " + name); 
			//id = rs.getLong(1); 

			//Log.getInstance().debug("Project " + name + " inserted");
			stmt.close();
			
		} catch (SQLException e) {
			throw new DataLayerException(e.getMessage());
		} catch (DBException e) {
			throw new DataLayerException(e.getMessage());
		}
	}	
}