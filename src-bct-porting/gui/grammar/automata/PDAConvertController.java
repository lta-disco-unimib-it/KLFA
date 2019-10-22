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
 
package gui.grammar.automata;

import grammar.Grammar;
import grammar.Production;
import grammar.cfg.ContextFreeGrammar;
import gui.viewer.SelectionDrawer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

import automata.State;
import automata.Transition;
import automata.pda.PDAToCFGConverter;
import automata.pda.PushdownAutomaton;

/**
 * This controls the conversion of a push down automaton to a context
 * free grammar.
 * 
 * @author Thomas Finley
 */

public class PDAConvertController extends ConvertController {
    /**
     * Instantiates a <CODE>PDAConvertController</CODE> for an
     * automaton.
     * @param pane the convert pane that holds the automaton pane and
     * the grammar table
     * @param drawer the selection drawer where the automaton is made
     * @param automaton the automaton to build the
     * <CODE>PDAConvertController</CODE> for
     */
    public PDAConvertController(ConvertPane pane,
				SelectionDrawer drawer,
				PushdownAutomaton automaton) {
	super(pane, drawer, automaton);
	converter = new PDAToCFGConverter();
	converter.initializeConverter();
	fillMap();
    }

    /**
     * Returns the productions for a particular state.  This method
     * will only be called once.
     * @param state the state to get the productions for
     * @return an array containing the productions that correspond to
     * a particular state
     */
    protected Production[] getProductions(State state) {
	return new Production[0];
    }

    /**
     * Returns the productions for a particular transition.  This
     * method will only be called once.
     * @param transition the transition to get the productions for
     * @return an array containing the productions that correspond to
     * a particular transition
     */
    protected Production[] getProductions(Transition transition) {
	return (Production[]) converter.createProductionsForTransition
	    (transition, getAutomaton()).toArray(new Production[0]);
    }

    /**
     * Returns the grammar that's the result of this conversion.
     * @return the grammar that's the result of this conversion
     * @throws GrammarCreationException if there are not enough
     * variables to uniquely identify every variable here
     */
    protected Grammar getGrammar() {
	if (converter.numberVariables() > 26)
	    throw new GrammarCreationException
		("There are more variables than can be uniquely represented.");
	int rows = getModel().getRowCount();
	ContextFreeGrammar grammar = new ContextFreeGrammar();
	grammar.setStartVariable("S");
	ArrayList productions = new ArrayList();
	for (int i=0; i<rows; i++) {
	    Production production = getModel().getProduction(i);
	    if (production == null) continue;
	    production = converter.getSimplifiedProduction(production);
	    productions.add(production);
	}
	Collections.sort(productions, new Comparator() {
		public int compare(Object o1, Object o2) {
		    Production p1 = (Production) o1, p2 = (Production) o2;
		    if ("S".equals(p1.getLHS())) {
			if (p1.getLHS().equals(p2.getLHS())) return 0;
			else return -1;
		    }
		    if ("S".equals(p2.getLHS())) return 1;
		    return p2.getLHS().compareTo(p1.getRHS());
		}
		public boolean equals(Object o) {return false;}
	    });
	Iterator it = productions.iterator();
	while (it.hasNext())
	    grammar.addProduction((Production) it.next());
	return grammar;
    }

    /** The converter object from which we get the productions. */
    private PDAToCFGConverter converter;
}
