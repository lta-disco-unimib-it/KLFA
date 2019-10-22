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
package it.unimib.disco.lta.fsa.closure;

import java.util.Collection;
import java.util.Set;

import automata.Automaton;
import automata.State;

/**
 * Class used to find all states reachable with lambda transitions and then add
 * them to the list.
 * 
 */
public class ClosureCalculator {
	
	/**
	 * Method that takes in input a list of states and then search from them all
	 * possible states reachable with lambda transitions. Add to the list only
	 * new states.
	 * @param stateList the list of state from which search lambda transitions.
	 * @param automaton the automaton.
	 */
	public static Set<State> calculateClosure(Collection<State> stateList, Automaton automaton) {
		ClosureIncrementer ci = new ClosureIncrementer();
		
		for(State state : stateList) {
			ci.addClosure(state, automaton);
		}
		
		return ci.getVisited();
	}
}
