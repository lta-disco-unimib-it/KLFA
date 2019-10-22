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

import automata.State;
import automata.Transition;

/**
 * A <CODE>PDATransition</CODE> is a <CODE>Transition</CODE> object
 * with additional fields for the label (input to read), the string 
 * to pop off the stack, and the string to push on the stack.
 * 
 * @see automata.pda.PushdownAutomaton
 * 
 * @author Ryan Cavalcante
 */

public class PDATransition extends Transition {
    /**
     * Instantiates a new <CODE>PDATransition</CODE> object.
     * @param from the state this transition comes from
     * @param to the state this transition goes to
     * @param inputToRead the string that the machine should
     * satisfy before moving on to the next state.
     * @param stringToPop the string that the machine should
     * pop from the stack.
     * @param stringToPush the string that the machine should
     * push on to the stack.
     */
    public PDATransition(State from, State to, String inputToRead, 
			 String stringToPop, String stringToPush) {
	super(from, to);
	setInputToRead(inputToRead);
	setStringToPop(stringToPop);
	setStringToPush(stringToPush);
    }

    /**
     * Returns a copy of this transition with new from and to states.
     * @param from the new from state for the returned transition
     * @param to the new to state for the returned transition
     * @return a copy of this trnasition with the new from and to
     * states
     */
    public Transition copy(State from, State to) {
	return new PDATransition(from, to, getInputToRead(),
				 getStringToPop(), getStringToPush());
    }

    /**
     * Returns the input to read portion of the transition label for
     * this transition.
     */
    public String getInputToRead() {
	return myInputToRead;
    }

    /**
     * Sets the input to read portion of the transition label for
     * this transition.
     * @param inputToRead the input to read portion of the transition
     * label.
     */
    public void setInputToRead(String inputToRead) {
	if (!automata.StringChecker.isAlphanumeric(inputToRead))
	    throw new IllegalArgumentException("Label must be alphanumeric!");
	myInputToRead = inputToRead;
	getAutomaton().transitionChanged(this);
    }
    
    /**
     * Returns the string to pop from stack portion of the transition 
     * label for this transition.
     */
    public String getStringToPop() {
	return myStringToPop;
    }

    /**
     * Sets the string to pop from stack portion of the transition 
     * label for this transition.
     * @param stringToPop the string to pop from the stack.
     */
    public void setStringToPop(String stringToPop) {
	if (!automata.StringChecker.isAlphanumeric(stringToPop))
	    throw new IllegalArgumentException("Pop string must "+
					       "be alphanumeric!");
	myStringToPop = stringToPop;
	getAutomaton().transitionChanged(this);
    }

    /**
     * Returns the string to push on to the stack portion of the 
     * transition label for this transition.
     */
    public String getStringToPush() {
	return myStringToPush;
	
    }

    /**
     * Sets the string to push on to the stack portion of the 
     * transition label for this transition.
     * @param stringToPush the string to push on to the stack.
     */
    public void setStringToPush(String stringToPush) {
	if (!automata.StringChecker.isAlphanumeric(stringToPush))
	    throw new IllegalArgumentException("Push string must "+
					       "be alphanumeric!");
	myStringToPush = stringToPush;
	getAutomaton().transitionChanged(this);
    }

    /**
     * Returns the description for this transition.
     * @return the description, in this case, the input to read,
     * the string to pop off the stack, and the string to push on
     * the stack.
     */
    public String getDescription() {
	String input = getInputToRead();
	if (input.length() == 0) input = "\u03BB";
	String toPop = getStringToPop();
	if (toPop.length() == 0) toPop = "\u03BB";
	String toPush = getStringToPush();
	if (toPush.length() == 0) toPush = "\u03BB";
	return input + " , " + toPop + " ; " + toPush;
    }

    /**
     * Returns the hashcode for this transition.
     * @return the hashcode for this transition
     */
    public int hashCode() {
	return super.hashCode() ^
	    myInputToRead.hashCode() ^
	    myStringToPop.hashCode() ^
	    myStringToPush.hashCode();
    }

    /**
     * Tests this transition against another object for equality.
     * @param object the object to test for equality
     * @return <CODE>true</CODE> if this transition equals the passed
     * in object, <CODE>false</CODE> otherwise
     */
    public boolean equals(Object object) {
	try {
	    PDATransition t = (PDATransition) object;
	    return super.equals(object) &&
		myInputToRead.equals(t.myInputToRead) &&
		myStringToPop.equals(t.myStringToPop) &&
		myStringToPush.equals(t.myStringToPush);
	} catch (ClassCastException e) {
	    return false;
	}
    }

    /**
     * Returns a string representation of this object.  This is the
     * same as the string representation for a regular transition
     * object, with the additional fields tacked on.
     * @see automata.Transition#toString
     * @return a string representation of this object
     */
    public String toString() {
	return super.toString() + ": \"" + getInputToRead() + "\"" + 
	    ": \"" + getStringToPop() + "\"" + ": \"" + 
	    getStringToPush() + "\"" ;
    }

    /** The input to read portion of the transition label. */
    protected String myInputToRead;
    /** The string to pop off the stack. */
    protected String myStringToPop;
    /** The string to push on the stack. */
    protected String myStringToPush;
}
