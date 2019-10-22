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
package it.unimib.disco.lta.alfa.eventsDetection;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;


public class ComponentsTracesManager {
	HashMap<String,File> componentsFiles = new HashMap<String,File>();
	HashMap<String,BufferedReader> componentsReaders = new HashMap<String, BufferedReader>();
	int getSymbolCount = 0;

	public void put(String componentName, File csvFile) {
		componentsFiles.put(componentName, csvFile);
	}

	public String getSymbol(String nextId) throws IOException, ComponentsTracesManagerException {
		BufferedReader reader = getReader(nextId);
		String s;
		++getSymbolCount;
		do {
			s = reader.readLine();
			if ( s == null ){
				throw new ComponentsTracesManagerException("No more lines, call #"+getSymbolCount+" nextId="+nextId);
			}
		} while ( s.equals("|") );

		return s;
	}

	private BufferedReader getReader(String nextId) throws FileNotFoundException {
		BufferedReader reader = componentsReaders.get(nextId);
		if ( reader == null ){
			reader = new BufferedReader( new FileReader(getComponentFile(nextId)) );
			componentsReaders.put(nextId, reader);
		}
		return reader;
	}

	private File getComponentFile(String nextId) {
		return componentsFiles.get(nextId);
	}
	
}
