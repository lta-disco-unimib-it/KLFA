package it.unimib.disco.lta.alfa.klfa.traceFilters;

import it.unimib.disco.lta.alfa.parametersAnalysis.TraceFilter;

public class TraceFilterAcceptAll implements TraceFilter {

	public boolean accept(String[] line) {
		return true;
	}

}
