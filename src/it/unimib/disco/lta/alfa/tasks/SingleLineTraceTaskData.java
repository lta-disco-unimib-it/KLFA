package it.unimib.disco.lta.alfa.tasks;

import java.io.File;
import java.util.List;

public class SingleLineTraceTaskData {

	private List<File> inputFiles;

	public SingleLineTraceTaskData( List<File> inputs ){
		inputFiles = inputs;
	}

	public List<File> getInputFiles() {
		return inputFiles;
	}

	public void setInputFiles(List<File> inputFiles) {
		this.inputFiles = inputFiles;
	}
	
}
