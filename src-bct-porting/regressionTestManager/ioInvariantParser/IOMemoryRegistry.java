package regressionTestManager.ioInvariantParser;

import java.util.HashMap;
import java.util.Stack;

/**
 * 
 * @author Fabrizio Pastore [ fabrizio.pastore at gmail dot com ]
 *	This class manages all different memory registries for every method.
 *	IoInvariantCheckerAspect calls IOMemoryRegistry to signal entrance or exit from a method.
 *	check.ioInvariantParser.Target use IOMemoryRegistry to get the current memory hasTable.
 *
 *	IOMemoryRegistry has this internal structure:
 *	<ul>
 *	<li>an HashMap to maintain memory stack of every thread</li>
 *	<li>a Stack for every thread with HashMaps to maintain parameter informations of current method</li>
 *	<li>an HashMap for every active method with informations on parameters</li>
 *	</ul>
 *
 *	Implements Singleton Design Pattern.
 *
 *	This class is thread safe for its purpose:
 *	creates and instance for each thread with its stack
 *	
 */
public class IOMemoryRegistry {
	//HashMap <Long,IOMemoryRegistry>
	private static HashMap threads = null;
	//Stack<HashMap>
	private Stack  stack = null;
	/**
	 * Constructor, is private because it is a singleton!
	 *
	 */
	private IOMemoryRegistry(){
		stack = new Stack();
	}
	
	/**
	 * Return the instance of IOMemoryRegistry for the current thread.
	 * 
	 * @return IOMemoryRegistry instance or the current thread
	 */
	public static synchronized IOMemoryRegistry getInstance(){
		long tid = Thread.currentThread().getId();
		if ( threads == null )
			threads = new HashMap();
		IOMemoryRegistry instance = (IOMemoryRegistry)threads.get(new Long(tid));
		if ( instance == null ){
			instance = new IOMemoryRegistry();
			threads.put(new Long(tid),instance);
		}
		return instance;
	}
	
	
	/**
	 * Tell the registry that we are entering another method.
	 * Must be called by IoInvariantCheckerAspect when a method is entered.
	 * 
	 */
	public void upLevel(){
		HashMap methodsMap = new HashMap();
		stack.push(methodsMap);
	}
	
	/**
	 * Tell the registry that we are exiting the current method.
	 * Must be called by IoInvariantCheckerAspect before exiting a method after 
	 * doing all checks on parameters.
	 *
	 */
	public void downLevel(){
		stack.pop();
	}
	
	/**
	 * Returns the memory map of the
	 * 
	 * @return HashMap current method memory map
	 */
	public HashMap getCurrentMethodsMap(){
		return (HashMap) stack.lastElement();
	}
}
