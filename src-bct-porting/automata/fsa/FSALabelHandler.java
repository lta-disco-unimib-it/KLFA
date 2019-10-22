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
 
package automata.fsa;

import automata.Automaton;
import automata.State;
import automata.Transition;

/**
 * The FSA label handler is an object that can convert a finite state
 * automaton with transition labels of more than one character in 
 * length into an equivalent finite state automaton with all 
 * transition labels exactly one character in length.
 *
 * @author Ryan Cavalcante
 */

public class FSALabelHandler {
    /**
     * Creates an instance of <CODE>FSALabelHandler</CODE>.
     */
    public FSALabelHandler() {
	
    }
    
    /**
     * Returns true if <CODE>automaton</CODE> has labels with multiple
     * characters, instead of single character labels.
     * @param automaton the automaton.
     * @return true if <CODE>automaton</CODE> has labels with multiple
     * characters, instead of single character labels.
     */
    public boolean hasMultipleCharacterLabels(Automaton automaton) {
	Transition[] transitions = automaton.getTransitions();
	for(int k = 0; k < transitions.length; k++) {
	    FSATransition transition = (FSATransition) transitions[k];
	    String label = transition.getLabel();
	    if(label.length() > 1) return true;
	}
	return false;
    }

    /**
     * Changes <CODE>transition</CODE> in <CODE>automaton</CODE> to
     * several transitions each with labels of one character in
     * length.  This algorithm introduces new states in
     * <CODE>automaton</CODE>.
     * @param transition the transition to break up into several
     * transitions of one character (in length) a piece.
     * @param automaton the automaton that has the transition.
     */
    public void handleLabel(Transition transition, Automaton automaton) {
	/*FSATransition trans = (FSATransition) transition;
	String label = trans.getLabel();
	String firstChar = label.substring(0,1);
	String restOfLabel = label.substring(1);
	
	StatePlacer sp = new StatePlacer();
	
	State newState = automaton.createState(sp.getPointForState(automaton));
	Transition newTrans1 = new FSATransition(trans.getFromState(),
						newState, firstChar);
	Transition newTrans2 = new FSATransition(newState, trans.getToState(),
						 restOfLabel);
	automaton.addTransition(newTrans1);
	automaton.addTransition(newTrans2);
	automaton.removeTransition(transition);
	if(restOfLabel.length() > 1) handleLabel(newTrans2, automaton);*/

	FSATransition trans = (FSATransition) transition;
	State from = transition.getFromState(), f = from, to = transition.getToState();
	automaton.removeTransition(trans);
	String label = trans.getLabel();
	int length = label.length();
	for (int i=0; i<length; i++) {
	    State going = i == length-1 ? to : automaton.createState
		(new java.awt.Point
		 ((f.getPoint().x*(length-i-1) + to.getPoint().x*(i+1))/length,
		  (f.getPoint().y*(length-i-1) + to.getPoint().y*(i+1))/length));
	    Transition newTrans =
		new FSATransition(from, going, label.substring(i,i+1));
	    automaton.addTransition(newTrans);
	    from = going;
	}
    }

    /**
     * Changes all transitions in <CODE>automaton</CODE> into transitions
     * with at most one character per label.  This could introduce more
     * states into <CODE>automaton</CODE>.
     * @param automaton the automaton.
     */
    public FiniteStateAutomaton removeMultipleCharacterLabels
	(Automaton automaton) {
	FiniteStateAutomaton fsa = (FiniteStateAutomaton) automaton.clone();
	Transition[] transitions = fsa.getTransitions();
	for(int k = 0; k < transitions.length; k++) {
	    FSATransition transition = (FSATransition) transitions[k];
	    String label = transition.getLabel();
	    if(label.length() > 1) {
		handleLabel(transition, fsa);
	    } 
	}
	return fsa;
    }

    /**
     * Changes all transitions in <CODE>automaton</CODE> into transitions
     * with at most one character per label.  This could introduce more
     * states into <CODE>automaton</CODE>.
     * @param automaton the automaton.
     */
    public void removeMultipleCharacterLabelsFromAutomaton
	(Automaton automaton) {
	Transition[] transitions = automaton.getTransitions();
	for(int k = 0; k < transitions.length; k++) {
	    FSATransition transition = (FSATransition) transitions[k];
	    String label = transition.getLabel();
	    if(label.length() > 1) {
		handleLabel(transition, automaton);
	    } 
	}
    }
}
