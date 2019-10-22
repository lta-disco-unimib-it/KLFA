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
package check;

public class Checker {

	public static synchronized void checkInteractionEnter(String signature, long threadId) {
		InteractionInvariantHandler.processCallEnter(threadId, signature);
	}

	public static synchronized void checkInteractionExit(String signature, long threadId) {
		InteractionInvariantHandler.processCallExit(threadId, signature);
	}

	public static synchronized void checkIoEnter(String methodSignature, Object[] parameters) {
		IoChecker.checkEnter(methodSignature, parameters);
	}

	public static synchronized void checkIoExit(String methodSignature, Object[] parameters) {
		IoChecker.checkExit(methodSignature, parameters);	
	}

	public static synchronized void checkIoExit(String methodSignature, Object[] parameters, Object returnValue) {
		IoChecker.checkExit(methodSignature, parameters, returnValue);	
	}
	

}
