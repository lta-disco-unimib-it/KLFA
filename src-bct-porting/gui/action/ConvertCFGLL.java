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

import grammar.Grammar;
import grammar.Production;
import grammar.cfg.CFGToPDALLConverter;
import gui.environment.GrammarEnvironment;
import gui.environment.Universe;
import gui.environment.tag.CriticalTag;
import gui.grammar.convert.ConvertPane;

import java.awt.event.ActionEvent;
import java.util.HashMap;

import javax.swing.JOptionPane;

import automata.graph.AutomatonGraph;
import automata.graph.GEMLayoutAlgorithm;
import automata.graph.LayoutAlgorithm;
import automata.pda.PushdownAutomaton;

/**
 * This is the action that initiates the conversion of a context free
 * grammar to a PDA using LL conversion.
 * 
 * @author Thomas Finley
 */

public class ConvertCFGLL extends GrammarAction {
    /**
     * Instantiates a new <CODE>ConvertCFGLL</CODE> action.
     * @param environment the grammar environment
     */
    public ConvertCFGLL(GrammarEnvironment environment) {
	super("Convert CFG to PDA (LL)", null);
	this.environment = environment;
    }

    /**
     * Performs the action.
     */
    public void actionPerformed(ActionEvent e) {
	Grammar grammar = environment.getGrammar();
	if (grammar == null) return;
	if (grammar.getProductions().length == 0) {
	    JOptionPane.showMessageDialog
		(Universe.frameForEnvironment(environment),
		 "The grammar should exist.");
	    return;
	}
	// Create the initial automaton.
	PushdownAutomaton pda = new PushdownAutomaton();
	CFGToPDALLConverter convert = new CFGToPDALLConverter();
	convert.createStatesForConversion(grammar, pda);
	// Create the map of productions to transitions.
	HashMap ptot = new HashMap();
	Production[] prods = grammar.getProductions();
	for (int i=0; i<prods.length; i++)
	    ptot.put(prods[i], convert.getTransitionForProduction(prods[i]));
	// Add the view to the environment.
	final ConvertPane cp = new ConvertPane(grammar, pda, ptot,
					       environment);
	environment.add(cp, "Convert to PDA (LL)", new CriticalTag() {});
	

	// Do the layout of the states.
	AutomatonGraph graph = new AutomatonGraph(pda);
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
