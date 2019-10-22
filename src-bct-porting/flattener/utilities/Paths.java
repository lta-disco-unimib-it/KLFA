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
package flattener.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * @author Davide Lorenzoli
 */
public class Paths {
	private Properties properties;
	
	/**
	 * @param propertiesFileName
	 */
	public Paths(String propertiesFileName) {
		FilesFinder fileFinder = new FilesFinder();		
		properties = new Properties();		
		try {			
			File propertiesFile = fileFinder.find(System.getProperty("user.dir"), propertiesFileName, true)[0];
			properties.load(new FileInputStream(propertiesFile));
		} catch (FileNotFoundException e) {			
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @param propertiesFile
	 */
	public Paths(File propertiesFile) {			
		properties = new Properties();		
		try {						
			properties.load(new FileInputStream(propertiesFile));
		} catch (FileNotFoundException e) {			
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}	
	
	public String getPath(String propertyName) { 
		return properties.getProperty(propertyName);
	}
}
