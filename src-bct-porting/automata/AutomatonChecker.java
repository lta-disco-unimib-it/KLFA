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

import automata.fsa.FSANondeterminismDetector;
import automata.fsa.FiniteStateAutomaton;

/**
 * The Automaton checker can be used to determine certain properties
 * about automata.
 *
 * @author Ryan Cavalcante
 */

public class AutomatonChecker {
    /**
     * Creates instance of <CODE>AutomatonChecker</CODE>.
     */
    public AutomatonChecker() {

    }

    /**
     * Returns true if <CODE>automaton</CODE> is a non-deterministic
     * finite state automaton.
     * @param automaton the automaton.
     * @return true if <CODE>automaton</CODE> is a non-deterministic
     * finite state automaton.
     */
    public boolean isNFA(Automaton automaton) {
	if(!(automaton instanceof FiniteStateAutomaton)) { 
	    return false;
	}
	NondeterminismDetector nd = new FSANondeterminismDetector();
	State[] nondeterministicStates = 
	    nd.getNondeterministicStates(automaton);
	return nondeterministicStates.length > 0;
    }

}
