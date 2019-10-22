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
 
package automata.fsa;

import automata.NondeterminismDetector;
import automata.Transition;

/**
 * The FSA nondeterminism detector object can be used to
 * find all the nondeterministic states in a finite state
 * automaton (i.e. all states with equal outward transitions).
 * 
 * @author Ryan Cavalcante
 */

public class FSANondeterminismDetector extends NondeterminismDetector {
    /** 
     * Creates an instance of <CODE>FSANondeterminismDetector</CODE>
     */
    public FSANondeterminismDetector() {
    }

    /**
     * Returns true if the transitions are identical (i.e. the
     * labels are equivalent), or if they introduce nondeterminism
     * (e.g. the label of one could be a prefix of the label of 
     * the other).
     * @param t1 a transition
     * @param t2 a transition
     * @return true if the transitions are nondeterministic.
     */
    public boolean areNondeterministic(Transition t1, Transition t2) {
	FSATransition transition1 = (FSATransition) t1;
	FSATransition transition2 = (FSATransition) t2;
	if(transition1.getLabel().equals(transition2.getLabel())) 
	    return true;
	else if(transition1.getLabel().startsWith(transition2.getLabel()))
	    return true;
	else if(transition2.getLabel().startsWith(transition1.getLabel()))
	    return true;
	else return false;
    }

}
