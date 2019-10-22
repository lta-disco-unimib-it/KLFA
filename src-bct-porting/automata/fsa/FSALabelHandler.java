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

import automata.Automaton;
import automata.State;
import automata.Transition;

/**
 * The FSA label handler is an object that can convert a finite state
 * automaton with transition labels of more than one character in 
 * length into an equivalent finite state automaton with all 
 * transition labels exactly one character in length.
 *
 * @author Ryan Cavalcante
 */

public class FSALabelHandler {
    /**
     * Creates an instance of <CODE>FSALabelHandler</CODE>.
     */
    public FSALabelHandler() {
	
    }
    
    /**
     * Returns true if <CODE>automaton</CODE> has labels with multiple
     * characters, instead of single character labels.
     * @param automaton the automaton.
     * @return true if <CODE>automaton</CODE> has labels with multiple
     * characters, instead of single character labels.
     */
    public boolean hasMultipleCharacterLabels(Automaton automaton) {
	Transition[] transitions = automaton.getTransitions();
	for(int k = 0; k < transitions.length; k++) {
	    FSATransition transition = (FSATransition) transitions[k];
	    String label = transition.getLabel();
	    if(label.length() > 1) return true;
	}
	return false;
    }

    /**
     * Changes <CODE>transition</CODE> in <CODE>automaton</CODE> to
     * several transitions each with labels of one character in
     * length.  This algorithm introduces new states in
     * <CODE>automaton</CODE>.
     * @param transition the transition to break up into several
     * transitions of one character (in length) a piece.
     * @param automaton the automaton that has the transition.
     */
    public void handleLabel(Transition transition, Automaton automaton) {
	/*FSATransition trans = (FSATransition) transition;
	String label = trans.getLabel();
	String firstChar = label.substring(0,1);
	String restOfLabel = label.substring(1);
	
	StatePlacer sp = new StatePlacer();
	
	State newState = automaton.createState(sp.getPointForState(automaton));
	Transition newTrans1 = new FSATransition(trans.getFromState(),
						newState, firstChar);
	Transition newTrans2 = new FSATransition(newState, trans.getToState(),
						 restOfLabel);
	automaton.addTransition(newTrans1);
	automaton.addTransition(newTrans2);
	automaton.removeTransition(transition);
	if(restOfLabel.length() > 1) handleLabel(newTrans2, automaton);*/

	FSATransition trans = (FSATransition) transition;
	State from = transition.getFromState(), f = from, to = transition.getToState();
	automaton.removeTransition(trans);
	String label = trans.getLabel();
	int length = label.length();
	for (int i=0; i<length; i++) {
	    State going = i == length-1 ? to : automaton.createState
		(new java.awt.Point
		 ((f.getPoint().x*(length-i-1) + to.getPoint().x*(i+1))/length,
		  (f.getPoint().y*(length-i-1) + to.getPoint().y*(i+1))/length));
	    Transition newTrans =
		new FSATransition(from, going, label.substring(i,i+1));
	    automaton.addTransition(newTrans);
	    from = going;
	}
    }

    /**
     * Changes all transitions in <CODE>automaton</CODE> into transitions
     * with at most one character per label.  This could introduce more
     * states into <CODE>automaton</CODE>.
     * @param automaton the automaton.
     */
    public FiniteStateAutomaton removeMultipleCharacterLabels
	(Automaton automaton) {
	FiniteStateAutomaton fsa = (FiniteStateAutomaton) automaton.clone();
	Transition[] transitions = fsa.getTransitions();
	for(int k = 0; k < transitions.length; k++) {
	    FSATransition transition = (FSATransition) transitions[k];
	    String label = transition.getLabel();
	    if(label.length() > 1) {
		handleLabel(transition, fsa);
	    } 
	}
	return fsa;
    }

    /**
     * Changes all transitions in <CODE>automaton</CODE> into transitions
     * with at most one character per label.  This could introduce more
     * states into <CODE>automaton</CODE>.
     * @param automaton the automaton.
     */
    public void removeMultipleCharacterLabelsFromAutomaton
	(Automaton automaton) {
	Transition[] transitions = automaton.getTransitions();
	for(int k = 0; k < transitions.length; k++) {
	    FSATransition transition = (FSATransition) transitions[k];
	    String label = transition.getLabel();
	    if(label.length() > 1) {
		handleLabel(transition, automaton);
	    } 
	}
    }
}
