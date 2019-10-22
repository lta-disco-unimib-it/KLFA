package traceReaders.normalized;

import java.util.Vector;

import traceReaders.raw.Token;

/**
 * 
 * Interface for all NormalizedInteractionTrace finders.
 * The aim of this interface is to define  methods useful to retrieve and store ioTraces
 *  
 * @author Fabrizio Pastore fabrizio.pastore AT gmail.com
 *
 */
public interface NormalizedInteractionTraceHandler {
		
	/**
	 * Add a method call sequence for the passed method
	 * 
	 * @param methodName	name of the method
	 * @param trace			sequence of methods called
	 */
	public abstract void addInteractionTrace(String methodName, Vector trace, String threadId) throws NormalizedTraceHandlerException;

	/**
	 * Returns an iterator over the normalized traces.
	 * 
	 * @return an iterator over the normalized interaction traces 
	 */
	public abstract NormalizedInteractionTraceIterator getInteractionTracesIterator();
	
}
