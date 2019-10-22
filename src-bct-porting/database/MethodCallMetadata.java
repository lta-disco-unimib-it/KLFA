package database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MethodCallMetadata {

	public static void insert(String methodSignature, String metadata) throws DataLayerException 
	{

		
		try {
			//autoincrement database 		
			PreparedStatement stmt = ConnectionDispenser.getConnection().prepareStatement(
			"INSERT INTO methodcallmetadata (BeginEndExecMethod_idBeginEndExecMethod, data) VALUES(?, ?)");
			
			//System.out.println("DATUM" + methodSignature + message);
			
			stmt.setInt(1, BeginEndExecMethod.getId());
			stmt.setString(2, metadata);
			
			//DatumDispenser.getInstance().addPreparedStatement(stmt);
			
			stmt.execute();
						
			ResultSet rs = stmt.getGeneratedKeys();
			if (! rs.first()) {
				throw new DataLayerException("Unable to insert record into Datum" + methodSignature + "\n" +metadata); 
			}
			 

			//Log.getInstance().debug("Project " + name + " inserted");
			
			// it colses the statement NOT the DB connection
			stmt.close();			 					
		} catch (SQLException e) {
			throw new DataLayerException(e.getMessage());
		} catch (DBException e) {
			throw new DataLayerException(e.getMessage());
		}
	}

}
