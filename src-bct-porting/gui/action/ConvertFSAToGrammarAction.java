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
 
package gui.action;

import gui.environment.AutomatonEnvironment;
import gui.environment.Universe;
import gui.grammar.automata.ConvertController;
import gui.grammar.automata.ConvertPane;
import gui.grammar.automata.FSAConvertController;
import gui.viewer.SelectionDrawer;

import javax.swing.JOptionPane;

import automata.Automaton;
import automata.fsa.FiniteStateAutomaton;

/**
 * This action handles the conversion of an FSA to a regular grammar.
 * 
 * @author Thomas Finley
 */

public class ConvertFSAToGrammarAction
    extends ConvertAutomatonToGrammarAction {
    /**
     * Instantiates a new <CODE>ConvertFSAToGrammarAction</CODE>.
     * @param environment the environment
     */
    public ConvertFSAToGrammarAction(AutomatonEnvironment environment) {
	super(environment);
    }

    /**
     * Checks the FSA to make sure it's ready to be converted.
     */
    protected boolean checkAutomaton() {
	if (getAutomaton().getStates().length > 26) {
	    JOptionPane.showMessageDialog
		(Universe.frameForEnvironment(getEnvironment()),
		 "There may be at most 26 states for conversion.",
		 "Number of States Error", JOptionPane.ERROR_MESSAGE);
	}
	return true;
    }

    /**
     * Initializes the convert controller.
     * @param pane the convert pane that holds the automaton pane and
     * the grammar table
     * @param drawer the selection drawer of the new view
     * @param automaton the automaton that's being converted; note
     * that this will not be the exact object returned by
     * <CODE>getAutomaton</CODE> since a clone is made
     * @return the convert controller to handle the conversion of the
     * automaton to a grammar
     */
    protected ConvertController initializeController
	(ConvertPane pane, SelectionDrawer drawer, Automaton automaton) {
	return new FSAConvertController(pane, drawer,
					(FiniteStateAutomaton)automaton);
    }
    
    /**
     * This action is applicable only to
     * <CODE>FiniteStateAutomaton</CODE>s.
     * @param object the object to check for applicability
     * @return <CODE>true</CODE> if the object is an FSA,
     * <CODE>false</CODE> otherwise
     */
    public static boolean isApplicable(Object object) {
	return object instanceof FiniteStateAutomaton;
    }
}
