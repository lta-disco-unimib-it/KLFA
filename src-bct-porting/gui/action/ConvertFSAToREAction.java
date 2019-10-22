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
