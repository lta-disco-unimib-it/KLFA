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
