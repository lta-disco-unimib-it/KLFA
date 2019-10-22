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
package it.unimib.disco.lta.alfa.parametersAnalysis;

import java.util.Set;
import java.util.regex.Pattern;


public class RuleParameter {
	
	private String ruleName;
	private int parameterPos;


	public RuleParameter(String ruleName, int parameterPos ){
		this.ruleName = ruleName;
		this.parameterPos = parameterPos;
	}

	

	public String getRuleName() {
		return ruleName;
	}

	public int getParameterPos() {
		return parameterPos;
	}
	
	public String toString(){
		return ruleName+"_"+parameterPos;
	}
	
	public int hashCode(){
		return toString().hashCode();
	}
	
	public boolean equals(Object o ){
		
		if ( ! ( o instanceof RuleParameter ) ){
			return false;
		}
		
		RuleParameter rhs = (RuleParameter) o;
		return ( rhs.ruleName.equals(ruleName) && rhs.parameterPos == parameterPos );
	}



}
