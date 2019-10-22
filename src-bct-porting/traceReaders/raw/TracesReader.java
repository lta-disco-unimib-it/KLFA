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
package traceReaders.raw;

import java.io.File;
import java.util.Iterator;

import traceReaders.TraceReaderException;

import conf.BctSettingsException;
import conf.InvariantGeneratorSettings;
import conf.TraceReaderSettings;
import database.DataLayerException;

public interface TracesReader {

	/**
	 * Used to initialize internal TraceReader parameters
	 * @param trs
	 * @throws BctSettingsException
	 */
	public void init( InvariantGeneratorSettings trs ) throws BctSettingsException;
	
	/**
	 * Returns an Iterator over all IoTraces
	 * @return
	 * @throws TraceException 
	 * @throws TraceException 
	 * @throws FileReaderException 
	 */
	public Iterator<IoTrace> getIoTraces() throws TraceException, TraceReaderException;

	/**
	 * Returns an Iterator over all Interaction Traces
	 * @return
	 */
	public Iterator<InteractionTrace> getInteractionTraces() throws TraceException;

	/**
	 * Returns the IO trace of the passed method.
	 * The passed name must be exactly equeals to the name of the method recorded in the data recording phase.
	 * 
	 * @param methodName
	 * @return
	 * @throws TraceReaderException 
	 */
	public IoTrace getIoTrace(String methodName) throws TraceReaderException;

}
