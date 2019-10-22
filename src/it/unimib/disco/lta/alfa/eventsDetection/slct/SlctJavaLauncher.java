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
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;

public class SlctJavaLauncher implements SlctLauncher {

	public List<Pattern> run(int support, File inputFile, File outliersFile) throws SlctRunnerException {
		SlctJava slct = new SlctJava(support);
		
		List<Pattern> patterns = slct.getPatterns(inputFile);
		
		BufferedReader reader = null;
		BufferedWriter w = null;
		try {
			reader = new BufferedReader(new FileReader(inputFile));
			
			w = new BufferedWriter(new FileWriter(outliersFile));
			
			String line;
			while ( ( line = reader.readLine() ) != null ) {
				boolean match = false;
				for ( Pattern  p : patterns ){
					if ( p.matcher(line).matches() ){
						match = true;
					}
				}
				
				if ( ! match ){
					w.append(line);
					w.newLine();
				}
			}
			
			return patterns;
			
		} catch (FileNotFoundException e) {
			throw new SlctRunnerException(e);
		} catch (IOException e) {
			throw new SlctRunnerException(e);
		} finally {
			if ( reader != null ){
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			
			if ( w != null ){
				try {
					w.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
