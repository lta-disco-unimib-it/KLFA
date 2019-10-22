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
package flattener.inspectorRecognizers;

import java.lang.reflect.Method;

import flattener.core.InspectorRecognizer;

/**
 * @author Davide Lorenzoli
 */
public class DefaultInspectorRecognizer implements InspectorRecognizer {

	/**
	 * Constructor
	 */
	public DefaultInspectorRecognizer() {
		super();
	}

	/**
	 * @see flattener.inspectorRecognizers.InspectorRecognizer#recognize(java.lang.reflect.Method)
	 */
	public boolean recognize(Object object, Method method) {
	    boolean isInspector = false;
		String methodName = method.getName(); 
		// Manage get*() methods							
		if (methodName.startsWith("get")) {
		    isInspector = true;
		}
		// Manage elements() methods
		else if (methodName.indexOf("elements") >= 0) {
		    isInspector = true;
		}
		// Manage length*() methods
		else if (methodName.startsWith("length")) {
		    isInspector = true;
		}
		// Manage toArray*() methods
		else if (methodName.startsWith("toArray")) {
		    isInspector = true;
		}
		// Manage is[Upper case char]*() methods
		else if (methodName.startsWith("is") && method.getReturnType().isPrimitive()) {
		 	char c = new Character(methodName.charAt(2)).charValue();
		 	isInspector = Character.isUpperCase(c) ? true : false;		 	
		}
		// Manage *clear*() methods		
		else if (methodName.indexOf("clear") >= 0) {
		    isInspector = false;
		}
		// Manage *remove*() methods		
		else if (methodName.indexOf("remove") >= 0) {
		    isInspector = false;
		}
		else {
		    isInspector = isInspectorMethod(object, method);
		}
		return isInspector;
	}
	
	/**
	 * <pre>
	 * Returns <code>true</code> if the method is an inspector, 
	 * <code>false</code> otherwise. 
	 *
	 * @param method The method to test
	 * @return
	 */
	private boolean isInspectorMethod(Object object, Method method) {	    
	    boolean isInspector = false;
	    return isInspector ;	    
	}	
}