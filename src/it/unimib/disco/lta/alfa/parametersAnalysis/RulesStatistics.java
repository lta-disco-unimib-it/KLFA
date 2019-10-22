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
