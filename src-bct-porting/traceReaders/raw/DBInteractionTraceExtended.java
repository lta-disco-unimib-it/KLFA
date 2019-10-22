package traceReaders.raw;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import database.ConnectionDispenser;
import database.DBException;

public class DBInteractionTraceExtended implements InteractionTrace {
	
	private String threadId;
	private String token = "";
	private Iterator it;
	private int idBeginEndExecMethod;
	private ArrayList al = new ArrayList();
	
	public DBInteractionTraceExtended(String threadId) {
		this.threadId = threadId;
		
		try {
			it = getTokens();
		} catch (TraceException e) {
			e.printStackTrace();
		}
	}
	private Iterator getTokens() throws TraceException {
		
		try {
			PreparedStatement stmt = ConnectionDispenser.getConnection().prepareStatement(
				//"SELECT methodDeclaration, beginEnd, idBeginEndExecMethod FROM method, beginendexecmethod, thread " +
				//"WHERE idMethod = method_idMethod AND idThread = thread_idThread AND idThread = ? " +
				//"ORDER BY occurrence ");
				" SELECT methodDeclaration, idBeginEndExecMethod, beginEnd FROM method, beginendexecmethod WHERE idMethod = method_idMethod " +
				" AND thread_idThread = ? " +
				" ORDER BY occurrence ");	
			stmt.setString(1, threadId);
			ResultSet rs = stmt.executeQuery();
			
			//insert the main method (begin)
			//al.add(new Token(000, "mainB"));
			
			while (rs.next()) {
				if(rs.getString("methodDeclaration").contains("chainOfResponsibility.TraceController.test(int)chainOfResponsibility.TraceController") && rs.getString("beginEnd").contains("B#")){
					try {
						PreparedStatement stmt1 = ConnectionDispenser.getConnection().prepareStatement(
							"SELECT methodDeclaration, beginEnd, idBeginEndExecMethod FROM method, beginendexecmethod " +
							"WHERE idMethod = method_idMethod AND ( idBeginEndExecMethod = ? )" );	
						stmt1.setInt(1, rs.getInt("idBeginEndExecMethod"));
						ResultSet rs1 = stmt1.executeQuery();
					
						while(rs1.next()) {
							token = rs1.getString("methodDeclaration") + rs1.getString("beginEnd").substring(0,rs1.getString("beginEnd").length()-1); 
							idBeginEndExecMethod = rs1.getInt("idBeginEndExecMethod");
							al.add(new Token(idBeginEndExecMethod, token));	
						}
						System.out.println(token);
						stmt1.close();	
					} catch (SQLException e) {
						throw new TraceException(e.getMessage());
					} catch (DBException e) {
						throw new TraceException(e.getMessage());
					}
					continue;
				}
				if(rs.getString("methodDeclaration").contains("chainOfResponsibility.TraceController.test(int)chainOfResponsibility.TraceController") && rs.getString("beginEnd").contains("E#")){
					try {
						PreparedStatement stmt1 = ConnectionDispenser.getConnection().prepareStatement(
							"SELECT methodDeclaration, beginEnd, idBeginEndExecMethod FROM method, beginendexecmethod " +
							"WHERE idMethod = method_idMethod AND ( idBeginEndExecMethod = ? )" );	
						stmt1.setInt(1, rs.getInt("idBeginEndExecMethod"));
						ResultSet rs1 = stmt1.executeQuery();
					
						while(rs1.next()) {
							token = rs1.getString("methodDeclaration") + rs1.getString("beginEnd").substring(0,rs1.getString("beginEnd").length()-1); 
							idBeginEndExecMethod = rs1.getInt("idBeginEndExecMethod");
							al.add(new Token(idBeginEndExecMethod, token));	
						}
						System.out.println(token);
						stmt1.close();	
					} catch (SQLException e) {
						throw new TraceException(e.getMessage());
					} catch (DBException e) {
						throw new TraceException(e.getMessage());
					}
					continue;
				}
				
				if(rs.getString("beginEnd").contains("E#")) {
					continue;
				}else {
					try {
						PreparedStatement stmt1 = ConnectionDispenser.getConnection().prepareStatement(
							"SELECT methodDeclaration, beginEnd, idBeginEndExecMethod FROM method, beginendexecmethod " +
							"WHERE idMethod = method_idMethod AND ( idBeginEndExecMethod = ? )" );	
						stmt1.setInt(1, rs.getInt("idBeginEndExecMethod"));
						ResultSet rs1 = stmt1.executeQuery();
					
						while(rs1.next()) {
							token = rs1.getString("methodDeclaration") + rs1.getString("beginEnd").substring(0,rs1.getString("beginEnd").length()-1); 
							idBeginEndExecMethod = rs1.getInt("idBeginEndExecMethod");
							al.add(new Token(idBeginEndExecMethod, token));	
						}
						stmt1.close();	
					} catch (SQLException e) {
						throw new TraceException(e.getMessage());
					} catch (DBException e) {
						throw new TraceException(e.getMessage());
					}
					/////////////////////////////////////
					try {
						PreparedStatement stmt1 = ConnectionDispenser.getConnection().prepareStatement(
							"SELECT methodDeclaration, beginEnd, idBeginEndExecMethod FROM method, beginendexecmethod " +
							"WHERE idMethod = method_idMethod AND ( startMethod = ? )" );	
						stmt1.setInt(1, rs.getInt("idBeginEndExecMethod"));
						ResultSet rs1 = stmt1.executeQuery();
					
						while(rs1.next()) {
							token = rs1.getString("methodDeclaration") + rs1.getString("beginEnd").substring(0,rs1.getString("beginEnd").length()-1); 
							idBeginEndExecMethod = rs1.getInt("idBeginEndExecMethod");
							al.add(new Token(idBeginEndExecMethod, token));	
						}
						stmt1.close();	
					} catch (SQLException e) {
						throw new TraceException(e.getMessage());
					} catch (DBException e) {
						throw new TraceException(e.getMessage());
					}
				}
			}
			stmt.close();
			} catch (SQLException e) {
				throw new TraceException(e.getMessage());
			} catch (DBException e) {
				throw new TraceException(e.getMessage());
			}
		//insert the main method (end)
		//al.add(new Token(000, "mainE"));
		
		return al.iterator();
	}

	//return a token 
	public Token getNextToken() {
		if(it.hasNext()) {
			return (Token) it.next();
		}
		return null;
	}

	public String getThreadId() {
		return threadId;
	}
	
	public String getNextMetaData() throws TraceException {
		// TODO Auto-generated method stub
		return null;
	}
	public void close() throws IOException {
		// TODO Auto-generated method stub
		
	}
}
