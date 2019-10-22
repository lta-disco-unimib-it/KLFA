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

import java.io.File;

import recorders.LoggingActionRecorder;
import util.TCExecutionRegistry;
import util.TCExecutionRegistryException;



import flattener.utilities.LogWriter;

public class TcInteractionLoggerProbe {

	/**
	 * 
	 * @param cName			class Name
	 * @param theMethodName method Name
	 * @param methodS		method SIgnature
	 * @param argsPassed	arguments
	 */
	public static void intLogEnter(
			String /*className*/ cName,
			String /*methodName*/ theMethodName,
			String /*methodSig*/ methodS,
			Object[] /*args*/ argsPassed ){
		
		String signature = ClassFormatter.getSignature(cName,theMethodName,methodS);
		//String signature = ClassFormatter.getSignature(methodS, argsPassed, cName, theMethodName );
		
		String currentTC;
		try {
			currentTC = TCExecutionRegistry.getInstance().getCurrentTest();
		} catch (TCExecutionRegistryException e) {
			currentTC = "";
			e.printStackTrace();
		}
		
		LoggingActionRecorder.logInteractionEnterMeta(signature, Thread.currentThread().getId(),currentTC);

	}

	/**
	 * 
	 * @param cName			class Name
	 * @param theMethodName method Name
	 * @param methodS		method SIgnature
	 * @param argsPassed	arguments
	 */
	public static void intLogExit(
			String /*className*/ cName,
			String /*methodName*/ theMethodName,
			String /*methodSig*/ methodS,
			Object[] /*args*/ argsPassed ){
		
		String signature = ClassFormatter.getSignature(cName,theMethodName,methodS);		
		//String signature = ClassFormatter.getSignature(methodS, argsPassed, cName, theMethodName );
		LoggingActionRecorder.logInteractionExit(signature, Thread.currentThread().getId());
	}

	
}
