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

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author Davide Lorenzoli
 *
 */
public class LogWriter {
	private static boolean APPEND = true;
	private static boolean NOT_APPEND = false;		
	
	public static void log(String logFilePath, String logFileName, String log) {		
	    logFilePath = logFilePath.endsWith("/") ? logFilePath : logFilePath + "/";
		
		log(logFilePath + logFileName, log);
	}
	
	public static void log(String logFile, String log) {								
		FileOutputStream fOut;
		try {
		    if (log != null) {
		        fOut = new FileOutputStream(logFile, APPEND);			
		        fOut.write(log.getBytes());			
		        fOut.close();
		    }
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}	
}
