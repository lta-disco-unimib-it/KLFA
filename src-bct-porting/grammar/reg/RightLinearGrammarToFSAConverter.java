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
 
package grammar.reg;

import grammar.Grammar;
import grammar.GrammarToAutomatonConverter;
import grammar.Production;
import grammar.ProductionChecker;

import java.awt.Point;

import automata.Automaton;
import automata.State;
import automata.StatePlacer;
import automata.Transition;
import automata.fsa.FSATransition;

/**
 * The right linear grammar converter can be used to convert regular
 * grammars, specifically right-linear grammars, to their 
 * equivalent finite state automata.  You can do the conversion all
 * at once by calling convertToAutomaton (a method inherited from
 * super class GrammarToAutomatonConverter), or you can do the 
 * conversion step by step by first calling createStatesForConversion
 * to create a state for each variable in the grammar. Then, one by one,
 * for each Production rule in the grammar, you can call 
 * getTransitionForProduction and add the returned transition to the
 * fsa that you are building.  Once you do this for each production
 * in the grammar, you will have the equivalent fsa.
 *
 * @author Ryan Cavalcante
 */

public class RightLinearGrammarToFSAConverter 
    extends GrammarToAutomatonConverter {
    /**
     * Creates an instance of <CODE>RightLinearGrammarToFSAConverter</CODE>.
     */
    public RightLinearGrammarToFSAConverter() {
	
    }

   /**
     * Returns the transition created by converting 
     * <CODE>production</CODE> to its equivalent transition.
     * @param production the production
     * @return the equivalent transition.
     */
    public Transition getTransitionForProduction(Production production) {
	String lhs = production.getLHS();
	State from = getStateForVariable(lhs);
	
	/** if of the form A->xB */
	if(ProductionChecker.isRightLinearProductionWithVariable(production)) {
	    String[] variables = production.getVariablesOnRHS();
	    String variable = variables[0];
	    State to = getStateForVariable(variable);
	    String rhs = production.getRHS();
	    String label = rhs.substring(0, rhs.length()-1);
	    FSATransition trans = new FSATransition(from, to, label); 
	    return trans;
	}
	/** if of the form A->x */
	else if(ProductionChecker.isLinearProductionWithNoVariable(production)) {
	    String transLabel = production.getRHS();
	    State finalState = getStateForVariable(FINAL_STATE);
	    FSATransition ftrans = new FSATransition(from, finalState,
						     transLabel); 
	    return ftrans;
	}
	return null;
    }

    /**
     * Adds all states to <CODE>automaton</CODE> necessary for the 
     * conversion of <CODE>grammar</CODE> to its equivalent
     * automaton.  This creates a state for each variable in 
     * <CODE>grammar</CODE> and maps each created state to the
     * variable it was created for by calling mapStateToVariable.
     * @param grammar the grammar being converted.
     * @param automaton the automaton being created.
     */
    public void createStatesForConversion(Grammar grammar, 
					  Automaton automaton) {
	initialize();
	StatePlacer sp = new StatePlacer();
	String[] variables = grammar.getVariables();
	for(int k = 0; k < variables.length; k++) {
	    String variable = variables[k];
	    Point point = sp.getPointForState(automaton);
	    State state = automaton.createState(point);
	    if(variable.equals(grammar.getStartVariable())) 
		automaton.setInitialState(state);
	    state.setLabel(variable);
	    mapStateToVariable(state,variable);
	}
	
	Point pt = sp.getPointForState(automaton);
	State finalState = automaton.createState(pt);
	automaton.addFinalState(finalState);
	mapStateToVariable(finalState, FINAL_STATE);
    }
    
    protected String FINAL_STATE = "FINAL";
    
}
