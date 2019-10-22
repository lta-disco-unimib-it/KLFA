package traceReaders.normalized;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import database.ConnectionDispenser;
import database.DBException;
import database.DataLayerException;

public class DBNormalizedTracesReader implements NormalizedTracesReader {

	public Iterator getMethods() throws DataLayerException {
		
		ArrayList al = new ArrayList();
		try {
			
			PreparedStatement stmt = ConnectionDispenser.getConnection().prepareStatement(
				"SELECT DISTINCT idMethod FROM method, interactiontrace WHERE idMethod = method_idMethod ");
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
				al.add(rs.getInt("idMethod"));
			}
			
			stmt.close();
		} catch (SQLException e) {
			throw new DataLayerException(e.getMessage());
		} catch (DBException e) {
			throw new DataLayerException(e.getMessage());
		}
		return al.iterator();
	}

	public Iterator getInteractionTraces(int method) throws DataLayerException {
		
		ArrayList al = new ArrayList();
		try {
			
			PreparedStatement stmt = ConnectionDispenser.getConnection().prepareStatement(
				"SELECT idInteractionTrace FROM interactiontrace WHERE method_idMethod = ? ");
			stmt.setInt(1, method);
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
				al.add(rs.getInt("idInteractionTrace"));
			}
			
			stmt.close();
		} catch (SQLException e) {
			throw new DataLayerException(e.getMessage());
		} catch (DBException e) {
			throw new DataLayerException(e.getMessage());
		}
		return al.iterator();
	}
	
	public int getInteractionThread(int interactionTrace) throws DataLayerException {
		
		int thread = 0;
		
		try {
			
			PreparedStatement stmt = ConnectionDispenser.getConnection().prepareStatement(
				"SELECT thread_idThread FROM interactiontrace WHERE idInteractionTrace = ? ");
			stmt.setInt(1, interactionTrace);
			ResultSet rs = stmt.executeQuery();
			
			if(rs.first())
				thread = rs.getInt("thread_idThread");
			
			stmt.close();
		} catch (SQLException e) {
			throw new DataLayerException(e.getMessage());
		} catch (DBException e) {
			throw new DataLayerException(e.getMessage());
		}
		return thread;
	}
	
	public Iterator getMethodCall(int interactionTrace) throws DataLayerException {
		
		ArrayList al = new ArrayList();
		try {
			
			PreparedStatement stmt = ConnectionDispenser.getConnection().prepareStatement(
				"SELECT method_idMethod FROM methodcall WHERE interactiontrace_idInteractionTrace = ? ");
			stmt.setInt(1, interactionTrace);
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
				al.add(rs.getInt("method_idMethod"));
			}
			
			stmt.close();
		} catch (SQLException e) {
			throw new DataLayerException(e.getMessage());
		} catch (DBException e) {
			throw new DataLayerException(e.getMessage());
		}
		return al.iterator();
	}
	
	public Iterator getIDMethodCall(int interactionTrace) throws DataLayerException {
		
		ArrayList al = new ArrayList();
		try {
			
			PreparedStatement stmt = ConnectionDispenser.getConnection().prepareStatement(
				"SELECT idMethodCall FROM methodcall WHERE interactiontrace_idInteractionTrace = ? ");
			stmt.setInt(1, interactionTrace);
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
				al.add(rs.getInt("idMethodCall"));
			}
			
			stmt.close();
		} catch (SQLException e) {
			throw new DataLayerException(e.getMessage());
		} catch (DBException e) {
			throw new DataLayerException(e.getMessage());
		}
		return al.iterator();
	}
	
	public String getNormalizedDataENTER(int methodCall) throws DataLayerException {
		
		String dataENTER = "";
		try {
			
			PreparedStatement stmt = ConnectionDispenser.getConnection().prepareStatement(
				"SELECT normalizedDataDefinition FROM normalizeddata WHERE methodcall_idMethodCall= ? ");
			stmt.setInt(1, methodCall);
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				if((rs.getString("normalizedDataDefinition")).contains(":::ENTER"))
					dataENTER = (rs.getString("normalizedDataDefinition"));
			}
			
			stmt.close();
		} catch (SQLException e) {
			throw new DataLayerException(e.getMessage());
		} catch (DBException e) {
			throw new DataLayerException(e.getMessage());
		}
		//System.out.println("ENTER " + dataENTER);
		return dataENTER;
	}
	
	public String getNormalizedDataEXIT(int methodCall) throws DataLayerException {
		
		String dataEXIT = "";
		try {
			
			PreparedStatement stmt = ConnectionDispenser.getConnection().prepareStatement(
				"SELECT normalizedDataDefinition FROM normalizeddata WHERE methodcall_idMethodCall= ? ");
			stmt.setInt(1, methodCall);
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				if((rs.getString("normalizedDataDefinition")).contains(":::EXIT"))
					dataEXIT = (rs.getString("normalizedDataDefinition"));
			}
			
			stmt.close();
		} catch (SQLException e) {
			throw new DataLayerException(e.getMessage());
		} catch (DBException e) {
			throw new DataLayerException(e.getMessage());
		}
		//System.out.println("EXIT " + dataEXIT);
		return dataEXIT;
	}

	public String getNormalizedDataENTEREXIT(int methodCall) throws DataLayerException {
		
		String dataENTEREXIT = "";
		try {
			
			PreparedStatement stmt = ConnectionDispenser.getConnection().prepareStatement(
				"SELECT normalizedDataDefinition FROM normalizeddata WHERE methodcall_idMethodCall= ? ");
			stmt.setInt(1, methodCall);
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				dataENTEREXIT = dataENTEREXIT.concat((rs.getString("normalizedDataDefinition")));
			}
			
			stmt.close();
		} catch (SQLException e) {
			throw new DataLayerException(e.getMessage());
		} catch (DBException e) {
			throw new DataLayerException(e.getMessage());
		}
		//System.out.println("ENTEREXIT " + dataENTEREXIT);
		return dataENTEREXIT;
	}
	
}
