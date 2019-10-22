package it.unimib.disco.lta.alfa.transitionsWeightsCalculator;

import java.util.Iterator;
import java.util.LinkedList;

import automata.ClosureTaker;
import automata.State;
import automata.Transition;
import automata.fsa.FSAConfiguration;
import automata.fsa.FSATransition;
import automata.fsa.FiniteStateAutomaton;
import grammarInference.Record.Symbol;
import grammarInference.Record.Trace;

public class FSATraverser {
	private Trace trace;
	private FiniteStateAutomaton fsa;
	private FSAVisitor visitor;
	private int start;

	public FSATraverser( FiniteStateAutomaton fsa, Trace trace,FSAVisitor visitor, int start ){
		this.fsa = fsa;
		this.trace = trace;
		this.visitor = visitor;
		this.start = start;
	}

	public void traverse() {
		traverse(fsa.getInitialState());
	}
	
	public void traverse(State initStateSimulation) {
		
		Iterator<String> sit = trace.getSymbolIterator();
		String next = null;
		String symbol;
		
		for ( int i = 0; i < start; ++i){
			sit.next();
		}
		
		if ( sit.hasNext() ){
			next = sit.next();
		}
		
		
		FSAConfiguration c = new FSAConfiguration(
				initStateSimulation,
				null,
				next,
				next);
		
		curLevel.add(c);
		
		while (next != null && curLevel.size() > 0 ){
			traversed++;
			if ( sit.hasNext() ){
				next = sit.next();
			} else {
				next = null;
			}
			
			while( curLevel.size() > 0 ){
				FSAConfiguration curConfig = curLevel.remove(0);
				traverse(curConfig,next);
			}
			
			curLevel = nextLevel;
			nextLevel = new LinkedList<FSAConfiguration>();
		}
		
	}
	
	public int traversed = 0;
	LinkedList<FSAConfiguration> nextLevel = new LinkedList<FSAConfiguration>();
	LinkedList<FSAConfiguration> curLevel = new LinkedList<FSAConfiguration>();
	
	private void traverse(FSAConfiguration curConfig, String next) {
		// TODO Auto-generated method stub
		FSAConfiguration configuration = curConfig;
		/** get all information from configuration. */
		String unprocessedInput = configuration.getUnprocessedInput();
		String totalInput = configuration.getInput();
		State currentState = configuration.getCurrentState();
		
		if ( ! visitor.visit(currentState) )
			return;
		
		
		Transition[] transitions = 
			fsa.getTransitionsFromState(currentState);
		
		
		for (int k = 0; k < transitions.length; k++) {
			FSATransition transition = (FSATransition) transitions[k];
			
			
			/** get all information from transition. */
			String transLabel = transition.getLabel();
			//AGGIUNTE
			//System.out.println("Controllo : originale : "+transLabel+" - "+unprocessedInput);
			if(transLabel.length() > 0) {
//				if(unprocessedInput.startsWith(transLabel)) {
				if(unprocessedInput.equals(transLabel)) {
					if ( ! visitor.visit(transition) ){
						continue;
					}
					
					State toState = transition.getToState();
					State[] closure = 
						ClosureTaker.getClosure(toState,fsa);
					if ( next != null ){
						for(int i = 0; i < closure.length; i++) {
							
							FSAConfiguration configurationToAdd = 
								new FSAConfiguration(closure[i], 
										configuration,
										totalInput,
										next);
							nextLevel.add(configurationToAdd);
							
						}
					}
				}
			}
		}
	}

	public int getTraversed() {
		return traversed;
	}
}
