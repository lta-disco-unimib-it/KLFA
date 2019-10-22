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

import gui.SplitPaneFactory;
import gui.editor.ArrowDisplayOnlyTool;
import gui.environment.Environment;
import gui.environment.Universe;
import gui.environment.tag.CriticalTag;
import gui.sim.multiple.InputTableModel;
import gui.viewer.AutomatonPane;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JToolBar;

import automata.Automaton;
import automata.AutomatonSimulator;
import automata.Configuration;
import automata.SimulatorFactory;
import automata.turing.TMSimulator;
import automata.turing.TuringMachine;

/**
 * This is the action used for the simulation of input on an automaton
 * with no interaction.  This method can operate on any automaton.  It
 * uses a special exception for the two tape case.
 * 
 * @author Thomas Finley
 */

public class MultipleSimulateAction extends NoInteractionSimulateAction {
    /**
     * Instantiates a new <CODE>SimulateAction</CODE>.
     * @param automaton the automaton that input will be simulated on
     * @param environment the environment object that we shall add our
     * it.unimib.disco.lta.conFunkHealer.simulator pane to
     */
    public MultipleSimulateAction(Automaton automaton,
				  Environment environment) {
	super(automaton, environment);
	putValue(NAME, "Multiple Run...");
	//putValue(ACCELERATOR_KEY, null);
    }

    /**
     * This will search configurations for an accepting configuration.
     * @param automaton the automaton input is simulated on
     * @param it.unimib.disco.lta.conFunkHealer.simulator the automaton it.unimib.disco.lta.conFunkHealer.simulator for this automaton
     * @param configurations the initial configurations generated
     * @param initialInput the object that represents the initial
     * input; this is a String object in most cases, but may differ
     * for multiple tape turing machines
     * @return <CODE>0</CODE> if this was an accept, <CODE>1</CODE> if
     * reject, and <CODE>2</CODE> if the user cancelled the run
     */
    protected int handleInput(Automaton automaton,
			      AutomatonSimulator simulator,
			      Configuration[] configs,
			      Object initialInput) {
	JFrame frame = Universe.frameForEnvironment(getEnvironment());
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
		if (!confirmContinue(numberGenerated, frame)) return 2;
		while (numberGenerated >= warningGenerated)
		    warningGenerated *= 2;
	    }
	    // Get the next batch of configurations.
	    ArrayList next = new ArrayList();
	    for (int i=0; i<configs.length; i++) {
		if (configs[i].isAccept()) {
		    numberAccepted++;
		    return 0;
		} else {
		    next.addAll(simulator.stepConfiguration(configs[i]));
		}
	    }
	    configs = (Configuration[]) next.toArray(new Configuration[0]);
	}
	if (numberAccepted == 0) {
	    //JOptionPane.showMessageDialog(frame, "The input was rejected.");
	    return 1;
	}
	return 0;
    }

    /**
     * Provides an initialized multiple input table object.
     * @param automaton the automaton to provide the multiple input
     * table for
     * @return a table object for this automaton
     */
    protected JTable initializeTable(Automaton automaton) {
	JTable table = new JTable(InputTableModel.getModel((getAutomaton())));
	table.setShowGrid(true);
	table.setGridColor(Color.lightGray);
	return table;
    }

    public void actionPerformed(ActionEvent e) {
	if (getAutomaton().getInitialState() == null) {
	    JOptionPane.showMessageDialog
		((Component)e.getSource(),
		 "Simulation requires an automaton\n"+"with an initial state!",
		 "No Initial State", JOptionPane.ERROR_MESSAGE);
	    return;
	}

	final JTable table = initializeTable(getAutomaton());
	JPanel panel = new JPanel(new BorderLayout());
	JToolBar bar = new JToolBar();
	panel.add(new JScrollPane(table), BorderLayout.CENTER);
	panel.add(bar, BorderLayout.SOUTH);
	// Add the running input thing.
	bar.add(new AbstractAction("Run Inputs") {
		public void actionPerformed(ActionEvent e) {
		    try {
			// Make sure any recent changes are registered.
			table.getCellEditor().stopCellEditing();
		    } catch (NullPointerException exception) {
			// We weren't editing anything, so we're OK.
		    }
		    InputTableModel model = (InputTableModel)table.getModel();
		    AutomatonSimulator simulator =
			SimulatorFactory.getSimulator(getAutomaton());
		    String[][] inputs = model.getInputs();
		    for (int r=0; r<inputs.length; r++) {
			Configuration[] configs = null;
			Object input = null;
			// Is this two tape?
			if (getAutomaton() instanceof TuringMachine) {
			    configs = ((TMSimulator)simulator)
				.getInitialConfigurations(inputs[r]);
			    input = inputs[r];
			} else { // If it's NOT two tape...
			    configs = simulator.getInitialConfigurations
				(inputs[r][0]);
			    input = inputs[r][0];
			}
			int result=handleInput(getAutomaton(), simulator,
					       configs, input);
			model.setResult(r, RESULT[result]);
		    }
		}
	    });
	// Add the clear button.
	bar.add(new AbstractAction("Clear") {
		public void actionPerformed(ActionEvent e) {
		    try {
			// Make sure any recent changes are registered.
			table.getCellEditor().stopCellEditing();
		    } catch (NullPointerException exception) {
			// We weren't editing anything, so we're OK.
		    }
		    InputTableModel model = (InputTableModel)table.getModel();
		    model.clear();
		}
	    });
	bar.add(new AbstractAction("Enter Lambda") {
		public void actionPerformed(ActionEvent e) {
		    int row = table.getSelectedRow();
		    if (row == -1) return;
		    for (int column=0; column<table.getColumnCount()-1;
			 column++)
			table.getModel().setValueAt("", row, column);
		}
	    });

	// Set up the final view.
	AutomatonPane ap = new AutomatonPane(getAutomaton());
	ap.addMouseListener(new ArrowDisplayOnlyTool(ap, ap.getDrawer()));
	JSplitPane split = SplitPaneFactory.createSplit
	    (getEnvironment(), true, 0.5, ap, panel);
	MultiplePane mp = new MultiplePane(split);
	getEnvironment().add(mp, "Multiple Inputs", new CriticalTag(){});
	getEnvironment().setActive(mp);
    }

    /**
     * This auxillary class is convenient so that the help system can
     * easily identify what type of component is active according to
     * its class.
     */
    private class MultiplePane extends JPanel {
	public MultiplePane(JSplitPane split) {
	    super(new BorderLayout());
	    add(split, BorderLayout.CENTER);
	}
    }

    private static String[] RESULT = {"Accept", "Reject", "Cancelled"};
    private static Color[] RESULT_COLOR = {Color.green, Color.red,
					   Color.black};
}
