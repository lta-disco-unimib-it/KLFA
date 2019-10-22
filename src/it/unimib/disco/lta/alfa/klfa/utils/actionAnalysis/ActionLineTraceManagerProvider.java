package it.unimib.disco.lta.alfa.klfa.utils.actionAnalysis;

import it.unimib.disco.lta.alfa.klfa.DistinctTracesManager;
import it.unimib.disco.lta.alfa.klfa.TraceManagerProvider;

import java.io.File;
import java.util.Map;

public class ActionLineTraceManagerProvider implements TraceManagerProvider {

	private Map<Integer,String> actionsLines;
	private boolean splitBahavioralSequences;

	public ActionLineTraceManagerProvider(Map<Integer, String> actionsLines, boolean splitBahavioralSequences ) {
		this.actionsLines = actionsLines;
		this.splitBahavioralSequences = splitBahavioralSequences;
	}

	public DistinctTracesManager getTraceManager(File outputDir, String prefix,
			String name) {
		return new ActionLineTraceManager(outputDir,prefix,"GLOBAL",actionsLines,splitBahavioralSequences);
	}

}
