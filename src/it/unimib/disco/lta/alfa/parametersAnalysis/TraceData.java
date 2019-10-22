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