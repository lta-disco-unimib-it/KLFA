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
 
package gui.action;

import gui.environment.Environment;
import gui.environment.EnvironmentFrame;
import gui.environment.Universe;

import java.awt.event.ActionEvent;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;

import automata.fsa.FiniteStateAutomaton;
import automata.graph.FSAEqualityChecker;

/**
 * This tests to see if two finite state automatons accept the same
 * language.
 * 
 * @author Thomas Finley
 */

public class DFAEqualityAction extends FSAAction {
    /**
     * Instantiates a new <CODE>DFAEqualityAction</CODE>.
     * @param automaton the automaton that input will be simulated on
     * @param environment the environment object that we shall add our
     * it.unimib.disco.lta.conFunkHealer.simulator pane to
     */
    public DFAEqualityAction(FiniteStateAutomaton automaton,
			     Environment environment) {
	super("Compare Equivalence", null);
	this.environment = environment;
	/*putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke
	  (KeyEvent.VK_R, MAIN_MENU_MASK+InputEvent.SHIFT_MASK));*/
    }

    /**
     * Runs a comparison with another automaton.
     * @param event the action event
     */
    public void actionPerformed(ActionEvent e) {
	JComboBox combo = new JComboBox();
	// Figure out what existing environments in the program have
	// the type of structure that we need.
	EnvironmentFrame[] frames = Universe.frames();
	for (int i=0; i<frames.length; i++) {
	    if (!isApplicable(frames[i].getEnvironment().getObject())
		|| frames[i].getEnvironment() == environment) continue;
	    combo.addItem(frames[i]);
	}
	// Set up our automaton.
	FiniteStateAutomaton automaton =
	    (FiniteStateAutomaton) environment.getObject();

	if (combo.getItemCount() == 0) {
	    JOptionPane
		.showMessageDialog(Universe.frameForEnvironment(environment),
				   "No other FAs around!");
	    return;
	}
	if (automaton.getInitialState() == null) {
	    JOptionPane
		.showMessageDialog(Universe.frameForEnvironment(environment),
				   "This automaton has no initial state!");
	    return;
	}
	// Prompt the user.
	int result = JOptionPane.showOptionDialog
	    (Universe.frameForEnvironment(environment),
	     combo, "Compare against FA",
	     JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE,
	     null, null, null);
	if (result != JOptionPane.YES_OPTION &&
	    result != JOptionPane.OK_OPTION) return;
	FiniteStateAutomaton other = (FiniteStateAutomaton) 
	    ((EnvironmentFrame) combo.getSelectedItem())
	    .getEnvironment().getObject();
	if (other.getInitialState() == null) {
	    JOptionPane.showMessageDialog
		(Universe.frameForEnvironment(environment),
		 "The other automaton has no initial state!");
	    return;
	}
	String checkedMessage = checker.equals(other, automaton) ?
	    "They ARE equivalent!" : "They AREN'T equivalent!";
	JOptionPane
	    .showMessageDialog(Universe.frameForEnvironment(environment),
			       checkedMessage);
    }

    /** The environment. */
    private Environment environment;
    /** The equality checker. */
    private static FSAEqualityChecker checker = new FSAEqualityChecker();
}
