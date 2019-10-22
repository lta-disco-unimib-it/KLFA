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

import it.unimib.disco.lta.alfa.eventsDetection.AutomatedEventTypesDetector;
import it.unimib.disco.lta.alfa.eventsDetection.AutomatedEventTypesDetectorException;
import it.unimib.disco.lta.alfa.eventsDetection.EventTypesDetectorException;

import java.io.File;
import java.io.IOException;


public class SlctComponentCsvGeneratorTask implements Task {

	private AutomatedEventTypesDetector typesDetector;
	private SingleLineTraceTaskData inputData;
	private File csvTraceFile;
	private File workDir;
	private File slctPatternFile;
	private static final String slctPatternFileName = "rules.properties";
	private static final String csvTraceFileName = "trace.csv";
	
	public SlctComponentCsvGeneratorTask( AutomatedEventTypesDetector typesDetector, File workDir ) {
		this.typesDetector = typesDetector;
		this.workDir = workDir;
		this.slctPatternFile = new File(workDir,slctPatternFileName);
		this.csvTraceFile = new File(workDir,csvTraceFileName );
	}
	
	
	public TaskData getResult() {
		return new CsvTraceTaskData(csvTraceFile,slctPatternFile,workDir);
	}

	public void run() throws TaskException {
		try {
			typesDetector.process(inputData.getInputFiles(), csvTraceFile );
			AutomatedEventTypesDetector.exportEventPatterns(typesDetector.getEventPatterns(), slctPatternFile);
		} catch (EventTypesDetectorException e) {
			throw new TaskException(e);
		} catch (IOException e) {
			throw new TaskException(e);
		}
	}

	public void setInput(TaskData input) throws TaskException {
		if ( input instanceof SingleLineTraceTaskData ){
			throw new TaskException("Wriong input: "+SingleLineTraceTaskData.class.getName()+" expected");
		}
		inputData = (SingleLineTraceTaskData) input;
	}

}
