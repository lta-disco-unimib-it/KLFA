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
package check;

public class StackChecker {
	public static boolean isLogging(){
		StackTraceElement ste[] = Thread.currentThread().getStackTrace();
	    for ( int i = 4; i < ste.length; i++) {
	      if ( ste[i].getClassName().startsWith("log.") ) {
	        return true;
	      }
	    }
	    return false;
	}
	
	public static boolean isChecking(){
		StackTraceElement ste[] = Thread.currentThread().getStackTrace();
	    for ( int i = 4; i < ste.length; i++) {
	    	//System.out.println(ste[i]);
	      if ( ste[i].getClassName().startsWith("check.") ) {
	        return true;
	      }
	    }
	    return false;
	}
}
