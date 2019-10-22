
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
