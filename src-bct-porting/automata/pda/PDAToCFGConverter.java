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
 
package automata.pda;

import grammar.Production;
import grammar.cfg.ContextFreeGrammar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

import automata.Automaton;
import automata.State;
import automata.Transition;

/**
 * The PDA to context free grammar converter can be used to convert
 * a pushdown automaton into an equivalent context free grammar.  The
 * pda and grammar will be equivalent in that they will accept exactly 
 * the same language.  Before using the converter, except for the first
 * time, you must call initializeConverter to prepare the converter for 
 * the conversion.  This will reset all data, maps, etc. used during 
 * the last conversion done by the converter. You can do the conversion 
 * simply by calling convertToContextFreeGrammar, or you can perform the 
 * conversion step by step repeatedly calling getProductionsForTransition 
 * on every transition in the pda and adding all of the returned productions 
 * to your context free grammar.  If you do this for every transition in the
 * pda, you will have an equivalent cfg.
 *
 * @see grammar.cfg.ContextFreeGrammar
 *
 * @author Ryan Cavalcante
 */

public class PDAToCFGConverter {
    /**
     * Creates an instance of <CODE>PDAToCFGConverter</CODE>.
     */
    public PDAToCFGConverter() {
	initializeConverter();
    }

    /**
     * Initializes converter for pda to cfg conversion
     * (clears map and sets unique id)
     */
    public void initializeConverter() {
	MAP = new HashMap();
	UNIQUE_ID = 0;
    }

    /**
     * Returns true if <CODE>automaton</CODE> has a single final 
     * state that is entered if and only if the stack is empty.
     * @param automaton the automaton.
     * @return true if <CODE>automaton</CODE> has a single final
     * state that is entered if and only if the stack is empty.
     */
    public boolean hasSingleFinalState(Automaton automaton) {
	State[] finalStates = automaton.getFinalStates();
	if(finalStates.length != 1) {
	    //System.err.println("There is not exactly one final state!");
	    return false;
	}
	
	State finalState = finalStates[0];
	Transition[] transitions = automaton.getTransitionsToState(finalState);
	for(int k = 0; k < transitions.length; k++) {
	    PDATransition trans = (PDATransition) transitions[k];
	    String toPop = trans.getStringToPop();
	    if(!(toPop.substring(toPop.length()-1)).equals(BOTTOM_OF_STACK)) {
		//System.err.println("Bad transition to final state! "+trans);
		//System.err.println(toPop.substring(toPop.length()-1));
		return false;
	    }
	}
	return true;
    }

    /**
     * Returns true if all transitions in <CODE>automaton</CODE>
     * read a single character from the input, pop a single character
     * from the stack and push either zero or two characters on to 
     * the stack.
     * @param automaton the automaton
     * @return true if all transitions in <CODE>automaton</CODE>
     * read a single character from the input, pop a single character
     * from the stack and push either zero or two characters on to 
     * the stack.
     */
    public boolean hasTransitionsInCorrectForm(Automaton automaton) {
	Transition[] transitions = automaton.getTransitions();
	for(int k = 0; k < transitions.length; k++) {
	    if(!isPushLambdaTransition(transitions[k]) && 
	       !isPushTwoTransition(transitions[k])) {
		return false;
	    }
	}
	return true;
    }

    /**
     * Returns true if <CODE>automaton</CODE> is in the correct form
     * to perform the conversion to CFG.  The correct form enforces
     * two restrictions on <CODE>automaton</CODE> :
     * 1. it has a single final state that is entered if and only if
     * the stack is empty.
     * 2. all transitions read a single character from the input,
     * pop a single character from the stack and either push two 
     * or zero characters on to the stack.
     * @param automaton the automaton
     * @return true if <CODE>automaton</CODE> is in the correct form
     * to perform the conversion to CFG.
     */
    public boolean isInCorrectFormForConversion(Automaton automaton) {
	if(hasSingleFinalState(automaton) && 
	   hasTransitionsInCorrectForm(automaton)) {
	    return true;
	}
	return false;
    }

    /**
     * Returns true if <CODE>transition</CODE> reads a single character
     * from the input tape, pops a single character from the stack,
     * and writes TWO characters to the stack.
     * @param transition the transition
     * @return true if <CODE>transition</CODE> reads a single character
     * from the input tape, pops a single character from the stack,
     * and writes TWO characters to the stack.
     */
    public boolean isPushTwoTransition(Transition transition) {
	PDATransition trans = (PDATransition) transition;
	String toPush = trans.getStringToPush();
	if(toPush.length() != 2) return false;
	/*String input = trans.getInputToRead();
	  if(input.length() != 1) return false;*/
	String toPop = trans.getStringToPop();
	if(toPop.length() != 1) return false;
	return true;
    }

    /**
     * Returns true if <CODE>transition</CODE> reads a single character
     * from the input tape, pops a single character from the stack,
     * and writes NO characters to the stack.
     * @param transition the transition
     * @return true if <CODE>transition</CODE> reads a single character
     * from the input tape, pops a single character from the stack,
     * and writes NO characters to the stack.
     */
    public boolean isPushLambdaTransition(Transition transition) {
	PDATransition trans = (PDATransition) transition;
	String toPush = trans.getStringToPush();
	if(toPush.length() != 0) return false;
	/*String input = trans.getInputToRead();
	  if(input.length() != 1) return false;*/
	String toPop = trans.getStringToPop();
	if(toPop.length() != 1) return false;
	return true;
    }

    /**
     * Returns a unique variable.
     * @return a unique variable.
     */
    private String getUniqueVariable() {
	char[] ch = new char[1];
	ch[0] = (char) ('A' + UNIQUE_ID);
	UNIQUE_ID++;
	if (('A'+UNIQUE_ID) == 'S') UNIQUE_ID++;
	return  new String(ch);
    }

    /**
     * Returns true if <CODE>variable</CODE> is the start symbol.
     * (i.e. "(q0Zqf)")
     * @param variable the variable.
     * @param automaton the automaton.
     * @return true if <CODE>variable</CODE> is the start symbol.
     */
    public boolean isStartSymbol(String variable, Automaton automaton) {
	State startState = automaton.getInitialState();
	State[] finalStates = automaton.getFinalStates();
	if(finalStates.length > 1) {
	    //System.err.println("MORE THAN ONE FINAL STATE");
	    return false;
	}
	State finalState = finalStates[0];
	String startSymbol = LEFT_PAREN.concat(startState.getName().concat(BOTTOM_OF_STACK.concat(finalState.getName().concat(RIGHT_PAREN))));
	if(variable.equals(startSymbol)) return true;
	return false;
    }

    /**
     * Returns a list of productions created for <CODE>transition</CODE>,
     * a transition that pushes TWO characters on the stack. 
     * @param transition the transition
     * @param automaton the automaton
     * @return a list of productions created for <CODE>transition</CODE>,
     * a transition that pushes TWO characters on the stack. 
     */
    public ArrayList getProductionsForPushTwoTransition
	(Transition transition, Automaton automaton)
    {
	ArrayList list = new ArrayList();
	String fromState = transition.getFromState().getName();
	String toState = transition.getToState().getName();
	PDATransition trans = (PDATransition) transition;
	String toPop = trans.getStringToPop();
	String toRead = trans.getInputToRead();
	String toPush = trans.getStringToPush();
	String toPushOne = toPush.substring(0,1);
	String toPushTwo = toPush.substring(1);
	
	State[] states = automaton.getStates();
	for(int k = 0; k < states.length; k++) {
	    String state = states[k].getName();
	    String lhs = LEFT_PAREN.concat(fromState.concat(toPop.concat(state.concat(RIGHT_PAREN))));
	    for(int j = 0; j < states.length; j++) {
		String lstate = states[j].getName();
		String variable1 = LEFT_PAREN.concat(toState.concat(toPushOne.concat(lstate.concat(RIGHT_PAREN))));
		String variable2 = LEFT_PAREN.concat(lstate.concat(toPushTwo.concat(state.concat(RIGHT_PAREN))));
		
		/** Map to unique variables. */
		if(MAP.get(lhs) == null) {
		    if(isStartSymbol(lhs, automaton)) MAP.put(lhs, START_SYMBOL);
		    else MAP.put(lhs, getUniqueVariable());
		}
		if(MAP.get(variable1) == null) {
		    if(isStartSymbol(variable1, automaton)) MAP.put(variable1, START_SYMBOL);
		    else MAP.put(variable1, getUniqueVariable());
		}
		if(MAP.get(variable2) == null) {
		    if(isStartSymbol(variable2, automaton)) MAP.put(variable2, START_SYMBOL);
		    else MAP.put(variable2, getUniqueVariable());
		}
		
		String rhs = toRead.concat(variable1.concat(variable2));
		
		Production p = new Production(lhs, rhs);
		list.add(p);
	    }
	}
	return list;
    }

    /**
     * Returns a list of productions created for <CODE>transition</CODE>,
     * a transition that pushes NO characters on the stack.  This list
     * will always contain a single production.
     * @param transition the transition
     * @return a list of productions created for <CODE>transition</CODE>,
     * a transition that pushes NO characters on the stack.  This list
     * will always contain a single produciton.
     */
    public ArrayList getProductionsForPushLambdaTransition
	(Transition transition, Automaton automaton) {
	ArrayList list = new ArrayList();
	String fromState = transition.getFromState().getName();
	String toState = transition.getToState().getName();
	PDATransition trans = (PDATransition) transition;
	String toPop = trans.getStringToPop();
	String toRead = trans.getInputToRead();
	
	String lhs = LEFT_PAREN.concat(fromState.concat(toPop.concat(toState.concat(RIGHT_PAREN))));
	if(MAP.get(lhs) == null) {
	    if(isStartSymbol(lhs, automaton)) MAP.put(lhs, START_SYMBOL);
	    else MAP.put(lhs, getUniqueVariable());
	}
	String rhs = toRead;
	
	Production production = new Production(lhs, rhs);
	list.add(production);
	return list;
    }

    /**
     * Returns a list of productions that represent the same functionality
     * as <CODE>transition</CODE> in <CODE>automaton</CODE>.
     * @param transition the transition
     * @param automaton the automaton that transition is a part of.
     * @return a list of productions.
     */
    public ArrayList createProductionsForTransition(Transition transition,
						    Automaton automaton) {
	ArrayList list = new ArrayList();
	if(isPushLambdaTransition(transition)) {
	    list.addAll(getProductionsForPushLambdaTransition(transition,
							      automaton));
	}
	else if(isPushTwoTransition(transition)) {
	    list.addAll(getProductionsForPushTwoTransition(transition,
							   automaton));
	}
	    
	return list;
    }

    /**
     * Returns an equivalent production to <CODE>production</CODE>
     * but with each variable (e.g. "q1Aq3") replaced by a unique
     * variable (e.g. "B");
     * @param production the production
     * @return an equivalent production to <CODE>production</CODE>
     * with a single variable replacing groups of characters.
     */
    public Production getSimplifiedProduction(Production production) {
	String lhs = (String) MAP.get(production.getLHS());
	String rhs = production.getRHS();
	int leftIndex, rightIndex; // Position of left and right parentheses.
	StringBuffer newRhs = new StringBuffer();
	while ((leftIndex = rhs.indexOf('(')) != -1 &&
	       (rightIndex = rhs.indexOf(')')) != -1) {
	    newRhs.append(rhs.substring(0, leftIndex));
	    String variable = rhs.substring(leftIndex, rightIndex+1);
	    newRhs.append(MAP.get(variable));
	    rhs = rhs.substring(rightIndex+1);
	}
	newRhs.append(rhs);
	Production p = new Production(lhs,newRhs.toString());
	return p;
    }

    /**
     * Returns the number of unique variables defined sofar in this
     * conversion.
     * @return the number of unique variables
     */
    public int numberVariables() {
	return (new HashSet(MAP.values())).size();
    }

    /**
     * Returns a ContextFreeGrammar object that represents a grammar
     * equivalent to <CODE>automaton</CODE>.
     * @param automaton the automaton.
     * @return a cfg equivalent to <CODE>automaton</CODE>.
     */
    public ContextFreeGrammar convertToContextFreeGrammar(Automaton automaton)
    {
	/** check if automaton is pda. */
	if (!(automaton instanceof PushdownAutomaton))
	    throw new IllegalArgumentException
		("automaton must be PushdownAutomaton");

	if (!isInCorrectFormForConversion(automaton))
	    throw new IllegalArgumentException
		("automaton not in correct form for conversion to CFG");

	initializeConverter();

	ArrayList list = new ArrayList();
	ContextFreeGrammar grammar = new ContextFreeGrammar();
	
	Transition[] transitions = automaton.getTransitions();
	for(int k = 0; k < transitions.length; k++) {
	    list.addAll(createProductionsForTransition(transitions[k], 
						       automaton));
	}
	
	Iterator it = list.iterator();
	while(it.hasNext()) {
	    Production p = (Production) it.next();
	    grammar.addProduction(getSimplifiedProduction(p));
	}
	
	return grammar;
    }
    
    protected static final String START_SYMBOL = "S";
    protected int UNIQUE_ID;
    protected HashMap MAP;
    protected static final String LEFT_PAREN = "(";
    protected static final String RIGHT_PAREN = ")";
    protected static final String BOTTOM_OF_STACK = "Z";
}
