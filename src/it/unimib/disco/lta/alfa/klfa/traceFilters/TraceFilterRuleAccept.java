package it.unimib.disco.lta.alfa.klfa.traceFilters;


public class TraceFilterRuleAccept extends TraceFilterRule {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6612887151839455037L;

	public TraceFilterRuleAccept() {
		super();
	}
	
	public TraceFilterRuleAccept(String ruleName) {
		super(ruleName);
	}

	@Override
	public boolean accept(String[] cols) {
		return match(cols);
	}

	@Override
	public boolean reject(String[] cols) {
		return false;
	}

}
