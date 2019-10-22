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

import grammar.Production;
import grammar.reg.RightLinearGrammar;
import grammar.reg.RightLinearGrammarToFSAConverter;
import gui.environment.GrammarEnvironment;
import gui.environment.Universe;
import gui.environment.tag.CriticalTag;
import gui.grammar.convert.ConvertPane;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.util.HashMap;

import javax.swing.JOptionPane;

import automata.Transition;
import automata.fsa.FiniteStateAutomaton;
import automata.graph.AutomatonGraph;
import automata.graph.GEMLayoutAlgorithm;
import automata.graph.LayoutAlgorithm;

/**
 * This is the action that initiates the conversion of right linear
 * grammar to an FSA.
 * 
 * @see gui.grammar.convert.ConvertPane
 * @see grammar.reg.RightLinearGrammarToFSAConverter
 * 
 * @author Thomas Finley
 */

public class ConvertRegularGrammarToFSA extends GrammarAction {
    /**
     * Instantiates a new <CODE>GrammarOutputAction</CODE>.
     * @param environment the grammar environment
     */
    public ConvertRegularGrammarToFSA(GrammarEnvironment environment) {
	super("Convert Right-Linear Grammar to FA", null);
	this.environment = environment;
    }

    /**
     * Performs the action.
     */
    public void actionPerformed(ActionEvent e) {
	// Construct the regular grammar.
	RightLinearGrammar grammar =
	    (RightLinearGrammar) environment.getGrammar
	    (RightLinearGrammar.class);
	if (grammar == null) return;
	if (grammar.getProductions().length == 0) {
	    JOptionPane.showMessageDialog
		(Universe.frameForEnvironment(environment),
		 "The grammar should exist.");
	    return;
	}

	// Create the initial automaton.
	FiniteStateAutomaton fsa = new FiniteStateAutomaton();
	RightLinearGrammarToFSAConverter convert =
	    new RightLinearGrammarToFSAConverter();
	convert.createStatesForConversion(grammar, fsa);
	AutomatonGraph graph = new AutomatonGraph(fsa);
	// Create the map of productions to transitions.
	HashMap ptot = new HashMap();
	Production[] prods = grammar.getProductions();
	for (int i=0; i<prods.length; i++) {
	    Transition t = convert.getTransitionForProduction(prods[i]);
	    graph.addEdge(t.getFromState(), t.getToState());
	    ptot.put(prods[i], t);
	}
	// Add the view to the environment.
	final ConvertPane cp = new ConvertPane(grammar, fsa, ptot, environment);
	environment.add(cp, "Convert to FA", new CriticalTag() {});
	Rectangle r = cp.getEditorPane().getAutomatonPane().getVisibleRect();
	LayoutAlgorithm layout = new GEMLayoutAlgorithm();
	layout.layout(graph, null);
	graph.moveAutomatonStates();
	environment.setActive(cp);
	environment.validate();
	cp.getEditorPane().getAutomatonPane().fitToBounds(20);
    }

    /** The grammar environment. */
    private GrammarEnvironment environment;
}
