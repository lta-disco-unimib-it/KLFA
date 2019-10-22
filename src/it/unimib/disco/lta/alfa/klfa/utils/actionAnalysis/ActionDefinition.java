package it.unimib.disco.lta.alfa.klfa.utils.actionAnalysis;

import it.unimib.disco.lta.alfa.klfa.traceFilters.TraceFilterPositional;
import it.unimib.disco.lta.alfa.klfa.traceFilters.TraceFilterRule;

import java.io.Serializable;
import java.util.List;

public class ActionDefinition implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<TraceFilterRule> rules;
	public List<TraceFilterRule> getRules() {
		return rules;
	}

	public String getName() {
		return name;
	}


	private String name;
	private TraceFilterPositional filter;

	public ActionDefinition ( String name, List<TraceFilterRule> rules ){
		this.name = name;
		this.rules = rules;
		filter = new TraceFilterPositional(rules);
	}
	
	public boolean isActionEvent ( String cols[] ){
		return filter.accept(cols);
	}
}
