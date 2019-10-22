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
 
package gui.editor;

import gui.viewer.AutomatonPane;
import automata.State;
import automata.Transition;
import automata.vdg.VDGTransition;

/**
 * This is a transition creator for variable dependency graphs.
 * 
 * @author Thomas Finley
 */

public class VDGTransitionCreator extends TransitionCreator {
    /**
     * Instantiates a transition creator.
     * @param parent the parent object that any dialogs or windows
     * brought up by this creator should be the child of
     */
    public VDGTransitionCreator(AutomatonPane parent) {
	super(parent);
    }

    /**
     * Creates a transition with user interaction and returns it.
     * @return returns the variable dependency transition
     */
    public Transition createTransition(State from, State to) {
	VDGTransition t = new VDGTransition(from, to);
	getParent().getDrawer().getAutomaton().addTransition(t);
	return null;
    }

    /**
     * Edits a given transition.  Ideally this should use the same
     * interface as that given by <CODE>createTransition</CODE>.
     * @param transition the transition to edit
     * @return <CODE>false</CODE> if the user decided to not edit a
     * transition, <CODE>true</CODE> if the edit was "approved"
     */
    public boolean editTransition(Transition transition) {
	return false;
    }
}
