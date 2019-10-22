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

public class GKTailNormalizedData {
	
	static int idGKTailNormalizedData;
	static String normalizedDataDefinition;
	
	//FK
	static Integer idGKTailMethodCall = null;
	
	public GKTailNormalizedData(){
	}
	
	public static void insert(int idGKTailMethodCall, String dataDefinition) throws DataLayerException 
	{
		try {
			PreparedStatement stmt = ConnectionDispenser.getConnection().prepareStatement(
			"INSERT INTO gktailnormalizeddata (gktailmethodcall_idGKTailMethodCall, normalizedDataDefinition) VALUES(?,?)");
			stmt.setObject(1, idGKTailMethodCall);
			stmt.setString(2, dataDefinition);
			stmt.execute();
			
			ResultSet rs = stmt.getGeneratedKeys();
			if (! rs.first()) throw new DataLayerException("Unable to insert NormalizedData for idMethodCall " + idGKTailMethodCall); 
			idGKTailNormalizedData = rs.getInt(1); 

			//Log.getInstance().debug("Project " + name + " inserted");
			stmt.close();
			
		} catch (SQLException e) {
			throw new DataLayerException(e.getMessage());
		} catch (DBException e) {
			throw new DataLayerException(e.getMessage());
		}
	}

	public static String getData(int idTrace) throws DataLayerException {

		String data = "";
		
		try {
			PreparedStatement stmt = ConnectionDispenser.getConnection().prepareStatement(
				"SELECT normalizedDataDefinition FROM gktailnormalizeddata " +
				"WHERE gktailmethodcall_idGKTailMethodCall = ?");
			stmt.setInt(1, idTrace);
			System.out.println("getData "+stmt.toString());
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				data = data.concat(rs.getString("normalizedDataDefinition"));
			}			
			stmt.close();
		} catch (SQLException e) {
			throw new DataLayerException(e.getMessage());
		} catch (DBException e) {
			throw new DataLayerException(e.getMessage());
		}
		return data;
	}
	
	

}
