package probes;


import java.util.Map;
import java.util.Stack;
import java.util.TreeMap;
import java.util.Collections;

/**
 * This class is used by probes and aspects to check (during logging and checking phases) if a component
 * is calling itself or is calling another element.
 * 
 * @author Fabrizio Pastore fabrizio.pastore AT gmail.com
 *
 */
public class ComponentCallStack extends Stack<Class>{

	private static final Map<Long,ComponentCallStack> stacks = Collections.synchronizedMap(  new TreeMap<Long,ComponentCallStack>() );  
	
	
	/**
	 * We need only one instance per Thread
	 *
	 */
	private ComponentCallStack(){
		
	}
	
	/**
	 * Return ComponentCallStack instance for current thread.
	 * Every thread has its own instance.
	 *  
	 * @return CallStack for current thread
	 * 
	 */
	public static ComponentCallStack getInstance(){
		ComponentCallStack instance = stacks.get(Thread.currentThread().getId());
		if ( instance == null ){
			instance = new ComponentCallStack();
			instance.push(null);
			
			stacks.put(Thread.currentThread().getId(), instance );
		}
		return instance;
	}
	
}
