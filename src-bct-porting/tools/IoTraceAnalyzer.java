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
package tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import dfmaker.core.Variable;

public class IoTraceAnalyzer {

	
	
	private File trace;

	public IoTraceAnalyzer(File inputTrace) {
		trace = inputTrace;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		if ( args.length < 1 ){
			printUsage();
			System.exit(-1);
		}
		File inputTrace = new File ( args[args.length-1] );
		IoTraceAnalyzer analyzer = new IoTraceAnalyzer( inputTrace );
		
		try {
			
			List<Variable> values = analyzer.getValues( args[0] );
			for ( Variable variable : values ){
				System.out.println( variable.getName()+","+variable.getValue()+","+variable.getModified()  );
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		

	}

	private List<Variable> getValues( String regex ) throws IOException {
		ArrayList<Variable> lines = new ArrayList<Variable>();
		
		BufferedReader reader = new BufferedReader( new FileReader ( trace ) );
		String line;
		while ( ( line = reader.readLine() ) != null ){
			if ( line.matches(regex) ){
				String value = reader.readLine();
				String modifier = reader.readLine();
				lines.add(new Variable(line, value, Integer.valueOf(modifier) ) );
			}
		}
		
		reader.close();
		
		return lines;
	}

	private static void printUsage() {
		System.err.println("Usage : "+IoTraceAnalyzer.class.getSimpleName()+" <regex> <traceFile>");
	}

}
