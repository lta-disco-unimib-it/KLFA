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
package database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import traceReaders.normalized.DBNormalizedTracesReader;

public class GKTailMethodCall {
	
	static int idGKTailMethodCall;
	static int occurrence = 0;
	static String marker = " ";
	
	//FK
	static int idMethod;
	static int idGKTailInteractionTrace;
	
	public GKTailMethodCall(){
	}
	
	public static void insert(int methodCall, int idGKTailInteractionTrace, String dataENTER, String dataEXIT, String dataENTEREXIT, int occurrence) throws DataLayerException 
	{
		//System.out.println("Inserting "+idGKTailInteractionTrace+" "+dataENTEREXIT);
		try {
			PreparedStatement stmt = ConnectionDispenser.getConnection().prepareStatement(
			"INSERT INTO gktailmethodcall (method_idMethod, gktailinteractiontrace_idGKTailInteractionTrace, occurrence, marker) VALUES(?,?,?,?)");
			stmt.setInt(1, methodCall);
			stmt.setInt(2, idGKTailInteractionTrace);
			stmt.setInt(3, occurrence);
			stmt.setString(4, marker);
			stmt.execute();
			
			ResultSet rs = stmt.getGeneratedKeys();
			if (! rs.first()) throw new DataLayerException("Unable to insert method call for idMethod" + methodCall + "for Trace " + idGKTailInteractionTrace); 
			idGKTailMethodCall = rs.getInt(1); 
			
			//Log.getInstance().debug("Project " + name + " inserted");
			stmt.close();	
			
		} catch (SQLException e) {
			throw new DataLayerException(e.getMessage());
		} catch (DBException e) {
			throw new DataLayerException(e.getMessage());
		}
		//GKTailNormalizedData.insert(idGKTailMethodCall, dataENTER);
		//GKTailNormalizedData.insert(idGKTailMethodCall, dataEXIT);
		GKTailNormalizedData.insert(idGKTailMethodCall, dataENTEREXIT);
	}
	
	public static Iterator getTracesID() throws DataLayerException {
		
		ArrayList al = new ArrayList();
		
		try {
			PreparedStatement stmt = ConnectionDispenser.getConnection().prepareStatement(
				"SELECT idGKTailMethodCall FROM gktailmethodcall");
			
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
		return al.iterator();
	}
	
	public static String getMethodName(int idTrace) throws DataLayerException {
		
		String methodName = "";
		
		try {
			PreparedStatement stmt = ConnectionDispenser.getConnection().prepareStatement(
				"SELECT methodDeclaration FROM method, gktailmethodcall " +
				"WHERE method_idMethod = idMethod AND idGKTailMethodCall = ?");
			stmt.setInt(1, idTrace);
			ResultSet rs = stmt.executeQuery();
			if(rs.first()) {
				methodName = rs.getString("methodDeclaration");
			}else {
				throw new DataLayerException("Invalid trace: " + idTrace );
			}
			stmt.close();
		} catch (SQLException e) {
			throw new DataLayerException(e.getMessage());
		} catch (DBException e) {
			throw new DataLayerException(e.getMessage());
		}
		return methodName;
	}

	public static void updateLine(int arrayListIdMethodCall, int marker) throws DataLayerException {
		
		try {
			PreparedStatement stmt = ConnectionDispenser.getConnection().prepareStatement(
			"UPDATE gktailmethodcall SET marker = ? WHERE idGKTailMethodCall = ?");
			stmt.setString(1, marker + "#");
			stmt.setInt(2, arrayListIdMethodCall);
			stmt.execute();
			stmt.close();	
			
		} catch (SQLException e) {
			throw new DataLayerException(e.getMessage());
		} catch (DBException e) {
			throw new DataLayerException(e.getMessage());
		}	
	}

}
