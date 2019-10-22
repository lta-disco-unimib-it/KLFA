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

import java.util.ArrayList;

import automata.Automaton;
import automata.ClosureTaker;
import automata.Configuration;
import automata.State;
import automata.Transition;

/**
 * The FSA step with closure it.unimib.disco.lta.conFunkHealer.simulator object simulates the behavior 
 * of a finite state automaton.  It takes an FSA object and an 
 * input string and runs the machine on the input.  It simulates 
 * the machine's behavior by stepping one state at a time, then
 * taking the closure of each state reached by one step of the
 * machine to find out all possible configurations of the machine
 * at any given point in the simulation.
 *
 * @author Ryan Cavalcante
 */

public class FSAStepWithClosureSimulator extends FSAStepByStateSimulator {
    /**
     * Creates an instance of <CODE>StepWithClosureSimulator</CODE>
     */
    public FSAStepWithClosureSimulator(Automaton automaton) {
	super(automaton);
    }
    
    /**
     * Returns an array of FSAConfiguration objects that represent 
     * the possible initial configurations of the FSA, before any input
     * has been processed, calculated by taking the closure of the
     * initial state.
     * @param input the input string.
     */
    public Configuration[] getInitialConfigurations(String input) {
	State init = myAutomaton.getInitialState();
	State[] closure = ClosureTaker.getClosure(init, myAutomaton);
	Configuration[] configs = new Configuration[closure.length];
	for(int k = 0; k < closure.length; k++) {
	    configs[k] = new FSAConfiguration(closure[k], null, input, input);
	}
	return configs;
    }

    /**
     * Simulates one step for a particular configuration, adding
     * all possible configurations reachable in one step to 
     * set of possible configurations.
     * @param config the configuration to simulate the one
     * step on.
     */
    public ArrayList stepConfiguration(Configuration config) {
    	
	ArrayList list = new ArrayList();
	FSAConfiguration configuration = (FSAConfiguration) config;
	/** get all information from configuration. */
	String unprocessedInput = configuration.getUnprocessedInput();
	String totalInput = configuration.getInput();
	State currentState = configuration.getCurrentState();
	//System.out.println("\n\n\nStepConfiguration");
	//System.out.println("unprocessedInput "+unprocessedInput);
	//System.out.println("totalInput : "+totalInput);
	Transition[] transitions = 
	    myAutomaton.getTransitionsFromState(currentState);
	for (int k = 0; k < transitions.length; k++) {
	    FSATransition transition = (FSATransition) transitions[k];
	    /** get all information from transition. */
	    String transLabel = transition.getLabel();
    	//AGGIUNTE
	//System.out.println("Controllo : originale : "+transLabel+" - "+unprocessedInput);
	    if(transLabel.length() > 0) {
//		if(unprocessedInput.startsWith(transLabel)) {
		if(unprocessedInput.equals(transLabel)) {
		    String input = "";
		    if(transLabel.length() < unprocessedInput.length()) {
			input = 
			    unprocessedInput.substring(transLabel.length()); 
		    }
		    State toState = transition.getToState();
		    State[] closure = 
			ClosureTaker.getClosure(toState,myAutomaton);
		    for(int i = 0; i < closure.length; i++) {
			FSAConfiguration configurationToAdd = 
			    new FSAConfiguration(closure[i], 
						 configuration,
						 totalInput,
						 input);
			list.add(configurationToAdd);
		    }
		}
	    }
	}
	return list;
    }


}
