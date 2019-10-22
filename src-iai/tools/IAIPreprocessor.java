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

import java.io.File;

/**
 * This program preprocess the IAI log file.
 * It 
 * 	inserts void lines for missing messages
 *  
 * @author Fabrizio Pastore fabrizio.pastore AT gmail.com
 *
 */
public class IAIPreprocessor {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		if ( args.length < 2 ){
			printUsage();
			System.exit(-1);
		}
		
		File input = new File(args[0]);
		File output = new File(args[args.length-1]);
	}

	private static void printUsage() {
		System.out.println("java "+IAIPreprocessor.class.getName()+" <input>+ <output>");
	}

}
