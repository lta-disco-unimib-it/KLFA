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
package it.unimib.disco.lta.alfa.eventsDetection.slct;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileLinesTokenizer {
	BufferedReader reader;
	private File file;
	
	public FileLinesTokenizer ( File file ) throws FileNotFoundException{
		this.file = file;
		reader = new BufferedReader ( new FileReader(file) );
	}
	
	public List<String> nextLine() throws IOException{
		String line = reader.readLine();
		
		if ( line == null ){
			return null;
		}
		
		String[] tokens = line.split(" ");
		
		
		int len = tokens.length;
		
		List<String> result = new ArrayList<String>(len);
		
		for ( int i = 0; i < len; i++ ){
			if ( tokens[i].length() > 0 ){
				result.add(tokens[i]);
			}
		}
		
		return result;
	}
	
	public void close() throws IOException{
		if ( reader != null ){
			reader.close();
		}
	}
}
