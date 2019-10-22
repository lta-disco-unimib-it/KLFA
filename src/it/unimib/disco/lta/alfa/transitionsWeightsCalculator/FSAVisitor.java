package it.unimib.disco.lta.alfa.transitionsWeightsCalculator;

import automata.State;
import automata.Transition;

public interface FSAVisitor {

	public boolean visit( State state );
	
	public boolean visit( Transition transition );
	
	public boolean newConfig();
}
