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
package regressionTestManager.ioInvariantParser;

import regressionTestManager.tcSpecifications.TcSpecification;

/**
 * This class represent a case in wich an array must contain the given element at least one time
 * 
 * It is used for example to expand one of with array ( parameter[0].myArray[] one of { 2, 3, 4}.
 * In this case in fact we want to rerun test for all the possible values that array elements can assume.
 * 
 * @author Fabrizio Pastore fabrizio.pastore AT gmail.com
 *
 */
public class TcSpecificationArrayContains implements TcSpecification {
	private Variable array;
	private Object element;
	
	/**
	 * Constructot
	 * 
	 * @param array	the array variable
	 * @param element	the element that the variable must contain
	 * 
	 */
	public TcSpecificationArrayContains(Variable array, Object element) {
		this.array = array;
		this.element = element;
	}
	
}
