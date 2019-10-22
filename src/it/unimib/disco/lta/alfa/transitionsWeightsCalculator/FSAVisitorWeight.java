package it.unimib.disco.lta.alfa.transitionsWeightsCalculator;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import com.sun.org.apache.xpath.internal.operations.Equals;

import automata.State;
import automata.Transition;
import automata.fsa.FSATransition;

public class FSAVisitorWeight implements FSAVisitor {

	private int sequenceLength;
	private LinkedList<TransitionsSequence> sequences = new LinkedList<TransitionsSequence>();
	private TransitionsSequence curSequence;
	private HashMap<TransitionsSequence,Integer> sequencesCount = new HashMap<TransitionsSequence, Integer>();
	
	public static class TransitionsSequence{
		
		private Transition[] transitions;
		private int elements;

		TransitionsSequence(int elementsNumber){
			transitions = new Transition[elementsNumber];
			elements = 0;
		}
		
		public void push(Transition t){
			transitions[elements++]=t;
		}
		
		public TransitionsSequence duplicateButFirst(){
			TransitionsSequence result = new TransitionsSequence(transitions.length);
			if ( elements < transitions.length -1 ){
				System.arraycopy(transitions, 0, result.transitions, 0, elements);
			} else {
				System.arraycopy(transitions, 1, result.transitions, 0, elements-1); //Copy the last k-1
			}
			return result;
		}
		
		public boolean equals(Object o ){
			if  ( o == null ){
				return false;
			}
			
			if ( ! ( o instanceof TransitionsSequence  ) ){
				return false;
			}
			TransitionsSequence rhs = (TransitionsSequence) o;
			
			if ( rhs.elements != this.elements ){
				return false;
			}
			
			
			return this.hashCode() == rhs.hashCode();
		}
		
		public int hashCode(){
			return toString().hashCode();
		}

		private String getName(FSATransition transition) {
			return transition.getFromState()+"-"+transition.getLabel()+"-"+transition.getToState();
		}
		
		public String toString(){
			
			StringBuffer sb = new StringBuffer();
			for ( int i = 0; i < elements; ++i){
				if ( i > 0 ){
					sb.append(";");
				}
				sb.append(getName((FSATransition) transitions[i]));
			}
			return sb.toString();
		}
		
	}
	
	public FSAVisitorWeight( int sequenceLength){
		this.sequenceLength = sequenceLength;
	}
	
	public boolean visit(State state) {
		
		if ( sequences.size() == 0 ){
			curSequence = new TransitionsSequence(sequenceLength);
		} else {
			curSequence = sequences.remove(0);
		}
		return true;
	}

	public boolean visit(Transition transition) {
		
		TransitionsSequence sequence = curSequence.duplicateButFirst();
		sequence.push(transition);
		sequences.add(sequence);
		Integer value = sequencesCount.get(sequence);
		if ( value == null ){
			sequencesCount.put(sequence, 1);
		} else {
			sequencesCount.put(sequence, value + 1);
		}
		
		return true;
	}
	
	public Map<TransitionsSequence,Integer> getSequences(){
		return sequencesCount;
	}

	public boolean newConfig() {
		// TODO Auto-generated method stub
		return true;
	}

}
