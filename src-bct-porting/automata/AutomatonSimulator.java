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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * The automaton it.unimib.disco.lta.conFunkHealer.simulator object simulates the behavior of an 
 * automaton.  It takes an automaton object and an input string
 * and runs the machine on the given input.  This is the root class
 * for the simulators of all forms of automata, including FSA, PDA,
 * and Turing machines.
 *
 * @author Ryan Cavalcante
 */

public abstract class AutomatonSimulator {
    /**
     * Creates an instance of <CODE>AutomatonSimulator</CODE>.  
     */
    public AutomatonSimulator(Automaton automaton) {
	myAutomaton = automaton;
	myConfigurations = new HashSet();
    }

    /**
     * Returns an array of Configuration objects that represent the
     * possible initial configuration of the automaton, before any input
     * has been processed.
     * @param input the input string.
     */
    public abstract Configuration[] getInitialConfigurations(String input);

    /**
     * Simulates one step for a particular configuration, adding
     * all possible configurations reachable in one step to 
     * set of possible configurations.
     * @param configuration the configuration to simulate the one
     * step on.
     */
    public abstract ArrayList stepConfiguration(Configuration config);

     /**
     * Returns true if the simulation of the input string on the 
     * automaton left the machine in an accept state (the criteria
     * for "accept" is defined differently for the different
     * automata).
     * @return true if the simulation of the input string on the 
     * automaton left the machine in an "accept" state.
     */
    public abstract boolean isAccepted();

    /**
     * Runs the automaton on the input string.
     * @param input the input string to be run on the 
     * automaton
     * @return true if the automaton accepts the input
     */
    public abstract boolean simulateInput(String input);
    
    /** The automaton that the string will be run on. */
    protected Automaton myAutomaton;
    /** The set of configurations the machine could possibly be in
     * at a given moment in the simulation. */
    protected Set myConfigurations;
}
