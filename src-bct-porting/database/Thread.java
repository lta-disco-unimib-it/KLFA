package database;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import flattener.writers.DBWriter;

public class Thread {
	
	static long idThread;
	
	//FK
	static int session_idSession = 0;
	
	public Thread(){
	}
	
	public static void insert(long threadId) throws DataLayerException {
		
		try {
			PreparedStatement stmt = ConnectionDispenser.getConnection().prepareStatement(
				"SELECT idThread FROM thread WHERE idThread = ? ");
			stmt.setLong(1, threadId);
			ResultSet rs = stmt.executeQuery();
			if (rs.first()) {
				stmt.close();
			}
			else {
				//System.out.println("THREAD-----------" + threadId);
				stmt = ConnectionDispenser.getConnection().prepareStatement(
				"INSERT INTO thread (idThread, session_idSession) VALUES(?,?)");
				stmt.setLong(1, threadId);
				stmt.setInt(2, session_idSession);
				stmt.execute();
				//if(!stmt.execute()) throw new DataLayerException("Unable to insert record into thread " + threadId );
				 
				//idBeginEndTrace inserted 
				stmt.close();
			}
		} catch (SQLException e) {
			throw new DataLayerException(e.getMessage());
		} catch (DBException e) {
			throw new DataLayerException(e.getMessage());
		}
	}
}