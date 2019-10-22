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

import automata.Automaton;
import automata.Configuration;
import automata.State;

/**
 * An <CODE>FSAConfiguration</CODE> object is a <CODE>Configuration</CODE> 
 * object with an additional field for the input string.  The current 
 * state of the automaton and the unprocessed input are the only 
 * necessary data for the simulation of an FSA.
 *
 * @author Ryan Cavalcante
 */

public class FSAConfiguration extends Configuration {
    /**
     * Instantiates a new FSAConfiguration.
     * @param state the state the automaton is currently in.
     * @param parent the configuration that is the immediate ancestor
     * of this configuration
     * @param input the input
     * @param unprocessed the unprocessed input
     */
    public FSAConfiguration(State state, FSAConfiguration parent,
			    String input, String unprocessed) {
	super(state, parent);
	myInput = input;
	myUnprocessedInput = unprocessed;
    }

    /**
     * Returns the total input.
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
     * input of the FSA.
     */
    public void setUnprocessedInput(String input) {
	myUnprocessedInput = input;
    }

    /**
     * Returns a string representation of this object.
     * @return a string representation of this object.
     */
    public String toString() {
	return super.toString() + ": " + getUnprocessedInput();
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
     * Checks for equality.  Two FSAConfigurations are equal if they
     * have the same unprocessed input, and satisfy the .equals() test
     * of the base <CODE>Configuration</CODE> class.
     * @see automata.Configuration#equals
     * @param configuration the configuration to check against this
     * one for equality
     * @return <CODE>true</CODE> if the two configurations are equal,
     * <CODE>false</CODE> otherwise
     */
    public boolean equals(Object configuration) {
	if (configuration == this) return true;
	try {
	    return super.equals(configuration) && myUnprocessedInput
		.equals(((FSAConfiguration) configuration).myUnprocessedInput);
	} catch (ClassCastException e) {
	    return false;
	}
    }

    /**
     * Returns a hashcode for this object.
     * @return a hashcode for this object
     */
    public int hashCode() {
	return super.hashCode() ^ myUnprocessedInput.hashCode();
    }

    /** The total input. */
    private String myInput;
    /** The unprocessed input. */
    private String myUnprocessedInput;
}
