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

/**
 * This class is a facade for the TCExecutionRegistry to make simple the calls to the registry to get information about the currently running test case
 *   
 * @author Fabrizio Pastore fabrizio.pastore AT gmail.com
 *
 */
public class TcMetaInfoHandler {
	
	/**
	 * Return the name of the currently running test case, if a problem occurrs during the call to the TCExecutionRegistry it returns a blnk line.
	 * 
	 * @return the name of the current test case or a blank line
	 * 
	 */
	public static String getCurrentTestCase() {
		String currentTC;

		try {
			currentTC = TCExecutionRegistry.getInstance().getCurrentTest();
		} catch (TCExecutionRegistryException e ) {
			if ( e.getStackTrace().length > 3 ){
				StackTraceElement sel = e.getStackTrace()[3];
				currentTC = "*"+sel.getClassName()+"."+sel.getMethodName();
			} else {
				currentTC="";
				e.printStackTrace();
			}
			
		}
		return currentTC;
	}
}
