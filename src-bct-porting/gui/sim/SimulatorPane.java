/*******************************************************************************
 *    Copyright 2019 Fabrizio Pastore, Leonardo Mariani
 *   
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 *******************************************************************************/
 
package gui.sim;

import gui.SplitPaneFactory;
import gui.editor.ArrowDisplayOnlyTool;
import gui.environment.Environment;
import gui.viewer.AutomatonPane;
import gui.viewer.SelectionDrawer;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import automata.Automaton;
import automata.AutomatonSimulator;
import automata.Configuration;

/**
 * The <CODE>SimulatorPane</CODE> is the main view for the GUI front
 * end for the simulation of input on automatons.  The automaton has
 * two major subviews: a section for the drawing of the automaton, and
 * a section for the list of machine configurations.
 * 
 * @see automata.Automaton
 * @see automata.Configuration
 * @see automata.AutomatonSimulator
 * 
 * @author Thomas Finley
 */

public class SimulatorPane extends JPanel {
    /**
     * Instantiates a it.unimib.disco.lta.conFunkHealer.simulator pane for a given automaton, and a
     * given input string.
     * @param automaton the automaton to create the it.unimib.disco.lta.conFunkHealer.simulator pane for
     * @param it.unimib.disco.lta.conFunkHealer.simulator the automaton it.unimib.disco.lta.conFunkHealer.simulator which we step through
     * the automaton on
     * @param configurations the initial configurations that this
     * it.unimib.disco.lta.conFunkHealer.simulator should start with
     * @param env the environment this it.unimib.disco.lta.conFunkHealer.simulator pane will be added to
     */
    public SimulatorPane(Automaton automaton, AutomatonSimulator simulator,
			 Configuration[] configurations, Environment env) {
	this.automaton = automaton;
	this.simulator = simulator;
	initView(configurations, env);
    }

    /**
     * Initiates the views, or in general, sets up the GUI.
     * @param configs an array of the initial configuration for this
     * it.unimib.disco.lta.conFunkHealer.simulator
     * @param env the environment the it.unimib.disco.lta.conFunkHealer.simulator pane will be added to
     */
    private void initView(Configuration[] configs, final Environment env) {
	this.setLayout(new BorderLayout());
	// Set up the main display.
	SelectionDrawer drawer = new SelectionDrawer(automaton);
	AutomatonPane display = new AutomatonPane(drawer, true);
	// Add the listener to the display.
	ArrowDisplayOnlyTool arrow = new ArrowDisplayOnlyTool(display,drawer);
	display.addMouseListener(arrow);

	// Initialize the lower display.
	JPanel lower = new JPanel();
	lower.setLayout(new BorderLayout());

	// Initialize the scroll pane for the configuration view.
	JScrollPane scroller =
	    new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
			    JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

	// Set up the configurations pane.
	ConfigurationPane configurations = new ConfigurationPane(automaton);
	configurations.setLayout(new GridLayout(0, 4));
	for (int i=0; i<configs.length; i++)
	    configurations.add(configs[i]);

	// Set up the bloody controller device.
	final ConfigurationController controller =
	    new ConfigurationController(configurations, simulator,
					drawer, display);
	env.addChangeListener(new ChangeListener() {
		public void stateChanged(ChangeEvent e) {
		    if (env.contains(SimulatorPane.this))
			return;
		    env.removeChangeListener(this);
		    controller.cleanup();
		}
	    });
	ControlPanel controlPanel = new ControlPanel(controller);
	// Set up the lower display.
	scroller.getViewport().setView(configurations);
	lower.add(scroller, BorderLayout.CENTER);
	lower.add(controlPanel, BorderLayout.SOUTH);
	
	// Set up the main view.
	JSplitPane split = SplitPaneFactory.createSplit
	    (env, false, .6, display, lower);
	this.add(split, BorderLayout.CENTER);
    }
    
    /** The automaton that this it.unimib.disco.lta.conFunkHealer.simulator pane is simulating. */
    private Automaton automaton;
    /** The it.unimib.disco.lta.conFunkHealer.simulator */
    private AutomatonSimulator simulator;
}
