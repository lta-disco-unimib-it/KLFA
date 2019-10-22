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
