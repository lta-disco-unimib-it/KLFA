package tools.fsa2xml.codec.utility;

import tools.fsa2xml.codec.impl.LabelFactory;
import automata.Transition;
import automata.fsa.FSATransition;
import automata.fsa.FiniteStateAutomaton;

public class FSAOptimization {

    public static void optimizeFSA(FiniteStateAutomaton fsa) {
    	LabelFactory labelFactory = new LabelFactory();
		for ( Transition t : fsa.getTransitions() ){
			String uniqueLabel = labelFactory.getLabel(t.getDescription());
			fsa.removeTransition(t);
			
			Transition newT = new FSATransition( t.getFromState(), t.getToState(), uniqueLabel );
			fsa.addTransition(newT);
		}
	}
	
}
