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

public class BeginEndTrace {
	
	static long idBeginEndTrace;
	
	//FK
	static int thread_idThread=0;
	
	public BeginEndTrace(){
	}
	
	public static void insert(long threadId) throws DataLayerException 
	{
		try {
			PreparedStatement stmt = ConnectionDispenser.getConnection().prepareStatement(
				"SELECT idBeginEndTrace FROM beginendtrace WHERE idBeginEndTrace = ? ");
			stmt.setLong(1, threadId);
			ResultSet rs = stmt.executeQuery();
			if (rs.first()) {
				stmt.close();
			}
			else {
				System.out.println("BEGINENDTRACE-----------" + threadId);
				stmt = ConnectionDispenser.getConnection().prepareStatement(
				"INSERT INTO beginendtrace (idBeginEndTrace, thread_idThread) VALUES(?,?)");
				stmt.setLong(1, threadId);
				stmt.setInt(2, thread_idThread);
				//rs = stmt.executeQuery(); ?????????
					if(!stmt.execute())throw new DataLayerException("Unable to insert record into beginendtrace " + threadId );
				//if (! rs.first()) throw new DataLayerException("Unable to insert record into beginendtrace " + threadId ); 
				//idBeginEndTrace inserted 
				stmt.close();
			}
		} catch (SQLException e) {
			throw new DataLayerException(e.getMessage());
		} catch (DBException e) {
			throw new DataLayerException(e.getMessage());
		}
	}	
}
