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
package it.unimib.disco.lta.alfa.dataTransformation;

/**
 * A ValueTransformer is the core element of the data transformation phase.
 * It receives a value and returns a symbolic value according to the tranformation 
 * rule implemented.
 *   
 * @author Fabrizio Pastore fabrizio.pastore AT gmail.com
 *
 */
public interface ValueTransformer {
	
	/**
	 * This method accept a value as input and returns a value transformed according to the
	 * trasformation rule implemented.
	 * 
	 * @param value
	 * @return
	 */
	public String getTransformedValue( String value );

	/**
	 * This method accept a value and a splitted line as input and returns a value transformed according to the
	 * trasformation rule implemented.
	 * 
	 * @param value
	 * @return
	 */
	public String getTransformedValue( String value, String[] line );
	
	/**
	 * This method reset the value transformer state as if it was just created
	 */
	public void reset();
	
	/**
	 * This method reset the value transformer state as if the last operation was not performed
	 */
	public void back() throws UnsupportedOperationException;

	
	
	
}
