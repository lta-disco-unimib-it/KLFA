package it.unimib.disco.lta.fsa.distinguishingSequences;

import it.unimib.disco.lta.fsa.closure.ClosureCalculator;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import automata.LambdaTransitionChecker;
import automata.State;
import automata.Transition;
import automata.fsa.FSALambdaTransitionChecker;
import automata.fsa.FiniteStateAutomaton;

public class ExtendedDiagnosingTree implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static class Node implements Serializable {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private List<State> states = new ArrayList<State>();
		private HashSet<State> statesSet = new HashSet<State>();
		private HashMap<String,Node> children = new HashMap<String, Node>();
		private boolean hasRepeatedStates = false;
		private boolean distinguishingSequenceLeaf;
		
		public boolean isDistinguishingSequenceLeaf() {
			return distinguishingSequenceLeaf;
		}

		public void setDistinguishingSequenceLeaf(boolean distinguishingSequenceLeaf) {
			System.out.println("LEAF"+this);
			this.distinguishingSequenceLeaf = distinguishingSequenceLeaf;
		}

		public Node getNodeForEdge( String edge ){
			return children.get(edge);
		}
		
		public Set<String> getEdges( ){
			return children.keySet();
		}
		
		public String toString(){
			StringBuffer sb = new StringBuffer();
			sb.append("{ ");
			for ( State state : states ){
				sb.append(state.getName());
			}
			sb.append(" }");
			return sb.toString();
		}
		
		
		public void addState( State state ){
			if (  statesSet.contains(state) ){
				hasRepeatedStates=true;
			}
			statesSet.add(state);
			this.states.add(state);
		}
		
		public void addOutgoingEdgeToState( String edge, State state ){
			
			Node childNode = children.get(edge);
//			System.out.println("EDGE:"+edge+";");
			if ( childNode == null ){
				childNode = new Node();
				children.put(edge, childNode);
			}
//			System.out.println("Adding "+edge+" "+state.getName()+" "+childNode.size());
			
			childNode.addState(state);
//			System.out.println(edge+" "+state.getName()+" "+childNode.size());
		}
		
		public List<Transition> getTransitions( String edge ){
			List<Transition> res = new LinkedList<Transition>();
			
			for ( State state : states ){
				Transition[] ts = state.getAutomaton().getTransitionsFromState(state);
				for ( Transition t : ts ){
					if ( t.getDescription().equals(edge) ){
						res.add(t);
					}
				}
			}
			
			return res;
		}
		
		public void addStates( Collection<State> states ){
			this.states.addAll(states);
		}

		public void addStates(State[] states) {
			for ( State state : states ){
				addState(state);
			}
		}
		
		public boolean hasRepeatedStates(){
			return hasRepeatedStates;
		}

		public List<State> getStates() {
			return states;
		}

		public void removeChildren() {
			children.clear();
		}

		public  Collection<Node> getChildren() {
			return children.values();
		}

		public int size() {
			return states.size();
		}
		
		public boolean equals(Object o){
			if ( o == null )
				return false;
			
			if ( ! ( o instanceof Node ) ){
				return false;
			}
			
			Node rhs = (Node) o;
			
			
			Collections.sort(states,new Comparator<State>(){

				public int compare(State o1, State o2) {
					return o1.getName().compareTo(o2.getName());
				}
				
			});
			
			Collections.sort(rhs.states,new Comparator<State>(){

				public int compare(State o1, State o2) {
					return o1.getName().compareTo(o2.getName());
				}
				
			});
			
			return states.equals(rhs.states);

			
		}
		
		public int hashCode(){
			Collections.sort(states,new Comparator<State>(){

				public int compare(State o1, State o2) {
					return o1.getName().compareTo(o2.getName());
				}
				
			});
			
			return states.hashCode();
		}
	}
	
	private FiniteStateAutomaton fsa;
	private List<DistinguishingSequence<String>> distinguishingSequences;
	private Node root;

	public ExtendedDiagnosingTree(FiniteStateAutomaton fsa) {
		this.fsa = fsa;
	}
	
	public List<DistinguishingSequence<String>> getDistinguishingSequences(){
		
		populate();
		distinguishingSequences=retrieveDistinguishingSequences();
		return distinguishingSequences;
		
	}

	private List<DistinguishingSequence<String>> retrieveDistinguishingSequences() {
		
		Node curNode = root;
		
		
		Set<String> edges = curNode.getEdges();
		
		List<DistinguishingSequence<String>> sequences = new ArrayList<DistinguishingSequence<String>>(); 
		
		for ( String edge : edges ){
			
			for ( Transition t : curNode.getTransitions(edge)){
				

				DistinguishingSequence<String> dist = new DistinguishingSequence<String>(t.getFromState());
				Node node = curNode.getNodeForEdge(edge);
				dist.addSymbol(t.getDescription(), t.getToState() );
				sequences.addAll(retrieveDistinguishingSequences(dist,node));
			}
		}
		
		return sequences;
	}

	private List<DistinguishingSequence<String>> retrieveDistinguishingSequences(
			DistinguishingSequence<String> dist, Node node) {
		
		LinkedList<DistinguishingSequence<String>> results = new LinkedList<DistinguishingSequence<String>>();
		
		LinkedList<Node> stack = new LinkedList<Node>();
		LinkedList<DistinguishingSequence<String>> stackDist = new LinkedList<DistinguishingSequence<String>>();
		
		
		stack.addFirst(node);
		stackDist.addFirst(dist);
		
		
		while ( ! stack.isEmpty() ){
			node = stack.removeFirst();
			dist = stackDist.removeFirst();

			if ( node.isDistinguishingSequenceLeaf() ){
				//System.out.println("ADDING "+node);
				results.add(dist);
			}

			Set<String> edges = node.getEdges();

			for ( String edge : edges ){

				for ( Transition t : node.getTransitions(edge) ){
					Node curNode = node.getNodeForEdge(edge);

					DistinguishingSequence<String> curDist = new DistinguishingSequence<String>(dist);
					if ( ! curDist.getLastState().equals(t.getFromState() ) ){
						continue;
					}
					curDist.addSymbol(t.getDescription(), t.getToState());

					stack.addFirst(curNode);
					stackDist.addFirst(curDist);
				}

			}
		}
		
		return results;
	}

	private void populate() {
		if ( distinguishingSequences != null ){
			return ;
		}
		
		distinguishingSequences = new ArrayList<DistinguishingSequence<String>>();
		
		LinkedList<Node> curLevel = new LinkedList<Node>();
		LinkedList<Node> nextLevel = new LinkedList<Node>();
		
		HashSet<State> visitedStates = new HashSet<State>();
		
		
		root = new Node();
		root.addStates( fsa.getStates() );
		
		
		nextLevel.add(root);
		int cycle = 0;
		
		LambdaTransitionChecker checker =new FSALambdaTransitionChecker();
		
		while ( nextLevel.size() > 0 ){
			LinkedList<Node> tmp = curLevel;
			curLevel = nextLevel;
			nextLevel = tmp;
			
			HashSet<State> visitedStatesCurLevel = new HashSet<State>();
			cycle++;
			while ( curLevel.size() > 0 ){
				Node node = curLevel.remove();
				System.out.println("POP "+node);
				//Terminate if states are repeated
				if ( node.hasRepeatedStates() ){
					continue;
				}
				
				
				
				boolean alreadyVisited = false;
				
				//Terminates if one of the states has been visited in a previous level
				for ( State s : node.states ){
					if ( visitedStates.contains(s) ){
						node.removeChildren();
						alreadyVisited = true;
						break;
					}
				}
				if ( alreadyVisited ){
					continue;
				}
				
				if ( node.size() == 1 ){
					node.setDistinguishingSequenceLeaf(true);
					continue;
				}
				
				
				Set<State> toVisit = ClosureCalculator.calculateClosure(node.getStates(), fsa );
				for ( State state : toVisit  ){
					if ( cycle > 1){
						visitedStatesCurLevel.add(state);
					}
					
					
					Transition[] transitions = fsa.getTransitionsFromState(state);
					for ( Transition transition : transitions ){
						if ( ! checker.isLambdaTransition(transition) ){
							node.addOutgoingEdgeToState(transition.getDescription(), transition.getToState());
						}
					}
				}
				
				//add children to the next level to visit
				for ( Node child : node.getChildren() ){
					//System.out.println(child);
					nextLevel.add(child);
				}
				
			}
			
			visitedStates.addAll(visitedStatesCurLevel);
		}
		
		
	}

	public Node getRoot() {
		return root;
	}

	public static ExtendedDiagnosingTree load(File file) 
	throws ClassNotFoundException, FileNotFoundException, IOException {
		File toBeOpened = file;
		if (!toBeOpened.exists()) {
			throw new FileNotFoundException();
		}

		ObjectInputStream in;
		ExtendedDiagnosingTree dtree = null;
		in = new ObjectInputStream(new FileInputStream(toBeOpened
				.getAbsolutePath()));
		dtree = (ExtendedDiagnosingTree) in.readObject();

		return dtree;
	}

	/**
	 * This method permits to remap the diagnosing tree to an FSa which is equals to the one from which the tree was built
	 * @param applicationModel
	 */
	public void mapToFSA(FiniteStateAutomaton applicationModel) {
		HashMap<String, State> statesMap = new HashMap<String, State>();
		
		for ( State state : applicationModel.getStates() ){
			statesMap.put(state.getName(),state);
		}
		
		LinkedList<Node> nodes = new LinkedList<Node>();
		
		nodes.add(root);
		
		
		//Iteratively visit all the nodes
		while ( nodes.size() > 0 ){
			Node curNode = nodes.remove();
		
			//Iteratively replace all the states in the node
			for ( int i = 0; i < curNode.states.size(); ++i ){
				State curState = curNode.states.get(i);
				State newState = statesMap.get(curState.getName());
				curNode.states.set(i,newState);
			}
			
			nodes.addAll( curNode.getChildren() );
		}
		
	}

	public void store(File dest) throws IOException {
		FileOutputStream fos = null;
		ObjectOutputStream out = null;

		fos = new FileOutputStream(dest);
		out = new ObjectOutputStream(fos);
		out.writeObject(this);
		out.close();


	}
	
	


}
