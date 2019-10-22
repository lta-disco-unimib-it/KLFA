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

import java.io.File;

/**
 * This class wraps a normalized interaction trace.
 * The behaviour of this class is managed by the NormalizedInteractionTraceMapper.
 *  
 * @author Fabrizio Pastore fabrizio.pastore AT gmail.com
 *
 */
public interface NormalizedInteractionTrace {
	
	/**
	 * Returns the name of the method this trace refers to
	 * 
	 * @return	the name of the method
	 */
	public String getMethodName();
	
	/**
	 * Returns the File on which the trace is stored. Since InferenceEngine work on files every normalizedTrace implementor
	 * must implement a method that generate a that file and return its file descriptor.
	 * 
	 * @return the file
	 */
	public File getTraceFile();

}
