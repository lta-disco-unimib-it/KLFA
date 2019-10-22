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

import automata.State;
import automata.Transition;

/**
 * An <CODE>FSATransition</CODE> is a <CODE>Transition</CODE> object
 * with an additional field for the label, which determines if the
 * machine should move on this transition.
 * 
 * @see automata.fsa.FiniteStateAutomaton
 * 
 * @author Thomas Finley
 */

public class FSATransition extends Transition {
    /**
     * Instantiates a new <CODE>FSATransition</CODE> object.
     * @param from the state this transition comes from
     * @param to the state this transition goes to
     * @param label the label for this transition, roughly intended to
     * be that string that the current string in the machine should
     * satisfy before moving on to the next state
     */
    public FSATransition(State from, State to, String label) {
	super(from, to);
	setLabel(label);
	try {
		throw new Exception("FSATransition");
	} catch (Exception e) {
		// TODO Auto-generated catch block
		//e.printStackTrace();
	}
    }

    /**
     * Produces a copy of this transition with new from and to states.
     * @param from the new from state
     * @param to the new to state
     * @return a copy of this transition with the new states
     */
    public Transition copy(State from, State to) {
	return new FSATransition(from, to, myLabel);
    }

    /**
     * Returns the label for this transition.
     */
    public String getLabel() {
	return myLabel;
    }

    /**
     * Sets the label for this transition.
     * @param label the new label for this transition
     * @throws IllegalArgumentException if the label contains any
     * "bad" characters, i.e., not alphanumeric
     */
    public void setLabel(String label) {
	//if (!automata.StringChecker.isAlphanumeric(label))
	//  throw new IllegalArgumentException("Label must be alphanumeric!");
	myLabel = label;
	getAutomaton().transitionChanged(this);
    }
    


    /**
     * Returns the description for this transition.
     * @return the description, in this case, simply the label
     */
    public String getDescription() {
	String desc = getLabel();
	if (desc.length() == 0) return "\u03BB"; // I am a badass.
	return getLabel();
    }

    /**
     * Returns a string representation of this object.  This is the
     * same as the string representation for a regular transition
     * object, with the label tacked on.
     * @see automata.Transition#toString
     * @return a string representation of this object
     */
    public String toString() {
	return super.toString() + ": \"" + getLabel() + "\"";
    }

    /**
     * Returns if this transition equals another object.
     * @param object the object to test against
     * @return <CODE>true</CODE> if the two are equal,
     * <CODE>false</CODE> otherwise
     */
    public boolean equals(Object object) {
	try {
	    FSATransition t = (FSATransition) object;
	    return super.equals(t) && myLabel.equals(t.myLabel);
	} catch (ClassCastException e) {
	    return false;
	}
    }

    /**
     * Returns the hash code for this transition.
     * @return the hash code for this transition
     */
    public int hashCode() {
	return super.hashCode() ^ myLabel.hashCode();
    }

    /** The label for this transition, which is intended to be used as
     * the precondition that a string must satisfy before the machine
     * continues. */
    protected String myLabel;
}
