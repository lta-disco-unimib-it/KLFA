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

public class MethodCallMetadata {

	public static void insert(String methodSignature, String metadata) throws DataLayerException 
	{

		
		try {
			//autoincrement database 		
			PreparedStatement stmt = ConnectionDispenser.getConnection().prepareStatement(
			"INSERT INTO methodcallmetadata (BeginEndExecMethod_idBeginEndExecMethod, data) VALUES(?, ?)");
			
			//System.out.println("DATUM" + methodSignature + message);
			
			stmt.setInt(1, BeginEndExecMethod.getId());
			stmt.setString(2, metadata);
			
			//DatumDispenser.getInstance().addPreparedStatement(stmt);
			
			stmt.execute();
						
			ResultSet rs = stmt.getGeneratedKeys();
			if (! rs.first()) {
				throw new DataLayerException("Unable to insert record into Datum" + methodSignature + "\n" +metadata); 
			}
			 

			//Log.getInstance().debug("Project " + name + " inserted");
			
			// it colses the statement NOT the DB connection
			stmt.close();			 					
		} catch (SQLException e) {
			throw new DataLayerException(e.getMessage());
		} catch (DBException e) {
			throw new DataLayerException(e.getMessage());
		}
	}

}
