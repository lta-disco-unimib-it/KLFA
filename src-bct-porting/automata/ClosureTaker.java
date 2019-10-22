/*******************************************************************************
 *    Copyright 2019 Fabrizio Pastore, Leonardo Mariani
 *   
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 *******************************************************************************/
 
package automata;

import java.util.ArrayList;
import java.util.List;

/**
 * The closure taker object can be used to take the closure of states
 * in an automaton.
 *
 * @author Ryan Cavalcante
 */

public class ClosureTaker {
    /**
     * Creates instance of <CODE>ClosureTaker</CODE>.
     */
    public ClosureTaker() {

    }

    /**
     * Returns the closure of <CODE>state</CODE>, that is, all states
     * reachable from <CODE>state</CODE> without changing any internal
     * state (e.g. stack, tape, input) via lambda transitions.
     * @param state the state whose closure is being taken.
     * @param automaton the automaton
     * @return the set of states that represent the closure of state.
     */
    public static State[] getClosure(State state, Automaton automaton) {
	List list = new ArrayList();
	list.add(state);
	for (int i=0; i<list.size(); i++) {
	    state = (State) list.get(i);
	    Transition transitions[] =
		automaton.getTransitionsFromState(state);
	    for(int k = 0; k < transitions.length; k++) {
		Transition transition = transitions[k];
		LambdaTransitionChecker checker = 
		    LambdaCheckerFactory.getLambdaChecker(automaton);
		/** if lambda transition */
		if(checker.isLambdaTransition(transition)) {
		    State toState = transition.getToState();
		    if(!list.contains(toState)) {
			list.add(toState);
		    }
		}
	    }
	}
	return (State[]) list.toArray(new State[0]);
    }


}
