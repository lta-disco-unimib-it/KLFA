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
