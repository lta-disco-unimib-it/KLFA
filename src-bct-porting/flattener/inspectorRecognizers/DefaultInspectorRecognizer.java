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