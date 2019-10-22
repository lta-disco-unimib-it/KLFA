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
package it.unimib.disco.lta.alfa.klfa.traceFilters;

import java.util.List;

import it.unimib.disco.lta.alfa.parametersAnalysis.TraceFilter;

public class TraceFilterPositional implements TraceFilter {

	private List<TraceFilterRule> rules;

	public TraceFilterPositional ( List<TraceFilterRule> rules ){
		this.rules = rules;
	}
	
	public boolean accept(String[] cols) {
		for ( TraceFilterRule rule : rules ){
			if ( rule.accept(cols) ){
				return true;
			} else if ( rule.reject(cols) ){
				return false;
			}
		}
		return true;
	}



}
