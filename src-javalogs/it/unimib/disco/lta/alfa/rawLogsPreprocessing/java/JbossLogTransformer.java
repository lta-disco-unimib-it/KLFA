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
package it.unimib.disco.lta.alfa.rawLogsPreprocessing.java;

import it.unimib.disco.lta.alfa.eventsDetection.slct.GenericLogToSlctPreprocessor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;




public class JbossLogTransformer extends GenericLogToSlctPreprocessor{




	@Override
	protected boolean isStart(String line) {
		boolean res = line.matches(logDate );
		return res;
	}

	protected String processLine(String line) {
		return line;
	}
	
	//private String curLine;

	protected String curComponent;
	protected String curEvent;
	protected String logDate = "\\d+:\\d+:\\d+,\\d\\d\\d .*";
	private String separator = "\t";
	protected static final String actionPrefix = "ACTION";
	protected boolean skip = false;
	



	
	public static void main(String args[]){
		String offset = null;
		ArrayList<File> inputs = new ArrayList<File>();
		JbossLogTransformer p = new JbossLogTransformer();
		
		for( int i = 0; i < args.length-1; ++i){
			
			if ( args[i].equals("-offset") ){
				offset=args[++i];
			} else if ( args[i].equals("-separator") ) {
				p.setSeparator(args[++i]);
			} else {
				inputs.add(new File(args[i]));
			}
			
		}
		p.setOffset(13);
		File input = new File(args[args.length-2]);
		File output = new File(args[args.length-1]);
		
		if ( offset != null ){
			p.setOffset(Integer.valueOf(offset));
		}
		
		
		
		
		try {
			p.process(inputs,output);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}


	
	


}
