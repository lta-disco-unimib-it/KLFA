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
import gui.environment.EnvironmentFrame;
import gui.environment.Universe;
import gui.grammar.automata.ConvertController;
import gui.grammar.automata.ConvertPane;
import gui.grammar.automata.PDAConvertController;
import gui.viewer.SelectionDrawer;
import gui.viewer.ZoomPane;

import java.awt.BorderLayout;
import java.util.HashSet;
import java.util.Iterator;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

import automata.Automaton;
import automata.State;
import automata.Transition;
import automata.pda.PDAToCFGConverter;
import automata.pda.PDATransition;
import automata.pda.PushdownAutomaton;

/**
 * This action handles the conversion of an PDA to a context free grammar.
 * 
 * @author Thomas Finley
 */

public class ConvertPDAToGrammarAction
    extends ConvertAutomatonToGrammarAction {
    /**
     * Instantiates a new <CODE>ConvertFSAToGrammarAction</CODE>.
     * @param environment the environment
     */
    public ConvertPDAToGrammarAction(AutomatonEnvironment environment) {
	super(environment);
    }

    /**
     * Checks the PDA to make sure it's ready to be converted.
     */
    protected boolean checkAutomaton() {
	EnvironmentFrame frame =
	    Universe.frameForEnvironment(getEnvironment());
	JPanel messagePanel = new JPanel(new BorderLayout());
	SelectionDrawer drawer = new SelectionDrawer(getAutomaton());
	JLabel messageLabel = new JLabel();
	ZoomPane zoom = new ZoomPane(drawer);
	JPanel tempPanel = new JPanel(new BorderLayout());
	tempPanel.setBorder(new BevelBorder(BevelBorder.LOWERED));
	zoom.setPreferredSize(new java.awt.Dimension(300,200));
	tempPanel.add(zoom, BorderLayout.CENTER);
	messagePanel.add(tempPanel, BorderLayout.CENTER);
	messagePanel.add(messageLabel, BorderLayout.SOUTH);
	// Check the final states.
	State[] finalStates = getAutomaton().getFinalStates();
	if (finalStates.length != 1) {
	    JOptionPane.showMessageDialog
		(frame, "There must be exactly one final state!",
		 "Final State Error", JOptionPane.ERROR_MESSAGE);
	    return false;
	}
	// Are all transitions to the final state okay?
	Transition[] toFinal =
	    getAutomaton().getTransitionsToState(finalStates[0]);
	HashSet bad = new HashSet();
	for (int i=0; i<toFinal.length; i++) {
	    PDATransition t = (PDATransition) toFinal[i];
	    if (!t.getStringToPop().equals("Z"))
		bad.add(t);
	}
	if (bad.size() != 0) {
	    drawer.clearSelected();
	    Iterator it = bad.iterator();
	    while (it.hasNext()) drawer.addSelected((Transition)it.next());
	    messageLabel.setText("Transitions to final must pop only 'Z'.");
	    JOptionPane.showMessageDialog
		(frame, messagePanel, "Final Transitions Error",
		 JOptionPane.ERROR_MESSAGE);
	    return false;
	}
	// Are the transitions okay?
	Transition[] transitions = getAutomaton().getTransitions();
	bad.clear();
	for (int i=0; i<transitions.length; i++) {
	    PDATransition t = (PDATransition) transitions[i];
	    if (//t.getInputToRead().length() != 1 ||
		t.getStringToPop().length() != 1 ||
		(t.getStringToPush().length() != 2 &&
		 t.getStringToPush().length() != 0))
		bad.add(t);
	}
	if (bad.size() != 0) {
	    drawer.clearSelected();
	    Iterator it = bad.iterator();
	    while (it.hasNext()) drawer.addSelected((Transition)it.next());
	    messageLabel.setText
		("Transitions must pop 1 and push 0 or 2.");
	    JOptionPane.showMessageDialog
		(frame, messagePanel, "Transitions Error",
		 JOptionPane.ERROR_MESSAGE);
	    return false;
	}
	return true;
    }

    /**
     * This object is only applicable to pushdown automatons.
     * @param object the object to test
     * @return <CODE>true</CODE> if the object is a pushdown
     * automaton, <CODE>false</CODE> otherwise
     */
    public static boolean isApplicable(Object object) {
	return object instanceof PushdownAutomaton;
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
	return new PDAConvertController
	    (pane, drawer, (PushdownAutomaton)automaton);
    }

    /** The environment this action is part of. */
    private AutomatonEnvironment environment;
    /** The automaton to convert. */
    private PushdownAutomaton automaton;
    /** The grammar converter. */
    private PDAToCFGConverter converter =
	new PDAToCFGConverter();
}
