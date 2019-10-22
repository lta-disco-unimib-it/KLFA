package it.unimib.disco.lta.alfa.transitionsWeightsCalculator;

import grammarInference.Record.Symbol;
import grammarInference.Record.Trace;
import grammarInference.Record.VectorTrace;
import grammarInference.Record.TraceParser;
import grammarInference.Record.kbhParser;

import it.unimib.disco.lta.alfa.transitionsWeightsCalculator.FSAVisitorWeight.TransitionsSequence;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Map.Entry;


import automata.ClosureTaker;
import automata.State;
import automata.Transition;
import automata.fsa.FSAConfiguration;
import automata.fsa.FSATransition;
import automata.fsa.FiniteStateAutomaton;


public class TransitionsCoverageWeightsCalculator implements TransitionsWeightsCalculator {
	
	private File[] traceFiles;
	private FiniteStateAutomaton fsa;
	private int symbolsSequenceSize;

	public TransitionsCoverageWeightsCalculator( FiniteStateAutomaton fsa, File[] traces, int symbolsSequenceSize ) {
		this.fsa = fsa;
		this.symbolsSequenceSize = symbolsSequenceSize;
		this.traceFiles = traces;
	}

	public static void main(String args[]){
		int k = Integer.valueOf(args[0]);
		String fsaFile = args[1];
		
		try {
			FiniteStateAutomaton fsa = FiniteStateAutomaton.readSerializedFSA(fsaFile);
			File[] files = new File[args.length-2];
			for ( int i = 2; i < args.length; ++i ){
				files[i-2]=new File(args[i]);
			}
			TransitionsCoverageWeightsCalculator c = new TransitionsCoverageWeightsCalculator(fsa,files,k);
			Map<TransitionsSequence, Double> wmap = c.calculateWeigths();
			
			Properties p = new Properties();
			for ( Entry<TransitionsSequence,Double> e : wmap.entrySet() ){
				p.put(e.getKey().toString(),e.getValue().toString());
			}
			
			FileOutputStream fos = new FileOutputStream("wout.txt");
			p.store(fos, "");
			fos.close();
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

	public Map<TransitionsSequence, Double> calculateWeigths() {
		Map<TransitionsSequence,Double> map = new HashMap<TransitionsSequence, Double>();
		try {
			for ( File traceFile : traceFiles ){
				calculateWeigths(traceFile,map);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}
	
	public void calculateWeigths(File traceFile, Map<TransitionsSequence,Double> wMap) throws FileNotFoundException {
		
		
		TraceParser fileParser = new kbhParser(traceFile.getAbsolutePath());
		
		Iterator<VectorTrace> it = fileParser.getTraceIterator();
		
		while ( it.hasNext() ){
			Trace trace = it.next();
			calculateWeigths(trace, wMap);
		}
		
		
	}
	
	public void calculateWeigths(Trace trace, Map<TransitionsSequence,Double> wMap) {
		
		FSAVisitorWeight visitor = new FSAVisitorWeight(symbolsSequenceSize);
		FSATraverser traverser = new FSATraverser(fsa,trace,visitor,0);
		traverser.traverse();
		
		Map<TransitionsSequence, Integer> map = visitor.getSequences();
		for ( Entry<TransitionsSequence,Integer> tse : map.entrySet() ){
			TransitionsSequence key = tse.getKey();
			Double value = wMap.get(key);
			if ( value == null ){
				wMap.put(key, (double)tse.getValue().intValue());
			} else {
				wMap.put(key, value+(double)tse.getValue().intValue());
			}
		}
		

	}
}
