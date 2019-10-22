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
