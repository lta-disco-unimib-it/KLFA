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
package regressionTestManager.tcSpecifications;

import regressionTestManager.ioInvariantParser.Variable;

public class TcSpecificationEqualsNull implements TcSpecification {

	private Variable variable;
	
	public TcSpecificationEqualsNull(Variable variable) {
		this.variable = variable;
	}

	public boolean equals( Object o ){
		if ( o == this )
			return true;
		if ( o instanceof TcSpecificationEqualsNull )
			return ((TcSpecificationEqualsNull)o).variable.equals(variable);
		return false;
	}
	
	public String toString(){
		return variable + " == null";
	}

	public Variable getVariable() {
		return variable;
	}
}
