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
package util;

import automata.Automaton;
import automata.State;
import automata.Transition;

public class FSAUtil {
	
	public static interface FSAVisitor {
		
		public boolean before ( State state );
		
		public boolean after (  State state );
		
		public boolean before ( Transition t);
		
		public boolean after ( Transition t);
		
	}
	
	public static void depthFirstVisit( State state, FSAVisitor visitor ){
		if ( ! visitor.before(state) )
			return;

		Automaton fsa = state.getAutomaton();
		Transition[] transitionsTo = fsa.getTransitionsFromState(state);
		for ( Transition t : transitionsTo ){
			if ( visitor.before(t) ){

				State toState = t.getToState();

				depthFirstVisit(toState, visitor);

			}
			visitor.after(t);
		}
		visitor.after(state);
	}
	

}
