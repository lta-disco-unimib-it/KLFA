/*
 * Created on 24-ago-2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package grammarInference.Engine;

import grammarInference.Record.Trace;
import grammarInference.Record.TraceParser;
import grammarInference.Record.kbhParser;
import gui.environment.EnvironmentFrame;
import gui.environment.FrameFactory;

import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;

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

/**
 * @author Leonardo Mariani
 * 
 * To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Generation - Code and Comments
 */
public class reissEngine {

	// params for displaying the automaton
	private int xStep = 60;

	private final int xInitValue = 30;

	private int yStep = 60;

	private final int yInitValue = 30;

	private int maxLength = 950;

	private int xPos;

	private int yPos;

	// params for the inference algorithm
	private FiniteStateAutomaton currentFSA = null;

	private State[] workingStates;

	private String[] kFuture;

	private int k = 2;

	private boolean enableMinimization = false;

	private final String separator = "@";

	public reissEngine() {
		currentFSA = new FiniteStateAutomaton();
		this.k = 2;

		xPos = xInitValue;
		yPos = yInitValue;

	}

	public void setK(int k) {
		this.k = k;
	}
	
	public void setEnableMinimization(boolean enableMinimization) {
		this.enableMinimization = enableMinimization;
	}
	
	public reissEngine(int k) {
		this();
		this.k = k;
	}

	public reissEngine(String conffile) {
		//TO DO: reading k from file
		this(2);
		enableMinimization=true;
//		enableMinimization = true;
	}

	private Point genPoint() {
		Point tmpPoint = new Point(xPos, yPos);
		updatePositions();
		return tmpPoint;
	}

	private void updatePositions() {
		xPos = xPos + xStep;

		if (xPos > maxLength) {
			xPos = xInitValue;
			yPos = yPos + yStep;

		}
	}

	public FiniteStateAutomaton inferFSAfromFile(String traceFile)
			throws FileNotFoundException {
		InteractionInferenceEngineSettings.logger.logEvent("--- Begin Inference ---\n");
		int traceCounter = 0;

		//currentFSA = new FiniteStateAutomaton();

		TraceParser fileParser = new kbhParser(traceFile);
		//		TraceParser fileParser = new AbbadingoParser(fileName);

		Iterator it = fileParser.getTraceIterator();

		while (it.hasNext()) {
			InteractionInferenceEngineSettings.logger.logEvent("Reading trace " + traceCounter++
					+ "\n");
			Trace trace = (Trace) it.next();
			InteractionInferenceEngineSettings.logger.logEvent("Reading complete\n");
			InteractionInferenceEngineSettings.logger.logInfo("Trace Value= " + trace + "\n");

			InteractionInferenceEngineSettings.logger.logEvent("Adding the trace to the FSA\n");
			addTraceToFSA(trace);
			InteractionInferenceEngineSettings.logger
					.logEvent("Branch addition performed performed\n");

		}

		InteractionInferenceEngineSettings.logger.logInfo("FSA before merging:" + currentFSA + "\n");
		InteractionInferenceEngineSettings.logger.logEvent("Beginning Merging \n");

		InteractionInferenceEngineSettings.logger.logEvent("Beginning State Merging\n");
		stateMerging();
		InteractionInferenceEngineSettings.logger.logEvent("End of State Merging\n");

		InteractionInferenceEngineSettings.logger.logEvent("End of Merging \n");

		if (enableMinimization) {
			InteractionInferenceEngineSettings.logger.logEvent("Starting Minimization\n");
			minimizeFSA();
			InteractionInferenceEngineSettings.logger.logEvent("Minimization Completed\n");
		}
		InteractionInferenceEngineSettings.logger.logEvent("--- End Inference ---\n");

		InteractionInferenceEngineSettings.logger.logInfo("Output Automaton:\n"
				+ currentFSA.toString());

		return currentFSA;
	}

	private void addTraceToFSA(Trace t) {
		State start = null;
		int startSymbol = 0;
		State toState = null;

		if (currentFSA.getStates().length <= 0) {
			InteractionInferenceEngineSettings.logger.logInfo("Creation of the initial state:\n");
			start = currentFSA.createState(genPoint());
			currentFSA.setInitialState(start);
		} else {
			InteractionInferenceEngineSettings.logger
					.logInfo("Computation of the already generated branch:\n");
			SimulationResult sr = FSASimulateAsPossible(currentFSA, currentFSA
					.getInitialState(), t, 0);
			if (sr.reachableStates == null) {
				start = currentFSA.getInitialState();
			} else {
				start = sr.reachableStates[0];
			}
			startSymbol = sr.nextSymbol;
			InteractionInferenceEngineSettings.logger.logInfo("Reached Point: Symbol pos="
					+ startSymbol + " state=" + start + "\n");

		}

		for (int i = startSymbol; i < t.getLength(); i++) {
			toState = currentFSA.createState(genPoint());
			FSATransition tr = new FSATransition(start, toState, t.getSymbol(i)
					.getValue());
			currentFSA.addTransition(tr);
			InteractionInferenceEngineSettings.logger.logInfo("Added the transition: =" + tr + "\n");
			start = toState;
		}
		currentFSA.addFinalState(start);
	}

	private SimulationResult FSASimulateAsPossible(FiniteStateAutomaton fsa,
			State initStateSimulation, Trace trace, int initTraceSimulation) {

		ArrayList confs1 = null; // previous set of reached configurations
		ArrayList confs2; // overall set of configurations that can be reached
						  // from the previous configuration
		ArrayList confstmp; // set of configurations reached by one state of the
							// previous configuration
		State reachableStates[] = null;

		int tracePos = initTraceSimulation;

		// Initial configuration
		FSAConfiguration curConfig = new FSAConfiguration(initStateSimulation,
				null, trace.getSymbol(tracePos).getValue(), trace.getSymbol(
						tracePos).getValue());

		//		FSAStepByStateSimulator it.unimib.disco.lta.conFunkHealer.simulator =
		//			new FSAStepByStateSimulator(currentFSA);

		// simulation of one step with closure (lambda transitions are follower)
		FSAStepWithClosureSimulator simulator = new FSAStepWithClosureSimulator(
				fsa);

		confs2 = simulator.stepConfiguration(curConfig);

		// iterative simulation as long as possible
		while ((!confs2.isEmpty()) && (tracePos < trace.getLength() - 1)) {
			tracePos++;

			confs1 = confs2;
			confs2 = new ArrayList();

			for (int i = 0; i < confs1.size(); i++) {
				((FSAConfiguration) confs1.get(i)).setUnprocessedInput(trace
						.getSymbol(tracePos).getValue());
				confstmp = simulator
						.stepConfiguration((FSAConfiguration) confs1.get(i));
				if (confstmp.size() > 0) {
					for (int h=0; h< confstmp.size(); h++) {
						if (contain(confs2,((FSAConfiguration)confstmp.get(h)).getCurrentState())==false) {
							confs2.add(confstmp.get(h));
						}
					}
				}
				//				reachableStates =
				// simulateOneStep(fsa,((FSAConfiguration)confs1.get(i)).getCurrentState(),trace,tracePos);
			}

		}

		// construction of the result
		SimulationResult result = new SimulationResult();
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
		State[] tmpStates = (State[]) Array.newInstance(State.class, confs
				.size());

		for (int i = 0; i < confs.size(); i++) {
			tmpStates[i] = ((FSAConfiguration) confs.get(i)).getCurrentState();
		}

		return tmpStates;
	}

	private void addKFuture() {
		workingStates = currentFSA.getStates();
		kFuture = (String[]) Array.newInstance(String.class,
				workingStates.length);

		for (int i = 0; i < workingStates.length; i++) {
			kFuture[i] = genKString(new String(""), workingStates[i], k);
			InteractionInferenceEngineSettings.logger.logInfo(kFuture[i]);
		}

		//		EngineConfig.logger.logInfo(kFuture.toString());
	}

	/*
	 * private String genKString(State s, int kvalue) { String result = new
	 * String("");
	 * 
	 * if (kvalue==0) return new String("");
	 * 
	 * Transition [] transitions = currentFSA.getTransitionsFromState(s); if
	 * (transitions==null) return new String("");
	 * 
	 * sortTransitions(transitions);
	 * 
	 * for (int i=0; i < transitions.length; i++) { result = result +
	 * ((FSATransition)transitions[i]).getLabel()+
	 * genKString(transitions[i].getToState(), k-1) + separator; }
	 * 
	 * return result; }
	 */
	private String genKString(String prefix, State s, int kvalue) {
		String result = new String("");

		if (kvalue == 0)
			return prefix + separator;

		Transition[] transitions = currentFSA.getTransitionsFromState(s);
		if ((transitions == null) || (transitions.length == 0))
			return prefix + separator;

		sortTransitions(transitions);

		for (int i = 0; i < transitions.length; i++) {
			String newprefix = prefix
					+ ((FSATransition) transitions[i]).getLabel();
			result = result
					+ genKString(newprefix, transitions[i].getToState(),
							kvalue - 1);
		}
		return result;
	}

	private void sortTransitions(Transition arrayTr[]) {
		for (int i = 0; i < arrayTr.length - 1; i++) {
			int minIndex = i;
			String min = ((FSATransition) arrayTr[minIndex]).getLabel();

			for (int j = i + 1; j < arrayTr.length; j++) {
				if (String.CASE_INSENSITIVE_ORDER.compare(
						((FSATransition) arrayTr[j]).getLabel(), min) > 0) {
					minIndex = j;
					min = ((FSATransition) arrayTr[minIndex]).getLabel();
				}
				//				 pause(i,j);
			}

			Transition T = arrayTr[minIndex];
			arrayTr[minIndex] = arrayTr[i];
			arrayTr[i] = T;
		}
	}

	private void stateMerging() {
		boolean mergePerformed = false;
		int i = 0;
		int j = 0;

		do {
			InteractionInferenceEngineSettings.logger.logEvent("Addition of kFuture\n");
			addKFuture();
			InteractionInferenceEngineSettings.logger.logEvent("End of kFuture\n");
			mergePerformed = false;
			i = 0;
			while (i < workingStates.length) {
				j = i + 1;
				while (j < workingStates.length) {
					InteractionInferenceEngineSettings.logger.logInfo("Considering states "
							+ workingStates[i] + " " + workingStates[j]
							+ " and corresponding behaviors 1 " + kFuture[i]
							+ " 2 " + kFuture[j]);
					if (sharing(kFuture[i], kFuture[j])
							&& (currentFSA.isState(workingStates[i]))
							&& (currentFSA.isState(workingStates[j]))) {
						InteractionInferenceEngineSettings.logger.logEvent("Merging states");
						mergeStates(workingStates[i], workingStates[j]);
						mergePerformed = true;
						removeElement(j);
					}
					j++;
				}
				i++;
			}

		} while (mergePerformed == true);
	}

	private boolean sharing(String a, String b) {
		Vector va = new Vector();
		Vector vb = new Vector();

		String label = "";
		for (int i = 0; i < a.length(); i++) {
			if (a.charAt(i) == separator.charAt(0)) {
				va.add(label);
				InteractionInferenceEngineSettings.logger.logInfo("Added Label " + label);
				label = "";
			} else {
				label = label + a.charAt(i);
			}
		}

		label = "";
		for (int i = 0; i < b.length(); i++) {
			if (b.charAt(i) == separator.charAt(0)) {
				vb.add(label);
				InteractionInferenceEngineSettings.logger.logInfo("Added Label " + label);
				label = "";
			} else {
				label = label + b.charAt(i);
			}
		}

		for (int i = 0; i < va.size(); i++) {
			for (int j = 0; j < vb.size(); j++) {
				if (((String) va.elementAt(i)).equals(vb.elementAt(j)))
					return true;
			}
		}
		return false;
	}

	private void removeElement(int i) {
		State[] tmpStates = (State[]) Array.newInstance(State.class,
				workingStates.length - 1);
		String[] tmpkFuture = (String[]) Array.newInstance(String.class,
				kFuture.length - 1);

		for (int j = 0; j < workingStates.length; j++) {
			if (i > j) {
				tmpStates[j] = workingStates[j];
				tmpkFuture[j] = kFuture[j];
			} else if (i < j) {
				tmpStates[j - 1] = workingStates[j];
				tmpkFuture[j - 1] = kFuture[j];
			}
		}
		kFuture = tmpkFuture;
		workingStates = tmpStates;

	}

	private void mergeStates(State to, State copy) {
		InteractionInferenceEngineSettings.logger.logInfo("merging state " + copy + " with state "
				+ to);
		//merging states
		Transition[] transitions = currentFSA.getTransitionsFromState(copy);

		for (int i = 0; i < transitions.length; i++) {
			FSATransition tr = new FSATransition(to, transitions[i]
					.getToState(), ((FSATransition) transitions[i]).getLabel());
			currentFSA.addTransition(tr);
			if (((FSATransition) transitions[i]).getToState() != copy)
				currentFSA.removeTransition(transitions[i]);
		}

		Transition[] transitions2 = currentFSA.getTransitionsToState(copy);

		for (int i = 0; i < transitions2.length; i++) {
			FSATransition tr = new FSATransition(
					transitions2[i].getFromState(), to,
					((FSATransition) transitions2[i]).getLabel());
			currentFSA.addTransition(tr);
			currentFSA.removeTransition(transitions2[i]);
		}
		if (currentFSA.isFinalState(copy))
			currentFSA.addFinalState(to);
		if (currentFSA.getInitialState() == copy)
			currentFSA.setInitialState(to);
		currentFSA.removeState(copy);
	}

	public void drawFSA() {
		EnvironmentFrame f = FrameFactory.createFrame(currentFSA);
	}

	public void minimizeFSA() {
		InteractionInferenceEngineSettings.logger.logInfo("Beginning Conversion to DFA");
		NFAToDFA converter = new NFAToDFA();
		currentFSA = converter.convertToDFA(currentFSA);
		InteractionInferenceEngineSettings.logger.logInfo("Conversion Completed, DFA=" + currentFSA);

		InteractionInferenceEngineSettings.logger.logInfo("Inizialitazione of the minimizer...");
		Minimizer minimizer = new Minimizer();
		minimizer.initializeMinimizer();

		Automaton tmpAutomaton = minimizer.getMinimizeableAutomaton(currentFSA);
		InteractionInferenceEngineSettings.logger.logInfo("Temp Automaton=" + tmpAutomaton);
		DefaultTreeModel dtm = minimizer
				.getDistinguishableGroupsTree(tmpAutomaton);
		InteractionInferenceEngineSettings.logger.logInfo("Distinguishable Group Tree=" + dtm);

		InteractionInferenceEngineSettings.logger
				.logInfo("Starting computation of the minimized automaton");
		currentFSA = (FiniteStateAutomaton) minimizer.getMinimumDfa(
				tmpAutomaton, dtm);
	}

	// ------------------ MAIN -----------------------

	public static void main(String[] args) {

		FiniteStateAutomaton fsa=null;
		String inputFile= null;
		String outputFile = null;
		String configFile = null;
		boolean drawFSA;
		
		if (args.length >0) {
			inputFile = args[0];
		} else {
			inputFile =
				new String(System.getProperty("user.dir")+ "/ktail.conf");
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
		
		if (args.length>3) {
			drawFSA = true;
		} else {
			drawFSA = false;
		}

		reissEngine engine = new reissEngine(configFile);

		long startTime = System.currentTimeMillis();
		try {
			fsa = engine.inferFSAfromFile(inputFile);
		} catch (FileNotFoundException e) {
			System.out.println("The file " + inputFile + " does not exist!");
		}
		long endTime = System.currentTimeMillis();
		System.out.println("Inference Time " + (endTime - startTime));
		System.out.println(fsa);
		InteractionInferenceEngineSettings.logger.logEvent("Drawing the FSA\n");
		if (drawFSA) engine.drawFSA();
		if (args.length>2) {
			serializeFSA(fsa,outputFile);
		}
	}

	class SimulationResult {
		public State reachableStates[];

		public int nextSymbol;
	}

	/**
	 * @return
	 */
	public int getXPos() {
		return xPos;
	}

	/**
	 * @return
	 */
	public int getXStep() {
		return xStep;
	}

	/**
	 * @return
	 */
	public int getYPos() {
		return yPos;
	}

	/**
	 * @return
	 */
	public int getYStep() {
		return yStep;
	}
	private static void serializeFSA(FiniteStateAutomaton fsa, String fileName) {
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

	    reissEngine engine = new reissEngine();
	    
	    engine.setEnableMinimization(EnvironmentalSetter.getInferenceEngineSettings().getBooleanMinimization());
	    engine.setK(EnvironmentalSetter.getInferenceEngineSettings().getMinTrustLen());
	    
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

