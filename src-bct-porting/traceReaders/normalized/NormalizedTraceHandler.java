package traceReaders.normalized;

import dfmaker.core.Superstructure;

/**
 * Interface to provide right InteractionTraceHandlers.
 * It is used by TraceHnadlerfactory.
 * 
 * @author Fabrizio Pastore fabrizio.pastore AT gmail.com
 *
 */
public interface NormalizedTraceHandler {
	
	NormalizedInteractionTraceHandler getInteractionTraceHandler();

	NormalizedIoTraceHandler getIoTraceHandler();
	
}
