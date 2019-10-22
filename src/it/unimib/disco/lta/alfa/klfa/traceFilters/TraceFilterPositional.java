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
