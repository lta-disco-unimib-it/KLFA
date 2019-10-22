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


public abstract class IoLoggerProbe {
	
	public static void ioLogEnter(String /*className*/ cName,
			String /*methodName*/ theMethodName,
			String /*methodSig*/ methodS,
			Object[] /*args*/ argsPassed ){
		

//		You need the following lines only if you use Lorenzoli ObjectFlattener
//		if ( AspectFlowChecker.isInsideAnAspect() )
//			return;
//		AspectFlowChecker.setInsideAnAspect( Boolean.TRUE );
//		
		String signature = ClassFormatter.getSignature(cName,theMethodName,methodS);
		//System.out.println("IOS "+signature);

		//System.out.println( Thread.currentThread().getId()+"#IO-ENTER : "+signature);
		
		LoggingActionRecorder.logIoEnter(signature, argsPassed);

//You need the following lines only if you use Lorenzoli ObjectFlattener
//		AspectFlowChecker.setInsideAnAspect( Boolean.FALSE );
	}
	

	public static void ioLogExit(String cName, String theMethodName, String methodS, Object[] argsPassed, Object ret){
//		You need the following lines only if you use Lorenzoli ObjectFlattener
//
//		if ( AspectFlowChecker.isInsideAnAspect() )
//			return;
//		AspectFlowChecker.setInsideAnAspect( Boolean.TRUE );
//
		String signature = ClassFormatter.getSignature(cName,theMethodName,methodS);

		//System.out.println( Thread.currentThread().getId()+"#IO-EXIT : "+signature);
		
		if ( methodS.endsWith("V") ){						//void method
			LoggingActionRecorder.logIoExit(signature, argsPassed);
		} else {											//non void method
			LoggingActionRecorder.logIoExit(signature, argsPassed, ret);
		}

//      You need the following lines only if you use Lorenzoli ObjectFlattener
//		
//		//System.out.println("IOE "+signature);
//		AspectFlowChecker.setInsideAnAspect( Boolean.FALSE );

	}
}
