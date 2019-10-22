package it.unimib.disco.lta.alfa.klfa.utils.actionAnalysis;

import it.unimib.disco.lta.alfa.klfa.DistinctTracesManager;
import it.unimib.disco.lta.alfa.klfa.TraceManagerProvider;

import java.io.File;
import java.util.List;

public class ActionDefinitionTraceManagerProvider implements TraceManagerProvider {

	private List<ActionDefinition> actionDefinitions;
	private boolean splitBehavioralSequences;
	private String columnSeparator;

	public ActionDefinitionTraceManagerProvider ( List<ActionDefinition> actionDefinitions, boolean splitBehavioralSequences, String columnSeparator ){
		this.actionDefinitions = actionDefinitions;
		this.splitBehavioralSequences = splitBehavioralSequences;
		this.columnSeparator = columnSeparator;
	}

	public DistinctTracesManager getTraceManager(File outputDir, String prefix,
			String name) {
		return new ActionDefinitiontTracesManager(outputDir,prefix,name,splitBehavioralSequences,actionDefinitions,columnSeparator);
	}
}
