package it.unimib.disco.lta.fsa.closure;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

import automata.Automaton;
import automata.LambdaCheckerFactory;
import automata.LambdaTransitionChecker;
import automata.State;
import automata.Transition;

/**
 * The closure incrementer object can be used to take the closure of 
 * a state in an automaton.
 *
 */
public class ClosureIncrementer {

	private Set<State> visited;
	
	/**
	 * Default constructor.
	 */
	public ClosureIncrementer() {
		visited = new HashSet<State>();
	}
	
	
	/**
	 * Returns the set of visited states.
	 * @return the set of visited states.
	 */
	public Set<State> getVisited() {
		return visited;
	}

	/**
	 * Perform the closure of state, that is, all states
	 * reachable from state without changing any internal
	 * state (e.g. stack, tape, input..) via lambda transitions.
	 * @param state the state whose closure is being taken.
	 * @param automaton the automaton.
	 */
	public void addClosure(State state, Automaton automaton) {
		LambdaTransitionChecker checker = LambdaCheckerFactory.getLambdaChecker(automaton);
		Stack<State> stack = new Stack<State>();
		stack.push(state);
		
		while(!stack.isEmpty()) {
			state = stack.pop();
			
			//one visit for each state
			if(visited.contains(state))
				continue;
			
			//add into visited list
			visited.add(state);
			//get transitions from the state
			Transition transitions[] = automaton.getTransitionsFromState(state);
			
			for(Transition transition : transitions) {				
				// if lambda transition
				if(checker.isLambdaTransition(transition)) {
					State toState = transition.getToState();
					//add into explorer list
					if(!visited.contains(toState) ) {
						stack.push(toState);
					}
				}
			}
		}
	}
	
}
