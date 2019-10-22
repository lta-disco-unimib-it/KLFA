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
 
package automata.fsa;

import java.util.ArrayList;

import automata.Automaton;
import automata.State;
import automata.StatePlacer;
import automata.Transition;

/**
 * The fsa to regular expression converter can be used to convert a
 * finite state automaton to its equivalent regular expression.  In
 * order to perform this conversion, you need to convert the finite
 * state automaton into a "simple" automaton (i.e. an automaton with a
 * single final state and a different single initial state and exactly
 * one transition between all combinations of states) by calling
 * convertToSimpleAutomaton.  Or you can do this conversion step by
 * step, by first calling getSingleFinalState to give your automaton a
 * unique, single final state.  Then, you would need to check all
 * combinations of pairs of states in your automaton to see if they
 * have a single transition between them (remember, the simple
 * automaton needs exactly one transition between all pairs of
 * states).  If there are 0 transitions between any two states, you
 * can call addTransitionOnEmptySet to create a transition on the
 * empty set between the two states.  Or if there are more than one
 * transitions between any two states, you can call
 * combineToSingleTransition to combine all those transitions to a
 * single transition between the two states.  Then you can convert
 * this automaton immediately to its equivalent regular expression by
 * calling convertToRegularExpression, or you can perform this
 * conversion step by step.  First you need to remove all states other
 * than the final and initial states.  You do this by calling
 * getTransitionsForRemoveState and then removeState for each state.
 * Once this is completed, you will have a generalized transition
 * graph (with only two states--an initial and final state).  At this
 * point you can get the regular expression by calling
 * getExpressionFromGTG, or you can do that work yourself by calling
 * getII, getIJ, getJJ, and getJI to get the expressions on the four
 * arcs in your two-state generalized transition graph, and then
 * calling getFinalExpression.
 * 
 * @author Ryan Cavalcante
 */

public class FSAToRegularExpressionConverter {
    /**
     * Creates an instance of <CODE>FSAToRegularExpressionConverter</CODE>.
     */
    public FSAToRegularExpressionConverter() {
	
    }

    /**
     * Returns true if <CODE>automaton</CODE> can be converted to
     * a regular expression (i.e. it has a unique initial and 
     * final state and it is a finite state automaton, and the
     * initial state is not the final state).
     * @param automaton the automaton to convert
     * @return true if <CODE>automaton</CODE> can be converted to 
     * a regular expression.
     */
    public boolean isConvertable(Automaton automaton) {
	if(!(automaton instanceof FiniteStateAutomaton)) return false;
	State[] finalStates = automaton.getFinalStates();
	if(finalStates.length != 1) {
	    return false;
	}
	
	State initialState = automaton.getInitialState();
	if(finalStates[0] == initialState) {
	    return false;
	}
	return true;
    }

    /**
     * Returns true if there are more removable states in 
     * <CODE>automaton</CODE>.
     * @param automaton the automaton
     * @return true if there are more removable states in 
     * <CODE>automaton</CODE>.
     */
    public boolean areRemovableStates(Automaton automaton) {
	State[] states = automaton.getStates();
	for(int k = 0; k < states.length; k++) {
	    if(isRemovable(states[k],automaton)) return true;
	}
	return false;
    }

    /**
     * Returns true if <CODE>state</CODE> is a removable state 
     * (i.e. it is not the unique initial or final state).
     * @param state the state to remove.
     * @param automaton the automaton.
     * @return true if <CODE>state</CODE> is a removable state
     */
    public boolean isRemovable(State state, Automaton automaton) {
	State[] finalStates = automaton.getFinalStates();
	State finalState = finalStates[0];
	State initialState = automaton.getInitialState();
	if(state == finalState || state == initialState) return false;
	return true;
    }

    /**
     * Returns a Transition object that represents the transition
     * between the states with ID's <CODE>p</CODE> and <CODE>q</CODE>,
     * with <CODE>expression</CODE> as the transition label.
     * @param p the ID of the from state.
     * @param q the ID of the to state.
     * @param expression the expression
     * @param automaton the automaton
     * @return a Transition object that represents the transition
     * between the states with ID's <CODE>p</CODE> and <CODE>q</CODE>,
     * with <CODE>expression</CODE> as the transition label.
     */
    public Transition getTransitionForExpression
	(int p, int q, String expression, Automaton automaton) {
	State fromState = automaton.getStateWithID(p);
	State toState = automaton.getStateWithID(q);
	Transition transition = new FSATransition(fromState, toState,
						  expression);
	return transition;
    }

    /**
     * Returns the expression on the transition between
     * <CODE>fromState</CODE> and <CODE>toState</CODE> in
     * <CODE>automaton</CODE>.
     * @param fromState the from state
     * @param toState the to state
     * @param automaton the automaton
     * @return the expression on the transition between
     * <CODE>fromState</CODE> and <CODE>toState</CODE> in
     * <CODE>automaton</CODE>.
     */
    public String getExpressionBetweenStates
	(State fromState, State toState, Automaton automaton) {
	Transition[] transitions =
	    automaton.getTransitionsFromStateToState(fromState,toState);
	FSATransition trans = (FSATransition) transitions[0];
	return trans.getLabel();
    } 

    /**
     * Returns the expression obtained from evaluating the following
     * equation: r(pq) = r(pq) + r(pk)r(kk)*r(kq), where p, q, and 
     * k represent the IDs of states in <CODE>automaton</CODE>.
     * @param p the from state
     * @param q the to state
     * @param k the state being removed.
     * @param automaton the automaton.
     * @return the expression obtained from evaluating the following
     * equation: r(pq) = r(pq) + r(pk)r(kk)*r(kq), where p, q, and 
     * k represent the IDs of states in <CODE>automaton</CODE>.
     */
    public String getExpression(int p, int q, int k, Automaton automaton) {
	State fromState = automaton.getStateWithID(p);
	State toState = automaton.getStateWithID(q);
	State removeState = automaton.getStateWithID(k);
	
	String pq = 
	    getExpressionBetweenStates(fromState,toState,automaton); 
	String pk = 
	    getExpressionBetweenStates(fromState,removeState,automaton); 
	String kk = 
	    getExpressionBetweenStates(removeState,removeState,automaton);
	String kq = 
	    getExpressionBetweenStates(removeState,toState,automaton);
	
	String temp1 = star(kk);
	String temp2 = concatenate(pk, temp1);
	String temp3 = concatenate(temp2, kq);
	String label = or(pq, temp3);
	return label;
    }

    /**
     * Returns the expression that represents <CODE>r1</CODE>
     * concatenated with <CODE>r2</CODE>.  (essentialy just
     * the two strings concatenated).
     * @param r1 the first part of the expression.
     * @param r2 the second part of the expression.
     * @return the expression that represents <CODE>r1</CODE>
     * concatenated with <CODE>r2</CODE>.  (essentialy just
     * the two strings concatenated).
     */
    public String concatenate(String r1, String r2) {
	if(r1.equals(EMPTY) || r2.equals(EMPTY)) return EMPTY;
	else if(r1.equals(LAMBDA))return r2;
	else if(r2.equals(LAMBDA)) return r1;
	if(needsParens(r1)) r1 = addParen(r1);
	if(needsParens(r2)) r2 = addParen(r2);
	return r1+r2;
    }

    /**
     * Returns the expression that represents <CODE>r1</CODE>
     * kleene-starred.
     * @param r1 the expression being kleene-starred.
     * @return the expression that represents <CODE>r1</CODE>
     * kleene-starred.
     */
    public String star(String r1) {
	if(r1.equals(EMPTY)||r1.equals(LAMBDA)) return LAMBDA;
	if(!isSingleCharacter(r1)) r1 = addParen(r1);
	return r1+KLEENE_STAR;
    }

    /**
     * Returns the string that represents <CODE>r1</CODE>
     * or'ed with <CODE>r2</CODE>.
     * @param r1 the first expression
     * @param r2 the second expression
     * @return the string that represents <CODE>r1</CODE>
     * or'ed with <CODE>r2</CODE>.
     */
    public String or(String r1, String r2) {
	if(r1.equals(EMPTY)) return r2;
	if(r2.equals(EMPTY)) return r1;
	if (r1.equals(LAMBDA) && r2.equals(LAMBDA)) return LAMBDA;
	if (r1.equals(LAMBDA)) r1 = LAMBDA_DISPLAY;
	if (r2.equals(LAMBDA)) r2 = LAMBDA_DISPLAY;
	//else if(r1.equals(LAMBDA)) return r2;
	//else if(r2.equals(LAMBDA)) return r1;
	if(needsParens(r1)) r1 = addParen(r1);
	if(needsParens(r2)) r2 = addParen(r2);
	return r1+OR+r2;
    }

    /**
     * Completely reconstructs <CODE>automaton</CODE>, removing
     * all transitions and <CODE>state</CODE> and adding
     * all transitions in <CODE>transitions</CODE>.
     * @param state the state to remove.
     * @param transitions the transitions returned for removing
     * <CODE>state</CODE>.
     * @param automaton the automaton.
     */
    public void removeState 
	(State state, Transition[] transitions, Automaton automaton) {
	Transition[] oldTransitions = automaton.getTransitions();
	for(int k = 0; k < oldTransitions.length; k++) {
	    automaton.removeTransition(oldTransitions[k]);
	}
	
	automaton.removeState(state);
       
	for(int i = 0; i < transitions.length; i++) {
	    automaton.addTransition(transitions[i]);
	}
    }

    /**
     * Returns a list of all transitions for 
     * <CODE>automaton</CODE> created by removing
     * <CODE>state</CODE>.
     * @param state the state to remove.
     * @param automaton the automaton.
     * @return a list of all transitions for 
     * <CODE>automaton</CODE> created by removing
     * <CODE>state</CODE>.
     */
    public Transition[] getTransitionsForRemoveState
	(State state, Automaton automaton) {
	if(!isRemovable(state, automaton)) return null;
	ArrayList list = new ArrayList();
	int k = state.getID();
	State[] states = automaton.getStates();
	for(int i = 0; i < states.length; i++) {
	    int p = states[i].getID();
	    if(p != k) {
		for(int j = 0; j < states.length; j++) {
		    int q = states[j].getID();
		    if(q != k) {
			String exp = 
			    getExpression(p,q,k,automaton);
			list.add(getTransitionForExpression
				 (p,q,exp,automaton));
		    }
		}
	    }
	}
	return (Transition[]) list.toArray(new Transition[0]);
    }

    /**
     * Adds a new transition to <CODE>automaton</CODE> between
     * <CODE>fromState</CODE> and </CODE>toState</CODE> on the symbol
     * for the empty set.
     * @param fromState the from state for the transition
     * @param toState the to state for the transition
     * @param automaton the automaton.
     * @return the <CODE>FSATransition</CODE> that was created
     */
    public FSATransition addTransitionOnEmptySet
	(State fromState, State toState, Automaton automaton) {
	FSATransition t = new FSATransition(fromState,toState,EMPTY);
	automaton.addTransition(t);
	return t;
    }

    /**
     * Removes all transitions in <CODE>transitions</CODE> from 
     * <CODE>automaton</CODE>, replacing them with a single 
     * transition in <CODE>automaton</CODE> between 
     * <CODE>fromState</CODE> and <CODE>toState</CODE> labeled with
     * a regular expression that represents the labels of all the 
     * removed transitions Or'ed together (e.g. a + (b*c) + (d+e)).
     * @param fromState the from state for <CODE>transitions</CODE>
     * and for the newly created transition.
     * @param toState the to state for <CODE>transitions</CODE> and
     * for the newly created transition.
     * @param transitions the transitions being removed and combined
     * into a single transition
     * @param automaton the automaton
     * @return the transition that replaced all of these
     */
    public FSATransition combineToSingleTransition
	(State fromState,State toState,Transition[] transitions,
	 Automaton automaton) {
	 String label = ((FSATransition) transitions[0]).getDescription();
	 automaton.removeTransition(transitions[0]);
	 for(int i = 1; i < transitions.length; i++) {
	     label = or(label,((FSATransition) transitions[i])
			.getDescription());
	     automaton.removeTransition(transitions[i]);
	 }
	 FSATransition t=new FSATransition(fromState,toState,label);
	 automaton.addTransition(t);
	 return t;
    }

    /**
     * Makes all final states in <CODE>automaton</CODE> non-final,
     * adding transitions from these states to a newly created
     * final state on lambda.
     * @param automaton the automaton
     */
    public void getSingleFinalState(Automaton automaton) {
	StatePlacer sp = new StatePlacer();
	State finalState = 
	    automaton.createState(sp.getPointForState(automaton));
	
	State[] finalStates = automaton.getFinalStates();
	for(int k = 0; k < finalStates.length; k++) {
	    State state = finalStates[k];
	    automaton.addTransition(new FSATransition
				    (state,finalState, LAMBDA));
	    automaton.removeFinalState(state);
	}
	automaton.addFinalState(finalState);
    }

    /**
     * Converts <CODE>automaton</CODE> to an equivalent automaton
     * with a single transition between all combinations of states.
     * (if there are currently more than one transition between
     * two states, it combines them into a single transition by
     * or'ing the labels of all the transitions.  If there is no
     * transition between two states, it creates a transition and
     * labels it with the empty set character (EMPTY).
     * @param automaton the automaton.
     */
    public void convertToSimpleAutomaton(Automaton automaton) {
	if(!isConvertable(automaton)) getSingleFinalState(automaton);
	State[] states = automaton.getStates();
	for(int k = 0; k < states.length; k++) {
	    for(int j = 0; j < states.length; j++) {
		Transition[] transitions =
		    automaton.getTransitionsFromStateToState
		    (states[k], states[j]);
		if(transitions.length == 0) {
		    addTransitionOnEmptySet(states[k],states[j],automaton);
		}
		if(transitions.length > 1) {
		    combineToSingleTransition(states[k],states[j],
					      transitions,automaton);
		}
	    }
	}
    }

    /**
     * Converts <CODE>automaton</CODE> into a generalized transition
     * graph with only two states, a unique initial state, and a unique
     * final state.
     * @param automaton the automaton.
     */
    public void convertToGTG(Automaton automaton) {
	State[] finalStates = automaton.getFinalStates();
	State finalState = finalStates[0];
	State initialState = automaton.getInitialState();
	State[] states = automaton.getStates();
	for(int k = 0; k < states.length; k++) {
	    State state = states[k];
	    if(state != finalState && state != initialState) {
		Transition[] transitions = 
		    getTransitionsForRemoveState(state, automaton);
		removeState(state,transitions,automaton);
	    }
	}
    }

    /**
     * Returns true if <CODE>word</CODE> is one character
     * long and it is a letter.
     * @param word the word
     * @return true if <CODE>word</CODE> is one character
     * long and it is a letter.
     */
    public boolean isSingleCharacter(String word) {
	if(word.length() != 1) return false;
	char ch = word.charAt(0);
	return Character.isLetter(ch);
    }

    /**
     * Returns true if <CODE>word</CODE> needs parens.
     * (i.e. it is an '+' (OR) expression)
     * @param word the word.
     * @return true if <CODE>word</CODE> needs parens.
     * (i.e. it is an '+' (OR) expression)
     */
    public boolean needsParens(String word) {
	for(int k = 0; k < word.length(); k++) {
	    char ch = word.charAt(k);
	    if(ch == '+') return true;
	}
	return false;
    }

    /**
     * Returns a string of <CODE>word</CODE> surrounded by
     * parentheses.  i.e. (<word>).
     * @param word the word.
     * @return a string of <CODE>word</CODE> surrounded by
     * parentheses.
     */
    public String addParen(String word) {
	return LEFT_PAREN.concat(word.concat(RIGHT_PAREN));
    }

    /**
     * Returns a non-unicoded version of <CODE>word</CODE> for
     * debug purposes.
     * @param word the expression to output
     * @return a non-unicoded version of <CODE>word</CODE> for
     * debug purposes.
     */
    public String getExp(String word) {
	if(word.equals(LAMBDA)) return "lambda";
	else if(word.equals(EMPTY)) return "empty";
	return word;
    }

    /**
     * Returns the expression for the values of ii, ij, jj, and ji
     * determined from the GTG with a unique initial and final
     * state.
     * @param ii the expression on the loop off the initial state
     * @param ij the expression on the arc from the initial state to
     * the final state.
     * @param jj the expression on the loop off the final state.
     * @param ji the expression on the arc from the final state to the
     * initial state.
     * @return the expression for the values of ii, ij, jj, and ji
     * determined from the GTG with a unique initial and final
     * state.
     */
    public String getFinalExpression
	(String ii, String ij, String jj, String ji) {
	String temp = concatenate(star(ii), concatenate(ij, concatenate(star(jj), ji)));
	String temp2 = concatenate(star(ii), concatenate(ij, star(jj)));
	String expression = concatenate(star(concatenate(LEFT_PAREN,concatenate(temp,RIGHT_PAREN))), temp2);
	return expression;
    }

    /**
     * Returns the expression on the loop off the initial state of
     * <CODE>automaton</CODE>.
     * @param automaton a generalized transition graph with only
     * two states, a unique initial and final state.
     * @return the expression on the loop off the initial state of
     * <CODE>automaton</CODE>.
     */
    public String getII(Automaton automaton) {
	State initialState = automaton.getInitialState();
	return getExpressionBetweenStates(initialState,initialState,automaton);
    }

    /**
     * Returns the expression on the arc from the initial state
     * to the final state of <CODE>automaton</CODE>.
     * @param automaton a generalized transition graph with only
     * two states, a unique initial and final state.
     * @return the expression on the arc from the initial state
     * to the final state of <CODE>automaton</CODE>.
     */
    public String getIJ(Automaton automaton) {
	State initialState = automaton.getInitialState();
	State[] finalStates = automaton.getFinalStates();
	State finalState = finalStates[0];
	return getExpressionBetweenStates(initialState,finalState,automaton);
    }

    /**
     * Returns the expression on the loop off the final state
     * of <CODE>automaton</CODE>
     * @param automaton a generalized transition graph with only
     * two states, a unique initial and final state.
     * @return the expression on the loop off the final state
     * of <CODE>automaton</CODE>
     */
    public String getJJ(Automaton automaton) {
	State[] finalStates = automaton.getFinalStates();
	State finalState = finalStates[0];
	return getExpressionBetweenStates(finalState,finalState,automaton);
    }
    
    /**
     * Returns the expression on the arc from the final state to
     * the initial state of <CODE>automaton</CODE>
     * @param automaton a generalized transition graph with only
     * two states, a unique initial and final state.
     * @return the expression on the arc from the final state to
     * the initial state of <CODE>automaton</CODE>
     */
    public String getJI(Automaton automaton) {
	State initialState = automaton.getInitialState();
	State[] finalStates = automaton.getFinalStates();
	State finalState = finalStates[0];
	return getExpressionBetweenStates(finalState,initialState,automaton);
    }

    /**
     * Returns the expression for the generalized transition graph
     * <CODE>automaton</CODE> with two states, a unique initial and
     * unique final state.  Evaluates to the expression
     * r = (r(ii)*r(ij)r(jj)*r(ji))*r(ii)*r(ij)r(jj)*. where r(ij) 
     * represents the expression on the transition between state
     * i (the initial state) and state j (the final state)
     * @param automaton the generalized transition graph with two states
     * (a unique initial and final state).
     * @return the expression for the generalized transition graph
     * <CODE>automaton</CODE> with two states, a unique initial and
     * unique final state
     */
    public String getExpressionFromGTG(Automaton automaton) {
	String ii = getII(automaton);
	String ij = getIJ(automaton);
	String jj = getJJ(automaton);
	String ji = getJI(automaton);
	
	return getFinalExpression(ii,ij,jj,ji);
    }

    /**
     * Returns the regular expression that represents 
     * <CODE>automaton</CODE>.
     * @param automaton the automaton
     * @return the regular expression that represents
     * <CODE>automaton</CODE>.
     */
    public String convertToRegularExpression(Automaton automaton) {
	if(!isConvertable(automaton)) return null;
	convertToGTG(automaton);
	return getExpressionFromGTG(automaton);
    }

    /* the string for the empty set. */
    public static String EMPTY = "\u00F8";
    /* the string for lambda. */
    public static String LAMBDA_DISPLAY = "\u03BB";
    public static String LAMBDA = "";
    /* the string for the kleene star. */
    public static String KLEENE_STAR = "*";
    /* the string for the or symbol. */
    public static String OR = "+";
    /** right paren. */
    public static String RIGHT_PAREN = ")";
    /** left paren. */
    public static String LEFT_PAREN = "(";
}
