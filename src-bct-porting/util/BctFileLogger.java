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
package util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class BctFileLogger {

	private static BctFileLogger instance;
	private BufferedWriter wr;

	public BctFileLogger(String string) {
		try {
			wr = new BufferedWriter( new FileWriter(string) );
		} catch (IOException e) {
			
		}
	}

	public static synchronized BctFileLogger getInstance() {
		if ( instance == null ){
			instance = new BctFileLogger("/tmp/bct.log");
		}
		return instance;
	}
	
	public void log(String text){
		try {
			wr.write(text+"\n");
			wr.flush();
		} catch (IOException e) {
			
		}
	}
}
