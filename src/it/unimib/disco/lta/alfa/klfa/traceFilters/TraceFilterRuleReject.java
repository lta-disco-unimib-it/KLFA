package it.unimib.disco.lta.alfa.klfa.traceFilters;

public class TraceFilterRuleReject extends TraceFilterRule {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3399345874516914711L;

	public TraceFilterRuleReject() {
		
	}

	public TraceFilterRuleReject(String ruleName) {
		super(ruleName);
	}

	@Override
	public boolean accept(String[] cols) {
		return false;
	}

	@Override
	public boolean reject(String[] cols) {
		return match(cols);
	}


	
	

}
