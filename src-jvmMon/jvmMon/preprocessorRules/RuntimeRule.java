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
import it.unimib.disco.lta.alfa.dataTransformation.ValueTransformer;


public class RuntimeRule extends GenericRule implements PreproessorRule {
	private static final String THREAD_START = "THREAD START";
	private static final Object THREAD_END = "THREAD END";
	private ValueTransformer keyDispenserThread;
	private ValueTransformer keyDispenserExceptions;

	/**
	 * 
	 * @param keyDispenserThread dispenser for thread nukmber key
	 */
	public RuntimeRule( ValueTransformer keyDispenserThread, ValueTransformer keyDispenserException ) {
		super("RUNTIME");
		this.keyDispenserThread = keyDispenserThread;
		this.keyDispenserExceptions = keyDispenserException;
	}

	@Override
	public String process(String line, LineIterator dispenser) {
		String[] words = line.split("\t");
		StringBuffer buffer = new StringBuffer();
		String action = words[2].trim();
		
		
		String key="";
		
		if ( action.equals(THREAD_START) || action.equals(THREAD_END) ){
			if (action.equals(THREAD_START))
				buffer.append("S");
			else
				buffer.append("E");
			key = keyDispenserThread.getTransformedValue( words[3] );
		} else if ( action.equals("EXCEPTION")) {
			key = keyDispenserExceptions.getTransformedValue(words[3]);
			if ( key == null )
				return null;
			buffer.append("EXC");
			
			//key = keyDispenserOthers.getKey( words[3] );
			
		} else {
			buffer.append("RT");
			buffer.append(".");
			
			buffer.append(action);
			
		}

		
		buffer.append( key );
		

		return buffer.toString();
	}

}
