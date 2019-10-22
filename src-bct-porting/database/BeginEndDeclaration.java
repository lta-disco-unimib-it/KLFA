package database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BeginEndDeclaration {
	
	static int idBeginEndDeclaration;
	static String beginDeclaration;
	static String endDeclaration;
	
	//FK
	static int idMethod;
	
	public BeginEndDeclaration(){
	}
	
	public static void insert(String methodName, String beginDeclaration, String endDeclaration) throws DataLayerException 
	{
		getMethodId(methodName);
		try {
			PreparedStatement stmt = ConnectionDispenser.getConnection().prepareStatement(
			"INSERT INTO beginenddeclaration (Method_idMethod, beginDeclaration, endDeclaration) VALUES(?,?,?)");
			stmt.setInt(1, idMethod);
			stmt.setString(2, beginDeclaration);
			stmt.setString(3, endDeclaration);
			stmt.execute();

			ResultSet rs = stmt.getGeneratedKeys();
			if (! rs.first()) throw new DataLayerException("Unable to insert BeginEndDeclaration declaration for " + methodName); 
			idBeginEndDeclaration = rs.getInt(1); 

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

	public static String getDeclaration(String methodName) throws DataLayerException {
		
		String declaration = "";
		try {
			PreparedStatement stmt = ConnectionDispenser.getConnection().prepareStatement(
				"SELECT beginDeclaration, endDeclaration FROM method, beginenddeclaration " +
				"WHERE method_idMethod = idMethod AND methodDeclaration = ?");
			stmt.setString(1, methodName);
			ResultSet rs = stmt.executeQuery();
			if (rs.first()) {
				declaration = rs.getString("beginDeclaration");
				declaration = declaration.concat(rs.getString("endDeclaration"));
			}else {
				throw new DataLayerException("Invalid Method: " + methodName ); 
			}
			stmt.close();
		} catch (SQLException e) {
			throw new DataLayerException(e.getMessage());
		} catch (DBException e) {
			throw new DataLayerException(e.getMessage());
		}
		return declaration;
	}
}
