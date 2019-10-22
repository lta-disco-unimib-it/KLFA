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

