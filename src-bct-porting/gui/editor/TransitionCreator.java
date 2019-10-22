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

import java.awt.Point;

import javax.swing.JOptionPane;

import automata.Automaton;
import automata.State;
import automata.Transition;

/**
 * A <CODE>TransitionCreator</CODE> object is used to present a
 * graphical environment for the creation and editing of transitions
 * for insertion into automata.
 * 
 * @author Thomas Finley
 */

public abstract class TransitionCreator {
    public TransitionCreator() {
	
    }

    public TransitionCreator(AutomatonPane parent) {
	this.parent = parent;
    }

    /**
     * Returns the automaton.
     * @return the automaton
     */
    protected Automaton getAutomaton() {
	return getParent().getDrawer().getAutomaton();
    }

    /**
     * Begins the process of creating a transition and returns it.
     * @param from the state the transition will go from
     * @param to the state the transition will go to
     */
    public abstract Transition createTransition(State from, State to);

    /**
     * Edits a given transition.  Ideally this should use the same
     * interface as that given by <CODE>createTransition</CODE>.
     * @param transition the transition to edit
     * @return <CODE>false</CODE> if the user decided to edit a
     * transition
     */
    public abstract boolean editTransition(Transition transition);

    /**
     * This is a static method used to return a transition creator for
     * the sort of automaton that is being edited.
     * @param automaton the automaton for which there will be created
     * a compatible transition creator
     * @param parent the component that should be the parent of any
     * dialog boxes or other windows brought up
     * @return a transition creator that generates transitions
     * compatible with the type of automaton passed in, or
     * <CODE>null</CODE> if this type of automaton is unknown
     */
    public static TransitionCreator creatorForAutomaton
	(Automaton automaton, AutomatonPane parent) {
	if (automaton instanceof automata.fsa.FiniteStateAutomaton)
	    return new FSATransitionCreator(parent);
	if (automaton instanceof automata.pda.PushdownAutomaton)
	    return new PDATransitionCreator(parent);
	if (automaton instanceof automata.turing.TuringMachine)
	    return new TMTransitionCreator(parent);
	if (automaton instanceof automata.vdg.VariableDependencyGraph)
	    return new VDGTransitionCreator(parent);
	return null;
    }

    /**
     * Edits the transition at the particular point.  By default, this
     * envokes the pointless method.
     * @param transition the transition to edit
     * @param point the point to edit the transition at
     */
    public void editTransition(Transition transition, Point point) {
	editTransition(transition);
    }

    /**
     * Returns the parent component of this transition creator.
     * @return the parent component of this transition creator
     */
    public AutomatonPane getParent() {
	return parent;
    }

    /**
     * Reports an error to the user through a dialog box based on an
     * illegal argument exception.
     * @param e the illegal argument exception
     */
    public void reportException(IllegalArgumentException e) {
	JOptionPane.showMessageDialog
	    (getParent(), "Bad format!\n"+e.getMessage(),
	     "Bad Format", JOptionPane.ERROR_MESSAGE);
    }

    /** The parent component for this transition creator. */
    private AutomatonPane parent = null;
}
