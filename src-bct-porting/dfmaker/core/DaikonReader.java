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
package dfmaker.core;

/**
 * This file cointain the logic to understand Daikon trace files.
 * 
 * @author Fabrizio Pastore [ fabrizio.pastore at gmail dot com ]
 *
 */
public class DaikonReader {

	public static boolean isProgramPoint(String line){
        return line.indexOf(":::") != -1 ? true : false;
	}
	
	
	/**
     * @param line
     * @return
     */
    public static boolean isEntryPoint(String line) {
        return line.indexOf(":::ENTER") != -1 ? true : false;
    }
    
    /**
     * @param line
     * @return
     */
    public static boolean isExitPoint(String line) {        
        return line.indexOf(":::EXIT") != -1 ? true : false;
    }
    
    /**
     * @param line
     * @return
     */
    public static boolean isObjectPoint(String line) {
        return line.indexOf(":::OBJECT") != -1 ? true : false;
    }    

}
