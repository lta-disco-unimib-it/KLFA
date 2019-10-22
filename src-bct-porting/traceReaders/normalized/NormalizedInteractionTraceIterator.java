package traceReaders.normalized;

import java.util.Iterator;

/**
 * Iterator for NormalizedInteractionTraces
 * 
 * @author Fabrizio Pastore fabrizio.pastore AT gmail.com
 *
 */
public interface NormalizedInteractionTraceIterator extends Iterator {
	
	public NormalizedInteractionTrace next();
	
}
