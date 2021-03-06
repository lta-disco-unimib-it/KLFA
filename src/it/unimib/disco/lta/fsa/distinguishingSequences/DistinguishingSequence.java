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
package it.unimib.disco.lta.fsa.distinguishingSequences;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import automata.State;

public class DistinguishingSequence<Symbol extends Object> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private List<Symbol> sequence = new ArrayList<Symbol>();
	private List<State> states = new ArrayList<State>();
	private State startingState;
	
	public DistinguishingSequence( State startingState ){
		this.startingState = startingState;
	}
	
	public DistinguishingSequence(DistinguishingSequence<Symbol> dist) {
		sequence.addAll(dist.sequence);
		states.addAll(dist.states);
		startingState = dist.startingState;
	}

	public void addSymbol( Symbol symbol, State arrivalState ){
		sequence.add(symbol);
		states.add(arrivalState);
	}

	public State getStartingState() {
		return startingState;
	}
	
	public boolean equals ( Object o ){
		if ( o == null ){
			return false;
		}
		
		if ( ! ( o instanceof DistinguishingSequence ) ){
			return false;
		}
		
		DistinguishingSequence<Symbol> rhs = (DistinguishingSequence<Symbol>) o;
		
		if ( ! sequence.equals(rhs.sequence) ){
			return false;
		}
		
		if ( ! states.equals(rhs.states) ){
			return false;
		}
		
		return true;
	}
	
	public String toString(){
		StringBuffer sb = new StringBuffer();
		sb.append(startingState.getName());
		sb.append(" ");
		for ( int i = 0 ; i < states.size(); ++i ){
			sb.append(sequence.get(i));
			sb.append(" ");
			sb.append(states.get(i).getName());
			sb.append(" ");
		}
		
		return sb.toString();
	}

	public Object getLastState() {
		int size = states.size();
		if ( size == 0 ){
			return startingState;
		}
		return states.get(size-1);
	}
}
