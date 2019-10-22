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

import java.util.ArrayList;

import automata.Automaton;
import automata.ClosureTaker;
import automata.Configuration;
import automata.State;
import automata.Transition;

public class PDAStepWithClosureSimulator extends PDAStepByStateSimulator {

    public PDAStepWithClosureSimulator(Automaton automaton) {
	super(automaton);
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
	State init = myAutomaton.getInitialState();
	State[] closure = ClosureTaker.getClosure(init, myAutomaton);
	Configuration[] configs = new Configuration[closure.length];
	for(int k = 0; k < closure.length; k++) {
	    CharacterStack stack = new CharacterStack();
	    stack.push("Z");
	    configs[k] = new PDAConfiguration(closure[k], null, input,
					      input, stack);
	}
	//Configuration[] configs = new Configuration[1];
	//CharacterStack stack = new CharacterStack();
	//stack.push("Z");
	//configs[0] = new PDAConfiguration(myAutomaton.getInitialState(),
	//		    null, input, stack);
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
		State[] closure =
		    ClosureTaker.getClosure(toState,myAutomaton);
		for(int i = 0; i < closure.length; i++) {
		    CharacterStack cstack = new CharacterStack(stack);
		    PDAConfiguration configurationToAdd = 
			new PDAConfiguration(closure[i], configuration, 
					     totalInput, input, cstack);
		    list.add(configurationToAdd);
		}
		
	    }
	}
	return list;
    }


}
