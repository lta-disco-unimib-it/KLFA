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

import gui.deterministic.ConversionPane;
import gui.environment.Environment;
import gui.environment.tag.CriticalTag;

import java.awt.event.ActionEvent;

import javax.swing.JOptionPane;

import automata.AutomatonChecker;
import automata.fsa.FiniteStateAutomaton;

/**
 * This is a simple action for showing the DFA form of an NFA.
 * 
 * @author Thomas Finley
 */

public class NFAToDFAAction extends FSAAction {
    /**
     * Instantiates a new <CODE>NFAToDFAAction</CODE>.
     * @param automaton the automaton that input will be simulated on
     * @param environment the environment object that we shall add our
     * it.unimib.disco.lta.conFunkHealer.simulator pane to
     */
    public NFAToDFAAction(FiniteStateAutomaton automaton,
			  Environment environment) {
	super("Convert to DFA", null);
	this.automaton = automaton;
	this.environment = environment;
	/*putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke
	  (KeyEvent.VK_R, MAIN_MENU_MASK+InputEvent.SHIFT_MASK));*/
    }

    /**
     * Puts the DFA form in another window.
     * @param event the action event
     */
    public void actionPerformed(ActionEvent e) {
	if (automaton.getInitialState() == null) {
	    JOptionPane
		.showMessageDialog(environment, 
				   "The automaton needs an initial state.",
				   "No Initial State",
				   JOptionPane.ERROR_MESSAGE);
	    return;
	}

	AutomatonChecker ac = new AutomatonChecker();
	if(!ac.isNFA(automaton)) {
	    JOptionPane
		.showMessageDialog(environment,"This is not an NFA!",
				   "Not an NFA",
				   JOptionPane.ERROR_MESSAGE);
	    return;
	}
	
	ConversionPane convert = new ConversionPane
	    ((FiniteStateAutomaton)automaton.clone(),environment);
	environment.add(convert, "NFA to DFA", new CriticalTag() {});
	environment.setActive(convert);
    }

    /** The automaton. */
    private FiniteStateAutomaton automaton;
    /** The environment. */
    private Environment environment;
}
