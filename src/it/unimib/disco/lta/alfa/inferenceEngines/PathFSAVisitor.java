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
package it.unimib.disco.lta.alfa.inferenceEngines;

import it.unimib.disco.lta.alfa.transitionsWeightsCalculator.FSAVisitor;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import automata.State;
import automata.Transition;

public class PathFSAVisitor implements FSAVisitor {
	
	public static class TransitionSequence implements Cloneable {
		ArrayList<Transition> transitions = new ArrayList<Transition>();
		
		public void add(Transition t ){
			transitions.add(t);
		}
		
		public TransitionSequence clone(){
			TransitionSequence result = new TransitionSequence();
			result.transitions.addAll(transitions);
			return result;
		}

		public int size() {
			return transitions.size();
		}

		public Transition getTransition(int index) {
			return transitions.get(index);
		}

		public State lastState() {
			return transitions.get(transitions.size()-1).getToState();
		}

		public State firstState() {
			return transitions.get(0).getFromState();
		}
		
	}
	
	private LinkedList<TransitionSequence> sequences = new LinkedList<TransitionSequence>();
	private TransitionSequence currentSequence;
	private int counter; 
	private int curCount;
	private LinkedList<Integer> countersSequence = new LinkedList<Integer>();
	private int transitionsCount;
	
	public boolean visit(State state) {

		countersSequence.add(curCount);
		curCount = 0;
		if ( --transitionsCount <= 0 ){
			if ( sequences.size() == 0 ){
				transitionsCount = 1;
				currentSequence = new TransitionSequence();
			} else {
				transitionsCount = countersSequence.removeFirst();
				currentSequence = sequences.get(counter++);
			}

		}
		return true;

	}

	public boolean visit(Transition transition) {
		TransitionSequence sequence = currentSequence.clone();
		sequence.add(transition);
		sequences.add(sequence);
		return true;
	}
	
	public List<TransitionSequence> getSequences(){
		LinkedList<TransitionSequence> results = new LinkedList<TransitionSequence>();
		
		if ( sequences.size() == 0 ){
			return sequences;
		}
		
		int maxSize = sequences.getLast().size();
		for ( int i = sequences.size()-1; i >=0; i-- ){
			TransitionSequence seq = sequences.get(i);
			if ( seq.size() < maxSize )
				break;
			results.add(seq);
		}
		
		return results;
	}

	public boolean newConfig() {
		curCount++;
		return true;
	}

}
