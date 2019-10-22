package check;

import java.util.HashMap;


/**
 * This class is used to check if an aspect is currently running.
 * 
 * @author Fabrizio Pastore [ fabrizio.pastore at gmail dot com ]
 *
 */
public class AspectFlowChecker {
	public static final HashMap<Long,Boolean> threads = new HashMap<Long,Boolean>();
	
	public static synchronized boolean isInsideAnAspect(){
		Boolean state = (Boolean) threads.get( new Long(Thread.currentThread().getId()));
		if ( state == null )
			return false;
		return state;
	}
	
	public static synchronized void setInsideAnAspect( Boolean state){
		threads.put( new Long(Thread.currentThread().getId()), state);
	}
	
}
