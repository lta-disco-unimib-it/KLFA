/*******************************************************************************
 *    Copyright 2019 Fabrizio Pastore, Leonardo Mariani
 *   
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 *******************************************************************************/
package traceReaders.raw;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import database.ConnectionDispenser;
import database.DBException;
import database.DataLayerException;

public class DBInteractionTrace implements InteractionTrace {
	private String threadId;
	
	private Iterator it;
	private int idBeginEndExecMethod;
	private ArrayList<Token> al = new ArrayList<Token>();
	
	public DBInteractionTrace(String threadId) {
		this.threadId = threadId;
		
		try {
			it = getTokens();
		} catch (TraceException e) {
			e.printStackTrace();
		}
	}
	private Iterator<Token> getTokens() throws TraceException {
		//System.out.println("getTokens()");
		try {
			PreparedStatement stmt = ConnectionDispenser.getConnection().prepareStatement(
				"SELECT methodDeclaration, beginEnd, idBeginEndExecMethod FROM method, beginendexecmethod, thread " +
				"WHERE idMethod = method_idMethod AND idThread = thread_idThread AND idThread = ? " +
				"ORDER BY occurrence ");
			stmt.setString(1, threadId);
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
				String token = rs.getString("methodDeclaration") + rs.getString("beginEnd").substring(0,rs.getString("beginEnd").length()-1); 
				//System.out.println("getTokens()" +token);
				idBeginEndExecMethod = rs.getInt("idBeginEndExecMethod");
				al.add(new Token(idBeginEndExecMethod, token));	
			}
			
			stmt.close();
		} catch (SQLException e) {
			throw new TraceException(e.getMessage());
		} catch (DBException e) {
			throw new TraceException(e.getMessage());
		}
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
		throw new NotImplementedException();
	}
	public void close() throws IOException {
		// TODO Auto-generated method stub
		
	}
}
