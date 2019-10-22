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


import java.util.Iterator;
import java.util.Properties;

public abstract class ConfigurationSettings {
	
	private Class type;
	private Properties properties;
	
	
	public ConfigurationSettings( Class type, Properties p ) {
		this.type = type;
		this.properties = p;
		
		replaceVariables();
		
	}

	/**
	 * this method replace property names used as variables in other properties 
	 * For example we can have:
	 * temporaryDir = /tmp
	 * normalizedDir = %temporaryDir%/normalized
	 * 
	 * after this method call we will have:
	 * 	
	 * 	temporaryDir = /tmp
	 *  normalizedDir = /tmp/normalized
	 *  
	 */
	private void replaceVariables() {
		

		Iterator keyIt = this.properties.keySet().iterator();
		
		while ( keyIt.hasNext() ){
			replaceVariableName( this.properties, (String)keyIt.next() );
		}
	}

	/**
	 * This method replace a property name used inside a property value with its value.
	 * For example we can have:
	 * temporaryDir = /tmp
	 * normalizedDir = %temporaryDir%/normalized
	 * 
	 * after this method call with temporaryDir as the second parameter we will have normalizedDir = /tmp/normalized
	 *  
	 * @param properties	properties that can be changed
	 * @param variable		the property name to replace
	 * 
	 */
	private void replaceVariableName(Properties properties, String variable) {
		Iterator keyIt = properties.keySet().iterator();
		String variableValue = properties.getProperty(variable);
		
		while ( keyIt.hasNext() ){
			String key = (String) keyIt.next();
			if ( ! key.equals(variable)){
				String value = properties.getProperty(key);
				String varKey = "%"+variable+"%";
				
				if ( value.contains(varKey)){
					//System.out.println("REPLACING KEY "+varKey+" IN "+key+":"+value+" WITH "+variableValue);
					String newValue = value.replace(varKey, variableValue);
					properties.put(key, newValue);
				}
			}
		}
	}


	public Class getType (){
		return type;
	}


	public String getProperty(String key) {
		return properties.getProperty(key);
	}
	
	
}
