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

import java.util.ArrayList;

import automata.AlphabetRetriever;
import automata.Automaton;
import automata.Transition;

/**
 * The FSA alphabet retriever object can be used to find the
 * alphabet for a given finite state automaton.  The method
 * of determining the alphabet for automaton involves examining
 * all transitions in the automaton and adding each new character
 * on a transition label to the alphabet.
 *
 * @author Ryan Cavalcante
 */

public class FSAAlphabetRetriever extends AlphabetRetriever{
    /**
     * Creates an instance of <CODE>FSAAlphabetRetriever</CODE>.
     */
    public FSAAlphabetRetriever() {

    }

    /**
     * Returns the alphabet of <CODE>automaton</CODE> by 
     * analyzing all transitions and their labels.
     * @param automaton the automaton
     * @return the alphabet, in a string[].
     */
    public String[] getAlphabet(Automaton automaton) {
	ArrayList list = new ArrayList();
	Transition[] transitions = automaton.getTransitions();
	for(int k = 0; k < transitions.length; k++) {
	    FSATransition transition = (FSATransition) transitions[k];
	    String label = transition.getLabel();
	    if(!label.equals("") && !list.contains(label)) {
		list.add(label);
	    }
	}
	return (String[]) list.toArray(new String[0]);
    }
}
