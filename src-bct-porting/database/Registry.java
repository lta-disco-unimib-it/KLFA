package database;

import java.util.HashMap;
import java.util.Stack;

/**
 * 
 *	This class manages all different memory registries for every thread.
 *	BeginEndExecMethod calls this class to signal entrance or exit from a method of a specific thread.
 *
 *	Registry has this internal structure:
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
public class Registry {

	private static HashMap hashMap = null;
	private Stack  thread = null;
	/**
	 * Constructor, is private because it is a singleton!
	 *
	 */
	private Registry(){
		thread = new Stack();
	}
	
	/**
	 * Return the instance of Registry for the current thread.
	 * 
	 * @return  instance or the stack of the current thread
	 */
	public static synchronized Registry getInstance(long threadId) {
		
		if ( hashMap == null )
			hashMap = new HashMap();
		Registry instance = (Registry)hashMap.get(new Long(threadId));
		if ( instance == null ){
			instance = new Registry();
			hashMap.put(new Long(threadId),instance);
		}
		return instance;
	}
	
	
	/**
	 * Tell the registry that we are entering another method.
	 * @param idBeginEndExecMethod 
	 * 
	 */
	public void push(int idBeginEndExecMethod){
		//HashMap methodsMap = new HashMap();
		//Integer i = null;
		thread.push(new Integer(idBeginEndExecMethod));
	}
	
	/**
	 * Tell the registry that we are exiting the current method.
	 *
	 */
	public int pop(){
		int id = ((Integer)thread.pop()).intValue();
		return id;
	}
}

