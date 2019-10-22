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
package conf;

import java.util.Properties;

public class DBConnectionSettings  {
	private Properties properties;
	private String databaseURI;
	private String databaseUSER;
	private String databasePASSWORD; 
	
	public DBConnectionSettings( Properties p) throws SettingsException {
		properties = p;
		
		Object val = p.get("databaseURI");
		if ( val == null ){
			 throw new SettingsException("no databaseURI defined");
		}
		databaseURI = val.toString();
		
		val = p.get("databaseUSER");
		if ( val == null ){
			 throw new SettingsException("no databaseUSER defined");
		}
		databaseUSER=val.toString();
		
		val = p.get("databasePASSWORD");
		if ( val == null ){
			 throw new SettingsException("no databasePASSWORD defined");
		}
		databasePASSWORD=val.toString();
		
	}

	public String getProperty(String key) {
		return properties.getProperty(key);
	}

	public String getDatabasePASSWORD() {
		return databasePASSWORD;
	}

	public String getDatabaseURI() {
		return databaseURI;
	}

	public String getDatabaseUSER() {
		return databaseUSER;
	}

	public Properties getProperties() {
		return properties;
	}

	
	
	

}
