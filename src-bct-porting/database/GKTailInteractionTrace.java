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
import java.util.HashMap;
import java.util.Iterator;
import java.util.TreeMap;
import java.util.Vector;

import traceReaders.raw.Token;

public class GKTailInteractionTrace {
	
	static int idGKTailInteractionTrace;
	
	//FK
	static int idMethod;
	static int idThread = 0;
	
	public GKTailInteractionTrace() {
	}
	
	public static void insert(int thread, int idMethod, Iterator methodCall, TreeMap dataENTER, TreeMap dataEXIT, TreeMap dataENTEREXIT) throws DataLayerException {
		
		try {
			PreparedStatement stmt = ConnectionDispenser.getConnection().prepareStatement(
			"INSERT INTO gktailinteractiontrace (method_idMethod, thread_idThread) VALUES(?,?)");
			stmt.setInt(1, idMethod);
			stmt.setInt(2, thread);
			stmt.execute();

			ResultSet rs = stmt.getGeneratedKeys();
			if (! rs.first()) throw new DataLayerException("Unable to insert interaction trace for method " + idMethod); 
			idGKTailInteractionTrace = rs.getInt(1); 

			//Log.getInstance().debug("Project " + name + " inserted");
			stmt.close();
			
		} catch (SQLException e) {
			throw new DataLayerException(e.getMessage());
		} catch (DBException e) {
			throw new DataLayerException(e.getMessage());
		}
		
		int occurrence = 1; 
		//System.out.println("DATAENTEREXIT "+dataENTEREXIT);
		while (methodCall.hasNext()) {
			int idMethodCall = (Integer) methodCall.next();
			GKTailMethodCall.insert(idMethodCall, idGKTailInteractionTrace, (String)dataENTER.get(dataENTER.firstKey()), (String)dataEXIT.get(dataEXIT.firstKey()), (String)dataENTEREXIT.get(dataENTEREXIT.firstKey()), occurrence++);
			dataENTER.remove(dataENTER.firstKey());
			dataEXIT.remove(dataEXIT.firstKey());
			dataENTEREXIT.remove(dataENTEREXIT.firstKey());
		}
	}
}
