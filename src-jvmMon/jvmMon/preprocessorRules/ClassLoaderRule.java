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
package jvmMon.preprocessorRules;

import it.unimib.disco.lta.alfa.dataTransformation.GenericRule;
import it.unimib.disco.lta.alfa.dataTransformation.LineIterator;

public class ClassLoaderRule extends GenericRule {

	public ClassLoaderRule( ){
		super( "CLASSLOADER" );
	}
	
	public String process(String line, LineIterator dispenser) {
		String[] words = line.split("\t");
		StringBuffer buffer = new StringBuffer();
		buffer.append("CL");
		
		//Action (LOAD_HOOK)
		if ( words[2] != "LOAD_HOOK" ){
			buffer.append(".");
			buffer.append(words[2]);
		}
		
		//Print class name
		buffer.append("_");
		buffer.append(words[3]);
//		
//		CLASSES PATHS
//		
//		String[] objects = words[3].split("/");
//		
//		buffer.append(objects[0]);
//		if ( objects.length > 1 ){
//			buffer.append(".");
//			buffer.append(objects[1]);
//		}
		

		return buffer.toString();
	}

}
