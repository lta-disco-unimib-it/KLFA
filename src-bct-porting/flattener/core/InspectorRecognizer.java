package flattener.core;

import java.lang.reflect.Method;


/**
 * An inspector is a method tha returns the state of an object without modify it's
 * state. There aren't well defined rules to identify an inspector, a class
 * implementing this interface must define it's own rules, i.e. euristics rules based
 * on the name of the object's methods.
 * 
 * @author Davide Lorenzoli
 */
public interface InspectorRecognizer {
	/**
	 * @param object
	 * @param method
	 * @return true if the methods is an inspector, false otherwise.
	 */
	public boolean recognize(Object object, Method method); 
}
