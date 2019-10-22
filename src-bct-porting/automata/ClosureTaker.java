/* -- JFLAP 4.0 --
 *
 * Copyright information:
 *
 * Susan H. Rodger, Thomas Finley
 * Computer Science Department
 * Duke University
 * April 24, 2003
 * Supported by National Science Foundation DUE-9752583.
 *
 * Copyright (c) 2003
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms are permitted
 * provided that the above copyright notice and this paragraph are
 * duplicated in all such forms and that any documentation,
 * advertising materials, and other materials related to such
 * distribution and use acknowledge that the software was developed
 * by the author.  The name of the author may not be used to
 * endorse or promote products derived from this software without
 * specific prior written permission.
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND WITHOUT ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, WITHOUT LIMITATION, THE IMPLIED
 * WARRANTIES OF MERCHANTIBILITY AND FITNESS FOR A PARTICULAR PURPOSE.
 */
 
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
