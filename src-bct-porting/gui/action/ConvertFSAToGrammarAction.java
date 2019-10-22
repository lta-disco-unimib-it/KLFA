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
