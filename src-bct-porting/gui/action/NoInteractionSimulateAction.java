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

import gui.environment.Environment;
import gui.environment.Universe;

import java.awt.Component;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import automata.Automaton;
import automata.AutomatonSimulator;
import automata.Configuration;

/**
 * This is the action used for the simulation of input on an automaton
 * with no interaction.  This method can operate on any automaton.  It
 * uses a special exception for the two tape case.
 * 
 * @author Thomas Finley
 */

public class NoInteractionSimulateAction extends SimulateAction {
    /**
     * Instantiates a new <CODE>SimulateAction</CODE>.
     * @param automaton the automaton that input will be simulated on
     * @param environment the environment object that we shall add our
     * it.unimib.disco.lta.conFunkHealer.simulator pane to
     */
    public NoInteractionSimulateAction(Automaton automaton,
				       Environment environment) {
	super(automaton, environment);
	putValue(NAME, "Fast Run...");
	putValue(ACCELERATOR_KEY, null);
	this.environment = environment;
    }

    /**
     * Reports a configuration that accepted.
     * @param configuration the configuration that accepted
     * @param component the parent component of dialogs brought up
     * @return <CODE>true</CODE> if we should continue searching, or
     * <CODE>false</CODE> if we should halt
     */
    protected boolean reportConfiguration(Configuration configuration,
					  Component component) {
	JComponent past = (JComponent)
	    gui.sim.TraceWindow.getPastPane(configuration);
	past.setPreferredSize(new java.awt.Dimension(300, 400));
	String[] options = {"Keep looking", "I'm done"};
	int result = JOptionPane.showOptionDialog
	    (component, past, "Accepting configuration found!",
	     JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE,
	     null, options, null);
	return result==0;
    }

    /**
     * Confirms if the user wants to keep searching.  This should be
     * called periodically to give the user a chance to break out of
     * infinite loops.
     * @param generated the number of configurations generated sofar
     * @param component the parent component of dialogs brought up
     * @return <CODE>true</CODE> if we should continue searching, or
     * <CODE>false</CODE> if we should halt
     */
    protected boolean confirmContinue(int generated, Component component) {
	int result = JOptionPane.showConfirmDialog
	    (component, generated+" configurations have been generated.  "+
	     "Should we continue?");
	return result == JOptionPane.YES_OPTION;
    }

    /**
     * This will search configurations for an accepting configuration.
     * @param automaton the automaton input is simulated on
     * @param it.unimib.disco.lta.conFunkHealer.simulator the automaton it.unimib.disco.lta.conFunkHealer.simulator for this automaton
     * @param configurations the initial configurations generated
     * @param initialInput the object that represents the initial
     * input; this is a String object in most cases, but may differ
     * for multiple tape turing machines
     */
    protected void handleInteraction(Automaton automaton,
				     AutomatonSimulator simulator,
				     Configuration[] configs,
				     Object initialInput) {
	JFrame frame = Universe.frameForEnvironment(environment);
	// How many configurations have we had?
	int numberGenerated = 0;
	// When should the next warning be?
	int warningGenerated = WARNING_STEP;
	// How many have accepted?
	int numberAccepted = 0;
	while (configs.length > 0) {
	    numberGenerated += configs.length;
	    // Make sure we should continue.
	    if (numberGenerated >= warningGenerated) {
		if (!confirmContinue(numberGenerated, frame)) return;
		while (numberGenerated >= warningGenerated)
		    warningGenerated *= 2;
	    }
	    // Get the next batch of configurations.
	    ArrayList next = new ArrayList();
	    for (int i=0; i<configs.length; i++) {
		if (configs[i].isAccept()) {
		    numberAccepted++;
		    if (!reportConfiguration(configs[i], frame)) return;
		} else {
		    next.addAll(simulator.stepConfiguration(configs[i]));
		}
	    }
	    configs = (Configuration[]) next.toArray(new Configuration[0]);
	}
	if (numberAccepted == 0) {
	    JOptionPane.showMessageDialog(frame, "The input was rejected.");
	    return;
	}
	JOptionPane.showMessageDialog
	    (frame, numberAccepted+" configuration"+(numberAccepted==1?"":"s")
	     +" accepted, and\nother possibilities are exhausted.");
    }

    /** The environment. */
    private Environment environment = null;
    /** The steps in warnings. */
    protected static final int WARNING_STEP = 500;
}
