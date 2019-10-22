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
 
package automata.turing;

import java.util.ArrayList;
import java.util.Iterator;

import automata.Automaton;
import automata.AutomatonSimulator;
import automata.Configuration;
import automata.State;
import automata.Transition;

/**
 * The TM it.unimib.disco.lta.conFunkHealer.simulator progresses TM configurations on a possibly
 * multitape Turing machine.
 *
 * @author Thomas Finley
 */

public class TMSimulator extends AutomatonSimulator {
    /**
     * Creates a TM it.unimib.disco.lta.conFunkHealer.simulator for the given automaton.
     * @param automaton the machine to simulate
     * @throws IllegalArgumentException if this automaton is not a
     * Turing machine
     */
    public TMSimulator(Automaton automaton) {	
	super(automaton);
	if (!(automaton instanceof TuringMachine))
	    throw new IllegalArgumentException
		("Automaton is not a Turing machine, but a "
		 +automaton.getClass());
    }

    /**
     * Returns a TMConfiguration object that represents the initial
     * configuration of the TM, before any input has been processed.
     * This returns an array of length one.  This method exists only
     * to provide compatibility with the general definition of
     * <CODE>AutomatonSimulator</CODE>.  One should use the version of
     * this function that accepts an array of inputs instead.
     * @param input the input string
     */
    public Configuration[] getInitialConfigurations(String input) {
	int tapes = ((TuringMachine)myAutomaton).tapes();
	String[] inputs=new String[tapes];
	for (int i=0; i<tapes; i++) inputs[i]=input;
	return getInitialConfigurations(inputs);
    }

    /**
     * Returns a TMConfiguration object that represents the initial
     * configuration of the TM, before any input has been processed.
     * This returns an array of length one.
     * @param input the input strings
     */
    public Configuration[] getInitialConfigurations(String[] inputs) {
	Tape[] tapes = new Tape[inputs.length];
	for (int i=0; i<tapes.length; i++) tapes[i]=new Tape(inputs[i]);
	Configuration[] configs = new Configuration[1];
	configs[0] = new TMConfiguration
	    (myAutomaton.getInitialState(), null, tapes);
	return configs;
    }

    /**
     * Simulates one step for a particular configuration, adding
     * all possible configurations reachable in one step to 
     * set of possible configurations.
     * @param configuration the configuration to simulate the one
     * step on.
     */
    public ArrayList stepConfiguration(Configuration config) {
	ArrayList list = new ArrayList();
	TMConfiguration configuration = (TMConfiguration) config;
	/** get all information from configuration. */

	State currentState = configuration.getCurrentState();
	Transition[] transitions = 
	    myAutomaton.getTransitionsFromState(currentState);
	for (int k = 0; k < transitions.length; k++) {
	    TMTransition t = (TMTransition) transitions[k];
	    Tape[] tapes = configuration.getTapes();
	    boolean okay = true;
	    for (int i=0; okay && i<tapes.length; i++) {
		String charAtHead = tapes[i].read();
		String toRead = t.getRead(i);
		if (!charAtHead.equals(toRead)) okay=false;
	    }
	    if (!okay) continue; // One of the toReads wasn't satisfied.
	    State toState = t.getToState();
	    Tape[] tapes2 = new Tape[tapes.length];
	    for (int i=0; i<tapes.length; i++) {
		tapes2[i]=new Tape(tapes[i]);
		String toWrite = t.getWrite(i);
		String direction = t.getDirection(i);
		tapes2[i].write(toWrite);
		tapes2[i].moveHead(direction);
	    }
	    TMConfiguration configurationToAdd = 
		new TMConfiguration(toState, configuration, tapes2);
	    list.add(configurationToAdd);
	}
	return list;
    }

    /**
     * Returns true if the simulation of the input string on the 
     * automaton left the machine in a final state.    
     * @return true if the simulation of the input string on the 
     * automaton left the machine in a final state.
     */
    public boolean isAccepted() {
	Iterator it = myConfigurations.iterator();
	while (it.hasNext()) {
	    TMConfiguration configuration = (TMConfiguration) it.next();
	    State currentState = configuration.getCurrentState();
	    /** check if in final state.  contents of tape are
	     * irrelevant. */
	    if(myAutomaton.isFinalState(currentState)) {
		return true;
	    }
	} 
	return false;
    }
    
    /**
     * Runs the automaton on the input string.
     * @param input the input string to be run on the 
     * automaton
     * @return true if the automaton accepts the input
     */
    public boolean simulateInput(String input) {
	/** clear the configurations to begin new simulation. */
	myConfigurations.clear();
	Configuration[] initialConfigs = getInitialConfigurations(input);
	for(int k = 0; k < initialConfigs.length; k++) {
	    TMConfiguration initialConfiguration = 
		(TMConfiguration) initialConfigs[k];
	    myConfigurations.add(initialConfiguration);
	} 
	while (!myConfigurations.isEmpty()) {
	    if(isAccepted()) return true;
	    ArrayList configurationsToAdd = new ArrayList();
	    Iterator it = myConfigurations.iterator();
	    while (it.hasNext()) {
		TMConfiguration configuration = (TMConfiguration) it.next();
		ArrayList configsToAdd = stepConfiguration(configuration);
		configurationsToAdd.addAll(configsToAdd);
		it.remove();
	    } 
	    myConfigurations.addAll(configurationsToAdd);
	}
	return false;
    }
 
}
