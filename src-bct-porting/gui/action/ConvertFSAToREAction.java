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
import gui.environment.tag.CriticalTag;
import gui.regular.ConvertPane;

import java.awt.event.ActionEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import automata.fsa.FSAToRegularExpressionConverter;

/**
 * This action handles the conversion of an FSA to a regular
 * expression.
 * 
 * @author Thomas Finley
 */

public class ConvertFSAToREAction extends FSAAction {
    /**
     * Instantiates a new <CODE>ConvertFSAToREAction</CODE>.
     * @param environment the environment
     */
    public ConvertFSAToREAction(AutomatonEnvironment environment) {
	super("Convert FA to RE", null);
	this.environment = environment;
    }

    /**
     * This method begins the process of converting an automaton to a
     * regular expression.
     * @param event the action event
     */
    public void actionPerformed(ActionEvent event) {
	JFrame frame = Universe.frameForEnvironment(environment);
	if (environment.getAutomaton().getInitialState() == null) {
	    JOptionPane.showMessageDialog
		(frame,
		 "Conversion requires an automaton\nwith an initial state!",
		 "No Initial State", JOptionPane.ERROR_MESSAGE);
	    return;
	}
	if (environment.getAutomaton().getFinalStates().length == 0) {
	    JOptionPane.showMessageDialog
		(frame, "Conversion requires at least\n"+"one final state!",
		 "No Final States", JOptionPane.ERROR_MESSAGE);
	    return;
	}
	ConvertPane pane = new ConvertPane(environment);
	environment.add(pane, "Convert FA to RE", new CriticalTag() {});
	environment.setActive(pane);
    }

    /** The automaton environment. */
    private AutomatonEnvironment environment;
    /** The converter object. */
    private FSAToRegularExpressionConverter converter 
	= new FSAToRegularExpressionConverter();
}
