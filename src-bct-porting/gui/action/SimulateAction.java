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
import gui.environment.tag.CriticalTag;
import gui.sim.SimulatorPane;

import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

import automata.Automaton;
import automata.AutomatonSimulator;
import automata.Configuration;
import automata.SimulatorFactory;
import automata.turing.TMSimulator;
import automata.turing.TuringMachine;

/**
 * This is the action used for the stepwise simulation of data.  This
 * method can operate on any automaton.  It uses a special exception
 * for the two tape case.
 * 
 * @author Thomas Finley
 */

public class SimulateAction extends AutomatonAction {
    /**
     * Instantiates a new <CODE>SimulateAction</CODE>.
     * @param automaton the automaton that input will be simulated on
     * @param environment the environment object that we shall add our
     * it.unimib.disco.lta.conFunkHealer.simulator pane to
     */
    public SimulateAction(Automaton automaton, Environment environment) {
	super("Step...", null);
	if (SimulateNoClosureAction.isApplicable(automaton))
	    putValue(NAME, "Step with Closure...");
	putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke
		 (KeyEvent.VK_R, MAIN_MENU_MASK));
	this.automaton = automaton;
	this.environment = environment;
    }

    /**
     * Returns the it.unimib.disco.lta.conFunkHealer.simulator for this automaton.
     * @param automaton the automaton to get the it.unimib.disco.lta.conFunkHealer.simulator for
     * @return a it.unimib.disco.lta.conFunkHealer.simulator for this automaton
     */
    protected AutomatonSimulator getSimulator(Automaton automaton) {
	return SimulatorFactory.getSimulator(automaton);
    }

    /**
     * Given initial configurations, the it.unimib.disco.lta.conFunkHealer.simulator, and the automaton,
     * takes any further action that may be necessary.  In the case of
     * stepwise operation, which is the default, an additional tab is
     * added to the environment
     * @param automaton the automaton input is simulated on
     * @param it.unimib.disco.lta.conFunkHealer.simulator the automaton it.unimib.disco.lta.conFunkHealer.simulator for this automaton
     * @param configurations the initial configurations generated
     * @param initialInput the object that represents the initial
     * input; this is a String object in most cases, but may differ
     * for multiple tape turing machines
     */
    protected void handleInteraction(Automaton automaton,
				     AutomatonSimulator simulator,
				     Configuration[] configurations,
				     Object initialInput) {
	SimulatorPane simpane =
	    new SimulatorPane(automaton, simulator, configurations,
			      environment);
	if (initialInput instanceof String[])
	    initialInput = java.util.Arrays.asList((String[])initialInput);
	environment.add(simpane, "Simulate: "+initialInput,
			new CriticalTag() {});
	environment.setActive(simpane);
    }

    /**
     * This returns an object that encapsulates the user input for the
     * starting configuration.  In most cases this will be a string,
     * except in the case of a multiple tape Turing machine.  This
     * method will probably involve some prompt to the user.  By
     * default this method prompts the user using a dialog box and
     * returns the result from that dialog.
     * @param component a parent for whatever dialogs are brought up
     * @return the object that represents the initial input to the
     * machine, or <CODE>null</CODE> if the user elected to cancel
     */
    protected Object initialInput(Component component) {
	if (!(getAutomaton() instanceof automata.turing.TuringMachine))
	    return JOptionPane.showInputDialog(component, "Input?");
	// Do the multitape stuff.
	TuringMachine tm = (TuringMachine) getAutomaton();
	int tapes = tm.tapes();
	JPanel panel = new JPanel(new GridLayout(tapes, 2));
	JTextField[] fields = new JTextField[tapes];
	for (int i=0; i<tapes; i++) {
	    panel.add(new JLabel("Input "+(i+1)));
	    panel.add(fields[i] = new JTextField());
	}
	int result = JOptionPane.showOptionDialog
	    (component, panel, "Input?",
	     JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE,
	     null, null, null);
	if (result != JOptionPane.YES_OPTION &&
	    result != JOptionPane.OK_OPTION) return null;
	String[] input = new String[tapes];
	for (int i=0; i<tapes; i++) input[i] = fields[i].getText();
	return input;
    }

    /**
     * Performs the action.
     */
    public void actionPerformed(ActionEvent e) {
	if (automaton.getInitialState() == null) {
	    JOptionPane.showMessageDialog
		((Component)e.getSource(),
		 "Simulation requires an automaton\n"+"with an initial state!",
		 "No Initial State", JOptionPane.ERROR_MESSAGE);
	    return;
	}
	Object input = initialInput((Component)e.getSource());
	if (input == null) return;
	Configuration[] configs = null;
	AutomatonSimulator simulator = getSimulator(automaton);
	// Get the initial configurations.
	if (getAutomaton() instanceof TuringMachine) {
	    String[] s = (String[]) input;
	    configs = 
		((TMSimulator) simulator).getInitialConfigurations(s);
	} else {
	    String s = (String) input;
	    configs = simulator.getInitialConfigurations(s);
	}
	handleInteraction(automaton, simulator, configs, input);
    }

    /**
     * Simulate actions are applicable to every automaton which
     * accepts a single string of input, i.e., every automaton except
     * for dual tape turing machines.
     * @param object to object to test for applicability
     */
    public static boolean isApplicable(Object object) {
	return object instanceof Automaton;
	/* &&
	   !(object instanceof automata.ttm.TwoTapeTuringMachine);*/
    }

    /**
     * Returns the automaton.
     * @return the automaton
     */
    protected Automaton getAutomaton() {
	return automaton;
    }

    /**
     * Returns the environment.
     * @return the environment
     */
    protected Environment getEnvironment() {
	return environment;
    }
    
    /** The automaton this simulate action runs simulations on! */
    private Automaton automaton;
    /** The environment that the simulation pane will be put in. */
    private Environment environment;

    /** The two tape input. */
   
}
