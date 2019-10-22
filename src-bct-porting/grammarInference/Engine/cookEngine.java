/*
 * Created on 24-ago-2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package grammarInference.Engine;

import gui.environment.EnvironmentFrame;
import gui.environment.FrameFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.lang.reflect.Array;

import conf.EnvironmentalSetter;
import conf.InteractionInferenceEngineSettings;

import automata.State;
import automata.Transition;
import automata.fsa.FSATransition;
import automata.fsa.FiniteStateAutomaton;

/**
 * @author Leonardo Mariani
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class cookEngine {

	private FiniteStateAutomaton currentFSA = null;
	private boolean modified = false;
	private int k =2;
	private boolean enableMinimization=true;
	
	public void setK(int k) {
		this.k = k;
	}
	
	public void setEnableMinimization(boolean enableMinimization) {
		this.enableMinimization = enableMinimization;
	}
	
	public cookEngine() {

	}

	public FiniteStateAutomaton inferFSAfromFile(String traceFile)
		throws FileNotFoundException {
		kTailEngine engine =
			new kTailEngine();

		engine.setK(k);
		engine.setEnableMinimization(enableMinimization);
		
		currentFSA = engine.inferFSAfromFile(traceFile);

		InteractionInferenceEngineSettings.logger.logEvent("Beginning Execution of Cook Optimization");
		do {
			modified = false;
			runCookMinimization();
		} while (modified == true);
		InteractionInferenceEngineSettings.logger.logEvent("End Execution of Cook Optimization");

		return currentFSA;
	}

	private void runCookMinimization() {
		State[] states = currentFSA.getStates();

		int i = 0;

		while (i < states.length) {
			InteractionInferenceEngineSettings.logger.logInfo("Checking state " + (i +1) + "of " + states.length + "\n");
			if (currentFSA.isState(states[i]) == false) {
				i++;
				continue;
			}
			Transition[] trSet = currentFSA.getTransitionsFromState(states[i]);
			sortTransitions(trSet);
			int j = 0;

			while (j < trSet.length) {
				int l = j + 1;
				while ((l < trSet.length)
					&& (((FSATransition) trSet[l])
						.getLabel()
						.equals(((FSATransition) trSet[j]).getLabel()))) {
					l++;
				}
				if (l != j + 1) {
					InteractionInferenceEngineSettings.logger.logInfo("Found state" + trSet[j].getFromState() + "with non deterministic behavior, l1=" + ((FSATransition) trSet[j]).getLabel() + " l2=" +((FSATransition) trSet[l-1]).getLabel());

					FSATransition[] trTrans =
						(FSATransition[]) Array.newInstance(
							FSATransition.class,
							l - j);
					for (int z = j; z < l; z++) {
						trTrans[z] = (FSATransition) trSet[z];
					}
					InteractionInferenceEngineSettings.logger.logInfo("Trying to merge target states");
					tryToMerge(trTrans);
					InteractionInferenceEngineSettings.logger.logInfo("End of the attempt");
				}
				j = l;
			}

			i++;
		}
	}

	private void tryToMerge(FSATransition[] transitions) {

		String[][] behaviors =
			(String[][]) Array.newInstance(String[].class, transitions.length);

		InteractionInferenceEngineSettings.logger.logInfo("Collecting behaviors of target states\n");
		for (int i = 0; i < transitions.length; i++) {
			Transition[] tset =
				currentFSA.getTransitionsFromState(transitions[i].getToState());
			behaviors[i] =
				(String[]) Array.newInstance(String.class, tset.length);
			for (int j = 0; j < tset.length; j++) {
				behaviors[i][j] = ((FSATransition) tset[j]).getLabel();
			}
		}

		InteractionInferenceEngineSettings.logger.logInfo("Ordering behaviors\n");
		orderBehaviors(behaviors, transitions);
		InteractionInferenceEngineSettings.logger.printDoubleStringArray(behaviors);
		
		boolean found = false;
		int index = 0;

		while ((found == false) && (index < behaviors.length-1)) {
			InteractionInferenceEngineSettings.logger.logInfo("Searching for a state subsuming the behaviors of the others. \n");
			if (checkInclusion(behaviors, index)) {
				InteractionInferenceEngineSettings.logger.logEvent("States to be merged have been found \n");
				InteractionInferenceEngineSettings.logger.logInfo("Including in state" + transitions[index].getToState() + " the states: \n");
				for (int h=index+1; h< transitions.length; h++) {
					InteractionInferenceEngineSettings.logger.logInfo("state" + h +" of " + (transitions.length - index-1)+ ": " + transitions[h].getToState() + " the states: \n");
				}
				
				found = true;
				mergeTransitionsandStates(transitions, index);
			}
			index++;
		}
	}

	private boolean checkInclusion(String[][] b, int ind) {
		int i =ind+1;
		boolean included = true;

		while ((i < b.length) && (included)) {
			InteractionInferenceEngineSettings.logger.logInfo("Lenghts " + b[ind].length + " " + b[i].length+ " \n");
			if (inclusion(b[ind],b[i])== false) {
				included = false;
			}
			i++;
		}
		
		return included;
	}

	private boolean inclusion(String[] large, String[] small) {
		int sindex = 0;

		while ((sindex < small.length) && (include(large, small[sindex]))) {
			sindex ++;
		}
		return (sindex >= small.length);
	}

	private boolean include(String[] ar, String v) {
		int i =0;
		
		while ((i < ar.length) && (ar[i].equals(v)== false)) {
			i++;
		}	
		return !(i>=ar.length);
	}
	
	private void mergeTransitionsandStates(FSATransition [] trSet, int initialpos) {
		modified = true;
		boolean existFS = false;
		boolean existIS = false;
		
		
		for (int j= initialpos+1; j < trSet.length; j++) {
			State stateToMerge = trSet[j].getToState();
			Transition [] trTrans = currentFSA.getTransitionsFromState(stateToMerge);
			
			for(int l = 0; l < trTrans.length; l++) {
				FSATransition tr = new FSATransition(trSet[initialpos].getToState(),trTrans[l].getToState(),((FSATransition)trTrans[l]).getLabel());
				currentFSA.addTransition(tr);
				currentFSA.removeTransition(trTrans[l]);
			}

			trTrans = currentFSA.getTransitionsToState(stateToMerge);
			
			for(int l = 0; l < trTrans.length; l++) {
				FSATransition tr = new FSATransition(trTrans[l].getFromState(),trSet[initialpos].getToState(),((FSATransition)trTrans[l]).getLabel());
				currentFSA.addTransition(tr);
				currentFSA.removeTransition(trTrans[l]);
			}

			if (currentFSA.isFinalState(stateToMerge)) existFS = true;
			if (currentFSA.getInitialState()==stateToMerge) existIS = true;
			currentFSA.removeState(stateToMerge);			
		}
		if (existFS) currentFSA.addFinalState(trSet[initialpos].getToState());
		if (existIS) currentFSA.setInitialState(trSet[initialpos].getToState());
	}



	private void orderBehaviors(String[][] a, Transition [] t) {
		for (int i = 0; i < a.length - 1; i++) {
			int minIndex = i;
			int min = a[minIndex].length;

			for (int j = i + 1; j < a.length; j++) {
				if (a[j].length > min) {
					minIndex = j;
					min = a[minIndex].length;
				}
			}

			String[] s = a[minIndex];
			a[minIndex] = a[i];
			a[i] = s;
			
			Transition temp = t[minIndex];
			t[minIndex] = t[i];
			t[i] = temp;
		}
	}

	private void sortTransitions(Transition arrayTr[]) {
		for (int i = 0; i < arrayTr.length - 1; i++) {
			int minIndex = i;
			String min = ((FSATransition) arrayTr[minIndex]).getLabel();

			for (int j = i + 1; j < arrayTr.length; j++) {
				if (String
					.CASE_INSENSITIVE_ORDER
					.compare(((FSATransition) arrayTr[j]).getLabel(), min)
					> 0) {
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

	public void drawFSA() {
		EnvironmentFrame f = FrameFactory.createFrame(currentFSA);
	}

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

		if (args.length>3) {
			drawFSA = true;
		} else {
			drawFSA = false;
		}

		
		cookEngine engine = new cookEngine();

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

	    cookEngine engine = new cookEngine();
	    
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
