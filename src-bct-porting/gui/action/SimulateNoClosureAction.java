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

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.Serializable;

import javax.swing.KeyStroke;

import automata.Automaton;
import automata.AutomatonSimulator;
import automata.fsa.FiniteStateAutomaton;
import automata.pda.PushdownAutomaton;

/**
 * This is the action used for the stepwise simulation of data without
 * closure, that is, without lambda transitions being automatically
 * traversed.
 * 
 * @author Thomas Finley
 */

public class SimulateNoClosureAction extends SimulateAction {
    /**
     * Instantiates a new <CODE>SimulateNoClosureAction</CODE>.
     * @param automaton the automaton that input will be simulated on
     * @param environment the environment object that we shall add our
     * it.unimib.disco.lta.conFunkHealer.simulator pane to
     */
    public SimulateNoClosureAction(Automaton automaton,
			  Environment environment) {
	super(automaton, environment);
	putValue(NAME, "Step by State...");
	putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke
		 (KeyEvent.VK_R, MAIN_MENU_MASK+InputEvent.SHIFT_MASK));
    }

    /**
     * Returns the it.unimib.disco.lta.conFunkHealer.simulator for this automaton.
     * @param automaton the automaton to get the it.unimib.disco.lta.conFunkHealer.simulator for
     * @return a it.unimib.disco.lta.conFunkHealer.simulator for this automaton
     */
    protected AutomatonSimulator getSimulator(Automaton automaton) {
	if (automaton instanceof automata.fsa.FiniteStateAutomaton)
	    return new automata.fsa.FSAStepByStateSimulator(automaton);
	else
	    return new automata.pda.PDAStepByStateSimulator(automaton);
    }

    /**
     * This particular action may only be applied to finite state
     * automata.
     * @param object the object to test for applicability
     * @return <CODE>true</CODE> if the passed in object is a finite
     * state automaton, <CODE>false</CODE> otherwise
     */
    public static boolean isApplicable(Serializable object) {
	return object instanceof FiniteStateAutomaton ||
	    object instanceof PushdownAutomaton;
    }
}
