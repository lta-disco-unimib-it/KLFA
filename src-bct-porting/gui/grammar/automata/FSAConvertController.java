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
 
package gui.grammar.automata;

import grammar.Grammar;
import grammar.Production;
import grammar.reg.RegularGrammar;
import gui.viewer.SelectionDrawer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

import automata.State;
import automata.Transition;
import automata.fsa.FSAToRegularGrammarConverter;
import automata.fsa.FiniteStateAutomaton;

/**
 * This controls the conversion of a finite state automaton to a
 * regular grammar.
 * 
 * @author Thomas Finley
 */

public class FSAConvertController extends ConvertController {
    /**
     * Instantiates a <CODE>FSAConvertController</CODE> for an
     * automaton.
     * @param pane the convert pane that holds the automaton pane and
     * the grammar table
     * @param drawer the selection drawer where the automaton is made
     * @param automaton the automaton to build the
     * <CODE>FSAConvertController</CODE> for; this automaton should be
     * editable
     */
    public FSAConvertController(ConvertPane pane,
				SelectionDrawer drawer,
				FiniteStateAutomaton automaton) {
	super(pane, drawer, automaton);
	converter = new FSAToRegularGrammarConverter();
	converter.initializeConverter(automaton);
	fillMap();
	// Sets the labels.
	State[] states = automaton.getStates();
	for (int i=0; i<states.length; i++)
	    states[i].setLabel(converter.variableForState(states[i]));
    }

    /**
     * Returns the productions for a particular state.  This method
     * will only be called once.
     * @param state the state to get the productions for
     * @return an array containing the productions that correspond to
     * a particular state
     */
    protected Production[] getProductions(State state) {
	if (!getAutomaton().isFinalState(state)) return new Production[0];
	Production[] p = {converter.getLambdaProductionForFinalState
			  (getAutomaton(), state)};
	return p;
    }

    /**
     * Returns the productions for a particular transition.  This
     * method will only be called once.
     * @param transition the transition to get the productions for
     * @return an array containing the productions that correspond to
     * a particular transition
     */
    protected Production[] getProductions(Transition transition) {
	Production[] p = {converter.getProductionForTransition(transition)};
	return p;
    }

    /**
     * Returns the grammar that's the result of this conversion.
     * @return the grammar that's the result of this conversion
     */
    protected Grammar getGrammar() {
	int rows = getModel().getRowCount();
	RegularGrammar grammar = new RegularGrammar();
	grammar.setStartVariable("S");
	ArrayList productions = new ArrayList();
	for (int i=0; i<rows; i++) {
	    Production production = getModel().getProduction(i);
	    if (production == null) continue;
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
		    return p1.getLHS().compareTo(p2.getRHS());
		}
		public boolean equals(Object o) {return false;}
	    });
	Iterator it = productions.iterator();
	while (it.hasNext())
	    grammar.addProduction((Production) it.next());
	return grammar;
    }

    /** The converter object from which we get the productions. */
    private FSAToRegularGrammarConverter converter;
}
