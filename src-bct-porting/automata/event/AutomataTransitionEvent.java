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
 
package automata.event;

import java.util.EventObject;

import automata.Automaton;
import automata.Transition;

/**
 * This event is given to listeners of an automaton interested in
 * events when a transition on an automaton is added or removed.
 * @see automata.Automaton
 * @see automata.Transition
 * @see automata.Automaton#addTransition
 * @see automata.Automaton#removeTransition
 * @see automata.event.AutomataTransitionListener
 * 
 * @author Thomas Finley
 */

public class AutomataTransitionEvent extends EventObject {
    /**
     * Instantiates a new <CODE>AutomataStateEvent</CODE>.
     * @param auto the <CODE>Automaton</CODE> that generated the event
     * @param state the <CODE>State</CODE> that was added or removed
     * @param add <CODE>true</CODE> if state added, <CODE>false</CODE>
     * if removed
     */
    public AutomataTransitionEvent(Automaton auto, Transition transition,
				   boolean add, boolean change) {
	super(auto);
	myTransition = transition;
	myAdd = add;
	myChange = change;
    }

    /**
     * Returns the <CODE>Automaton</CODE> that generated this event.
     * @return the <CODE>Automaton</CODE> that generated this event
     */
    public Automaton getAutomaton() {
	return (Automaton) getSource();
    }

    /**
     * Returns the <CODE>Transition</CODE> that was added/removed.
     * @return the <CODE>Transition</CODE> that was added/removed
     */
    public Transition getTransition() {
	return myTransition;
    }

    /**
     * Returns if this was an add.
     * @return <CODE>true</CODE> if this event indicates the addition
     * of a transition, <CODE>false</CODE> otherwise
     */
    public boolean isAdd() {
	return myAdd;
    }

    /**
     * Returns if this was a delete.
     * @return <CODE>true</CODE> if this event indicates the removal
     * of a transition, <CODE>false</CODE> otherwise
     */
    public boolean isDelete() {
	return !(myAdd || myChange);
    }

    /**
     * Returns if this was a simple change in a property of the
     * transition.
     * @return <CODE>true</CODE> if the properties of this transition
     * were changed, <CODE>false</CODE> otherwise
     */
    public boolean isChange() {
	return myChange;
    }
    
    /** Was this an add? */
    private boolean myAdd;
    /** Which transition did we add/remove? */
    private Transition myTransition;
    /** Is this a change in property? */
    private boolean myChange;
}
