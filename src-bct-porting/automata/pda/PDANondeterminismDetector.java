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
 
package automata.pda;

import automata.NondeterminismDetector;
import automata.Transition;

/**
 * The PDA nondeterminism detector object can be used to
 * find all the nondeterministic states in a pushdown
 * automaton (i.e. all states with equivalent outward transitions).
 * 
 * @author Ryan Cavalcante
 */

public class PDANondeterminismDetector extends NondeterminismDetector {
    /** 
     * Creates an instance of <CODE>PDANondeterminismDetectr</CODE>
     */
    public PDANondeterminismDetector() {
    }

    /**
     * Returns true if either of the two strings is a prefix
     * of the other string.
     * @return true if either of the two strings is a prefix 
     * of the other string.
     */
    public boolean arePrefixesOfEachOther(String s1, String s2) {
	if(s1.startsWith(s2) || s2.startsWith(s1)) return true;
	return false;
    }

    /**
     * Returns true if the transitions are identical (i.e. the
     * first 2 components of the labels are equivalent) or
     * if the transitions introduce nondeterminism (e.g. the 
     * labels of one transition could be a prefix of the label
     * of the other transition.)
     * @param t1 a transition
     * @param t2 a transition
     * @return true if the transitions introduce nondeterminism.
     */
    public boolean areNondeterministic(Transition t1, Transition t2) {
	PDATransition transition1 = (PDATransition) t1;
	PDATransition transition2 = (PDATransition) t2;
	String input1 = transition1.getInputToRead();
	String input2 = transition2.getInputToRead();
	String toPop1 = transition1.getStringToPop();
	String toPop2 = transition2.getStringToPop();
	
	if(arePrefixesOfEachOther(input1, input2) &&
		arePrefixesOfEachOther(toPop1, toPop2)) return true;
	else return false;
    }

}
