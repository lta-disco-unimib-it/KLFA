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
