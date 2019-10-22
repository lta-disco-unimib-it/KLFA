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
