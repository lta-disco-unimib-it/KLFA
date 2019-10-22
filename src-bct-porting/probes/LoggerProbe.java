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

public class LoggerProbe {
	
	/**
	 * Record the information about a method enter
	 * 
	 * @param cName
	 * @param theMethodName
	 * @param methodS
	 * @param argsPassed
	 */
	public static void enter(String /*className*/ cName,
			String /*methodName*/ theMethodName,
			String /*methodSig*/ methodS,
			Object[] /*args*/ argsPassed ){
		
		String signature = ClassFormatter.getSignature(cName,theMethodName,methodS);
		
		LoggingActionRecorder.logIoInteractionEnter(signature, argsPassed, Thread.currentThread().getId());
		
	}
	
	/**
	 * Record the information about a method exit
	 * 
	 * @param cName
	 * @param theMethodName
	 * @param methodS
	 * @param argsPassed
	 * @param ret
	 */
	public static void exit(String cName, String theMethodName, String methodS, Object[] argsPassed, Object ret){
//		You need the following lines only if you use Lorenzoli ObjectFlattener
//
//		if ( AspectFlowChecker.isInsideAnAspect() )
//			return;
//		AspectFlowChecker.setInsideAnAspect( Boolean.TRUE );
//
		String signature = ClassFormatter.getSignature(cName,theMethodName,methodS);

		//System.out.println( Thread.currentThread().getId()+"#IO-EXIT : "+signature);
		
		if ( methodS.endsWith("V") ){						//void method
			LoggingActionRecorder.logIoInteractionExit(signature, argsPassed,Thread.currentThread().getId());
		} else {											//non void method
			LoggingActionRecorder.logIoInteractionExit(signature, argsPassed, ret,Thread.currentThread().getId());
		}

//      You need the following lines only if you use Lorenzoli ObjectFlattener
//		
//		//System.out.println("IOE "+signature);
//		AspectFlowChecker.setInsideAnAspect( Boolean.FALSE );

	}

	public static void exitMeta(String cName, String theMethodName, String methodS, Object[] argsPassed, Object ret, String currentTestCase) {
//		You need the following lines only if you use Lorenzoli ObjectFlattener
		//
//				if ( AspectFlowChecker.isInsideAnAspect() )
//					return;
//				AspectFlowChecker.setInsideAnAspect( Boolean.TRUE );
		//
				String signature = ClassFormatter.getSignature(cName,theMethodName,methodS);

				//System.out.println( Thread.currentThread().getId()+"#IO-EXIT : "+signature);
				
				if ( methodS.endsWith("V") ){						//void method
					LoggingActionRecorder.logIoInteractionExitMeta(signature, argsPassed,Thread.currentThread().getId(),currentTestCase);
				} else {											//non void method
					LoggingActionRecorder.logIoInteractionExitMeta(signature, argsPassed, ret,Thread.currentThread().getId(),currentTestCase);
				}

//		      You need the following lines only if you use Lorenzoli ObjectFlattener
//				
//				//System.out.println("IOE "+signature);
//				AspectFlowChecker.setInsideAnAspect( Boolean.FALSE );
	}

	public static void enterMeta(String className, String methodName, String methodSig, Object[] args, String currentTestCase) {
		String signature = ClassFormatter.getSignature(className,methodName,methodSig);
		
		LoggingActionRecorder.logIoInteractionEnterMeta(signature, args, Thread.currentThread().getId(),currentTestCase);
	}

}
