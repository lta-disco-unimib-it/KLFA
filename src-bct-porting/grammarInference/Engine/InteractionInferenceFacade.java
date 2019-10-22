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

import conf.InteractionInferenceEngineSettings;


public class InteractionInferenceFacade {

	
	public static void process(String inferenceEngine, File inputFile, File outputFile) {
		if (inferenceEngine.equals(InteractionInferenceEngineSettings.KTAIL)) {
			kTailEngine.process(inputFile,outputFile);
		} else if (inferenceEngine.equals(InteractionInferenceEngineSettings.KBEHAVIOR)) {
			KBehaviorEngine.process(inputFile, outputFile);
		} else if (inferenceEngine.equals(InteractionInferenceEngineSettings.COOK)) {
			cookEngine.process(inputFile,outputFile);
		} else if (inferenceEngine.equals(InteractionInferenceEngineSettings.KINCLUSION)) {
			kInclusionEngine.process(inputFile,outputFile);
		} else if (inferenceEngine.equals(InteractionInferenceEngineSettings.REISS)) {
			reissEngine.process(inputFile,outputFile);
		} else {
			System.err.println("The specified inference engine is not available, the kTail engine will be loaded.");
			kTailEngine.process(inputFile,outputFile);
		}
	}
	
	
}
