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
package it.unimib.disco.lta.fsa.utils;

import static org.junit.Assert.*;

import java.awt.Point;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import automata.State;
import automata.Transition;
import automata.fsa.FSATransition;
import automata.fsa.FiniteStateAutomaton;

public class AutomataMinimizerTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testMinimizeFSA() {
		FiniteStateAutomaton nfa = new FiniteStateAutomaton();
		
		
		State state1 = nfa.createState(new Point(0,0));
		State state2 = nfa.createState(new Point(0,0));
		Transition t = new FSATransition(state1,state2,"A");
		Transition t2 = new FSATransition(state1,state2,"A");
		nfa.setInitialState(state1);
		nfa.addFinalState(state2);
		nfa.addTransition(t);
		nfa.addTransition(t2);
		
		
		AutomataMinimizer minimizer = new AutomataMinimizer();
		FiniteStateAutomaton dfa = minimizer.minimizeFSA(nfa);
		
		assertEquals(2, dfa.getStates().length );
		assertEquals(1, dfa.getTransitions().length );
		assertEquals("A", dfa.getTransitions()[0].getDescription() );
	}

}
