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
 
package automata.turing;

import automata.NondeterminismDetector;
import automata.Transition;

/**
 * The TTM nondeterminism detector object can be used to find all the
 * nondeterministic states in a Turing machine (i.e. all states with
 * transitions that read the same symbols on each tape).
 * 
 * @author Thomas Finley
 */

public class TMNondeterminismDetector extends NondeterminismDetector {
    /** 
     * Creates an instance of a <CODE>TMNondeterminismDetector</CODE>.
     */
    public TMNondeterminismDetector() {
    }

    /**
     * Returns true if the transitions introduce 
     * nondeterminism (e.g. the input to read from tapes one and
     * two portions of the transition labels are identical).
     * @param t1 a transition
     * @param t2 a transition
     * @return true if the transitions introduce nondeterminism
     */
    public boolean areNondeterministic(Transition t1, Transition t2) {
	TMTransition transition1 = (TMTransition) t1;
	TMTransition transition2 = (TMTransition) t2;
	
	for (int i=0; i<transition1.tapes(); i++) {
	    String read1 = transition1.getRead(i);
	    String read2 = transition2.getRead(i);
	    if (!read1.equals(read2)) return false;
	}
	return true;
    }

}
