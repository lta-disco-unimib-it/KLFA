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

public class ParametersIntersection {

	private RuleParameter ruleParameter2;
	private RuleParameter ruleParameter1;
	private int intersectionSize;

	public ParametersIntersection(RuleParameter ruleParameter1,RuleParameter ruleParameter2,int size){
		this.ruleParameter1 = ruleParameter1;
		this.ruleParameter2 = ruleParameter2;
		this.intersectionSize = size;
	}

	public RuleParameter getRuleParameter2() {
		return ruleParameter2;
	}

	public RuleParameter getRuleParameter1() {
		return ruleParameter1;
	}

	public int getIntersectionSize() {
		return intersectionSize;
	}
}
