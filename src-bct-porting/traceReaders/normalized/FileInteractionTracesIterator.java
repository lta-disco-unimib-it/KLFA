package traceReaders.normalized;

import java.util.Iterator;

import tools.TraceRepository;
import util.FileIndex.FileIndexException;

/**
 * This inner class is an iterator over FileInteractionTraces.
 * It is declared as private because we want only be created by NormalizedInteractionTraceHandlerFile.
 * 
 * @author Fabrizio Pastore fabrizio.pastore AT gmail.com
 *
 */
class FileInteractionTracesIterator implements NormalizedInteractionTraceIterator {

	
	private Iterator<NormalizedInteractionTraceFile> it;

	
	public FileInteractionTracesIterator(TraceRepository repository) {
		try {
			it = repository.getTraces().iterator();
		} catch (FileIndexException e) {
			
		}
	}

	public boolean hasNext() {
		if ( it == null )
			return false;
		return ( it.hasNext() );
	}

	public NormalizedInteractionTrace next() {
		 
		return it.next();
		
		
	}

	public void remove() {
		throw new UnsupportedOperationException();

	}

}