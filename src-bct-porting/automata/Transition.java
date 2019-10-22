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
 
package automata;

import java.io.Serializable;

/**
 * A <CODE>Transition</CODE> object is a simple abstract class
 * representing a transition between two state objects in an
 * automaton.  Subclasses of this transition class will have
 * additional fields containing the particulars necessary for their
 * transition.
 * 
 * @see automata.State
 * @see automata.Automaton
 * 
 * @author Thomas Finley
 */

public abstract class Transition implements Serializable {
    /**
     * Instantiates a new <CODE>Transition</CODE>.
     * @param from the state this transition is from
     * @param to the state this transition moves to
     */
    public Transition(State from, State to) {
	this.from = from;
	this.to = to;
    }

    /**
     * Returns a copy of this transition, except for a new
     * <CODE>from</CODE> and <CODE>to</CODE> state.
     * @param from the state this transition goes to
     * @param to the state this transition comes from
     * @return a copy of this transition as described
     */
    public abstract Transition copy(State from, State to);
    
    /**
     * Returns the state this transition eminates from.
     * @return the state this transition eminates from
     */
    public State getFromState(){
	return this.from;
    }
    
    /**
     * Returns the state this transition travels to.
     * @return the state this transition travels to
     */
    public State getToState(){
	return this.to;
    }

    /**
     * Returns the automaton this transition is over.
     * @return the automaton this transition is over
     */
    public Automaton getAutomaton() {
	return this.from.getAutomaton();
    }

    /**
     * Gets the description for a Transition.  This defaults to
     * nothing.  Subclasses should override.
     * @return an empty string
     */
    public String getDescription() {
	return "";
    }

    /**
     * Returns a string representation of this object.  The string
     * returned is the string representation of the first state, and
     * the string representation of the second state.
     * @return a string representation of this object
     */
    public String toString() {
	return "["+getFromState().toString()+"] -> ["
	    +getToState().toString()+"]";
    }

    /**
     * Returns if this transition equals another object.
     * @param object the object to test against
     * @return <CODE>true</CODE> if the two are equal,
     * <CODE>false</CODE> otherwise
     */
    public boolean equals(Object object) {
	try {
	    Transition t = (Transition) object;
	    return from==t.from && to==t.to;
	} catch (ClassCastException e) {
	    return false;
	}
    }

    /**
     * Returns the hash code for this transition.
     * @return the hash code for this transition
     */
    public int hashCode() {
	return from.hashCode() ^ to.hashCode();
    }
    
    /** The states this transition goes between. */
    protected State from, to;
}
