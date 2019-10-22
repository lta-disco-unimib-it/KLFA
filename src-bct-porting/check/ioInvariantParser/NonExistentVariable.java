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
package check.ioInvariantParser;

public class NonExistentVariable {
	
	/**
	 * A non existent variable cannot be equal to another variable. If a variable does not exists
	 * it is also be not equals to another non existent one.
	 * 
	 * @param Object o the object to check if equal to
	 * @return false
	 * 
	 */
	public boolean equals ( Object o ){
		return false;
	}

	
}
