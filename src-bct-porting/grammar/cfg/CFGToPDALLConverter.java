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
 
package grammar.cfg;

import grammar.Grammar;
import grammar.GrammarToAutomatonConverter;
import grammar.Production;
import automata.Automaton;
import automata.State;
import automata.StatePlacer;
import automata.Transition;
import automata.pda.PDATransition;

/**
 * The CFG to PDA (LL parsing) converter can be used to convert
 * a context free grammar to a pushdown automaton that can be
 * used for LL parsing.  You can do the conversion simply by
 * calling convertToAutomaton, or you can do the conversion step
 * by step by first calling createStatesForConversion, which will
 * create all the states in the pushdown automaton necessary for 
 * the conversion, and then calling getTransitionForProduction for
 * each production in the grammar.  You must of course add each 
 * Transition returned by this call to your pushdown automaton.  
 * When you have done this for each production in your grammar, the
 * equivalent PDA will be complete.
 *
 * @author Ryan Cavalcante
 */

public class CFGToPDALLConverter 
    extends GrammarToAutomatonConverter {
    /**
     * Creates an instance of <CODE>CFGToPDALLConverter</CODE>.
     */
    public CFGToPDALLConverter() {

    }

    /**
     * Returns the transition created by converting 
     * <CODE>production</CODE> to its equivalent transition.
     * @param production the production
     * @return the equivalent transition.
     */
    public Transition getTransitionForProduction(Production production) {
	String lhs = production.getLHS();
	String rhs = production.getRHS();
	Transition transition =
	    new PDATransition(INTERMEDIATE_STATE, INTERMEDIATE_STATE,
			      "", lhs, rhs);
	return transition;
    }

    /**
     * Adds all states to <CODE>automaton</CODE> necessary for the 
     * conversion of <CODE>grammar</CODE> to its equivalent
     * automaton.  This creates three states--an initial state,
     * an intermediate state, and a final state.  It also adds
     * transitions connecting the three states, and transitions
     * for each terminal in <CODE>grammar</CODE>
     * @param grammar the grammar being converted.
     * @param automaton the automaton being created.
     */
    public void createStatesForConversion(Grammar grammar, 
					  Automaton automaton) {
	initialize();
	StatePlacer sp = new StatePlacer();

	State initialState = 
	    automaton.createState(sp.getPointForState(automaton));
	automaton.setInitialState(initialState);
	
	State intermediateState = 
	    automaton.createState(sp.getPointForState(automaton));
	INTERMEDIATE_STATE = intermediateState;

	State finalState = 
	    automaton.createState(sp.getPointForState(automaton));
	automaton.addFinalState(finalState);

	String startVariable = grammar.getStartVariable();
	String temp = startVariable.concat(BOTTOM_OF_STACK);
	PDATransition trans1 = 
	    new PDATransition(initialState, intermediateState, 
			      "", BOTTOM_OF_STACK, temp);
	automaton.addTransition(trans1);
	PDATransition trans2 = 
	    new PDATransition(intermediateState, finalState, "", 
			      BOTTOM_OF_STACK, "");
	automaton.addTransition(trans2);

	String[] terminals = grammar.getTerminals();
	for(int k = 0; k < terminals.length; k++) {
	    PDATransition trans = 
		new PDATransition(intermediateState, intermediateState,
				  terminals[k], terminals[k], "");
	    automaton.addTransition(trans);
	}

    }
    
   
    /** the intermediate state in the automaton. */
    protected State INTERMEDIATE_STATE;
}
