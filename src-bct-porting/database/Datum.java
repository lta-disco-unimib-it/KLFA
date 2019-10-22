package database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Datum {
	
	static int idDatum;
	static String dataDefinition;
	
	//FK
	static int idBeginEndExecMethod = 0;
	
	public Datum(){
	}
	
	public static void insert(String methodSignature, String message) throws DataLayerException 
	{
		getIdBeginEndExecMethod();
		
		try {
			//autoincrement database 		
			PreparedStatement stmt = ConnectionDispenser.getConnection().prepareStatement(
			"INSERT INTO datum (BeginEndExecMethod_idBeginEndExecMethod, dataDefinition) VALUES(?, ?)");
			
			//System.out.println("DATUM" + methodSignature + message);
			
			stmt.setInt(1, BeginEndExecMethod.getId());
			stmt.setString(2, methodSignature + message);
			
			//DatumDispenser.getInstance().addPreparedStatement(stmt);
			
			stmt.execute();
						
			ResultSet rs = stmt.getGeneratedKeys();
			if (! rs.first()) {
				throw new DataLayerException("Unable to insert record into Datum" + methodSignature + "\n" +message); 
			}
			idDatum = rs.getInt(1); 

			//Log.getInstance().debug("Project " + name + " inserted");
			
			// it colses the statement NOT the DB connection
			stmt.close();			 					
		} catch (SQLException e) {
			throw new DataLayerException(e.getMessage());
		} catch (DBException e) {
			throw new DataLayerException(e.getMessage());
		}
	}

	private static void getIdBeginEndExecMethod() {
		
	}	
}
