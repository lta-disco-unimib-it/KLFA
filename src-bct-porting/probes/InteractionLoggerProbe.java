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

import check.AspectFlowChecker;

import recorders.LoggingActionRecorder;



import flattener.utilities.LogWriter;

public class InteractionLoggerProbe {

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
		if ( AspectFlowChecker.isInsideAnAspect() )
			return;
		AspectFlowChecker.setInsideAnAspect( Boolean.TRUE );
		String signature = ClassFormatter.getSignature(cName,theMethodName,methodS);

		LoggingActionRecorder.logInteractionEnter(signature, Thread.currentThread().getId());
		AspectFlowChecker.setInsideAnAspect( Boolean.FALSE );
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
		if ( AspectFlowChecker.isInsideAnAspect() )
			return;
		AspectFlowChecker.setInsideAnAspect( Boolean.TRUE );
		String signature = ClassFormatter.getSignature(cName,theMethodName,methodS);		
		
		LoggingActionRecorder.logInteractionExit(signature, Thread.currentThread().getId());
		AspectFlowChecker.setInsideAnAspect( Boolean.FALSE );
	}

	
}
