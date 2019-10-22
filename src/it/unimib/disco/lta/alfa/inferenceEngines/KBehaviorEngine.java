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

/**
 * 
 * @author Leonardo Mariani
 *
 * The kBehavior Engine is the class implementing the inference engine. Once
 * it has been created, it is possible to use the method inferFSAfromFile to
 * infer the FSA. 
 * 
 * When the use of the inference engine is completed, the method releaseResources
 * must be invoked to release acquired resources.
 */
public class KBehaviorEngine {
	/** The lambda string. */
	protected final String LAMBDA = "";

	//params of the technique

	// params of extension mechanism
	protected int minTrustLen = 2;
	protected int maxTrustLen = 4;
	protected boolean cutOffSearch = true;
	private FSAExtensionsRecorder extensionsRecorder = new FSAExtensionsRecorder();
	
	/*	// params for reg.Expr.
		private boolean enableRegExpr = true;
		private int rptLength = 3;
		private int numberRpt = 2; */



	// params for minimization
//	private boolean enableMinimization = true;
	private String enableMinimization = new String("step");
/*	private static final String NO_MINIMIZATION = "none";
	private static final String STEP = "step";
	private static final String END = "end";*/

	// params for displaying the automaton
	private int xStep = 60;
	private final int xInitValue = 30;
	private int yStep = 60;
	private final int yInitValue = 30;
	private int maxLength = 950;

	// coordinate of the position that is actually considered for placing next node
	private int xPos;
	private int yPos;

	protected Logger logger = new ConsoleLogger(Logger.debuginfoLevel);;
	
	// the current FSA where all methods are aplpied
	private FiniteStateAutomaton currentFSA = null;

	//Generators of rundom numbers
	private Random randomGen = new Random();

	//used to save FSA before extending it
	private boolean stepSave = false;

	private int mainTracePos;

	private int minimizationLimit = 0;

	private String fileName;

	
	/**
	 *  The constructor initializes all params to default value. Defaults values are:
	 * 	minTrustLen = 2;
	 * 	maxTrustLen=4
	 * 	cutOffSearch=true
	 * 	enableMinimization = step
	 */
	public KBehaviorEngine() {
		this( new ConsoleLogger( 3 ) );
				
		logger.logEvent("Initialization complete \n");
	}

	/**
	 * The inference engine is created by reading params values from the configuration 
	 * file specified as parameter.
	 * 
	 * @param configFile name of the file storing the configuration
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public KBehaviorEngine(String configFile) throws FileNotFoundException, IOException {
		logger.logEvent(
				"Loading parameters from file " + configFile);
		Properties properties = new Properties();
		properties.load(new FileInputStream(configFile));
		init(properties);
	}
	
	/**
	 * The inference engine is created and the passed config parameters are loaded
	 * @param properties
	 */
	public KBehaviorEngine(Properties properties) {
		init(properties);
	}
	
	private void init(Properties properties){

		
		// Load properties
		//try {
			

			minTrustLen =
				Integer.parseInt(properties.getProperty("minTrustLen"));
			//FIXME: was cutOffLenth
			maxTrustLen =
				Integer.parseInt(properties.getProperty("maxTrustLen"));
			cutOffSearch =
				Boolean.valueOf(properties.getProperty("cutOffSearch")).booleanValue();
/*			enableMinimization =
				Boolean.valueOf(
					properties.getProperty("enableMinimization")).booleanValue();*/
			enableMinimization = properties.getProperty("enableMinimization");
			
			String ml;
			 
			ml = properties.getProperty("minimizationLimit");
			if ( ml != null ){
				minimizationLimit = Integer.valueOf(ml);
			}
			
			
			
			stepSave = Boolean.valueOf(properties.getProperty("stepSave"));
			
			int verbosity = Integer.valueOf(properties.getProperty("level"));
			
			
			logger.logInfo("changing loglevel to "+verbosity);
			
			logger.setVerboseLevel(verbosity);

			
				
			if ( !enableMinimization.equals(InteractionInferenceEngineSettings.END) && !enableMinimization.equals(InteractionInferenceEngineSettings.STEP) && !enableMinimization.equals(InteractionInferenceEngineSettings.NO_MINIMIZATION) ) 
			{
				logger.logEvent("Invalid Value for enableMinimization. Read value=" + enableMinimization +". The value is fixed to default");
				this.enableMinimization = InteractionInferenceEngineSettings.STEP;
			} 

			logger.logInfo(
				"Parameters:\n minTrustLen="
					+ minTrustLen
					+ " maxTrustLen="
					+ maxTrustLen
					+ " cutOffSearch="
					+ cutOffSearch
					+ " enableMinimization="
					+ enableMinimization
					+ " stepSave ="
					+ stepSave );
			logger.logEvent("Loading parameters completed");
			
			
		/*} catch (Exception e) {
			minTrustLen = 2;
			maxTrustLen = 4;
			cutOffSearch = true;
			enableMinimization = InteractionInferenceEngineSettings.STEP;
			logger.logUnexpectedEvent(
				"ERROR... CAUSE="
					+ e.getCause()
					+ " MESSAGE="
					+ e.getMessage());
			logger.logUnexpectedEvent(
				"Loading parameters failed. Default values loaded");
		}*/
	}

	/**
	 * The constructor creates a new inference engine with the parameters specified as
	 * argument
	 * 
	 * @param minTrustLen miniomum length for a matching sub-behavior
	 * @param maxTrustLen length of the behavior enabling the cutOffSearch
	 * @param cutOffSearch boolean variable for enabling or disabling the curOffSearch
	 * @param enableMinimization enable or disable the execution of the minimization step
	 */
	public KBehaviorEngine(
		int minTrustLen,
		int maxTrustLen,
		boolean cutOffSearch,
		String enableMinimization,
		Logger logger) {
		this(logger);

		this.minTrustLen = minTrustLen;
		this.maxTrustLen = maxTrustLen;
		this.cutOffSearch = cutOffSearch;

		/*	this.enableRegExpr = enableRegExpr;
			this.rptLength = rptLength;
			this.numberRpt = numberRpt;*/

		this.enableMinimization = enableMinimization;
	}

	
	
	/**
	 * Constructor with all parameters. Parameters about the regular expressions are 
	 * not supported. 
	 * @param minTrustLen miniomum length for a matching sub-behavior
	 * @param maxTrustLen length of the behavior enabling the cutOffSearch
	 * @param cutOffSearch boolean variable for enabling or disabling the curOffSearch
	 * @param enableRegExpr enable the generalization step on the regular expression
	 * @param rptLength the max length considered for finding repeated substrings
	 * @param numberRpt the amount of times a substring must be repeated to be considered 
	 * for generalization
	 * @param enableMinimization enable or disable the execution of the minimization step
	 */
	public KBehaviorEngine(
		int minTrustLen,
		int maxTrustLen,
		boolean cutOffSearch,
		boolean enableRegExpr,
		int rptLength,
		int numberRpt,
		String enableMinimization) {
		this();

		this.minTrustLen = minTrustLen;
		this.maxTrustLen = maxTrustLen;
		this.cutOffSearch = cutOffSearch;

		/*	Comment to be removed if support for RE is added
		 	
		 	this.enableRegExpr = enableRegExpr;
			this.rptLength = rptLength;
			this.numberRpt = numberRpt;*/

		this.enableMinimization = enableMinimization;
	}

	
	public KBehaviorEngine(Logger logger) {
		this.logger = logger;
		currentFSA = new FiniteStateAutomaton();

		xPos = xInitValue;
		yPos = yInitValue;

		randomGen = new Random();
	}

	public void setCutOffSearch(boolean cutOffSearch) {
		this.cutOffSearch = cutOffSearch;
	}

	
	/**
	 * The method infers a FSA by the kBehavior algorithm. The input traces are read
	 * from a file
	 * 
	 * @param fileName name of the file storing the input traces
	 * @param inputFSA FSA that we want to extendwith the traces
	 * @return the inferred FSA
	 * @throws FileNotFoundException 
	 * @throws FileNotFoundException the exception is generated if the input file is not found
	 */
	public FiniteStateAutomaton inferFSAfromFile(String fileName ) throws FileNotFoundException{
		return inferFSAfromFile(fileName, new FiniteStateAutomaton() );
	}
	
	
	public FiniteStateAutomaton inferFSAfromFile(String fileName, String fileToExtend ) throws FileNotFoundException, ClassNotFoundException, IOException{
		FiniteStateAutomaton fsaToExtend = FiniteStateAutomaton.readSerializedFSA(fileToExtend);
		return inferFSAfromFile(fileName,fsaToExtend);
	}
	
	
	
	/**
	 * The method infers a FSA by the kBehavior algorithm. The input traces are read
	 * from a file
	 * 
	 * @param fileName name of the file storing the input traces
	 * @param inputFSA FSA that we want to extendwith the traces
	 * @return the inferred FSA
	 * @throws FileNotFoundException the exception is generated if the input file is not found
	 */
	public FiniteStateAutomaton inferFSAfromFile(String fileName, FiniteStateAutomaton inputFSA )
		throws FileNotFoundException {
		
		this.fileName = fileName;
		logger.logEvent("--- Begin Inference ---\n");
		
		logger.logEvent(""+minTrustLen);
		currentFSA = inputFSA;
		
		logger.logInfo("Parsing file " + fileName);

		TraceParser fileParser = new kbhParser(fileName);
//		TraceParser fileParser = new AbbadingoParser(fileName);

		Iterator it = fileParser.getTraceIterator();
	
		return extendFSAwithTraces(it, inputFSA);
	}
	
	
		public FiniteStateAutomaton extendFSAwithTrace(Trace trace, FiniteStateAutomaton inputFSA ){
			
			List<Trace> traces = new ArrayList<Trace>();
			traces.add(trace);
			return extendFSAwithTraces(traces.iterator(), inputFSA);
			
		}
		
		public FiniteStateAutomaton extendFSAwithTraces(List<Trace> traces, FiniteStateAutomaton inputFSA ){
			return extendFSAwithTraces(traces.iterator(), inputFSA);
		}
		
		public FiniteStateAutomaton extendFSAwithTraces(Iterator it, FiniteStateAutomaton inputFSA ){
			currentFSA=inputFSA;
		int cycle = 0;
		int logOffset=0;
		
		long traceCounter=1;
		
		while (it.hasNext()) {
			
			 
			
			int numStates_tmp = currentFSA.getStates().length;
			int numTransitions_tmp = currentFSA.getTransitions().length;
			int numFinalStates_tmp = currentFSA.getFinalStates().length;
			
			logger.logEvent("Reading trace " + traceCounter++ + "\n");
			Trace trace = (Trace) it.next();
			logger.logEvent("Reading complete\n");
			//System.out.println("L"+trace.getLength()); 
			if ( trace.getLength() < 100 )
				logger.logInfo("Trace Value= " + trace + "\n");
			else
				logger.logInfo("Trace Value not printed: size too big\n");
			
			if ( fileName != null && stepSave ){
				logger.logEvent("Saving current FSA...\n");
				serializeFSA(currentFSA, fileName+".fsa"+cycle+".ser" );
			}
			
			logger.logEvent("Extending current FSA...\n");
			extendFSAwithTrace(trace);
			logger.logEvent("Extension performed\n");

			// if you prefer minimization at the end of the inference, you can comment
			// next instruction and uncomment the call to minimizeFSA below the end of the cycle
			if (enableMinimization.equals(InteractionInferenceEngineSettings.STEP)) {
				
				if ( minimizationLimit <= 0 || currentFSA.getStates().length < minimizationLimit ){

					logger.logEvent("Starting Minimization\n");
					if (currentFSA.getStates().length!=numStates_tmp || currentFSA.getTransitions().length!=numTransitions_tmp || currentFSA.getFinalStates().length!=numFinalStates_tmp) minimizeFSA();
					logger.logEvent("Minimization Completed\n");
				}
			}
			
			logger.logEvent("FSA generated after trace "+traceCounter);
			logger.logEvent(currentFSA.toString());
			
			
			
			logOffset += trace.getLength()+1;	
			
			
			extensionsRecorder.setOffset( logOffset );
			
			++cycle;
		}

		if (enableMinimization.equals(InteractionInferenceEngineSettings.END)) {
			logger.logEvent("Starting Minimization\n");
			if ( minimizationLimit <= 0 || currentFSA.getStates().length < minimizationLimit ){
			minimizeFSA();
			}
			logger.logEvent("Minimization Completed\n");
		}
		logger.logEvent("--- End Inference ---\n");

		logger.logInfo(
			"Output Automaton:\n" + currentFSA.toString());

		logger.logEvent("Starting visualization...\n");

		return currentFSA;
	}

	/**
	 * This method automatically performs all steps necessary to minimize the current FSA
	 */
	public void minimizeFSA() {
		/*
		 * The minimizer is able to minimize only DFA, therefore we need to transform the
		 * currentFSA into a DFA. 
		 */
		logger.logInfo("Beginning Conversion to DFA");
		 NFAToDFA converter = new NFAToDFA();
		 currentFSA=converter.convertToDFA(currentFSA);
		 logger.logInfo("Conversion Completed, DFA=" + currentFSA);
		 
		 logger.logInfo("Inizialitazione of the minimizer...");
		 Minimizer minimizer = new Minimizer();
		 minimizer.initializeMinimizer();
		 Automaton tmpAutomaton = minimizer.getMinimizeableAutomaton(currentFSA);
	
		 DefaultTreeModel dtm = minimizer.getDistinguishableGroupsTree(tmpAutomaton);
		
/*		EngineConfig.logger.logInfo("Tree Model Built: " + dtm);
		 while (dtm != null) {
			EngineConfig.logger.logInfo("Begin Splitting States");
			ArrayList groups = minimizer.split(tmpAutomaton.getStates(),tmpAutomaton, dtm);
			EngineConfig.logger.logInfo("States Splitted");
			
			dtm = minimizer.getDistinguishableGroupsTree(tmpAutomaton);
			EngineConfig.logger.logInfo("Tree Model Built: " + dtm);
		 }*/
		 
		 logger.logInfo("Starting computation of the minimized automaton");
		 currentFSA= (FiniteStateAutomaton) minimizer.getMinimumDfa(tmpAutomaton,dtm);
	}

	/**
	 * The method must be invoked when the engine is not going to tbe used anymore. 
	 * It releases all acquired resources.
	 */
	public void releaseResources() {
		logger.close();
	}

	/**
	 *  The current FSA is drawn on the screen
	 */
	public void drawFSA() {
		EnvironmentFrame f = FrameFactory.createFrame(currentFSA);
	}

	/**
	 * The current FSA is extended with a single trace
	 * 
	 * @param trace the trace that is used for extending the current FSA
	 */
	public void extendFSAwithTrace(Trace trace) {
		FSAConfiguration myConfig = null;
		State curState = null;
		State nextState = null;
		State statePos = null;

		int tracePos = -1;
		
		/* the first part of the code (up to the end of the if) moves the algorithm
		   to a state where some transitions have been considered and it is necessary
		   to extend the FSA. The considered transitions can be those just added
		   (if the FSA is empty) or those covering the first part of the String
		   (if the FSA already exists). In the  second part the algorithm is involved in
		   a searchBP-extend cycle
		*/
		
		if (currentFSA.getInitialState() == null) {
			/*initialization is performed by creating a FSA with 
			 a number of transitions equal to the length of the basic 
			trustable behavior. Exit of this block when generation of 
			new behavior is blocked. */

			logger.logEvent("Initialization of the FSA\n");
			logger.logInfo("minTrustLen=" + minTrustLen + "\n");
			logger.logInfo(
				"trace length=" + trace.getLength() + "\n");

			int i = 0;

			/* 
			 * the FSA is initialized with the first n behaviors. n 
			 * is the minimum value between the lengh of trustable sequences
			 * and the length of the trace.
			*/

			// WARNING, ASSUMED minTrustLen >0 e trace length >0
			nextState = genState();
			currentFSA.setInitialState(nextState);

			// generation of behaviors
			while (i < minTrustLen && i < trace.getLength()) {
				curState = nextState;
				nextState = genState();
//				CountingFSATransition t =
//					new CountingFSATransition(
//						curState,
//						nextState,
//						trace.getSymbol(i).getValue(),getMainTracePosition(i));
				FSATransition t =
					new FSATransition(
						curState,
						nextState,
						trace.getSymbol(i));
				currentFSA.addTransition(t);

				i++;
			}

			tracePos = i;
			statePos = nextState;

			if (i == trace.getLength()) {
				// the first part fo the behavior consists on the whole trace
				
				extensionsRecorder.addFinal(trace, nextState, tracePos);
				currentFSA.addFinalState(nextState);
				logger.logEvent("Initialization Complete");
				logger.logInfo(
					"automaton initialized = " + currentFSA);
				return;
			} else {
				// initialization complete, now the FSA must be extended. 
				// Considering extension after early initialization enables the 
				// possibility to directly detect repeated behaviors without 
				// requiring reduction steps
				logger.logEvent("Initialization Complete");
				logger.logInfo(
					"automaton initialized = " + currentFSA);
			}
		} else {
			/* searching of the longest already generated behavior */

			logger.logInfo(
				"Searching for the longest already generated behavior\n");

			if ( trace.getLength() == 0 ){
				State state = currentFSA.getInitialState();
				if ( ! currentFSA.isFinalState(state) ){
					

					extensionsRecorder.addFinal(trace, state , 0);
					currentFSA.addFinalState( state );
					logger.logEvent("Initial state set as Final");
				}
				return;
			}
			
			SimulationResult sr =
				FSASimulateAsPossible(
					currentFSA,
					currentFSA.getInitialState(),
					trace,
					0);

			tracePos = sr.nextSymbol;
			if (sr.reachableStates == null) {
				/* if no reachable states are available, it means that no symbols are
				 generated. Since I necessarily started from the initial state, we 
				 must go back to the initial state */
				statePos = currentFSA.getInitialState();
			} else {
				// next state is searched in a non'deterministic way
				statePos = randomSelectionState(sr.reachableStates);
			}

			logger.logInfo(
				"Behavior generated up to position " + tracePos + "\n");

			if (sr.nextSymbol >= trace.getLength()) {
				// the considered string is already generated from the current FSA

				// checking if there is a final accepting state for the string
				if (!existFinalState(currentFSA, sr.reachableStates)) {
					
					
					
					
					// non-deterministic addition of a new final accepting state
					State finalState = randomSelectionState(sr.reachableStates);
					
					
//					Automaton fsa = finalState.getAutomaton();
//					State[] states = fsa.getFinalStates();
//					boolean isFinal = true;
//					for ( State state : states ){
//						System.out.println("final name "+ state.getName());
//						if ( state.getName().equals(finalState.getName()) ){ 
//							isFinal = false;
//							break;
//						}
//					}
//					if ( isFinal ){
//						cr.addFinal(trace, finalState, trace.getLength()-1 );
//					}
					if ( !finalState.getAutomaton().isFinalState(finalState)){	
						extensionsRecorder.addFinal(trace, finalState, trace.getLength()-1 );
					}
					
					
					currentFSA.addFinalState(finalState);
					logger.logInfo(
						"The state "
							+ finalState
							+ " has been turned to an acceptiong state\n");
					
					
					
					
				}
				return;
			}

		}

		// ----------------- SearchBP-Extend-move cycle -----------------------
		/*
		 * Once we are at a fixed point, we must (1) searching for a behavioral patter,
		 * (2) extending the current FSA according to the located behavioral pattern,
		 * (3) simulating execution until possible (4) repeating behaviors 1-3
		 * If no behavioral patterns are found, a tail must be added*/

		// tracePos and statePos have been set in the first part of the method
		//boolean inTail = false;
		boolean isRecording = getRecordExtensions();
		
		while (tracePos < trace.getLength()) {
			// detecting the first behavioral pattern
			BehavioralPatternData bpd =
				nextBehavioralPattern(currentFSA, trace, tracePos, curState);

			if (bpd.fromState != null) {

				/* a behavioral pattern has been recognized; therefore, the
				 * beginning of the behavioral pattern must be suitably 
				 * connected to the current state */

				logger.logEvent("Adding a branch to the FSA");
				addBranch(currentFSA, statePos, trace, tracePos, bpd);
				logger.logEvent("Branch added");

				/* cursors switch to the end of the behavioral pattern and
				 * the corresponding position in the trace is update. In fact,
				 * the current FSA generates the new string up to the end of the
				 * behavioral pattern. 
				 */
				statePos = bpd.toState;
				tracePos = bpd.toTrace + 1;
				logger.logInfo(
						"cursors updates; new values state="
						+ statePos
						+ " pos="
						+ tracePos);

				if (tracePos < trace.getLength()) {
					// the actual trace is not yet finished

					logger.logInfo("Continuing over FSA");
					// continue simulating as possible
					SimulationResult res =
						FSASimulateAsPossible(
								currentFSA,
								statePos,
								trace,
								tracePos);
					if (res.reachableStates != null) {

						tracePos = res.nextSymbol;
						//non deterministic choice of next state
						statePos = randomSelectionState(res.reachableStates);
						logger.logInfo(
								"Stopped at State="
								+ statePos
								+ " Trace Pos="
								+ tracePos);
					} else {
						logger.logInfo("Continuation impossible");
					}
				}
				if (tracePos >= trace.getLength()) {


					if ( ! currentFSA.isFinalState(statePos) ){
						
						extensionsRecorder.addFinal(trace, statePos, trace.getLength()-1 );
						

						currentFSA.addFinalState(statePos);
					}

				}
			} else {

				/*NEW IMPLEMENTATION WITH INCREMENTAL ADDITION OF SINGLE STATES*/
				State sttemp = genState();
				logger.logInfo("State " + sttemp +" created in the new tail");

				FSATransition tr =
					new FSATransition(
							statePos,
							sttemp,
							trace.getSymbol(tracePos));
				currentFSA.addTransition(tr);
				logger.logInfo("Transition " + tr +" created in the new tail");

				
				extensionsRecorder.addTail(trace, statePos,tracePos);
				//We are in a tail, from now on stop recording
				extensionsRecorder.setRecord(false);
				

				statePos = sttemp;
				tracePos++;
				if (tracePos==trace.getLength()) {






					if ( ! currentFSA.isFinalState(sttemp) ){
						
						extensionsRecorder.addFinal(trace, sttemp, trace.getLength()-1 );	
						
					}

					currentFSA.addFinalState(sttemp);
					logger.logInfo("End of creation of the new tail");
				}

			}
		}
		setRecordExtensions(isRecording);
		//		ExtendFSA(bpd);
	}

	private int getMainTracePosition(int i) {
		return mainTracePos + i;
	}

	private State splitState(State s) {
		State newState = currentFSA.createState(genPoint());

		Transition [] trSet = currentFSA.getTransitionsToState(s);

		for (int i=0; i< trSet.length; i++) {
			FSATransition tr;
//			if ( trSet[i] instanceof CountingFSATransition ){
//				tr = new CountingFSATransition(trSet[i].getFromState(),newState, ((CountingFSATransition)trSet[i]).getLabel(), ((CountingFSATransition)trSet[i]).getSymbolIds());
//			} else {
//				tr = new CountingFSATransition(trSet[i].getFromState(),newState, ((FSATransition)trSet[i]).getLabel(), -1);
//			}
			tr = new FSATransition(trSet[i].getFromState(),newState, ((FSATransition)trSet[i]).getLabel());
			currentFSA.addTransition(tr);
			currentFSA.removeTransition(trSet[i]);
		}
		//CountingFSATransition tr = new CountingFSATransition(newState, s, LAMBDA, -1);
		FSATransition tr = new FSATransition(newState, s, LAMBDA);
		currentFSA.addTransition(tr);
		if (currentFSA.getInitialState()==s) {
			currentFSA.setInitialState(newState);
		}
		return newState;
	}

	/**
	 * The method adds a new branch in the FSA connecting the current position
	 * with the begin of the behavioral pattern taking into account the
	 * actual trace
	 * 
	 * @param fsa the current FSA
	 * @param actualState the current state of the FSA
	 * @param t the actual trace
	 * @param actualPos the current position in the trace t
	 * @param bpd the behavioral pattern that must be connected
	 */
	private void addBranch(
		FiniteStateAutomaton fsa,
		State actualState,
		Trace t,
		int actualPos,
		BehavioralPatternData bpd) {

		State curState = null;
		State nextState = null;

		logger.logInfo("Adding Branch, actualState = "+actualState+" ,actualPos"+actualPos);
		
		
		

		extensionsRecorder.addBranch(actualState,actualPos,t,bpd,mainTracePos+actualPos);
		
		boolean crState = extensionsRecorder.isRecording();
		extensionsRecorder.setRecord(false);
		
		if (actualState.equals(bpd.fromState)) actualState = splitState(actualState);

		if (actualPos == bpd.fromTrace) {
			//adding a lambda transition
			logger.logInfo("Adding Lambda Transition");
			//CountingFSATransition tr = new CountingFSATransition(actualState, bpd.fromState, LAMBDA, actualPos);
			FSATransition tr =new FSATransition(actualState, bpd.fromState, LAMBDA);
				
			fsa.addTransition(tr);

		} else {
			
			
			logger.logInfo("Preparation to Recursion Step");

			// (Recursively) Inferring the FSA for the subbehavior that must be 
			// used for connecting the behavioral pattern to the actual position
			KBehaviorEngine localEngine =
				new KBehaviorEngine(
					this.minTrustLen,
					this.maxTrustLen,
					this.cutOffSearch,
					this.enableMinimization,
					this.logger);
			logger.logInfo(
				"Recursion on trace"
					+ t.getSubTrace(actualPos, bpd.fromTrace - 1));
			
			mainTracePos+=actualPos;
			
			localEngine.extendFSAwithTrace(
				t.getSubTrace(actualPos, bpd.fromTrace - 1));
			FiniteStateAutomaton localFSA = localEngine.getCurrentFSA();

			mainTracePos-=actualPos;
			
			logger.logInfo("Starting Merging of Automata");
			//merging degli automi
			MergeAutomata(fsa, localFSA, actualState, bpd.fromState);
			logger.logInfo("End Merging");

			/*			nextState = actualState;
						for (int i = 0; i < bpd.fromTrace - actualPos - 1; i++) {
							curState = nextState;
							nextState = genState();
			
							FSATransition tr =
								new FSATransition(
									curState,
									nextState,
									t.getSymbol(actualPos + i).getValue());
							fsa.addTransition(tr);
						}
			
						// transition to the joining point of the existing FSA
						FSATransition tr =
							new FSATransition(
								nextState,
								bpd.fromState,
								t.getSymbol(bpd.fromTrace - 1).getValue());
						fsa.addTransition(tr);
			*/
			
		}

		extensionsRecorder.setRecord(crState);
		
		// if final new behavior to check for accepting state
		if ((bpd.toTrace == t.getLength() - 1)
			&& fsa.isFinalState(bpd.toState) == false) {
			

			extensionsRecorder.addFinal(t, bpd.toState, bpd.toTrace);
			
			fsa.addFinalState(bpd.toState);
			
		}
		
		
	}

	/**
	 * The method merge the automaton specified as second parameter into 
	 * the automaton specified as the first parameter. The two states are
	 * the gluing point.
	 * 
	 * @param fromFSA the automaton that will contain the new automaton
	 * @param toFSA the automaton merged inside the first automaton
	 * @param beginState the state of the fromFSA automaton that must coincide 
	 * with the initial state of the toFSA automaton
	 * @param endState the state of the from FSA automaton that must coindice
	 * with the final state of the toFSA automaton; it is assumeted that the
	 * toFSA automaton has got only one final state
	 */
	private void MergeAutomata(
		FiniteStateAutomaton largeFSA,
		FiniteStateAutomaton innerFSA,
		State beginState,
		State endState) {

		State[] states = innerFSA.getStates(); // all states that must be added

		HashMap IDmap = new HashMap();
		/* hashmap with pairs IDstate, cardinal number
											   cardinal numbers are used to indentify states in 
											   the new FSA */
		int id = 0;

		logger.logInfo("Creation Map of IDs");

		/* population of the HashMap. Initial and Final states are not added because
		 * must be mapped to states already existing in the largeFSA
		 */
		for (int i = 0; i < states.length; i++) {
			if ((!innerFSA.isFinalState(states[i]))
				&& (innerFSA.getInitialState().getID() != states[i].getID())) {
				IDmap.put(new Integer(states[i].getID()), new Integer(id++));
			}
		}

		logger.logInfo(
			"Creation of "
				+ (states.length - 2)
				+ " new states from the inner FSA");

		// creation of new states in the largeFSA. The cardinal number is used to pair
		// new states with old ones

		int arrayLength = 0;
		
		if (innerFSA.isFinalState(innerFSA.getInitialState())) {
			arrayLength = states.length -1;
		} else {
			arrayLength = states.length -2;
		}
		
		State[] newStates =
			(State[]) Array.newInstance(State.class, arrayLength);

		for (int i = 0; i < newStates.length; i++) {
			newStates[i] = largeFSA.createState(genPoint());
		}
		
		//Copying all transitions. Transitions involving the initial or final states of innerFSA
		// are modified to be connected with the corresponding states of the largeFSA
		Transition[] trans = innerFSA.getTransitions();
		State fromState = null;
		State toState = null;

		logger.logInfo(
			"Creation of "
				+ (trans.length)
				+ " new transitions from the inner FSA");

		for (int i = 0; i < trans.length; i++) {
			// defining source of transition
			if (innerFSA.isFinalState(trans[i].getFromState())) {
				fromState = endState;
			} else if (
				innerFSA.getInitialState().getID()
					== trans[i].getFromState().getID()) {
				fromState = beginState;
			} else {

				logger.logInfo(
					"IDstate="
						+ (Integer) IDmap.get(
							new Integer(trans[i].getFromState().getID())));
				fromState =
					newStates[(
						(Integer) IDmap.get(
							new Integer(trans[i].getFromState().getID())))
						.intValue()];
			}

			// defining target of the transition
			if (innerFSA.isFinalState(trans[i].getToState())) {
				toState = endState;
			} else if (
				innerFSA.getInitialState().getID()
					== trans[i].getToState().getID()) {
				toState = beginState;
			} else {
				toState =
					newStates[(
						(Integer) IDmap.get(
							new Integer(trans[i].getToState().getID())))
						.intValue()];
			}

			//creation of the transition
//			CountingFSATransition tr =
//				new CountingFSATransition(
//					fromState,
//					toState,
//					((CountingFSATransition) trans[i]).getLabel(), ((CountingFSATransition) trans[i]).getSymbolIds());
			FSATransition tr =
				new FSATransition(
					fromState,
					toState,
					((FSATransition) trans[i]).getLabel());
			largeFSA.addTransition(tr);

			logger.logInfo("Transition " + tr + " added");
		}

	}

	/**
	 * It merges the inner automaton to the large one by adding a new branch starting from 
	 * a given state. It is assumed that the inner automaton has got one initial state and
	 * one final state.
	 * 
	 * @param largeFSA the automaton that is extended
	 * @param innerFSA the automaton representing the new branch
	 * @param beginState the state of the largeFSA where the initial state of the inner FSA
	 * must be mapped
	 */
	private void MergeTailAutomata(
		FiniteStateAutomaton largeFSA,
		FiniteStateAutomaton innerFSA,
		State beginState) {

		// all states of the inner automata are retrieved
		State[] states = innerFSA.getStates();
		int finalState = -1;

		/* hashmap with pairs IDstate, cardinal number
		   cardinal numbers are used to indentify states in the new FSA */
		HashMap IDmap = new HashMap();
		int id = 0;

		logger.logInfo("Creation Map of IDs");

		/* population of the HashMap. Initial state is not added because
		 * must be mapped to the corresponding state of the largeFSA
		 */
		for (int i = 0; i < states.length; i++) {
			if ((innerFSA.getInitialState().getID() != states[i].getID())) {
				if (innerFSA.isFinalState(states[i])) {
					finalState = id;
				}
				IDmap.put(new Integer(states[i].getID()), new Integer(id++));
			}
		}

		logger.logInfo(
			"Creation of "
				+ (states.length - 1)
				+ " new states from the inner FSA");

		// creation of new states in the largeFSA. The cardinal number is used to pair
		// new states with old ones
		State[] newStates =
			(State[]) Array.newInstance(State.class, states.length - 1);
		for (int i = 0; i < newStates.length; i++) {
			newStates[i] = largeFSA.createState(genPoint());
		}

		largeFSA.addFinalState(newStates[finalState]);
		
		
		//Copying all transitions. Transitions involving the initial state of innerFSA
		// are modified to be connected with the corresponding state of the largeFSA
		Transition[] trans = innerFSA.getTransitions();
		State fromState = null;
		State toState = null;

		logger.logInfo(
			"Creation of "
				+ (trans.length)
				+ " new transitions from the inner FSA");

		for (int i = 0; i < trans.length; i++) {
			// definition of the source of the transition
			if (innerFSA.getInitialState().getID()
				== trans[i].getFromState().getID()) {
				fromState = beginState;
			} else {

				fromState =
					newStates[(
						(Integer) IDmap.get(
							new Integer(trans[i].getFromState().getID())))
						.intValue()];
			}

			// definition of the target of the transition
			if (innerFSA.getInitialState().getID()
				== trans[i].getToState().getID()) {
				toState = beginState;
			} else {
				toState =
					newStates[(
						(Integer) IDmap.get(
							new Integer(trans[i].getToState().getID())))
						.intValue()];
			}

			// addition of the transition
//			CountingFSATransition tr =
//				new CountingFSATransition(
//					fromState,
//					toState,
//					((CountingFSATransition) trans[i]).getLabel(),
//					((CountingFSATransition) trans[i]).getSymbolIds());
			FSATransition tr =
				new FSATransition(
					fromState,
					toState,
					((FSATransition)trans[i]).getLabel());
			
			largeFSA.addTransition(tr);

			logger.logInfo("Transition " + tr + " added");
		}

	}

	/**
	 * The method extends the current FSA by adding a tail 
	 * 
	 * @param fsa the current FSA
	 * @param fromState the current state of the FSA, the tail is added from
	 * that state
	 * @param t the actual trace that is analyzed
	 * @param fromPos the position where the tail begins
	 */
	private void addTail(
		FiniteStateAutomaton fsa,
		State fromState,
		Trace t,
		int fromPos) {
		
		extensionsRecorder.addTail(t, fromState, fromPos );
		boolean recording = extensionsRecorder.isRecording();
		extensionsRecorder.setRecord(false);
		
		logger.logInfo("Adding Tail, fromState = "+fromState+" ,fromPos = "+fromPos);
		if (t.getLength() - fromPos < minTrustLen) {
			// if the tail is shorter that the minimum length of symbols for locating
			// behavioral patterns, the tail is added as plain generation of symbols
			logger.logInfo("Plain addition of Tail");

			State curState = null;
			State nextState = fromState;

			for (int i = 0; i < t.getLength() - fromPos; i++) {
				curState = nextState;
				nextState = genState();

//				CountingFSATransition tr =
//					new CountingFSATransition(
//						curState,
//						nextState,
//						t.getSymbol(fromPos + i).getValue(),
//						fromPos+i);
				FSATransition tr =
					new FSATransition(
						curState,
						nextState,
						t.getSymbol(fromPos + i)
						);
				fsa.addTransition(tr);

			}
			fsa.addFinalState(nextState);
		} else {
			logger.logInfo("Preparation to Recursion Step");
			//ChangesRecorder cr = ChangesRecorder.getInstance();
			
			
			// The tail is added after (recursive) computation of FSA representing it
			KBehaviorEngine localEngine =
				new KBehaviorEngine(
					this.minTrustLen,
					this.maxTrustLen,
					this.cutOffSearch,
					this.enableMinimization,
					this.logger);
			logger.logInfo(
				"Recursion on tail"
					+ t.getSubTrace(fromPos, t.getLength() - 1));
			localEngine.extendFSAwithTrace(
				t.getSubTrace(fromPos, t.getLength() - 1));
			FiniteStateAutomaton localFSA = localEngine.getCurrentFSA();

			logger.logInfo("Starting Merging of Automata");
			//the derived automaton is merged
			MergeTailAutomata(fsa, localFSA, fromState);
			
			logger.logInfo("End Merging");
		}
		extensionsRecorder.setRecord(recording);
	}

	/**
	 * The function returns true if the one of the states passed as parameters is a 
	 * final state for the automaton
	 * 
	 * @param fsa the automaton considered for searching the final state
	 * @param states the set of states where the final state must be searched
	 * @return true if one final state if contained in states, otherwise false.
	 */
	private boolean existFinalState(FiniteStateAutomaton fsa, State[] states) {
		int i = 0;

		while (i < states.length) {
			if (fsa.isFinalState(states[i]))
				return true;
			i++;
		}

		return false;
	}

	/**
	 * Non-deterministic selection of a state 
	 * @param states set of states where the non deterministic selection must take place
	 * @return selected state
	 */
	protected State randomSelectionState(State[] states) {
		return states[randomGen.nextInt(states.length)];
	}

	/**
	 * The method search nex behavioral pattern that must be considered. The 
	 * research always starts from the first state on the FSA and then moves 
	 * along all states. The order is defined from the recording order and
	 * not the navigational order.
	 * 
	 * Long behavioral patterns are first searched. If there are no long
	 * behavioral pattern, the algorithm tries with shorter ones, up to the
	 * minimum possible legth for a behavioral pattern. If there are no 
	 * behavioral patterns, the method sets the state pointer to null. 
	 * 
	 * @param fsa the FSA where the search must be performed
	 * @param trace the trace there the behavioral pattern must be identified
	 * @param searchPos the part of the trace that must be considered begins
	 * from searchPos
	 * @return a complex object embedding all data
	 */
	protected BehavioralPatternData nextBehavioralPattern(
		FiniteStateAutomaton fsa,
		Trace trace,
		int searchPos,
		State curState) {
		SimulationResult sr;

		int bestState = -1;
		int bestSearchPos = -1;
		int bestLength = 0;
		State bestReachedState = null;

		BehavioralPatternData result;

		logger.logEvent(
			"Begin Searching for Behavioral Pattern...");

		State[] states = getStatesForNextBehavioralPattern(fsa,curState);
		if (states.length == 0)
			return null;

		int i = 0;

		do { // searching over all possible future subtraces starting from the current one

			i = 0;

			do { // searching over all states following the storing order
				logger.logDebugInfo(
					"simulate as possible from searchpos="
						+ searchPos
						+ " from state="
						+ states[i]);

				// perform a simulation of a given subtrace from a given point
				sr = FSASimulateAsPossible(fsa, states[i], trace, searchPos);
				if (sr.reachableStates != null) {
					// the best result is always stored
					if ((sr.nextSymbol - searchPos) > bestLength) {
						bestLength = sr.nextSymbol - searchPos;
						bestSearchPos = searchPos;
						bestState = i;
					
						bestReachedState =
							randomSelectionState(sr.reachableStates);
						logger.logDebugInfo(
							"Quality of Behavioral Pattern Increased. Actual Best Behavioral Pattern is: Length="
								+ bestLength
								+ " Position="
								+ bestSearchPos
								+ " from State"
								+ states[i]);
					}
				}
				i++;
			// if the best result satisfies the cutOffSearch requirement (and the cuttOffSearch
			// is enabled), we exit from cycle and we keep the best result up to now as 
			// identified behavioral pattern. 
			} while (
				(i < states.length)
					&& ((bestLength < maxTrustLen) || (!cutOffSearch)));
			searchPos++;
		// the search continues for all positions or until the CutOffSearch criterion is 
		// satisfied (if enabled)
		}
		while ((searchPos
			<= (trace.getLength() - Math.max(bestLength, minTrustLen)))
			&& ((bestLength < maxTrustLen) || (!cutOffSearch)));

		result = new BehavioralPatternData();

		// building the result
		if (bestLength >= minTrustLen) {
			result.fromState = states[bestState];
			//non-determionistic choise of the sub-machine generating the behavioral pattern
			result.toState = bestReachedState;
			result.fromTrace = bestSearchPos;
			result.toTrace = bestSearchPos + bestLength - 1;
			result.lengthTrace = bestLength;
			logger.logInfo(
				"Suitable Behavioral Pattern Identified: " + result);
		} else {
			result.fromState = null;
			result.toState = null;
			result.fromTrace = -1;
			result.toTrace = -1;
			result.lengthTrace = -1;
			logger.logInfo(
				"No Suitable Behavioral Pattern in the remaning part of the trace");
		}

		return result;
	}

	private State[] getStatesForNextBehavioralPattern(FiniteStateAutomaton fsa, State curState) {
		return fsa.getStates();
	}

	/**
	 * The method simulates the execution of the FSSA from the given state by using
	 * a subtrace as long as possible
	 * 
	 * @param fsa the fsa used for the simulation
	 * @param initStateSimulation the starting state for the simulation
	 * @param trace the trace to be sued for the simulation
	 * @param initTraceSimulation the position in the trace where the simulation must start
	 * @return result of the simulation
	 */
	protected SimulationResult FSASimulateAsPossible(
		FiniteStateAutomaton fsa,
		State initStateSimulation,
		Trace trace,
		int initTraceSimulation) {

		ArrayList confs1 = null; // previous set of reached configurations
		ArrayList confs2; // overall set of configurations that can be reached from the previous configuration
		ArrayList confstmp; // set of configurations reached by one state of the previous configuration
		State reachableStates[] = null;

		int tracePos = initTraceSimulation;

		// Initial configuration
		FSAConfiguration curConfig =
			new FSAConfiguration(
				initStateSimulation,
				null,
				trace.getSymbol(tracePos),
				trace.getSymbol(tracePos));

		//		FSAStepByStateSimulator it.unimib.disco.lta.conFunkHealer.simulator =
		//			new FSAStepByStateSimulator(currentFSA);

		// simulation of one step with closure (lambda transitions are follower)
		FSAStepWithClosureSimulator simulator =
			new FSAStepWithClosureSimulator(fsa);

		confs2 = simulator.stepConfiguration(curConfig);

//		State preState=null;
		
		// iterative simulation as long as possible
		while ((!confs2.isEmpty()) && (tracePos < trace.getLength() - 1)) {
			tracePos++;
			confs1 = confs2;
			confs2 = new ArrayList();
			for (int i = 0; i < confs1.size(); i++) {
				((FSAConfiguration) confs1.get(i)).setUnprocessedInput(
					trace.getSymbol(tracePos));
				confstmp =
					simulator.stepConfiguration(
						(FSAConfiguration) confs1.get(i));
				if (confstmp.size() > 0) {
//					preState = ((FSAConfiguration) confs1.get(i)).getCurrentState();
					for (int h=0; h< confstmp.size(); h++) {
						if (contain(confs2,((FSAConfiguration)confstmp.get(h)).getCurrentState())==false) {
							confs2.add(confstmp.get(h));
						}
					}
//					confs2.addAll(confstmp);
				}
				//				reachableStates	= simulateOneStep(fsa,((FSAConfiguration)confs1.get(i)).getCurrentState(),trace,tracePos);
			}

		}

		// construction of the result
		SimulationResult result = new SimulationResult();
//		result.preState = preState;
		if (!confs2.isEmpty()) {
			// the trace is ended
			result.reachableStates = extractStatesFromConf(confs2);
			result.nextSymbol = ++tracePos;
		} else {
			if (!(confs1 == null)) {
				result.reachableStates = extractStatesFromConf(confs1);
				result.nextSymbol = tracePos;
			} else {
				result.reachableStates = null;
				result.nextSymbol = tracePos;
			}
		}

		return result;
	}


	private boolean contain(ArrayList a, State s) {
		for (int i=0; i< a.size(); i++) {
			if (((FSAConfiguration)a.get(i)).getCurrentState().equals(s)) {
				return true;
			}
		}
		return false;
	}

	private State[] extractStatesFromConf(ArrayList confs) {
		State[] tmpStates =
			(State[]) Array.newInstance(State.class, confs.size());

		for (int i = 0; i < confs.size(); i++) {
			tmpStates[i] = ((FSAConfiguration) confs.get(i)).getCurrentState();
		}

		return tmpStates;
	}
	
	/**
	 * Generation of one new step for the currentFSA. The generation of steps automatically
	 * manages the visualization of states.
	 * 
	 * @return the generated state
	 */
	private State genState() {

		State tmpState = currentFSA.createState(new Point(xPos, yPos));
		updatePositions();
		return tmpState;
	}

	/**
	 * Generate the point where next state must be visualized
	 * 
	 * @return next point available for placing states
	 */
	private Point genPoint() {

		Point tmpPoint = new Point(xPos, yPos);
		updatePositions();
		return tmpPoint;
	}



	/**
	 * Update the current situation for management of visualization of states
	 */
	private void updatePositions() {
		xPos = xPos + xStep;

		if (xPos > maxLength) {
			xPos = xInitValue;
			yPos = yPos + yStep;

		}
	}

	// ------------- set & get methods ---------------

	/**
	 * Return the current minimization time, can be none, step or end
	 * 
	 * @return value of the current minimization time
	 */
	public String isEnableMinimization() {
		return enableMinimization;
	}

	/**
	 * Set the value of the current minimization time
	 * 
	 * @param enableMinimization can be none, step or end. If a different value is used there is no effect
	 */
	public void setEnableMinimization(String enableMinimization) {
		if (!enableMinimization.equals(InteractionInferenceEngineSettings.END)&&!enableMinimization.equals(InteractionInferenceEngineSettings.STEP)&&!enableMinimization.equals(InteractionInferenceEngineSettings.NO_MINIMIZATION)) {
			return;
		}
		this.enableMinimization = enableMinimization;
	}

	/**
	 * Return the current state of the cutOffSearch parameter
	 * 
	 * @return the value of the parameter
	 */
	public int getMaxTrustLen() {
		return maxTrustLen;
	}

	/**
	 * Set the value of the parameter for cutOffSearch
	 * 
	 * @param maxTrustLen the new value for the parameter
	 */
	public void setMaxTrustLen(int maxTrustLen) {
		this.maxTrustLen = maxTrustLen;
	}

	/**
	 * return the value of the minimal length for a behavioral pattern
	 * 
	 * @return value of the parameter
	 */
	public int getMinTrustLen() {
		return minTrustLen;
	}

	/**
	 * Set the value for the minimal length of a behavioral pattern
	 * 
	 * @param minTrustLen the new value of the parameter
	 */
	public void setMinTrustLen(int minTrustLen) {
		this.minTrustLen = minTrustLen;
	}

	/**
	 * Return the current FSA
	 * 
	 * @return the current FSA
	 */
	public FiniteStateAutomaton getCurrentFSA() {
		return currentFSA;
	}

	/**
	 * set the new FSA that must be considered for extension
	 * 
	 * @param currentFSA the new FSA
	 */
	public void setCurrentFSA(FiniteStateAutomaton currentFSA) {
		this.currentFSA = currentFSA;
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

		KBehaviorEngine engine;
		try {
			engine = new KBehaviorEngine(configFile);
		

		long startTime = System.currentTimeMillis();
		try {
			if ( FSAtoExtend == null )
				fsa = engine.inferFSAfromFile(inputFile);
			else{
				//ChangesRecorder.getInstance().setRecord(true);
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
	
	public List<FSAExtension> getFSAExtensions() {
		return extensionsRecorder.getFSAExtensions();
	}
	
	public void setRecordExtensions(boolean record){
		extensionsRecorder.setRecord(record);
	}
	
	public boolean getRecordExtensions(){
		return extensionsRecorder.isRecording();
	}

	public static void serializeFSA(FiniteStateAutomaton fsa, String fileName) {
		ObjectOutputStream oos = null;
		try {
			oos = new ObjectOutputStream(new FileOutputStream(fileName));
		} catch (FileNotFoundException e) {
			System.out.println("Impossible to create the file " + fileName);
			e.printStackTrace();
			System.exit(1);
		} catch (IOException e) {
			System.out.println("Impossible to create the file "
					+ fileName);
			e.printStackTrace();
			System.exit(1);
		}

		try {
			oos.writeObject(fsa);
		} catch (IOException e1) {
			System.out.println("Impossible to serialize the finite state automaton ");
			e1.printStackTrace();
			System.exit(1);
		}

		try {
			oos.close();
		} catch (IOException e2) {
			System.out.println("impossible to close the output stream" + oos);
			e2.printStackTrace();
		}

	}
	
	
	  public static void process(File inputFile, File outputFile) {
	    FiniteStateAutomaton fsa = null;

	    KBehaviorEngine engine = new KBehaviorEngine();
	    
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

	  /**
	   * Set the kbehavior logger.
	   * 
	   * @param logger
	   */
	  public void setLogger( Logger logger ){
		  this.logger = logger;
	  }
	  
	  /**
	   * Returns the verbosity level of the current associated logger.
	   * 
	   * @return
	   */
	public int getVerboseLevel() {
		return logger.getVerboseLevel();
	}

	public int getMinimizationLimit() {
		return minimizationLimit;
	}

	public void setMinimizationLimit(int minimizationLimit) {
		this.minimizationLimit = minimizationLimit;
	}
	

	  
}
