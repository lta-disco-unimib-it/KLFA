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

import automata.Automaton;
import automata.Configuration;
import automata.State;

/**
 * A <CODE>PSAConfiguration</CODE> object is a <CODE>Configuration</CODE> 
 * object with additional fields for the input string and the stack 
 * contents.  The current state of the automaton, the stack contents, 
 * and the unprocessed input are the only necessary data for the 
 * simulation of a PDA.
 *
 * @author Ryan Cavalcante
 */

public class PDAConfiguration extends Configuration {
    /**
     * Instantiates a new PDAConfiguration.
     * @param state the state the automaton is currently in.
     * @param parent the immediate ancestor for this configuration
     * @param input the original input.
     * @param unprocessed the unprocessed input.
     * @param stack the stack contents
     */
    public PDAConfiguration(State state, PDAConfiguration parent,
			    String input, String unprocessed,
			    CharacterStack stack) {
	super(state, parent);
	myInput = input;
	myUnprocessedInput = unprocessed;
	myStack = stack;
    }

    /**
     * Returns the original input.
     * @return the original input.
     */
    public String getInput() {
	return myInput;
    }

    /**
     * Returns the unprocessed input.
     * @return the unprocessed input.
     */
    public String getUnprocessedInput() {
	return myUnprocessedInput;
    }

    /**
     * Changes the unprocessed input.
     * @param input the string that will represent the unprocessed
     * input of the PDA.
     */
    public void setUnprocessedInput(String input) {
	myUnprocessedInput = input;
    }

    /**
     * Returns the stack.
     * @return the stack.
     */
    public CharacterStack getStack() {
	return myStack;
    }

    /**
     * Returns a string representation of this object.  This is the 
     * same as the string representation for a regular configuration
     * object, with the additional fields tacked on.
     * @see automata.Configuration#toString
     * @return a string representation of this object.
     */
    public String toString() {
	return super.toString() + " INPUT: " + getUnprocessedInput() + 
	    " STACK: " + myStack.toString();
    }

    /**
     * Returns <CODE>true</CODE> if this configuration is an accepting
     * configuration, which in this case means that there is no more
     * input and our state is an accept state.
     * @return <CODE>true</CODE> if this configuration is accepting,
     * <CODE>false</CODE> otherwise
     */
    public boolean isAccept() {
	if (getUnprocessedInput().length() != 0) return false;
	State s = getCurrentState();
	Automaton a = s.getAutomaton();
	return a.isFinalState(s);
    }

    /**
     * Determines whether this configuration equals another
     * configuration.  Two PDA configurations are equal if they have
     * the same stack, and if they satisfy the
     * <CODE>Configuration.equals()</CODE> method.
     * @see automata.Configuration#equals
     * @param configuration the configuration to check for equality
     * @return <CODE>true</CODE> if the configuration is equal to this
     * one, <CODE>false</CODE> if it is not
     */
    public boolean equals(Object configuration) {
	if (configuration == this) return true;
	try {
	    return super.equals(configuration) &&
		myUnprocessedInput.equals
		(((PDAConfiguration)configuration).myUnprocessedInput) &&
		myStack.equals(((PDAConfiguration)configuration).myStack);
	} catch (ClassCastException e) {
	    return false;
	}
    }

    /**
     * Returns a hash code for this configuration.
     * @return a hash code for this configuration
     */
    public int hashCode() {
	return super.hashCode() ^ myStack.hashCode() ^
	    myUnprocessedInput.hashCode();
    }

    /** The original input. */
    protected String myInput;
    /** The unprocessed input. */
    protected String myUnprocessedInput;
    /** The stack of the PDA. */
    protected CharacterStack myStack;
}
