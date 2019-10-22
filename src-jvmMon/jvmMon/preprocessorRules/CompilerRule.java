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
import it.unimib.disco.lta.alfa.dataTransformation.PreproessorRule;


public class CompilerRule extends GenericRule implements PreproessorRule {

	public CompilerRule() {
		super("COMPILER");
		// TODO Auto-generated constructor stub
	}

	@Override
	public String process(String line, LineIterator dispenser) {
		//System.out.println(line);
		String[] words = line.split("\t");
		StringBuffer buffer = new StringBuffer();
		buffer.append("c");
		//buffer.append(".");
		//buffer.append(words[2]);
		
		//
		//Classi caricate
		//
//		buffer.append("_");
//		buffer.append(words[5].split("/")[0]);
//		buffer.append(".");
//		buffer.append(words[5].split("/")[1]);
		
		
//		buffer.append("_");
//		buffer.append(words[4]);
//		buffer.append("_");
//		buffer.append(words[5]);
//		buffer.append("_");
//		buffer.append(words[6]);
		return buffer.toString();
	}

}
