package it.unimib.disco.lta.alfa.tasks;

import java.io.File;

public class CsvTraceTaskData implements TaskData {

	private File csvTraceFile;
	private File slctPatternFile;
	private File componentsPatternsDir;

	public CsvTraceTaskData(File csvTraceFile, File slctPatternFile, File componentsPatternsDir) {
		this.csvTraceFile = csvTraceFile;
		this.slctPatternFile = slctPatternFile;
		this.componentsPatternsDir = componentsPatternsDir;
	}

	public File getCsvTraceFile() {
		return csvTraceFile;
	}

	public File getSlctPatternFile() {
		return slctPatternFile;
	}

}
