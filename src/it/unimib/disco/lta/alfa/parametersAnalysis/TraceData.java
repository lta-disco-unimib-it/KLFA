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
/**
 * 
 */
package it.unimib.disco.lta.alfa.parametersAnalysis;

import java.util.ArrayList;


public class TraceData {
	private RulesStatistics rulesStatistics;
	private int lineCount = 0;


	public int getLineCount() {
		return lineCount;
	}

	public TraceData(boolean countLines){
		rulesStatistics = new RulesStatistics(countLines);
	}
	
	public void addStat(String signature, ArrayList<String> parameters) {
		RuleStatistics rs = rulesStatistics.get(signature);
		rs.addAll(parameters,lineCount);
	}
	
	/**
	 * Return the statistics for all the rules
	 * @return
	 */
	public RulesStatistics getRulesStatistics() {
		return rulesStatistics;
	}

	public void newLine(){
		++lineCount;
	}

	protected void setRulesStatistics(RulesStatistics rulesStatistics) {
		this.rulesStatistics = rulesStatistics;
	}
	
	
}