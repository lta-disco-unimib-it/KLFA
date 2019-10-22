package grammarInference.Engine;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import database.ConnectionDispenser;
import database.DBException;
import database.DataLayerException;

import traceReaders.normalized.DBGKTailTracesReader;
import traceReaders.normalized.GKTailTracesReader;

import grammarInference.Record.Symbol;
import grammarInference.Record.Trace;
import grammarInference.Record.VectorTrace;
import grammarInference.Record.TraceParser;

//FIXME: it's in the correct package?
public class DBGKTailParser implements TraceParser {
	
	int traceMethod;

	public DBGKTailParser(int method) {
		traceMethod = method;
	}

	public Iterator getTraceIterator() {
		
		Iterator it = null;
		try {
			it = getTraces();
		} catch (DataLayerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return it;
	}
	
	private Iterator getTraces() throws DataLayerException {
		ArrayList al = new ArrayList();
		
		try {
			PreparedStatement stmt = ConnectionDispenser.getConnection().prepareStatement(
				"SELECT idGKTailInteractionTrace FROM gktailinteractiontrace WHERE method_idMethod = ? ");
			stmt.setInt(1, traceMethod);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				al.add(readTrace(rs.getInt("idGKTailInteractionTrace")));
			}
			
			stmt.close();
		} catch (SQLException e) {
			throw new DataLayerException(e.getMessage());
		} catch (DBException e) {
			throw new DataLayerException(e.getMessage());
		}
		System.out.println("TRACES " + al);
		return al.iterator();
	}

	public Trace readTrace(int idTrace) throws DataLayerException {
		
		Trace trace = new VectorTrace();
		
		try {
			PreparedStatement stmt = ConnectionDispenser.getConnection().prepareStatement(
				"SELECT method_IdMethod, marker FROM gktailmethodcall " +
				"WHERE gktailinteractiontrace_idGKTailInteractionTrace = ? ");
			stmt.setInt(1, idTrace);
			ResultSet rs = stmt.executeQuery();
			
			if (!rs.first()) {		
				trace.addSymbol(new Symbol(""));
				return trace;
			}
			rs.beforeFirst();
			while (rs.next()) {
				System.out.println("TRACE READ " + rs.getString("marker") + getMethodName(rs.getInt("method_IdMethod")));
				trace.addSymbol(new Symbol(rs.getString("marker") + getMethodName(rs.getInt("method_IdMethod"))));
			}

			stmt.close();
		} catch (SQLException e) {
			throw new DataLayerException(e.getMessage());
		} catch (DBException e) {
			throw new DataLayerException(e.getMessage());
		}	
		return trace;
	}
	
	private String getMethodName(int idMethod) throws DataLayerException {
		
		String methodName = "";
		
		try {
			PreparedStatement stmt = ConnectionDispenser.getConnection().prepareStatement(
				"SELECT methodDeclaration FROM method, gktailmethodcall " +
				"WHERE method_idMethod = idMethod AND method_idMethod = ? ");
			stmt.setInt(1, idMethod);
			ResultSet rs = stmt.executeQuery();
			if(rs.first()) {
				methodName = rs.getString("methodDeclaration");
			}else {
				throw new DataLayerException("Invalid method: " + idMethod );
			}
			stmt.close();
		} catch (SQLException e) {
			throw new DataLayerException(e.getMessage());
		} catch (DBException e) {
			throw new DataLayerException(e.getMessage());
		}
		return methodName;
	}

}
