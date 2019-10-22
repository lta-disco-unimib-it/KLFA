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
