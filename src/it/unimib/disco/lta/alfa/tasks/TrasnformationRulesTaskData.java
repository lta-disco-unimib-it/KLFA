package it.unimib.disco.lta.alfa.tasks;

import java.io.File;

public class TrasnformationRulesTaskData implements TaskData {

	private File transformersFile;
	private File preprocessingrulesFile;
	private File clustersAnalysisFile;

	public TrasnformationRulesTaskData(File transformersFile,
			File preprocessingRulesFile, File clustersAnalysisFile) {
		this.transformersFile = transformersFile;
		this.preprocessingrulesFile = preprocessingRulesFile;
		this.clustersAnalysisFile = clustersAnalysisFile;
	}

	public File getTransformersFile() {
		return transformersFile;
	}

	public File getPreprocessingrulesFile() {
		return preprocessingrulesFile;
	}

	public File getClustersAnalysisFile() {
		return clustersAnalysisFile;
	}

}
