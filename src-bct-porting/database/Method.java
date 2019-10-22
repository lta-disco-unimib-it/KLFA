package database;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Method {
	
	static int idMethod;
	static String methodDeclaration;
	
	public Method(){
	}
	
	public static void insert(String methodSignature) throws DataLayerException 
	{
		try {
			PreparedStatement stmt = ConnectionDispenser.getConnection().prepareStatement(
				"SELECT methodDeclaration FROM method WHERE methodDEclaration = ? ");
			stmt.setString(1, methodSignature);
			ResultSet rs = stmt.executeQuery();
			if (rs.first()) {
				//the methodSignature has already inserted
				stmt.close();
			}
			else {
				//autoincrement database 
				stmt = ConnectionDispenser.getConnection().prepareStatement(
				"INSERT INTO method (methodDeclaration) VALUES(?)");
				stmt.setString(1, methodSignature);
				stmt.execute();
				
				rs = stmt.getGeneratedKeys();
				if (! rs.first()) throw new DataLayerException("Unable to insert record into Method " + methodSignature); 
				idMethod = rs.getInt(1); 
				stmt.close();
			}
		} catch (SQLException e) {
			throw new DataLayerException(e.getMessage(),e);
		} catch (DBException e) {
			throw new DataLayerException(e.getMessage(),e);
		}
	}
	
	public static int getID() {
		return idMethod;
	}
}
