package traceReaders.normalized;

import traceReaders.raw.IoTrace;
import traceReaders.raw.TraceException;

/**
 * Classes extending this abstract class are responsible of saving IoTraces
 * 
 * @author Fabrizio Pastore fabrizio.pastore AT gmail.com
 *
 */
public interface NormalizedIoTraceHandler {
	
	/**
	 * Create a new IoTrace for the method. If a trace already exists it will be overwritten 
	 * (we do this beacuse this method is only called by InvariantGenerator during the generation of NormalizedTraces, 
	 * so we don't take care of previously existing normalizedTraces). 
	 * 
	 * 
	 * @param trace
	 * @return a void NormalizedIoTrace to be filled in with superstructures and program-points
	 * @throws TraceNotSupportedException 
	 */
	public abstract NormalizedIoTrace newIoTrace( IoTrace trace ) throws TraceNotSupportedException;

	/**
	 * Returns a {@link NormalizedIoTraceIterator} to all traces saved
	 * @return
	 */
	public abstract NormalizedIoTraceIterator getIoTracesIterator();

	/**
	 * Saves changes we have done on a trace.
	 * 
	 * @param normalizedTrace
	 * @throws TraceException
	 */
	public abstract void saveTrace(NormalizedIoTrace normalizedTrace) throws TraceException;
	
		
}
