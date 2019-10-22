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
import automata.pda.PDANondeterminismDetector;
import automata.turing.TMNondeterminismDetector;

/**
 * The <CODE>NondeterminismDetectorFactory</CODE> is a factory class
 * for returning a <CODE>NondeterminismDetector</CODE> based on the
 * automaton that is passed in.
 * 
 * @author Thomas Finley
 */

public class NondeterminismDetectorFactory {
    /**
     * Returns the nondeterminism detector for this type of automaton.
     * @param automaton the automaton to get the nondeterminism
     * detector for
     * @return the appropriate automaton it.unimib.disco.lta.conFunkHealer.simulator for this automaton,
     * or <CODE>null</CODE> if there is no automaton it.unimib.disco.lta.conFunkHealer.simulator known
     * for this type of automaton
     */
    public static NondeterminismDetector getDetector(Automaton automaton) {
	if (automaton instanceof automata.fsa.FiniteStateAutomaton)
	    return new FSANondeterminismDetector();
	else if (automaton instanceof automata.pda.PushdownAutomaton)
	    return new PDANondeterminismDetector();
	else if (automaton instanceof automata.turing.TuringMachine)
	    return new TMNondeterminismDetector();
	return null;
    }
}
