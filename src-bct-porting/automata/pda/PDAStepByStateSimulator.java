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
 
package automata.pda;

import java.util.ArrayList;
import java.util.Iterator;

import automata.Automaton;
import automata.AutomatonSimulator;
import automata.Configuration;
import automata.State;
import automata.Transition;

/**
 * The PDA it.unimib.disco.lta.conFunkHealer.simulator object simulates the behavior of a pushdown
 * automaton.  Given a  PDA object and an input string, it can
 * determine whether the machine accepts the input or not.
 *
 * @author Ryan Cavalcante
 */

public class PDAStepByStateSimulator extends AutomatonSimulator {
    /**
     * Creates a PDA it.unimib.disco.lta.conFunkHealer.simulator for the given automaton.
     * @param automaton the machine to simulate
     */
    public PDAStepByStateSimulator(Automaton automaton) {
	super(automaton);
	/** default acceptance is by final state. */
	myAcceptance = FINAL_STATE;
    }

    /**
     * Returns a PDAConfiguration array that represents the
     * initial configuration of the PDA, before any input
     * has been processed.  It returns an array of length
     * one.
     * @param input the input string.
     */
    public Configuration[] getInitialConfigurations(String input) {
	/** The stack should contain the bottom of stack marker. */
	Configuration[] configs = new Configuration[1];
	CharacterStack stack = new CharacterStack();
	stack.push("Z");
	configs[0] = new PDAConfiguration(myAutomaton.getInitialState(),
				    null, input, input, stack);
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
	PDAConfiguration configuration = (PDAConfiguration) config;
	/** get all information from configuration. */
	String unprocessedInput = configuration.getUnprocessedInput();
	String totalInput = configuration.getInput();
	State currentState = configuration.getCurrentState();
	Transition[] transitions = 
	    myAutomaton.getTransitionsFromState(currentState);
	for (int k = 0; k < transitions.length; k++) {
	    PDATransition transition = (PDATransition) transitions[k];
	    /** get all information from transition. */
	    String inputToRead = transition.getInputToRead();
	    String stringToPop = transition.getStringToPop();
	    CharacterStack tempStack = configuration.getStack();
	    /** copy stack object so as to not alter original. */
	    CharacterStack stack = new CharacterStack(tempStack);
	    String stackContents = stack.pop(stringToPop.length());
	    if(unprocessedInput.startsWith(inputToRead) &&
	       stringToPop.equals(stackContents)) {
		String input = "";
		if(inputToRead.length() < unprocessedInput.length()) {
		    input = unprocessedInput.substring(inputToRead.length());
		}
		State toState = transition.getToState();
		stack.push(transition.getStringToPush());
		PDAConfiguration configurationToAdd = 
		    new PDAConfiguration(toState, configuration, totalInput,
					 input, stack);
		list.add(configurationToAdd);
	    }
	}
	return list;
    }

    /**
     * Sets acceptance to accept by final state.
     */
    public void setAcceptByFinalState() {
	myAcceptance = FINAL_STATE;
    }

    /**
     * Sets acceptance to accept by empty stack.
     */
    public void setAcceptByEmptyStack() {
	myAcceptance = EMPTY_STACK;
    }

    /**
     * Returns true if the simulation of the input string on the 
     * automaton left the machine in a final state.  If the entire
     * input string is processed and the machine is in a final state,
     * return true.
     * @return true if the simulation of the input string on the 
     * automaton left the machine in a final state.
     */
    public boolean isAccepted() {
	Iterator it = myConfigurations.iterator();
	while (it.hasNext()) {
	    PDAConfiguration configuration = (PDAConfiguration) it.next();
	    if(myAcceptance == FINAL_STATE) {
		State currentState = configuration.getCurrentState();
		if(configuration.getUnprocessedInput() == "" &&
		   myAutomaton.isFinalState(currentState)) {
		    return true;
		}
	    }
	    else if(myAcceptance == EMPTY_STACK) {
		CharacterStack stack = configuration.getStack();
		if(configuration.getUnprocessedInput() == "" &&
		   stack.height() == 0) {
		    return true;
		}
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
	    PDAConfiguration initialConfiguration = 
		(PDAConfiguration) initialConfigs[k];
	    myConfigurations.add(initialConfiguration);
	}
 
	while (!myConfigurations.isEmpty()) {
	    if(isAccepted()) return true;
	    ArrayList configurationsToAdd = new ArrayList();
	    Iterator it = myConfigurations.iterator();
	    while (it.hasNext()) {
		PDAConfiguration configuration = (PDAConfiguration) it.next();
		ArrayList configsToAdd = stepConfiguration(configuration);
		configurationsToAdd.addAll(configsToAdd);
		it.remove();
	    } 
	    myConfigurations.addAll(configurationsToAdd);
	}
	return false;
    }
 
    /** The mode of acceptance (either by final state or empty stack). */
    protected int myAcceptance;
    /** The variable to represent accept by empty stack. */
    protected static final int EMPTY_STACK = 0;
    /** The variable to represent accept by final state. */
    protected static final int FINAL_STATE = 1;
    
}
