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

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import conf.DBConnectionSettings;
import conf.EnvironmentalSetter;

public class ConnectionDispenser {

	private static Connection conn = null;

	private ConnectionDispenser () {
	
	}

	/**
	 * @return
	 * @throws DBException
	 */
	public static Connection getConnection() throws DBException {
		try {
			if (conn == null) {
				Class.forName("com.mysql.jdbc.Driver").newInstance();
			}
			if (conn == null || conn.isClosed())
			{
				DBConnectionSettings settings = EnvironmentalSetter.getDBConnectionSettings();
				String databaseURI = settings.getProperty("databaseURI");
				String databaseUser = settings.getProperty("databaseUSER");
				String databasePassword = settings.getProperty("databasePASSWORD");
				
				conn = DriverManager.getConnection(databaseURI, databaseUser, databasePassword);
				
				//System.out.println("database.ConnectionDispenser > Connection opened to " + databaseURI);
			}
		} catch (Exception e) {
			throw new DBException("Unable to connect to DB: " + e.getMessage(),e);
		}		
		return conn;
	}

	/**
	 * @throws DBException
	 */
	public static void closeDBConnection() throws DBException {
		if (conn != null) {
			try {				
				conn.close();
				System.out.println("database.ConnectionDispenser > Connection opened to " + EnvironmentalSetter.getDBConnectionSettings().getProperty("databaseURI"));
			} catch (SQLException e) {
				throw new DBException("Unable to close DB Connection: " + e.getMessage());
			}
			conn = null;
		}		
	}

	/**
	 * @see java.lang.Object#finalize()
	 */
	@Override
	protected void finalize() throws Throwable {		
		super.finalize();
		ConnectionDispenser.closeDBConnection();
	}
	
	
}