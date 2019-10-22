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
