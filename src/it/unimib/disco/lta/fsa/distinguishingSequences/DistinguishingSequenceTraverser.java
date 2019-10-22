package it.unimib.disco.lta.fsa.distinguishingSequences;

import it.unimib.disco.lta.fsa.distinguishingSequences.ExtendedDiagnosingTree.Node;

import java.util.LinkedList;
import java.util.List;

import automata.State;

public class DistinguishingSequenceTraverser {

	private ExtendedDiagnosingTree diagnosingTree;
	private List<ExtendedDiagnosingTree.Node> curNodes = new LinkedList<Node>();

	
	public DistinguishingSequenceTraverser(ExtendedDiagnosingTree diagnosingTree){
		this.diagnosingTree=diagnosingTree;
	}
	
	/**
	 * Returns null if a distinguishing sequence has not been found
	 * Otherwise returns the detected state
	 * 
	 * @return
	 */
	public State addSymbol(String symbol){

		curNodes.add( diagnosingTree.getRoot() );
		LinkedList<Node> newNodes = new LinkedList<Node>();
		
		for ( Node node : curNodes ){
			Node child = node.getNodeForEdge(symbol);
			
			if ( child == null ){
				continue;
			}
			
			if ( child.isDistinguishingSequenceLeaf() ){
				return child.getStates().get(0);
			}
			
			
			
			
			newNodes.add(child);
		}
		
		curNodes = newNodes;
		
		return null;
		
	}
	
	public void clear(){
		curNodes.clear();
	}
}
