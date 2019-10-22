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
 
package automata.graph;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

import automata.Automaton;
import automata.State;

/**
 * The disjoint sets detector can be used to determine the disjoint 
 * sets of states in a given automaton.  Two sets of states are determined
 * to be disjoint if there are no transitions from one set to the other.
 * Also, given a state, the disjoint sets detector can return the
 * set of states that are connected to that state.
 *
 * @author Ryan Cavalcante
 */

public class DisjointSetsDetector {
    /**
     * Instantiates a <CODE>DisjointSetsDetector</CODE>.
     */
    public DisjointSetsDetector() {
	STATES_IN_A_SET = new ArrayList();
    }

    /**
     * Adds the states in the set <CODE>states</CODE> to the set
     * of states accounted for in the determination of the disjoint
     * sets.
     * @param states the set of states to account for
     */
    private void accountForStates(HashSet states) {
	Iterator it = states.iterator();
	while(it.hasNext()) {
	    State state = (State) it.next();
	    if(!STATES_IN_A_SET.contains(state))
		STATES_IN_A_SET.add(state);
	}
    }
    
    /**
     * Returns true if <CODE>s1</CODE> and <CODE>s2</CODE> from
     * <CODE>automaton</CODE> are directly connected.  (i.e. there is
     * either a transition from <CODE>s1</CODE> to <CODE>s2</CODE> or
     * a transition from <CODE>s2</CODE> to <CODE>s1</CODE>.
     * @param s1 a state
     * @param s2 a state
     * @param automaton the automaton
     * @return true if <CODE>s1</CODE> and <CODE>s2</CODE> from
     * <CODE>automaton</CODE> are directly connected.  (i.e. there is
     * either a transition from <CODE>s1</CODE> to <CODE>s2</CODE> or
     * a transition from <CODE>s2</CODE> to <CODE>s1</CODE>.
     */
    private boolean areDirectlyConnected
	(State s1, State s2, Automaton automaton) {
	if(s1==s2) return false;
	if(automaton.getTransitionsFromStateToState(s1,s2).length == 0 &&
	   automaton.getTransitionsFromStateToState(s2,s1).length == 0)
	    return false;
	return true;
    }

    /**
     * Returns a list of states in <CODE>automaton</CODE> that are 
     * connected directly to <CODE>state</CODE>.
     * @param state the state
     * @param automaton the automaton
     * @return a list of states in <CODE>automaton</CODE> that are 
     * connected directly to <CODE>state</CODE>.
     */
    private ArrayList getStatesConnectedToState
	(State state, Automaton automaton) {
	ArrayList list = new ArrayList();
	State[] states = automaton.getStates();
	for(int k = 0; k < states.length; k++) {
	    if(areDirectlyConnected(state,states[k],automaton)) {
		list.add(states[k]);
	    }
	}
	return list;
    }

    /**
     * Adds all contents of <CODE>toAdd</CODE> that are not in 
     * <CODE>set</CODE> to <CODE>list</CODE>.
     * @param toAdd a list of states
     * @param set a set of states
     * @param list a list of states
     */
    private void addAllNotInSetToList(ArrayList toAdd, HashSet set, 
				     ArrayList list) {
	Iterator it = toAdd.iterator();
	while(it.hasNext()) {
	    State state = (State) it.next();
	    if(!set.contains(state)) list.add(state);
	}
    }

    /**
     * Returns a set containing all states in <CODE>automaton</CODE>,
     * including <CODE>state</CODE>, that are connected to
     * <CODE>state</CODE>.
     * @param state the state
     * @param automaton the automaton
     * @return a set containing all states in <CODE>automaton</CODE>,
     * including <CODE>state</CODE>, that are connected to
     * <CODE>state</CODE>.
     */
    public HashSet getSetIncludingState(State state, Automaton automaton) {
	HashSet set = new HashSet();
	ArrayList list = new ArrayList();
	list.add(state);
	while(!list.isEmpty()) {
	    ArrayList toAdd = new ArrayList();
	    Iterator it = list.iterator();
	    while(it.hasNext()) {
		State s = (State) it.next();
		toAdd.addAll(getStatesConnectedToState(s,automaton));
		set.add(s);
		it.remove();
	    }
	    addAllNotInSetToList(toAdd,set,list);
	}

	return set;
    }

    /**
     * Returns true if <CODE>state</CODE> has been accounted for in
     * the determination of disjoint sets
     * @param state the state
     */
    private boolean isAccountedFor(State state) {
	if(STATES_IN_A_SET.contains(state)) return true;
	return false;
    }

    /**
     * Returns true if all states in <CODE>automaton</CODE> have been
     * accounted for in the determination of disjoint sets.
     * @param automaton the automaton
     * @return true if all states in <CODE>automaton</CODE> have been
     * accounted for in the determination of disjoint sets.
     */
    private boolean accountedForAllStates(Automaton automaton) {
	if(getUnaccountedForState(automaton) == null) return true;
	return false;
    }

    /**
     * Returns a state in <CODE>automaton</CODE> that has not yet
     * been accounted for in the determination of disjoint sets.
     * @param automaton the automaton
     * @return a state in <CODE>automaton</CODE> that has not yet
     * been accounted for in the determination of disjoint sets.
     */
    public State getUnaccountedForState(Automaton automaton) {
	State[] states = automaton.getStates();
	for(int k = 0; k < states.length; k++) {
	    if(!isAccountedFor(states[k])) return states[k];
	}
	return null;
    }

    /**
     * Returns an array of all the disjoint sets of states in 
     * <CODE>automaton</CODE>.
     * @param automaton the automaton
     * @return an array of all the disjoint sets of states in 
     * <CODE>automaton</CODE>.
     */
    public HashSet[] getDisjointSets(Automaton automaton) {
	ArrayList list = new ArrayList();
	STATES_IN_A_SET = new ArrayList();

	while(!accountedForAllStates(automaton)) {
	    State state = getUnaccountedForState(automaton);
	    HashSet set = getSetIncludingState(state,automaton);
	    accountForStates(set);
	    list.add(set);
	}
	return (HashSet[]) list.toArray(new HashSet[0]);
    }

    /** the states accounted for in the determination of disjoint sets. */
    protected ArrayList STATES_IN_A_SET;

}
