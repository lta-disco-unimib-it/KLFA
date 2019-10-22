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
package tools.fsa2xml.codec.utility;

import tools.fsa2xml.codec.impl.LabelFactory;
import automata.Transition;
import automata.fsa.FSATransition;
import automata.fsa.FiniteStateAutomaton;

public class FSAOptimization {

    public static void optimizeFSA(FiniteStateAutomaton fsa) {
    	LabelFactory labelFactory = new LabelFactory();
		for ( Transition t : fsa.getTransitions() ){
			String uniqueLabel = labelFactory.getLabel(t.getDescription());
			fsa.removeTransition(t);
			
			Transition newT = new FSATransition( t.getFromState(), t.getToState(), uniqueLabel );
			fsa.addTransition(newT);
		}
	}
	
}
