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

import recorders.LoggingActionRecorder;
import util.TCExecutionRegistry;
import util.TCExecutionRegistryException;
import check.AspectFlowChecker;

public class TcIoLoggerProbe {

	public static void ioLogEnter(String /*className*/ cName,
			String /*methodName*/ theMethodName,
			String /*methodSig*/ methodS,
			Object[] /*args*/ argsPassed ){
		
		
		if ( AspectFlowChecker.isInsideAnAspect() )
			return;
		AspectFlowChecker.setInsideAnAspect( true );
		
		String signature = ClassFormatter.getSignature(cName,theMethodName,methodS);
		//String signature = ClassFormatter.getSignature(methodS,argsPassed,cName,theMethodName);

		String currentTC;
		try {
			currentTC = TCExecutionRegistry.getInstance().getCurrentTest();
			
		} catch (TCExecutionRegistryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			currentTC = "";
			
		}
		LoggingActionRecorder.logIoEnterMeta(signature, argsPassed, currentTC );
		

		AspectFlowChecker.setInsideAnAspect( false );
	}
	

	public static void ioLogExit(String cName, String theMethodName, String methodS, Object[] argsPassed, Object ret){

		if ( AspectFlowChecker.isInsideAnAspect() )
			return;
		AspectFlowChecker.setInsideAnAspect( true );

		String signature = ClassFormatter.getSignature(cName,theMethodName,methodS);
		//String signature = ClassFormatter.getSignature(methodS,argsPassed,cName,theMethodName);

		String currentTC;
		try {
			currentTC = TCExecutionRegistry.getInstance().getCurrentTest();
		} catch (TCExecutionRegistryException e) {
			currentTC = "";
			e.printStackTrace();
		}
		
		if ( methodS.endsWith("V") ){						//void method
			LoggingActionRecorder.logIoExitMeta(signature, argsPassed, currentTC);
		} else {											//non void method
			LoggingActionRecorder.logIoExitMeta(signature, argsPassed, ret, currentTC);
		}	
		AspectFlowChecker.setInsideAnAspect( false );

	}
	
}
