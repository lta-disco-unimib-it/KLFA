package util;

import automata.Automaton;
import automata.State;
import automata.Transition;

public class FSAUtil {
	
	public static interface FSAVisitor {
		
		public boolean before ( State state );
		
		public boolean after (  State state );
		
		public boolean before ( Transition t);
		
		public boolean after ( Transition t);
		
	}
	
	public static void depthFirstVisit( State state, FSAVisitor visitor ){
		if ( ! visitor.before(state) )
			return;

		Automaton fsa = state.getAutomaton();
		Transition[] transitionsTo = fsa.getTransitionsFromState(state);
		for ( Transition t : transitionsTo ){
			if ( visitor.before(t) ){

				State toState = t.getToState();

				depthFirstVisit(toState, visitor);

			}
			visitor.after(t);
		}
		visitor.after(state);
	}
	

}
