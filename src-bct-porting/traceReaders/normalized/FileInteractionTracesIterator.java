/*******************************************************************************
 *    Copyright 2019 Fabrizio Pastore, Leonardo Mariani
 *   
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 *******************************************************************************/
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