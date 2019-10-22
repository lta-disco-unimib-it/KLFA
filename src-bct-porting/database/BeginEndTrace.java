package database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BeginEndTrace {
	
	static long idBeginEndTrace;
	
	//FK
	static int thread_idThread=0;
	
	public BeginEndTrace(){
	}
	
	public static void insert(long threadId) throws DataLayerException 
	{
		try {
			PreparedStatement stmt = ConnectionDispenser.getConnection().prepareStatement(
				"SELECT idBeginEndTrace FROM beginendtrace WHERE idBeginEndTrace = ? ");
			stmt.setLong(1, threadId);
			ResultSet rs = stmt.executeQuery();
			if (rs.first()) {
				stmt.close();
			}
			else {
				System.out.println("BEGINENDTRACE-----------" + threadId);
				stmt = ConnectionDispenser.getConnection().prepareStatement(
				"INSERT INTO beginendtrace (idBeginEndTrace, thread_idThread) VALUES(?,?)");
				stmt.setLong(1, threadId);
				stmt.setInt(2, thread_idThread);
				//rs = stmt.executeQuery(); ?????????
					if(!stmt.execute())throw new DataLayerException("Unable to insert record into beginendtrace " + threadId );
				//if (! rs.first()) throw new DataLayerException("Unable to insert record into beginendtrace " + threadId ); 
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
