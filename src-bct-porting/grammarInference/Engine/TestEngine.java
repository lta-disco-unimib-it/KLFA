package grammarInference.Engine;

import java.io.File;
import java.io.FileNotFoundException;

import automata.fsa.FiniteStateAutomaton;
import conf.EnvironmentalSetter;
import conf.InteractionInferenceEngineSettings;
import flattener.utilities.LogWriter;

/**
 * @author Leonardo Mariani
 * 
 * The purpose of this class is testing the inference protocols with data
 * supplied in trace files
 *  
 */
public class TestEngine {

	private static final String FILENAME = "c:\\Documents and Settings\\Leonardo Mariani\\Desktop\\TMP\\invariants\\02-normalized\\normalizedInteractionInvariants\\org.gos.freesudoku.Game.initPartida(int,boolean).txt";
	
	public static void main(String[] args) {
		FiniteStateAutomaton fsa = null;

	
		EnvironmentalSetter.setConfigurationValues();
		KBehaviorEngine engine = new KBehaviorEngine();

		engine.setEnableMinimization(new String("END"));
		engine.setMinTrustLen(2);
		engine.setMaxTrustLen(4);
		engine.setCutOffSearch(false);

		File inputFile = new File(FILENAME);

		try {
			fsa = engine.inferFSAfromFile(inputFile.getAbsolutePath());
		} catch (FileNotFoundException e) {
			System.out.println("The file " + inputFile + " does not exist!");
		}
		System.out.println(fsa);
	}

}

