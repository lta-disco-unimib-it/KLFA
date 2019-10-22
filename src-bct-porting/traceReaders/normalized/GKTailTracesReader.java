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

/**
 * @author 
 * This class contains methods to get from DB data needed to execute 
 * the third step of the GK-Tail algorithm (merge equivalent trace)
 */
public interface GKTailTracesReader {
	
	/**
	 * Returns an Iterator over all Methods 
	 * @return
	 */
	public Iterator getMethods() throws DataLayerException;

	/**
	 * Returns an Iterator over all Interaction Traces
	 * @return
	 */
	public ArrayList getInteractionTraces(Iterator methodList) throws DataLayerException;
	
	/**
	 * Returns an Iterator over all Method Calls of an Interaction Trace
	 * @return
	 */
	public Iterator getMethodCall(ArrayList interactionTraceList) throws DataLayerException;
	
	/**
	 * Returns an Iterator over all ID Method Calls of an Interaction Trace
	 * @return
	 */
	public Iterator getIDMethodCall(int traceMethod, ArrayList interactionTraceList) throws DataLayerException;

	public String getConstraint(int idMethodCall) throws DataLayerException;
	
}
