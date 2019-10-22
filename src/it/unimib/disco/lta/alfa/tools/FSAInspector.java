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
package it.unimib.disco.lta.alfa.tools;

import grammarInference.Log.ConsoleLogger;
import grammarInference.Record.Trace;




import it.unimib.disco.lta.alfa.inferenceEngines.KBehaviorEngine;
import it.unimib.disco.lta.alfa.inferenceEngines.FSAExtensionsRecorder.FSAExtension;
import it.unimib.disco.lta.alfa.inferenceEngines.FSAExtensionsRecorder.FSAExtensionBranch;
import it.unimib.disco.lta.alfa.inferenceEngines.FSAExtensionsRecorder.FSAExtensionTail;
import it.unimib.disco.lta.alfa.klfa.AnomaliesAnalyzer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.Stack;

import tools.fsa2xml.LazyFSALoader;
import tools.fsa2xml.LazyFSALoader.LazyFSALoaderException;


import automata.Automaton;
import automata.State;
import automata.Transition;
import automata.fsa.FSATransition;
import automata.fsa.FiniteStateAutomaton;

/**
 * @deprecated Test fails, need time to double check
 * @author Fabrizio Pastore - fabrizio.pastore@gmail.com
 *
 */
@Deprecated
public class FSAInspector {
	
	/**
	 * Filter for searching for paths
	 * 
	 * @author Fabrizio Pastore
	 *
	 */
	public static interface PathFilter{
		
		public boolean matchPosition ( Transition t, int position );
		
		public int getPathLen();
	}
	
	public static class PathFilterRegexp implements PathFilter {
		
		
		private String[] regexps;

		/**
		 * Costruct a new PathFilter, the passed strings are regular expressions that indicate the name of the element that can be matched
		 * 
		 * @param regexps
		 */
		public PathFilterRegexp ( List<String> regexps ){
			String exps[] = new String[regexps.size()];
			this.regexps = regexps.toArray(exps);
		}
		
		public PathFilterRegexp(String[] transitionsExpr) {
			regexps = new String[transitionsExpr.length];
			System.arraycopy(transitionsExpr, 0, regexps, 0, transitionsExpr.length);
		}

		public boolean matchPosition ( Transition t, int position ){
			if ( position >= regexps.length )
				return true;
			
			return matchTransitionExpr(t.getDescription(),regexps[position]);
			
			
		}

		public int getPathLen() {
			return regexps.length;
		}
	}	
	
	private static final String lambdaRegexp = "\\(lambda\\)";
	public static boolean matchTransitionExpr(String transitionValue, String transitionExpr){
		//System.out.println("match "+t.getFromState()+"-"+t.getDescription()+t.getToState()+" "+position);
		//System.out.println("MATCH ?"+transitionValue+" "+transitionExpr);
		if ( transitionExpr.equals(lambdaRegexp) ){
			if ( isEpsilonTransition(transitionValue) ) {
				return true;	
			} else {
				return false;
			}
		}
		
		return transitionValue.matches(transitionExpr);
	}
	
	public static class PathFilterExact implements PathFilter {
		
		private String[] regexps;

		/**
		 * Costruct a new PathFilter, the passed strings are regular expressions that indicate the name of the element that can be matched
		 * 
		 * @param regexps
		 */
		public PathFilterExact ( List<String> regexps ){
			String exps[] = new String[regexps.size()];
			this.regexps = regexps.toArray(exps);
		}
		
		

		public boolean matchPosition ( Transition t, int position ){
			return t.getDescription().equals(regexps[position]);
		}
		
		public int getPathLen() {
			return regexps.length;
		}
	}

	public static class TransitionPath{
	
		private List<Transition> transitions = new LinkedList<Transition>();
		private List<State> states = new LinkedList<State>();
		private int epsilonCount = 0;
		
		
		public TransitionPath( State s ){
			states.add(s);
		}
		
		public TransitionPath( TransitionPath rhs ){
			states.addAll(rhs.states);
			transitions.addAll(rhs.transitions);
		}
		
		public void addStep( Transition t, State s ){
			transitions.add(t);
			if ( isEpsilonTransition ( t) ){
				++epsilonCount;
			}
			states.add(s);
		}


		public List<State> getStates() {
			return states;
		}

		public List<Transition> getTransitions() {
			return transitions;
		}

		public Transition getTransitionAtLevel( int level ) {
			return transitions.get(level);
		}
		
		public State getLastState() {
			return states.get(states.size()-1);
		}

		public int length() {
			return transitions.size()-epsilonCount ;
		}
		
		public int fullLength() {
			return transitions.size();
		}
		
		public String getTransitionsString(){
			StringBuffer msg = new StringBuffer();
			
			for ( int i = 0; i < transitions.size(); ++i ){
				if ( i > 0 ){
					msg.append("-->");
				}
				
				msg.append(transitions.get(i).getDescription());
				
			}
			
			return msg.toString();
			
		}
		
		public String toString(){
			StringBuffer msg = new StringBuffer();
			msg.append("PATH ("+transitions.size()+"):");
			msg.append(" ("+states.get(0).getName()+") ");
			if ( transitions.size() > 0 ){
				msg.append("-->");
			}
			for ( int i = 0; i < transitions.size(); ++i ){
				msg.append(transitions.get(i).getDescription());
				msg.append("-->");
				msg.append(" ("+states.get(i+1).getName()+") ");
			}
			
			return msg.toString();
		}
		
		public boolean equals ( Object o ){
			if ( o == this ){
				return true;
			}
			if ( ! ( o instanceof TransitionPath ) ){
				return false;
			}
			
			TransitionPath rhs = (TransitionPath)o;
			 if ( ! this.states.equals(rhs.states))
				 return false;
			 return this.transitions.equals(rhs.transitions);
		}
		
		public int hashCode(){
			return toString().hashCode();
		}

		public boolean matchSequence(List<String> sequence) {
			if ( transitions.size() != sequence.size() )
				return false;
			int size = transitions.size();
			for ( int i = 0; i < size; ++i ){
				Transition t = transitions.get(i);
				if ( ! ( t.getDescription().equals(sequence.get(i)) ) ){
					return false;
				}
			}
			
			return true;
		}

		public State getFirstState() {
			return states.get(0);
		}

		public void removeLastStep() {
			states.remove(states.size()-1);
			transitions.remove(transitions.size()-1);
		}

		public void addInitialStep(Transition t, State toState) {
			transitions.add(0, t);
			states.add(0,toState);
		}

		public void intersect(TransitionPath path) {
			
		}

		public void setLastState(State toState) {
			states.remove(states.size()-1);
			states.add(toState);
		}
	}
	
	
	public static class NoSuchStateException extends Exception {
		private String name;
		
		public NoSuchStateException(String stateName) {
			super("Cannot find "+stateName);
			name = stateName;
		}
		
		public String getStateName(){
			return name;
		}
	}

	private static File containingFile;
	private boolean epsilonAsSymbol = false;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String outGoing = null;
		String transitionExpr = null;
		String transitionPath = null;
		String tgfFile = null;
		boolean print = false;
		boolean incomingFinal = false;
		int level = 2;
		
		if ( args.length < 2 ){
			printHelp();
			System.exit(-1);
		}
		int i;
		String incoming = null;
		String intersect = null;
		
		FSAInspector fsaInspector = new FSAInspector();
		boolean stats = false;
		ArrayList<String> files = new ArrayList<String>();
		for( i = 0; i < args.length; ++i ){
			if ( args[i].equals("-print") ) {
				print = true;
			} else if ( args[i].equals("-stats") ){
				stats = true;
			} else if ( args[i].equals("-lambdaAsSymbol") ){
				fsaInspector.setEpsilonAsSymbol(true);
			} else if ( args[i].equals("-incomingFinal") ){
				incomingFinal = true;
			} else {
//				if ( i >= args.length -2 ){
//					printHelp();
//					System.exit(-1);
//				}
				if ( args[i].equals("-outgoing") ){
					outGoing = args[++i].replace("_", "");
				} else if ( args[i].equals("-sibling") ){
					intersect  = args[++i].replace("_", "");
				} else if ( args[i].equals("-incoming") ){
					incoming  = args[++i];
				} else if ( args[i].equals("-transition") ){
					transitionExpr = args[++i].replace("(", "\\(").replace(")", "\\)");
				} else if ( args[i].equals("-containing") ){
					containingFile = new File(args[++i]);
				}
				else if ( args[i].equals("-exportTGF") ){
					tgfFile = args[++i];
				}
				else if ( args[i].equals("-transitionsPath") ){
					transitionPath = args[++i].replace("(", "\\(").replace(")", "\\)");
				}	else if ( args[i].equals("-level") || args[i].equals("-len")){
					level = Integer.valueOf(args[++i]);
				} else {
					files.add(args[i]);
				}

			}
		}
		
		if ( stats ){
			try {
				printStats( files );
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (LazyFSALoaderException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		for ( String file : files ){
		
		try {
			FiniteStateAutomaton fsa = FiniteStateAutomaton.readSerializedFSA(file);
			if ( print ){
				printFSA ( fsa );
				
			}
			
			
			
			if ( incomingFinal ){
				State[] finalStates = fsa.getFinalStates();
				for ( State state : finalStates ){
					Transition[] transitions = fsa.getTransitionsToState(state);
					for ( Transition transition : transitions ){
						System.out.println( transition.getDescription() + " --> " + transition.getToState().getName() );
					}	
				}
			}
			if ( incoming != null || outGoing != null || transitionPath != null ){
//				State state = getState(fsa,outGoing);
//				List<TransitionPath> paths = getPathsFrom(state, level);
//				
//				System.out.println("Paths from "+state.getName());
//				for ( TransitionPath p : paths ){
//					System.out.println(p.toString());
//				}
				
				String stateExpr;
				if ( outGoing == null ){
					stateExpr =".*";
				} else {
					stateExpr = outGoing;
				}
				
				String incomingExpr;
				if ( incoming == null ){
					incomingExpr = ".*";
				} else {
					incomingExpr = incoming;
				}
				
				System.out.println("Searching for path : "+transitionPath + " from state "+ outGoing + " to state "+incomingExpr);
				
				String transitionRegExp;
				if ( transitionPath == null ){
					transitionRegExp = ".*";
				} else {
					transitionRegExp = transitionPath;
				}
				
				List<TransitionPath> transitionPaths = fsaInspector.getTransitionPaths(fsa, transitionRegExp,stateExpr,incomingExpr,level);
				
				
				
				
				if ( intersect != null ){
					
					try {
						List<TransitionPath> incomingsGiven = fsaInspector.getIncomingPaths(fsaInspector.getState(fsa,intersect), level, null);
						for ( TransitionPath path : transitionPaths ){
							List<TransitionPath> incomings = fsaInspector.getIncomingPaths(path.getFirstState(), level, null);
							for ( TransitionPath incomingPath : incomings ){
								for ( TransitionPath incomingGiven : incomingsGiven ){
									incomingGiven.intersect(incomingPath);
								}
							}
						}
					} catch (NoSuchStateException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
						
				}
				
				for ( TransitionPath path : transitionPaths ){
					System.out.println(path.toString());
				}
				
				if ( containingFile != null ){
					List<ContainmentStatistics> res = sortedByContainment(transitionPaths, containingFile);
					System.out.println("Missing symbols\tChanges (archs added)\tAdded sequences len\tTransition Path");
					for ( ContainmentStatistics s : res ){
						System.out.println(s.getMissing()+"\t"+s.getArchs()+"\t"+s.getOut()+"\t"+s.getTp().getTransitionsString());
					}
				}
				
				
				
//				Transition[] transitions = fsa.getTransitionsFromState(state);
//				for ( Transition transition : transitions ){
//					System.out.println( transition.getDescription() + " --> " + transition.getToState().getName() );
//					
//				}
			}
			if ( transitionExpr != null ) {
				Collection<Transition> transitions = fsaInspector.getTransitions(fsa,transitionExpr,".*");
				for ( Transition transition : transitions ){
					printPathTo( transition.getFromState(), 3 );
					
					System.out.println( " :::"+transition.getFromState().getName() + "-->" + transition.getDescription() + " --> " + transition.getToState().getName() );
					
					printPathFrom( transition.getToState(), level );
					
				}
			}
			
			
			
			if ( tgfFile != null ) {
				exportToTGF( fsa, tgfFile );
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
		} 
		}

	}

	private static void printStats(List<String> files) throws FileNotFoundException, ClassNotFoundException, IOException, LazyFSALoaderException {
		String separator = " ";
		System.out.println(
		"FSA"+separator+		
		"States"+separator+
		"FinalStates" +separator+
		"Final/States" +separator+
		"Transitions" +separator+
		"Transitions/States" +separator+
		"Symbols"		);
		
		int tstates=0;
		int tfinals=0;
		double tfs=0;
		int ttransitions=0;
		double tts=0;
		int tsymbols=0;
		int dimension=0;
		
		String biggestFSA = null;
		
		for ( String file : files ){
			FiniteStateAutomaton fsa = LazyFSALoader.loadFSA(file);
			
			int states = fsa.getStates().length;
			int finals = fsa.getFinalStates().length;
			double fs = (double)finals/(double)states;
			int transitions = fsa.getTransitions().length;
			double ts = (double)transitions/(double)states;
			int symbols = getSymbols(fsa).size();
			int fsaDimension = states*transitions;
			
			if ( fsaDimension > dimension ){
				dimension=fsaDimension;
				biggestFSA = file;
			}
			
			tstates+=states;
			tfinals+=finals;
			tfs+=fs;
			ttransitions+=transitions;
			tts+=ts;
			tsymbols+=symbols;
			
			System.out.println(
					file+separator+
					states+separator+
					finals+separator+
					fs+separator+
					transitions+separator+
					ts+separator+
					symbols+separator
					);
		}
		int total = files.size();
		
		System.out.println(
				total+separator+
				tstates/total+separator+
				tfinals/total+separator+
				tfs/total+separator+
				ttransitions/total+separator+
				tts/total+separator+
				tsymbols/total+separator
				);
		System.out.println("Biggest automaton (states*transitions): "+biggestFSA);
	}

	private static Set<String> getSymbols(FiniteStateAutomaton fsa) {
		HashSet<String> symbols = new HashSet<String>();
		for ( Transition t : fsa.getTransitions() ){
			FSATransition tt = (FSATransition) t;
			symbols.add(tt.getLabel());
		}
		return symbols;
	}

	private static List<ContainmentStatistics> sortedByContainment(
			List<TransitionPath> transitionPaths, File containingFile2) throws IOException {
		 
		List<String> sequence = loadSequence ( containingFile2 );
		List<ContainmentStatistics> stats = new LinkedList<ContainmentStatistics>();
		
		for ( TransitionPath tp : transitionPaths ){
			stats.add(getContainmentStatistics(tp,sequence));
		}
		
		Collections.sort(stats,new Comparator<ContainmentStatistics>(){

			public int compare(ContainmentStatistics o1,
					ContainmentStatistics o2) {
				
				return o1.getMissing()-o2.getMissing();
			}
			
		});
		
		return stats;
	}

	private static ContainmentStatistics getContainmentStatistics(TransitionPath tp,
			List<String> sequence) {
		
		final List<String> ssequence = new ArrayList<String>();
		for ( String s : sequence ){
			ssequence.add(s);
		}
		
		Trace t = new ListTrace(ssequence);
		
		FiniteStateAutomaton fsa = AnomaliesAnalyzer.getFSA(tp);
		KBehaviorEngine kb = new KBehaviorEngine(2,2,true,"none", new ConsoleLogger(0));
		kb.setCurrentFSA(fsa);
		
		
		kb.setRecordExtensions(true);
		kb.extendFSAwithTrace(t);
		List<FSAExtension> data = kb.getFSAExtensions();
		
		int out=0;
		int archs=0;
		for ( FSAExtension cd : data ){
			if ( cd instanceof FSAExtensionBranch ){
				FSAExtensionBranch cdb = (FSAExtensionBranch) cd;
				out+=cdb.getTraceLen();
				++archs;
			} else if ( cd instanceof FSAExtensionTail ){
				FSAExtensionTail cdt = (FSAExtensionTail) cd;
				out+=ssequence.size()-cdt.getStartPosition();
				++archs;
			}
		}
		
		List<String> symbols = new LinkedList<String>();
		symbols.addAll(sequence);
		int missing = 0;
		List<String> tsymbols = new LinkedList<String>();
		Iterator<Transition> it = tp.transitions.iterator();
		while ( it.hasNext() ){
			String v = it.next().getDescription();
			tsymbols.add(v);
			
		}
		
		for ( String s : symbols ){
			
			if ( ! tsymbols.remove(s) ){
				++missing;
			}
			
		}
		
		
		
		return new ContainmentStatistics(tp,missing,out,archs);
	}




	private static List<String> loadSequence(File containingFile2) throws IOException {
		BufferedReader r = new BufferedReader(new FileReader(containingFile2));
		ArrayList<String> res = new ArrayList<String>();
		String line;
		while ( ( line = r.readLine()) != null ){
			res.add(line);
		}
		return res;
	}


	public List<TransitionPath> getTransitionPaths(FiniteStateAutomaton fsa, String transitionPathExpr, String initialStateExpr, String incomingExpr) {
		return getTransitionPaths(fsa, transitionPathExpr, initialStateExpr, incomingExpr, Integer.MAX_VALUE);
	}

	/**
	 * Return a list of paths that match the given transition regular expression, start from a state whose name matches the given regular expression, and end into a state that match the given regular expression
	 * The transition regular expression must be in the form <transitionDescriptionPattern>--><transitionDescriptionPattern>.
	 * maxLen is the maximum depth of the retrieved path, if it less than the length of the transition expression the transition expression length would be used.
	 * 
	 * @param fsa
	 * @param transitionPathExpr
	 * @param incomingExpr 
	 * @param maxLen
	 * @return
	 */
	public List<TransitionPath> getTransitionPaths(FiniteStateAutomaton fsa, String transitionPathExpr, String initialStateExpr, String incomingExpr, int maxLen) {
		String[] transitionsExpr = transitionPathExpr.split("-->");
		
		
		String firstTransition = transitionsExpr[0];
		
		Collection<Transition> transitions = getTransitions(fsa, firstTransition, initialStateExpr);
		
		PathFilter filter = new PathFilterRegexp(transitionsExpr);
		List<TransitionPath> paths = new ArrayList<TransitionPath>();
		for ( Transition transition : transitions ){
			System.out.println("Transition "+ transition.getDescription());
			for ( TransitionPath path : getOutgoingPaths(transition, Math.max(transitionsExpr.length-1,maxLen), filter ) ){
				if ( path.getLastState().getName().matches(incomingExpr) ){
					paths.add(path);
				}
			}
			
		}
		
		
		return paths;
		
	}

	/**
	 * Export the given automaton to a TGF file. TGF is a simple graph format.
	 *  
	 * @param fsa
	 * @param tgfFile
	 * @throws IOException
	 */
	public static void exportToTGF(FiniteStateAutomaton fsa, String tgfFile) throws IOException {
		File dest = new File( tgfFile );
		
		BufferedWriter bw = new BufferedWriter( new FileWriter( dest ));
		
		HashSet<State> finals = new HashSet<State>();
		for ( State state : fsa.getFinalStates() )
			finals.add(state);
		
		
		
		
		for ( State state : fsa.getStates() ){
			int stateId = state.getID();
			String stateName = state.getName();
			if ( state.equals(fsa.getInitialState()) ){
				stateName=">"+stateName+"<";
			}
			if ( finals.contains(state) )
				stateName="["+stateName+"]";
			bw.write(stateId+" "+stateName+"\n");
		}
		bw.write("#\n");
		for ( Transition transition : fsa.getTransitions() ){
			bw.write(transition.getFromState().getID()+" "+transition.getToState().getID()+" "+transition.getDescription()+"\n");
		}
		
		bw.close();
	}

	private static void printPathTo( State state, int level) {
		if ( level == 0 )
			return;
		
		Automaton fsa = state.getAutomaton();
		Transition[] transitionsTo = fsa.getTransitionsToState(state);
		for ( Transition t : transitionsTo ){
			printPathTo( t.getFromState(), level -1 );
			String tabs="";
			for ( int i = level; i > 0; --i )
				tabs+="\t";
			System.out.println(tabs+t.getDescription()+"-->"+state.getName());
		}
	}
	
	private static void printPathFrom( State state, int level) {
		
		if ( level == 0 )
			return;
		Automaton fsa = state.getAutomaton();
		Transition[] transitionsTo = fsa.getTransitionsFromState(state);
		for ( Transition t : transitionsTo ){
			String tabs="";
			for ( int i = level; i > 0; --i )
				tabs+="\t";
			System.out.println(tabs+state.getName()+"-->"+t.getDescription());
			printPathFrom( t.getToState(), level -1 );
		}
	}
	
	public List<TransitionPath> getPathsFrom( State state, int level) {
		TransitionPath path = new TransitionPath(state);
		return getOutgoingPaths( path, level, null );
	}
	
	/**
	 * Returns all the different paths that start from the given transition with a maximum length of maxLen
	 * @param t
	 * @param maxLen
	 * @return
	 */
	public List<TransitionPath> getOutgoingPaths( Transition t, int maxLen) {
		return getOutgoingPaths(t, maxLen, null);
	}
	
	/**
	 * Returns all the different paths that start from the given transition with a maximum length of maxLen, that match the given PathFilter.
	 * @param t
	 * @param maxLen
	 * @return
	 */
	public List<TransitionPath> getOutgoingPaths( Transition t, int maxLen, PathFilter filter ) {
		TransitionPath path = new TransitionPath(t.getFromState());
		path.addStep(t, t.getToState());
		return getOutgoingPaths( path, maxLen, filter );
	}
	
	/**
	 * Traverse an automaton and add the paths that start from tah given transition path.
	 * 
	 * @param path
	 * @param maxLen
	 * @param filter
	 * @return
	 */
	private List<TransitionPath> getOutgoingPaths( TransitionPath path, int maxLen, PathFilter filter) {
		ArrayList<TransitionPath> paths = new ArrayList<TransitionPath>();
		if ( maxLen <= 0 ){
			if ( path.length() >= filter.getPathLen() ){
				paths.add(path);
			}
			return paths;
		}
		State state = path.getLastState();
		Automaton fsa = state.getAutomaton();
		Transition[] transitionsTo = fsa.getTransitionsFromState(state);
		for ( Transition t : transitionsTo ){
			System.out.println(t.getFromState()+" "+t.getDescription()+" "+t.getToState());
			path.length();
			
			int pathLen;
			
			if ( epsilonAsSymbol ){
				pathLen = path.fullLength();
			} else {
				pathLen = path.length();
			}
			
			if ( filter != null && ( epsilonAsSymbol  || ! isEpsilonTransition(t) ) && ! filter.matchPosition(t, pathLen ) ){
				System.out.println("CONTINUE");
				continue;
			}
			
			TransitionPath tpath;
			if ( epsilonAsSymbol || ! isEpsilonTransition(t) ) {	//case in which we have a symbol
				tpath = new TransitionPath(path);
				tpath.addStep(t, t.getToState());
			} else {												//we are processing an epsilon transition
				path.setLastState(t.getToState());
				tpath = path;
			}
			paths.addAll(getOutgoingPaths( tpath, maxLen -1, filter ));
		}
		
		return paths;
	}

	private static boolean isEpsilonTransition(Transition t) {
		return isEpsilonTransition(t.getDescription());
	}
	
	private static boolean isEpsilonTransition(String transitionValue) {
		return transitionValue.equals("\u03BB");
	}

	/**
	 * Traverse an automaton and add the paths that start from tah given transition path.
	 * 
	 * @param path
	 * @param maxLen
	 * @param filter
	 * @return
	 */
	private List<TransitionPath> getIncomingPaths( TransitionPath path, int maxLen, PathFilter filter) {
		ArrayList<TransitionPath> paths = new ArrayList<TransitionPath>();
		if ( maxLen <= 0 ){
			if ( path.length() >= filter.getPathLen()){
				paths.add(path);
			}
			return paths;
		}
		State state = path.getFirstState();
		Automaton fsa = state.getAutomaton();
		Transition[] transitionsTo = fsa.getTransitionsToState(state);
		for ( Transition t : transitionsTo ){
			if ( filter != null && ! filter.matchPosition(t, path.length() ) ){
				continue;
			}
			TransitionPath tpath = new TransitionPath(path);
			
			tpath.addInitialStep(t, t.getToState());
			paths.addAll(getOutgoingPaths( tpath, maxLen -1, filter ));
		}
		
		return paths;
	}

	public static void printFSA(FiniteStateAutomaton fsa) {
		// TODO Auto-generated method stub
		System.out.println ( "Number of States : "+fsa.getStates().length );
		System.out.println ( "Number of Transitions : "+fsa.getTransitions().length );
		System.out.println( "Initial state: "+fsa.getInitialState().getName() );
		System.out.println ( "FSA : " );
		System.out.println ( fsa.toString() );
	}

	/**
	 * Return a list of transitions that start from a state that matches the stateExpr regular expression, and whose name match the transitionExpr regular expression
	 * @param fsa
	 * @param transitionExpr
	 * @param stateExpr
	 * @return
	 */
	public Collection<Transition> getTransitions(FiniteStateAutomaton fsa, String transitionExpr, String stateExpr) {
		ArrayList<Transition> ts = new ArrayList<Transition>();
		
		ArrayList<Transition> ets = new ArrayList<Transition>();
		Transition[] transitions = fsa.getTransitions();
		for ( Transition transition : transitions ){
			if ( transition.getFromState().getName().matches(stateExpr) ){
				if ( !epsilonAsSymbol && isEpsilonTransition(transition) ){
					ets.add(transition);
				} else if ( matchTransitionExpr(transition.getDescription(),transitionExpr)){
					//System.out.println("MATCH "+transition.getDescription());
					ts.add(transition);
				}
			}
		}
		
		for ( Transition t : ets ){
			Set<Transition> nts = getFirstNonEpsilon(t);
			for ( Transition tr : nts ){
				
				if ( matchTransitionExpr(tr.getDescription(),transitionExpr)){
					//System.out.println("MATCH "+tr.getDescription());
					ts.add(tr);
				}
			}
		}
		
		return ts;
	}

	private Set<Transition> getFirstNonEpsilon(Transition t) {
		HashSet<Transition> seen = new HashSet<Transition>();
		Automaton fsa = t.getAutomaton();
		
		seen.add(t);
		
		Stack<Transition> toProcess = new Stack<Transition>();
		Stack<Transition> children = new Stack<Transition>();
		
		toProcess.add(t);
		Set<Transition> result = new HashSet<Transition>();
		do {
			while ( toProcess.size() != 0 ){
				Transition tr = toProcess.pop();
				
				if ( !epsilonAsSymbol && isEpsilonTransition(tr) ){
					State toState = tr.getToState();
					for ( Transition tt : fsa.getTransitionsFromState(toState) ){
						if (!seen.contains(tt)){	
							children.add(tt);
						}
					}
					
				} else {
					result.add(tr);
				}
			}
			Stack<Transition> aux = children;
			children = toProcess;
			toProcess = aux;
		} while ( toProcess.size() > 0 );
		
		return result;
	}

	public State getState(FiniteStateAutomaton fsa, String stateName) throws NoSuchStateException {
		State[] states = fsa.getStates();
		for ( State state : states ){
			
			if ( state.getName().equals(stateName) )
				return state;
		}
		throw new NoSuchStateException( stateName );
	}

	private static void printHelp() {
		System.err.println("This program permits to analyze FSA files.");
		String usage ="\nUsage:\n\t tools.FSAInspector [options] <serializedFsaFile>\n";
		String options="Options are:" +
				"\n-outgoing <state> : returns transitions paths that start from the given <state> " +
				"(path len is determined by the -len or -transtionPaths option)\n" +
				"\n-incoming <state> : returns transitions paths that arrive into the given <state> " +
				"(path len is determined by the -len or -transtionPaths option)\n" +
				"-transition <regexp> : returns transitions whose description match the given java regular expression. For each transition the program prints also the three transitions that preceeds and follow it.\n" +
				"-transitionsPath <pathexp> : returns all the paths that match the given expression.\n" +
				"\t\t<pathexp> should be in the form <regexp>--><regexp>. " +
				"E.g A-->.*-->B-->.*\n" +
				"The string (lambda) is used to match lambda transitions when -lambdaAsSymbols is used\n" +
				"\t\tthe length of the returned path is the len of the provided regex path, in case also the -len <value> option is provided, the length is the maximum of the two.\n" +
				"-len <value>: specifies the length of the returned paths"+
				"-print : print FSA to stdout\n" +
				"-lambdaAsSymbol : consider lambda transitions as transitions identified normal symbols. " +
				"When paths are processed with this option enabled, states connected by lambda transitions are not merged together.\n" +
				"-containing <File> : sorts the results on the basis of the number of transition present in the <File> taht are covered.\n" +
				"-exportTGF <outputFileName> : export fsa into tgf graph format \n";
		String example="Examples:" +
				"\nTo print the content of an FSA: java -cp path/to/jar -print myFSA.fsa" +
				"\nTo show the archs going out from state q_6: java -cp path/to/jar -outgoing q_6 myFSA.fsa" +
				"\nTo show the 4 length paths from transitions that starts with \"componenet_startup\":" +
				"\n\tjava -cp path/to/jar -transitionsPath component_startup.*-->.*-->.*-->.*" +
				"\nTo show transitions that go out for state q2, following a path like A-->B-->.*-->C, and arrive into state q10 with a maximum length of 8: " +
				"\n\tjava -cp path/to/jar -outgoing q2 -incoming q10 -transitionsPath \"A-->B-->.*-->C\" -len 8 myFSA.fsa" +
				"\nTo show transitions that go out for state q2, following a path like A-->B-->.*-->C, and arrive into state q10 with a length of 6: " +
				"\n\tjava -cp path/to/jar -outgoing q2 -incoming q10 -transitionsPath \"A-->B-->.*-->C-->.*-->.*\" myFSA.fsa" +
				"\nShow paths containing lambda transitions:" +
				"\njava -cp path/to/jar -lambdaAsSymbol -transitionsPath \"(lambda)\" myFSA.fsa" +
				"\n";
		System.err.println(usage+"\n\n"+options+"\n\n"+example);
		
	}

	public List<TransitionPath> getPathsFrom(State state, int k, PathFilter f) {
		TransitionPath path = new TransitionPath(state);
		return getOutgoingPaths(path, k, f);
	}

	public List<TransitionPath> getPathsThrough(State state, int delta) {
		List<TransitionPath> ip = getIncomingPaths(state, delta, null);
		
		List<TransitionPath> res = new ArrayList<TransitionPath>();
		for ( TransitionPath trp : ip ){
			res.addAll(getOutgoingPaths(trp, delta, null));
		}
		
		return res;
	}

	private List<TransitionPath> getIncomingPaths(State state, int delta, PathFilter filter) {


		TransitionPath tp = new TransitionPath(state);
		return getIncomingPaths(tp, delta, filter);


	}

	public boolean isEpsilonAsSymbol() {
		return epsilonAsSymbol;
	}

	public void setEpsilonAsSymbol(boolean epsilonAsSymbol) {
		this.epsilonAsSymbol = epsilonAsSymbol;
	}

	
}
