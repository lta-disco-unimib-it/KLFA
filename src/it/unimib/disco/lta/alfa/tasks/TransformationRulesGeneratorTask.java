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

import it.unimib.disco.lta.alfa.parametersAnalysis.TransformationRulesGenerator;
import it.unimib.disco.lta.alfa.parametersAnalysis.TransformationRulesGeneratorException;

import java.io.File;
import java.io.IOException;


public class TransformationRulesGeneratorTask implements Task {

	private TransformationRulesGenerator trg;
	private File preprocessingRulesFile;
	private File transformersFile;
	private File clustersAnalysisFile;
	private static final String preprocessingRulesFileName = "preprocessingRules.txt";
	private static final String transformersFileName = "transformers.txt";
	private static final String clustersAnalysisFileName = "cluasterAnalysis.csv";
	private File workingDir;
	private CsvTraceTaskData input;

	public TransformationRulesGeneratorTask( TransformationRulesGenerator trg, File workingDir  ){
		this.trg = trg;
		this.workingDir = workingDir;
		preprocessingRulesFile = new File( workingDir, preprocessingRulesFileName );
		transformersFile = new File( workingDir, transformersFileName );
		clustersAnalysisFile = new File( workingDir, clustersAnalysisFileName );
	}
	
	public TaskData getResult() throws TaskException {
		
		return new TrasnformationRulesTaskData( transformersFile, preprocessingRulesFile, clustersAnalysisFile );
	}

	public void run() throws TaskException {
		try {
			trg.setCsvFile(input.getCsvTraceFile());
			trg.exportSuggestedPreprocessingRulesConfigurationToFile(preprocessingRulesFile);
			trg.exportSuggestedTranformersConfigurationToFile(transformersFile);
			trg.exportAllClustersStatisticsToFile(clustersAnalysisFile);

		} catch (TransformationRulesGeneratorException e) {
			throw new TaskException(e);
		} catch (IOException e) {
			throw new TaskException(e);
		}
	}

	public void setInput(TaskData input) throws TaskException {
		if ( ! ( input instanceof CsvTraceTaskData ) ){
			throw new TaskException();
		}
		this.input = (CsvTraceTaskData) input;
	}

}
