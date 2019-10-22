package it.unimib.disco.lta.fsa.closure;

import java.util.Collection;
import java.util.Set;

import automata.Automaton;
import automata.State;

/**
 * Class used to find all states reachable with lambda transitions and then add
 * them to the list.
 * 
 */
public class ClosureCalculator {
	
	/**
	 * Method that takes in input a list of states and then search from them all
	 * possible states reachable with lambda transitions. Add to the list only
	 * new states.
	 * @param stateList the list of state from which search lambda transitions.
	 * @param automaton the automaton.
	 */
	public static Set<State> calculateClosure(Collection<State> stateList, Automaton automaton) {
		ClosureIncrementer ci = new ClosureIncrementer();
		
		for(State state : stateList) {
			ci.addClosure(state, automaton);
		}
		
		return ci.getVisited();
	}
}
