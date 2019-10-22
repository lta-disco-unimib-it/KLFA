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
package dfmaker.core;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;

/**
 * @author Davide Lorenzoli
 * 
 * Handles the Daikon output containing the inferred models.
 */
public interface DaikonModelsParserInterface {
	
	/**
	 * Parse the ENTER model set and the EXIT model set and returns the
	 * <code>IoModel</code> representing the set of ENTER and EXIT models. 
	 * @param reader
	 * @return <code>IoModel</code> representing the set of ENTER end EXIT models
	 * @throws IOException
	 */
	//public IoModel getModels(Reader reader) throws IOException;
	
	/**
	 * Parse the ENTER model set and the EXIT model set and returns the
	 * <code>IoModel</code> representing the set of ENTER and EXIT models. 
	 * @param reader
	 * @return <code>IoModel</code> representing the set of ENTER end EXIT models
	 * @throws IOException
	 */
	public ArrayList<String> getPreconditions(Reader reader) throws IOException;
	
	/**
	 * Parse the EXIT model set and returns the <code>IoModel</code>
	 * representing the set of EXIT models.
	 * @param reader
	 * @return <code>IoModel</code> representing the set of EXIT models
	 * @throws IOException
	 */
	public ArrayList<String> getPostconditions(Reader reader) throws IOException;
}