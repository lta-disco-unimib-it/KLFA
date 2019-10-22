package it.unimib.disco.lta.alfa.utils;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.Stack;

import automata.Automaton;
import automata.ClosureTaker;
import automata.LambdaCheckerFactory;
import automata.LambdaTransitionChecker;
import automata.State;
import automata.Transition;
import automata.fsa.FSALambdaTransitionChecker;

public class FSAUtil {

	
	
	/**
	 * This method returns all the transitions outgoing from a state
	 * 
	 * @param state
	 * @return
	 */
	public static Set<Transition> returnOutGoingTransitions( State state ){
		ClosureTaker ct = new ClosureTaker();
		
		Set<Transition> transitions = new HashSet<Transition>();
		Automaton fsa = state.getAutomaton();
	
		LambdaTransitionChecker ltc = LambdaCheckerFactory.getLambdaChecker(fsa);
		
		for ( State closureState : ct.getClosure(state,fsa) ){
			for ( Transition expectedTransition : fsa.getTransitionsFromState(closureState) ){
				if ( ! ltc.isLambdaTransition(expectedTransition) ){
					transitions.add(expectedTransition);
				}
			}
		}
		
		return transitions;
	}

	/**
	 * Return a set with the transitions that lead to this state (directly or through lambda transitions)
	 * @param state
	 * @return
	 */
	public static Set<Transition> returnIncomingTransitions (State state){
		Automaton automaton = state.getAutomaton();
		LambdaTransitionChecker checker = LambdaCheckerFactory.getLambdaChecker(automaton);
		Set<State> visited = new HashSet<State>();
		Stack<State> stack = new Stack<State>();
		stack.push(state);
		Set<Transition> result = new HashSet<Transition>();
		while(!stack.isEmpty()) {
			state = stack.pop();

			//one visit for each state
			if(visited.contains(state))
				continue;

			//add into visited list
			visited.add(state);
			//get transitions from the state
			Transition transitions[] = automaton.getTransitionsToState(state);


			for(Transition transition : transitions) {				
				// if lambda transition
				if(checker.isLambdaTransition(transition)) {
					State fromState = transition.getFromState();
					//add into explorer list
					if(!visited.contains(fromState) ) {
						stack.push(fromState);
					}
				} else {
					
					result.add(transition);
				}
			}
		}
		return result;	
	}
	
}
