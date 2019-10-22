package it.unimib.disco.lta.alfa.klfa;

import grammarInference.Log.ConsoleLogger;
import grammarInference.Record.Trace;

import it.unimib.disco.lta.alfa.inferenceEngines.FSAExtensionsRecorder;
import it.unimib.disco.lta.alfa.inferenceEngines.KBehaviorEngine;
import it.unimib.disco.lta.alfa.inferenceEngines.FSAExtensionsRecorder.FSAExtension;
import it.unimib.disco.lta.alfa.klfa.utils.componentAnalysis.ComponentTracesFile;
import it.unimib.disco.lta.alfa.tools.FSAInspector;
import it.unimib.disco.lta.alfa.tools.FSAInspector.TransitionPath;

import java.awt.Point;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


import automata.State;
import automata.Transition;
import automata.fsa.FSATransition;
import automata.fsa.FiniteStateAutomaton;

public class AnomaliesAnalyzer {

	public static class ExpectedTransitionPath {
		private TransitionPath transitionPath;
		private double similarity;
		
		public ExpectedTransitionPath( TransitionPath transitionPath, double similarity ){
			this.similarity = similarity;
			this.transitionPath = transitionPath;
		}

		public TransitionPath getTransitionPath() {
			return transitionPath;
		}

		public void setTransitionPath(TransitionPath transitionPath) {
			this.transitionPath = transitionPath;
		}

		public double getSimilarity() {
			return similarity;
		}

		public void setSimilarity(double similarity) {
			this.similarity = similarity;
		}
	}

	private int delta;
	
	public List<ExpectedTransitionPath> getExpectedBehaviors(Transition[] expectedTransitions,
			ComponentTracesFile traceFile, int startPosition) throws IOException {
		ArrayList<ExpectedTransitionPath> result = new ArrayList<ExpectedTransitionPath>();
		
		for ( Transition expectedTransition : expectedTransitions ){
			result.addAll(getUnsortedExpectedBehaviors(expectedTransition,
					traceFile, startPosition));
		}
		
		//sort results
		Collections.sort(result, new Comparator<ExpectedTransitionPath>(){

			public int compare(ExpectedTransitionPath o1,
					ExpectedTransitionPath o2) {
				
				double diff = o2.getSimilarity()-o1.getSimilarity();
				if ( diff == 0 ){
					return 0;
				}
				if ( diff > 0 ){
					return 1;
				}
				return -1;
			}
			
		});
		
		
		return result;
	}

	private Collection<? extends ExpectedTransitionPath> getUnsortedExpectedBehaviors(
			Transition expectedTransition, ComponentTracesFile traceFile,
			int startPosition) throws IOException {
		
		
			Trace trace = traceFile.getSubTrace(startPosition - delta, startPosition + delta);
			FSAInspector inspector = new FSAInspector();
			
			List<ExpectedTransitionPath> result = new ArrayList<ExpectedTransitionPath>();
			
			List<TransitionPath> paths = inspector.getPathsThrough(expectedTransition.getFromState(),delta);
			for ( TransitionPath to : paths ){
				int sim = getDifferences(to,trace);
				result.add(new ExpectedTransitionPath(to,(double)sim/(double)trace.getLength()));
			}
			
			return result;
	}

	private int getDifferences(TransitionPath to, Trace trace) {
		KBehaviorEngine kbehavior = new KBehaviorEngine(2,2,true,"none",new ConsoleLogger(0));
		
		
		kbehavior.setRecordExtensions(false);
		
		FiniteStateAutomaton currentFSA = getFSA(to);
		kbehavior.setCurrentFSA(currentFSA);
		
		kbehavior.setRecordExtensions(true);
		kbehavior.extendFSAwithTrace(trace);
		
		int differences = 0;
		List<FSAExtension> anomalies = kbehavior.getFSAExtensions();
		for ( FSAExtension cd : anomalies ){
			Trace at = cd.getAnomalousTrace();
			if ( at == null ){
				++differences;
			} else {
				differences+=at.getLength();
			}
		}
		
		return differences;
		
	}

	public static FiniteStateAutomaton getFSA(TransitionPath to) {
		FiniteStateAutomaton fsa = new FiniteStateAutomaton();
		
		State from = fsa.createState(new Point(0,0));
		fsa.setInitialState(from);
		
		for ( Transition t : to.getTransitions() ){
			State toState = fsa.createState(new Point(0,0));
			Transition trans = new FSATransition(from,toState,t.getDescription());
			fsa.addTransition(trans);
			from = toState;
		}
		
		fsa.addFinalState(from);
		
		return fsa;
	}

	
}
