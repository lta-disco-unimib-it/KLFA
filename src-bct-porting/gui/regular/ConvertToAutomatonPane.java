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
 
package gui.regular;

import gui.editor.ArrowNontransitionTool;
import gui.editor.ToolBox;
import gui.environment.RegularEnvironment;
import gui.environment.Universe;
import gui.viewer.AutomatonDrawer;
import gui.viewer.AutomatonPane;
import gui.viewer.SelectionDrawer;

import java.awt.BorderLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.util.LinkedList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToolBar;

import regular.Discretizer;
import automata.State;
import automata.fsa.FSATransition;
import automata.fsa.FiniteStateAutomaton;

/**
 * This is the pane that holds the tools necessary for the conversion
 * of a regular expression to a finite state automaton.
 * 
 * @author Thomas Finley
 */

public class ConvertToAutomatonPane extends JPanel {
    /**
     * Creates a new conversion pane for the conversion of a regular
     * expression to an automaton.
     * @param environment the environment that this convert pane will
     * be a part of
     */
    public ConvertToAutomatonPane(RegularEnvironment environment) {
	this.environment = environment;
	JFrame frame = Universe.frameForEnvironment(environment);

	setLayout(new BorderLayout());

	JPanel labels = new JPanel(new BorderLayout());
	labels.add(mainLabel, BorderLayout.NORTH);
	labels.add(detailLabel, BorderLayout.SOUTH);
	mainLabel.setText(" ");
	detailLabel.setText(" ");
	
	add(labels, BorderLayout.NORTH);
	SelectionDrawer automatonDrawer = new SelectionDrawer(automaton);

	// Do the initialization of the automaton.
	State initialState = automaton.createState(new Point(60,40));
	State finalState = automaton.createState(new Point(450,250));
	automaton.setInitialState(initialState);
	automaton.addFinalState(finalState);
	FSATransition initialTransition =
	    new FSATransition
	    (initialState, finalState,
	     Discretizer.delambda(environment.getExpression()
				  .asString().replace('!', '\u03BB')));
	automaton.addTransition(initialTransition);
	
	controller = new REToFSAController(this, automaton);

	gui.editor.EditorPane ep = new gui.editor.EditorPane
	    (automatonDrawer, new ToolBox() {
		public List tools
		    (AutomatonPane view, AutomatonDrawer drawer) {
		    LinkedList tools = new LinkedList();
		    tools.add(new ArrowNontransitionTool(view, drawer));
		    tools.add(new RegularToAutomatonTransitionTool
			      (view, drawer, controller));
		    tools.add(new DeexpressionifyTransitionTool
			      (view, drawer, controller));
		    return tools;
		}
	    });

	JToolBar bar = ep.getToolBar();
	bar.addSeparator();
	bar.add(doStepAction);
	bar.add(doAllAction);
	bar.add(exportAction);
	//bar.add(exportAction2);

	add(ep, BorderLayout.CENTER);
    }
    
    /** The environment that holds the regular expression.  The
     * regular expression from the environment is itself not
     * modified. */
    RegularEnvironment environment;
    /** The automaton being built, which will be modified throughout
     * this process. */
    private FiniteStateAutomaton automaton = new FiniteStateAutomaton();
    /** The controller object. */
    private REToFSAController controller;

    /** The frame that holds the environment. */
    JFrame frame;
    /** The labels holding the current directions. */
    JLabel mainLabel = new JLabel();
    JLabel detailLabel = new JLabel();
    /** The actions. */
    AbstractAction doStepAction = new AbstractAction("Do Step") {
		public void actionPerformed(ActionEvent e) {
		    controller.completeStep();
		} };
    AbstractAction doAllAction = new AbstractAction("Do All") {
		public void actionPerformed(ActionEvent e) {
		    controller.completeAll();
		} };
    AbstractAction exportAction = new AbstractAction("Export") {
		public void actionPerformed(ActionEvent e) {
		    controller.export();
		} };
    AbstractAction exportAction2 = new AbstractAction("Export Now") {
		public void actionPerformed(ActionEvent e) {
		    controller.exportToTab();
		} };
}
