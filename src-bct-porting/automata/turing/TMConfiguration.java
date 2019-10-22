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
 
package automata.turing;

import automata.Configuration;
import automata.State;

/**
 * A <CODE>TMConfiguration</CODE> object is a <CODE>Configuration</CODE> 
 * object with additional fields for the input string and the tape 
 * contents.  The current state of the automaton and the tape contents, 
 * are the only necessary data for the simulation of a 1-tape 
 * Turing machine.
 *
 * @author Ryan Cavalcante
 */

public class TMConfiguration extends Configuration {
    /**
     * Instantiates a new TMConfiguration.
     * @param state the state the automaton is currently in
     * @param parent the immediate ancestor for this configuration
     * @param tapes the read/write tapes
     */
    public TMConfiguration(State state, TMConfiguration parent, Tape[] tapes) {
	super(state, parent);
	this.myTapes = tapes;
    }

    /**
     * Returns the tapes.
     * @return the tapes
     */
    public Tape[] getTapes() {
	return myTapes;
    }

    /**
     * Returns a string representation of this object.  This is the 
     * same as the string representation for a regular configuration
     * object, with the additional fields tacked on.
     * @see automata.Configuration#toString
     * @return a string representation of this object.
     */
    public String toString() {
	StringBuffer sb = new StringBuffer(super.toString());
	for (int i=0; i<myTapes.length; i++) {
	    sb.append(" TAPE ");
	    sb.append(i);
	    sb.append(": ");
	    sb.append(myTapes[i].toString());
	}
	return sb.toString();
    }

    /**
     * Returns <CODE>true</CODE> if this configuration is an accepting
     * configuration, which in this case means our state is an accept
     * state.
     * @return <CODE>true</CODE> if this configuration is accepting,
     * <CODE>false</CODE> otherwise
     */
    public boolean isAccept() {
	State s = getCurrentState();
	return s.getAutomaton().isFinalState(s);
    }

    /**
     * Compares two TM configurations for equality.  Two
     * configurations are equal if the tapes are equal, and if they
     * arose from the same configuration and are at the same state.
     * @param configuration the configuration to test for equality
     * @return <CODE>true</CODE> if the configurations are equal,
     * <CODE>false</CODE> if they are not
     */
    public boolean equals(Object configuration) {
	if (configuration == this) return true;
	try {
	    if (!super.equals(configuration)) return false;
	    Tape[] tapes = ((TMConfiguration)configuration).myTapes;
	    if (tapes.length != myTapes.length) return false;
	    for (int i=0; i<tapes.length; i++)
		if (!tapes[i].equals(myTapes[i])) return false;
	    return true;
	} catch (ClassCastException e) {
	    return false;
	}
    }

    /**
     * Returns a hash code for this configuration.
     * @return a hash code for this configuration
     */
    public int hashCode() {
	int code = super.hashCode();
	for (int i=0; i<myTapes.length; i++)
	    code = code ^ myTapes[i].hashCode();
	return code;
    }

    /** The tapes. */
    protected Tape[] myTapes;
}
