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
package probes;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import util.TCExecutionRegistry;
import util.TCExecutionRegistryException;

/**
 * 
 * This class is used to keep in memory the name of the executed Test Case when test cases cannot be instrumented (e.g. human driven system tests).
 * This class is used to read the name of the currently running test case from a file whose path is passed to this class.
 * 
 * @author Fabrizio Pastore fabrizio.pastore AT gmail.com
 *
 */
public class TCObserverProbeNotInstrumented {
	
	/**
	 * Get the name of the current test case from the passed file.
	 * 
	 * @param fileName path to the file with the name of the current test case
	 * @return
	 */
	private static String getName( String fileName ){
		try {
			BufferedReader br = new BufferedReader ( new FileReader ( fileName ) );
			String name = br.readLine();
			br.close();
			return name;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
		
	}
	
	/**
	 * We are entering a test case, the name of the test case is written on the passed file name.
	 * The file must have the name of teh executed test case in the first line.
	 * 
	 * @param fileName path to the file with the name of the current test case
	 */
	public static void enter( String fileName ){
		try {
			//System.out.println("ENTER");
			TCExecutionRegistry.getInstance().tcEnter(getName(fileName));
		} catch (TCExecutionRegistryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * We are exiting a test case, the name of the test case is written on the passed file name.
	 * The file must have the name of teh executed test case in the first line.
	 * 
	 * @param fileName path to the file with the name of the current test case
	 */
	public static void exit( String fileName ){
		try {
			//System.out.println("EXIT");
			TCExecutionRegistry.getInstance().tcExit(getName(fileName));
		} catch (TCExecutionRegistryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
