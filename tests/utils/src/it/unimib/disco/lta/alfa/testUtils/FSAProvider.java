package it.unimib.disco.lta.alfa.testUtils;

import java.awt.Point;

import automata.State;
import automata.fsa.FSATransition;
import automata.fsa.FiniteStateAutomaton;

public class FSAProvider {
	
	/**
	 * Return a simple automaton
	 * 
	 * A->B->C->D->E->F->G
	 * @return 
	 */
	public static FiniteStateAutomaton getSimpleFSA(){
		return getSimpleFSA("");
	}
	
	public static FiniteStateAutomaton getSimpleFSA(String prefix){
		FiniteStateAutomaton fsa = new FiniteStateAutomaton();
		State state = fsa.createState(new Point(0,0));
		fsa.setInitialState(state);
		String[] transitions = new String[]{"A","B","C","D","E","F","G","H","I","L","M","N","O"};
		
		State prevState = state;
		for ( String transition : transitions ){
			state =  fsa.createState(new Point(0,0));
			FSATransition trans = new FSATransition(prevState,state,prefix+transition);
			fsa.addTransition(trans);
			prevState = state;
		}
		
		fsa.addFinalState(state);
		
		return fsa;
	}
}
