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

import util.TCExecutionRegistry;
import util.TCExecutionRegistryException;

/**
 * 
 * This class is used to keep in memory the name of the executed TestCase when the test process it is possible to instrument directly 
 * the test case with probes to maintain the name of the current test in the test registry.
 * 
 * @author Fabrizio Pastore fabrizio.pastore AT gmail.com
 *
 */
public class TCObserverProbeInstrumented {
	
	public static void enter( String className, String  methodName, String methodSignature ){
		try {
			//System.out.println("ENTER");
			TCExecutionRegistry.getInstance().tcEnter(ClassFormatter.getSignature(className, methodName, methodSignature));
		} catch (TCExecutionRegistryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void exit( String className, String  methodName, String methodSignature ){
		try {
			//System.out.println("EXIT");
			TCExecutionRegistry.getInstance().tcExit(ClassFormatter.getSignature(className, methodName, methodSignature));
		} catch (TCExecutionRegistryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
