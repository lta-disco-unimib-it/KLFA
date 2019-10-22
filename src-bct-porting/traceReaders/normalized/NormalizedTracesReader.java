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

import java.util.ArrayList;
import java.util.Iterator;

import database.DataLayerException;

import traceReaders.raw.TraceException;

/**
 * @author 
 * This class contains methods to get from DB data needed to execute 
 * the first step of the GK-Tail algorithm (merge similar trace)
 */

public interface NormalizedTracesReader {
	
	/**
	 * Returns an Iterator over all Methods 
	 * @return
	 */
	public Iterator getMethods() throws DataLayerException;

	/**
	 * Returns an Iterator over all Interaction Traces
	 * @return
	 */
	public Iterator getInteractionTraces(int method) throws DataLayerException;
	
	/**
	 * Returns thread of the specified trace
	 * @return
	 */
	public int getInteractionThread(int interactionTrace) throws DataLayerException;
	
	/**
	 * Returns an Iterator over all Method Calls of an Interaction Trace
	 * @return
	 */
	public Iterator getMethodCall(int interactionTrace) throws DataLayerException;
	
	/**
	 * Returns an Iterator over all ID Method Calls of an Interaction Trace
	 * @return
	 */
	public Iterator getIDMethodCall(int interactionTrace) throws DataLayerException;
	
	/**
	 * Returns an Iterator over all Enter Normalized Data of a Method Call
	 * @return
	 */
	public String getNormalizedDataENTER(int idMethodCall) throws DataLayerException;
	
	/**
	 * Returns an Iterator over all Exit Normalized Data of a Method Call
	 * @return
	 */
	public String getNormalizedDataEXIT(int idMethodCall) throws DataLayerException;

	/**
	 * Returns an Iterator over all Normalized Data of a Method Call
	 * @return
	 */
	public String getNormalizedDataENTEREXIT(int idMethodCall) throws DataLayerException; 
}
