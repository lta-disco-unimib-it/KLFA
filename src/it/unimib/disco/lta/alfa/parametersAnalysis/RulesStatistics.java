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

import it.unimib.disco.lta.alfa.logging.Logger;

import java.util.Collection;
import java.util.HashMap;
import java.util.regex.Pattern;


public class RulesStatistics {
	private HashMap<String, RuleStatistics> statistics = new HashMap<String, RuleStatistics>();
	private boolean countLines;
	
	public RulesStatistics ( boolean countLines ){
		this.countLines = countLines;
	}
	
	public RuleStatistics get(String rule) {
		RuleStatistics ruleStat = statistics.get(rule);
		if ( ruleStat == null ){
			Logger.finer("Rule Statistic for "+rule+" Not Found, creating one");
			ruleStat = new RuleStatistics(rule,countLines);
			statistics.put(rule, ruleStat);
		}
		return ruleStat;
	}

	public Collection<RuleStatistics> getStatistics() {
		return statistics.values();
	}

}
