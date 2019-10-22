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
 
package automata;


/**
 * This class represents a configuration of an automaton for
 * simulation of input on an automaton (i.e. the state the automaton
 * is currently in, the unprocessed input, and the contents of the
 * tapes/stack if applicable).  A <CODE>Configuration</CODE> object is
 * simply a data structure that stores this information, and does not
 * do any work of progressing this configuration to other
 * configurations as a it.unimib.disco.lta.conFunkHealer.simulator would.
 * 
 * @see automata.AutomatonSimulator
 *
 * @author Ryan Cavalcante
 */

public abstract class Configuration {
    /**
     * Instantiates a new configuration.
     * @param state the state the automaton is currently in.
     */
    public Configuration(State state, Configuration parent) {
	myCurrentState = state;
	this.parent = parent;
    }

    /**
     * Returns the state the automaton is currently occupying.
     * @return the state the automaton is currently occupying.
     */
    public State getCurrentState() {
	return myCurrentState;
    }

    /**
     * Sets current state.
     * @param state the state the automaton is currently in.
     */
    public void setCurrentState(State state) {
	myCurrentState = state;
    }

    /**
     * Returns a string representation of this object.  The string
     * returned is the string representation of the current state.
     * @return a string representation of this object
     */
    public String toString() {
	return "[" + getCurrentState().toString() + "]";
    }

    /**
     * Returns the "parent" for this configuration, that is, the
     * configuration that led to this configuration.
     * @see automata.AutomatonSimulator#stepConfiguration
     * @return the <CODE>Configuration</CODE> that led to this
     * configuration, or <CODE>null</CODE> if this is the initial
     * configuration
     */
    public Configuration getParent() {
	return parent;
    }

    /**
     * Returns if this configuration is an accepting configuration.
     * @return <CDOE>true</CODE> if this configuration is an accepting
     * configuration, <CODE>false</CODE> otherwise
     */
    public abstract boolean isAccept();

    /**
     * The basic equals method considers two configurations equal if
     * they both have the same state, and proceeded from the same
     * configuration.  By "the same configuration" it is meant a
     * comparison of the parents via the == operation rather than the
     * <CODE>.equals()</CODE> operation, since the latter would lead
     * to rather lengthly traversions.
     * @param configuration the configuration to test for equality
     */
    public boolean equals(Object configuration) {
	Configuration config = (Configuration) configuration;
	if (parent != config.parent) return false;
	return config.myCurrentState == myCurrentState;
    }

    /**
     * Returns the base hash code for a configuration.  Subclasses
     * should override so as not to have all configurations with the
     * same parent configuration and state map to the same hash entry.
     * @return a value for hashing
     */
    public int hashCode() {
	/*return myCurrentState.hashCode() ^
	  (parent == null ? 0 : parent.hashCode());*/
	return myCurrentState.hashCode() ^
	    (parent == null ? 0 : parent.primitiveHashCode());
    }

    /**
     * Returns the "primitive" hash code of the superclass, which is
     * the generic hash code of the object.
     */
    private int primitiveHashCode() {
	return super.hashCode();
    }
    
    /** The state the automaton is currently in. */
    private State myCurrentState;
    /** The parent for this configuration. */
    private Configuration parent;
}
