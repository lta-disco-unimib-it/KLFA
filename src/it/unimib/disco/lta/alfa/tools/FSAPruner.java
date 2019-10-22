package it.unimib.disco.lta.alfa.tools;

import grammarInference.Record.FlyweigthKbhParser;
import grammarInference.Record.Symbol;
import grammarInference.Record.Trace;
import grammarInference.Record.VectorTrace;

import it.unimib.disco.lta.alfa.tools.FSAInspector.PathFilter;
import it.unimib.disco.lta.alfa.tools.FSAInspector.TransitionPath;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.Stack;

import automata.State;
import automata.Transition;
import automata.fsa.FiniteStateAutomaton;
import util.FSAUtil;
import util.FSAUtil.FSAVisitor;

public class FSAPruner {

	private FiniteStateAutomaton fsa;
	private Iterator<VectorTrace> traceIterator;
	private int k;
	private List<TransitionPath> matchingPaths = new ArrayList<TransitionPath>();
	private boolean initialized;
	private FiniteStateAutomaton prunedFsa;
	private List<TransitionPath> fakePaths;

	
	public class InvalidPathsVisitor implements  FSAVisitor {
		int k;
		private VisitedPaths vp;
		private TransitionPath path;
		private int level = -1;
		private Stack<List<TransitionPath>> explorablePaths = new Stack<List<TransitionPath>>();
		private HashSet<Transition> validTransitions;
		private List<TransitionPath> validPaths;
		
		public InvalidPathsVisitor(int k, VisitedPaths vp) {
			this.k = k;
			this.vp = vp;
		}


		public boolean after(State state) {
			--level;
			return true;
		}


		public boolean after(Transition t) {
			path.removeLastStep();
			validTransitions = null;
			validPaths = null;
			
			return true;
		}


		public boolean before(State state) {
			
			++level;
			if ( level == 0 ){
				path = new TransitionPath(state);
				explorablePaths.push( vp.getPaths(state.getName()) );
			}
			if ( level >= k ){
				return false;
			}
			return true;
		}


		public boolean before(Transition t) {
			Set<Transition> ts = getValidTransitions();
			
			if ( ! ts.contains(t) ){
				TransitionPath invalidPath = new TransitionPath(path);
				invalidPath.addStep(t, t.getToState());
				return false;
			}
			
			List<TransitionPath> paths = getValidPaths();
			explorablePaths.push(paths);
			path.addStep(t, t.getToState());
			
			return true;
		}


		private List<TransitionPath> getValidPaths() {
			return validPaths;
		}


		private Set<Transition> getValidTransitions() {
			if ( validTransitions == null ){

				List<TransitionPath> paths = explorablePaths.lastElement();
				validTransitions = new HashSet<Transition>();
				validPaths = new ArrayList<TransitionPath>();
				int i = 0;
				for ( TransitionPath tpath : paths ){
					validPaths.add( tpath );
					validTransitions.add( tpath.getTransitionAtLevel(level) );
				}
			}
			return validTransitions;
		}
		
		
		
	}
	
	public class VisitedPaths {
		HashMap<String,List<TransitionPath>> paths = new HashMap<String, List<TransitionPath>>();
		
		void add(TransitionPath t ){
			String state = t.getFirstState().getName();
			List<TransitionPath> list = paths.get(state);
			if ( list == null ){
				list = new ArrayList<TransitionPath>();
				paths.put(state, list);
			}
			list.add(t);
		}
		
		public List<TransitionPath> getPaths(String stateName){
			if ( paths.containsKey(stateName) ){
				return new ArrayList(0);
			}
			return paths.get(stateName);
		}
	}
	
	
	public class SymbolSequenceDispenser{

		private Iterator it;
		private LinkedList<String> curSequence = new LinkedList<String>();
		private int k;
		private Trace curTrace;
		private Iterator<String> curTraceIt;
		
		public SymbolSequenceDispenser(Iterator traceIterator, int k) throws FSAPrunerException {
			this.it = traceIterator;
			this.k = k;
		}

		
		
		public List<String> nextSequence(){
			if ( curTrace == null )
				return null;
			
			if ( curSequence == null ){
				return null;
			}
			
			ArrayList<String> lastSequence = new ArrayList<String>();
			lastSequence.addAll(curSequence);
			
			if ( curTraceIt.hasNext()) {
				curSequence.removeFirst();
				curSequence.add( curTraceIt.next());
				
			} else {
				curSequence = null;
			}
			return lastSequence;
			
		}

		public boolean hasTrace() {
			return it.hasNext();
		}

		public void nextTrace() {
			curTrace = (Trace) it.next();
			curTraceIt = curTrace.getSymbolIterator();
			curSequence = new LinkedList<String>();
			int i = 0;
			while ( curTraceIt.hasNext() && i < k ){
				++i;
				curSequence.add(curTraceIt.next());
			}
		}
	}
	
	
	private class FSAPrunerException extends Exception{
		public FSAPrunerException(String string) {
			super(string);
		}
	}
	
	public FSAPruner(FiniteStateAutomaton fsa, Iterator<VectorTrace> traceIterator, int k) {
		this.fsa = fsa;
		this.traceIterator = traceIterator;
		this.k = k;
	}

	public static void main(String args[]){
		
		if ( args.length != 3 ){
			System.out.println("Usage :" +
					FSAPruner.class.getName()+" <kFuture>  <trace>  <fsa>");
			return;
		}
		
		FiniteStateAutomaton fsa;
		try {
			int k = Integer.valueOf(args[0]);
			FlyweigthKbhParser parser = new FlyweigthKbhParser(args[1]);
			fsa = FiniteStateAutomaton.readSerializedFSA(args[2]);
			
			FSAPruner pruner= new FSAPruner(fsa,parser.getTraceIterator(),k);
			//pruner.getPrunedFSA();
			
			List<TransitionPath> fakePaths = pruner.getFakePaths();
			System.out.println("Fake paths");
			for ( TransitionPath tp : fakePaths ){
				System.out.println( tp );
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FSAPrunerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	private List<TransitionPath> getFakePaths() throws FSAPrunerException {
		init();
		return new ArrayList<TransitionPath>(fakePaths);
	}

	private void init() throws FSAPrunerException {
		if ( initialized == true ){
			return;
		}
		
		
		SymbolSequenceDispenser ssd = new SymbolSequenceDispenser(traceIterator, k);
		
		VisitedPaths visitedPaths = new VisitedPaths();
		int traceCounter = 0;
		while ( ssd.hasTrace() ){
			++traceCounter;
			System.out.println("Next trace");
			ssd.nextTrace();
			State state = fsa.getInitialState();
			
			List<String> symbolsSequence = ssd.nextSequence();
			
			List<String> valueSequence = new ArrayList<String>(symbolsSequence.size());
			for ( String s : symbolsSequence ){
				valueSequence.add(s);
			}
			FSAInspector fsaInspector = new FSAInspector();
			
			while ( symbolsSequence != null ){
				
				PathFilter f = new FSAInspector.PathFilterExact(valueSequence);
				
				List<TransitionPath> paths = fsaInspector.getPathsFrom(state, k,f);
				if ( paths.size() > 1 ){
					System.err.println("NONDETERMINISTIC AUTOMATON?");
					break;
				} 
				
				if ( paths.size() == 0 ){
					System.err.println("Trace not match FSA : "+traceCounter);
					break;
				}
				TransitionPath matchingPath = paths.get(0);
				visitedPaths.add(matchingPath);
				state = matchingPath.getStates().get(1);
				
				symbolsSequence = ssd.nextSequence();
			}
		}
		
		InvalidPathsVisitor ipv = new InvalidPathsVisitor( k, visitedPaths );
		
		for ( State state : fsa.getStates() ){
			FSAUtil.depthFirstVisit(state, ipv);
		}
		
		initialized = true;
	}

	private FiniteStateAutomaton getPrunedFSA() throws FSAPrunerException {
		return prunedFsa;
	}

	private List<TransitionPath> getAllPaths(FiniteStateAutomaton fsa) {
//		State[] states = fsa.getStates();
//		ArrayList<TransitionPath> paths = new ArrayList<TransitionPath>();
//		for ( State state : states ){
//			System.out.println("State "+state.getName());
//			paths.addAll(FSAInspector.getPathsFrom(state, k));
//		}
//		return paths;
		FSAInspector fsaInspector = new FSAInspector();
		return fsaInspector.getPathsFrom(fsa.getInitialState(), k);
	}

	private void addMatchingPath(TransitionPath matchingPath) {
		matchingPaths .add(matchingPath);
	}

	private TransitionPath getMatchingPath(List<TransitionPath> paths, List<Symbol> symbolsSequence) {
		List<String> sequence = new ArrayList();
		
		for ( Symbol symbol : symbolsSequence ){
			sequence.add(symbol.getValue());
		}
		
		for ( TransitionPath path : paths ){
			if ( path.matchSequence( sequence ) ){
				return path;
			}
		}
		return null;
	}
}
