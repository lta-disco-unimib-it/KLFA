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

import gui.editor.ArrowDisplayOnlyTool;
import gui.environment.Environment;
import gui.environment.tag.CriticalTag;
import gui.viewer.AutomatonPane;
import gui.viewer.SelectionDrawer;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.swing.JLabel;
import javax.swing.JPanel;

import automata.Automaton;
import automata.LambdaCheckerFactory;
import automata.LambdaTransitionChecker;
import automata.Transition;

/**
 * This is an action that will highlight all states that have lambda
 * transitions.
 * 
 * @author Thomas Finley
 */

public class LambdaHighlightAction extends AutomatonAction {
    public LambdaHighlightAction(Automaton automaton,
				 Environment environment) {
	super("Highlight Lambda Transitions", null);
	this.automaton = automaton;
	this.environment = environment;
    }

    public void actionPerformed(ActionEvent event) {
	Transition[] t = automaton.getTransitions();
	Set lambdas = new HashSet();
	LambdaTransitionChecker checker =
	    LambdaCheckerFactory.getLambdaChecker(automaton);
	for (int i=0; i<t.length; i++)
	    if (checker.isLambdaTransition(t[i]))
		lambdas.add(t[i]);
	
	// Create the selection drawer thingie.
	SelectionDrawer as = new SelectionDrawer(automaton);
	Iterator it = lambdas.iterator();
	while (it.hasNext()) {
	    Transition lt = (Transition) it.next();
	    as.addSelected(lt);
	}

	// Put that in the environment.
	LambdaPane pane = new LambdaPane(new AutomatonPane(as));
	environment.add(pane, "Lambda Transitions", new CriticalTag() {});
	environment.setActive(pane);
    }

    /**
     * A class that exists to make integration with the help system
     * feasible.
     */
    private class LambdaPane extends JPanel {
	public LambdaPane(AutomatonPane ap) {
	    super(new BorderLayout());
	    add(ap, BorderLayout.CENTER);
	    add(new JLabel("Lambda transitions are highlighted."),
		BorderLayout.NORTH);
	    ArrowDisplayOnlyTool tool =
		new ArrowDisplayOnlyTool(ap, ap.getDrawer());
	    ap.addMouseListener(tool);
	}
    }

    /** The automaton to find the lambda transitions of. */
    private Automaton automaton;
    /** The environment to add the pane with the highlighted lambdas to. */
    private Environment environment;
}
