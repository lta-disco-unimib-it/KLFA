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
