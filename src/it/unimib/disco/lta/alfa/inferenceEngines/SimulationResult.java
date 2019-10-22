package it.unimib.disco.lta.alfa.inferenceEngines;

import java.util.List;

import it.unimib.disco.lta.alfa.inferenceEngines.PathFSAVisitor.TransitionSequence;
import automata.State;

/**
 * 
 * @author Leonardo Mariani
 *
 * Inner class for returning the result of a simulation
 */
class SimulationResult {
	public State reachableStates[];
	public int nextSymbol;
	public State preState;
	public List<TransitionSequence> sequences;
	
}