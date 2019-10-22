package traceReaders.normalized;

import java.util.Iterator;

/**
 * Iterator for NormalizedIoTraces
 * 
 * @author Fabrizio Pastore fabrizio.pastore AT gmail.com
 *
 */
public interface NormalizedIoTraceIterator extends Iterator {
	public NormalizedIoTrace next();
}
