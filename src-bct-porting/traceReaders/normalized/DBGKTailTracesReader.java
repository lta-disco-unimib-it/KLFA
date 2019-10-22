package traceReaders.normalized;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import database.ConnectionDispenser;
import database.DBException;
import database.DataLayerException;

public class DBGKTailTracesReader implements GKTailTracesReader {

	public Iterator getMethods() throws DataLayerException {
		
		ArrayList al = new ArrayList();
		try {
			
			PreparedStatement stmt = ConnectionDispenser.getConnection().prepareStatement(
				"SELECT DISTINCT idMethod FROM method, gktailinteractiontrace WHERE idMethod = method_idMethod ");
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

	public ArrayList getInteractionTraces(/*int method*/Iterator methodList) throws DataLayerException {
		ArrayList al = new ArrayList();
		
		while (methodList.hasNext()) {
			try {
				
				PreparedStatement stmt = ConnectionDispenser.getConnection().prepareStatement(
					"SELECT idGKTailInteractionTrace FROM gktailinteractiontrace WHERE method_idMethod = ? ");
				stmt.setInt(1,(Integer) methodList.next());
				ResultSet rs = stmt.executeQuery();
				
				while (rs.next()) {
					al.add(rs.getInt("idGKTailInteractionTrace"));
				}
				
				stmt.close();
			} catch (SQLException e) {
				throw new DataLayerException(e.getMessage());
			} catch (DBException e) {
				throw new DataLayerException(e.getMessage());
			}
		}
		return al;
	}

	public Iterator getMethodCall(ArrayList interactionTraceList) throws DataLayerException {
		ArrayList al = new ArrayList();
		
		Iterator traceList = interactionTraceList.iterator();
		while(traceList.hasNext()) {
			try {
				PreparedStatement stmt = ConnectionDispenser.getConnection().prepareStatement(
					"SELECT DISTINCT method_IdMethod FROM gktailmethodcall " +
					"WHERE gktailinteractiontrace_idGKTailInteractionTrace = ? ");
				stmt.setInt(1, (Integer) traceList.next());
				ResultSet rs = stmt.executeQuery();
				
				while (rs.next()) {
					al.add(rs.getInt("method_IdMethod"));
				}
				
				stmt.close();
			} catch (SQLException e) {
				throw new DataLayerException(e.getMessage());
			} catch (DBException e) {
				throw new DataLayerException(e.getMessage());
			}	
		}
		return al.iterator();
	}

	public Iterator getIDMethodCall(int traceMethod, ArrayList interactionTraceList) throws DataLayerException {
		ArrayList al = new ArrayList();
		
		Iterator traceList = interactionTraceList.iterator();
		while(traceList.hasNext()) {
			try {
				PreparedStatement stmt = ConnectionDispenser.getConnection().prepareStatement(
					"SELECT idGKTailMethodCall FROM gktailmethodcall " +
					"WHERE gktailinteractiontrace_idGKTailInteractionTrace = ?  AND " +
					"method_IdMethod = ? ");
				stmt.setInt(1, (Integer) traceList.next());
				stmt.setInt(2, traceMethod);
				ResultSet rs = stmt.executeQuery();
				
				while (rs.next()) {
					al.add(rs.getInt("idGKTailMethodCall"));
				}
				
				stmt.close();
			} catch (SQLException e) {
				throw new DataLayerException(e.getMessage());
			} catch (DBException e) {
				throw new DataLayerException(e.getMessage());
			}
		}
		return al.iterator();
	}

	public String getConstraint(int idMethodCall) throws DataLayerException {
		
		String constraint = "";
		
		try {
			PreparedStatement stmt = ConnectionDispenser.getConnection().prepareStatement(
				"SELECT modelIN, modelOUT FROM gktaildatamodel " +
				"WHERE gktailmethodcall_idGKTailMethodCall = ? ");
			stmt.setInt(1, idMethodCall);
			ResultSet rs = stmt.executeQuery();
						
			
			if(rs.first()) {
				String modelIn = rs.getString("modelIN");
				String modelOut = rs.getString("modelOUT");
				
				System.out.println("modelIn: " + modelIn);
				System.out.println("modelOut: " + modelOut);
				
				constraint = (modelIn);
				constraint = constraint.concat(modelOut);	
			} else {
				constraint = "no constraint";
			}
			
			stmt.close();
		} catch (SQLException e) {
			throw new DataLayerException(e.getMessage());
		} catch (DBException e) {
			throw new DataLayerException(e.getMessage());
		}
	
		return constraint;
	}
}
