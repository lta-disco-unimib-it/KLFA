/*
 * Created on 12-lug-2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package it.unimib.disco.lta.alfa.inferenceEngines;
 
import grammarInference.Log.ConsoleLogger;
import grammarInference.Log.Logger;
import grammarInference.Record.Trace;
import grammarInference.Record.TraceParser;
import grammarInference.Record.kbhParser;
import gui.environment.EnvironmentFrame;
import gui.environment.FrameFactory;

import it.unimib.disco.lta.alfa.inferenceEngines.FSAExtensionsRecorder.FSAExtension;

import java.awt.Point;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.Set;

import javax.swing.tree.DefaultTreeModel;

import conf.EnvironmentalSetter;
import conf.InteractionInferenceEngineSettings;

import automata.Automaton;
import automata.State;
import automata.Transition;
import automata.fsa.FSAConfiguration;
import automata.fsa.FSAStepWithClosureSimulator;
import automata.fsa.FSATransition;
import automata.fsa.FiniteStateAutomaton;
import automata.fsa.Minimizer;
import automata.fsa.NFAToDFA;
//import automata.LambdaTransitionChecker;


public class KBehaviorEngineSearchNeighbourhood extends KBehaviorEngine {


	public KBehaviorEngineSearchNeighbourhood() {
		super();
		// TODO Auto-generated constructor stub
	}



	public KBehaviorEngineSearchNeighbourhood(int minTrustLen, int maxTrustLen,
			boolean cutOffSearch, boolean enableRegExpr, int rptLength,
			int numberRpt, String enableMinimization) {
		super(minTrustLen, maxTrustLen, cutOffSearch, enableRegExpr, rptLength,
				numberRpt, enableMinimization);
		// TODO Auto-generated constructor stub
	}



	public KBehaviorEngineSearchNeighbourhood(int minTrustLen, int maxTrustLen,
			boolean cutOffSearch, String enableMinimization, Logger logger) {
		super(minTrustLen, maxTrustLen, cutOffSearch, enableMinimization, logger);
		// TODO Auto-generated constructor stub
	}



	public KBehaviorEngineSearchNeighbourhood(Logger logger) {
		super(logger);
		// TODO Auto-generated constructor stub
	}



	public KBehaviorEngineSearchNeighbourhood(Properties properties) {
		super(properties);
		// TODO Auto-generated constructor stub
	}



	public KBehaviorEngineSearchNeighbourhood(String configFile)
			throws FileNotFoundException, IOException {
		super(configFile);
		// TODO Auto-generated constructor stub
	}



	/**
	 * Return all the automata states in reachability order
	 * 
	 * @param fsa
	 * @param statePos
	 * @return
	 */
	protected State[] getStatesForNextBehavioralPattern(FiniteStateAutomaton fsa,State statePos) {
		
		if ( statePos == null ){
			statePos = fsa.getInitialState();
		}
		
		Set<State> visitedStates = new HashSet<State>();
		List<State> states = new ArrayList<State>();
		LinkedList<State> statesToVisit = new LinkedList<State>();
		
		statesToVisit.add(statePos);
		State[] allStates = fsa.getStates();
		



		while( statesToVisit.size() > 0 ){
			State state = statesToVisit.removeFirst();
			if ( visitedStates.contains(state) ){
				continue;
			}
			visitedStates.add(state);
			states.add(state);
			
			
			Transition[] fromT = fsa.getTransitionsFromState(state);
			if ( fromT == null ){
				continue;
			}
			
			for ( Transition t : fromT ){
				State to = t.getToState();
				if ( ! visitedStates.contains(to)){ 
					statesToVisit.add(to);
				}
			}
		}
		
		
		if ( states.size() < allStates.length ){
			statesToVisit.add(statePos);
			boolean first = true;
			while( statesToVisit.size() > 0 ){
				State state = statesToVisit.removeFirst();
				if ( !first && visitedStates.contains(state) ){
					continue;
				}
				
				if ( ! first ){
					states.add(state);
				}
				
				first=false;
				visitedStates.add(state);
				
				
				Transition[] toT = fsa.getTransitionsToState(state);
				if ( toT == null ){
					continue;
				}
				
				for ( Transition t : toT  ){
					State to = t.getFromState();
					if ( ! visitedStates.contains(to)){ 
						statesToVisit.add(to);
					}
				}
			}
		}
		
		return states.toArray(new State[states.size()]);
	}
	


	public static void main(String[] args) {

		FiniteStateAutomaton fsa=null;
		String inputFile= null;
		String outputFile = null;
		String configFile = null;
		boolean drawFSA;
		String FSAtoExtend = null;
		
		if (args.length < 4) {
			System.out.println("Usage: java grammarInference.Engine.KBehaviorEngine <traceFile> <confFile> <outputFile> <showFsa> [<extendFSA>]");
			System.out.println("<traceFile> is the file that stores traces");
			System.out.println("<confFile> is the configuration file");
			System.out.println("<outputFile> is the file where the output FSA will be recorded");
			System.out.println("<showFsa> is true if the inferred FSA must be displayed graphically");
			System.out.println("<extendFsa> if you want to extend an existing FSA add this optional parameter indicating the fsa file name");
			System.out.println("");
			System.out.println("Example: java grammarInference.Engine.KBehaviorEngine trace.txt conf/files/BCT.properties output.fsa true");
			System.exit(0);
		}
		
		if (args.length >0) {
			inputFile = args[0];
		} else {
			inputFile =
			new String(System.getProperty("user.dir") + "/exampletrace.txt");
		}
		//System.out.println(inputFile);
	
		if (args.length>1) {
			configFile = args[1];
		} else {
			configFile = new String(System.getProperty("user.dir") + "/inference.conf");
		}
		
		if (args.length>2) {
			outputFile = args[2];
		}
		
		if ((args.length>3) && (args[3].equals("true"))) {
			drawFSA = true;
		} else {
			drawFSA = false;
		}

		if (args.length>4) {
			FSAtoExtend = args[4];
		}
		

		//inputFile = "c:\\inputFile.txt";
		//outputFile ="c:\\outputFile.txt"; 
		//drawFSA = true;
		try {
			EnvironmentalSetter.setConfigurationValues(new FileInputStream(configFile));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		//KBehaviorEngine engine = new KBehaviorEngine(2,4,true,"none");

		KBehaviorEngineSearchNeighbourhood engine;
		try {
			engine = new KBehaviorEngineSearchNeighbourhood(configFile);
		

		long startTime = System.currentTimeMillis();
		try {
			if ( FSAtoExtend == null )
				fsa = engine.inferFSAfromFile(inputFile);
			else{
				engine.setRecordExtensions(true);
				FiniteStateAutomaton extend = FiniteStateAutomaton.readSerializedFSA(FSAtoExtend);
				fsa = engine.inferFSAfromFile(inputFile,extend);
			}
		} catch (FileNotFoundException e) {
			System.out.println("The file " + inputFile + " does not exist!");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		long endTime=System.currentTimeMillis();
		System.out.println("Inference Time " + (endTime-startTime));
		System.out.println(fsa);
		
		for ( Transition t : fsa.getTransitions()){
			System.out.println(t.getClass().getName()+" "+t.getFromState());
			//CountingFSATransition tr = (CountingFSATransition)t;
			//tr.setLabel(tr.getLabel()+"\n"+tr.getSymbolIds());
		}
		
		if(drawFSA) engine.drawFSA();

		if (args.length>2) {
			serializeFSA(fsa,outputFile);
		}
		
		
		System.out.println("Changes ");
		
		for ( FSAExtension data : engine.getFSAExtensions() ){
			System.out.println(data.getExtensionType()+" "
					+data.getStartPosition()+" "
					+data.getStartState().getName()+" "
					+data.getAnomalousTrace());
		}
		} catch (FileNotFoundException e1) {
			
			e1.printStackTrace();
		} catch (IOException e1) {
			
			e1.printStackTrace();
		}
	}
	

	
	
	  public static void process(File inputFile, File outputFile) {
	    FiniteStateAutomaton fsa = null;

	    KBehaviorEngineSearchNeighbourhood engine = new KBehaviorEngineSearchNeighbourhood();
	    
	    engine.setEnableMinimization(EnvironmentalSetter.getInferenceEngineSettings().getEnableMinimization());
	    engine.setMinTrustLen(EnvironmentalSetter.getInferenceEngineSettings().getMinTrustLen());
	    engine.setMaxTrustLen(EnvironmentalSetter.getInferenceEngineSettings().getMaxTrustLen());
	    engine.setCutOffSearch(EnvironmentalSetter.getInferenceEngineSettings().isCutOffSearch());
	    
	    try {
	      fsa = engine.inferFSAfromFile(inputFile.getAbsolutePath());
	    }
	    catch (FileNotFoundException e) {
	      System.out.println("The file " + inputFile + " does not exist!");
	    }
	    System.out.println(fsa);
	    serializeFSA(fsa, outputFile.getAbsolutePath());
	  }


	  
}
