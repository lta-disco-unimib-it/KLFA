package it.unimib.disco.lta.alfa.transitionsWeightsCalculator;

import it.unimib.disco.lta.alfa.transitionsWeightsCalculator.FSAVisitorWeight.TransitionsSequence;

import java.util.Map;


import automata.Transition;

public interface TransitionsWeightsCalculator {

	public Map<TransitionsSequence, Double> calculateWeigths();	
	
}
