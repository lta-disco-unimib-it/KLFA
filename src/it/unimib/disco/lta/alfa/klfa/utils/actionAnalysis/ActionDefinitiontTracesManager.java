package it.unimib.disco.lta.alfa.klfa.utils.actionAnalysis;

import it.unimib.disco.lta.alfa.klfa.DistinctTracesManager;
import it.unimib.disco.lta.alfa.klfa.DistinctTracesManager.KeyData;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class ActionDefinitiontTracesManager extends DistinctTracesManager {

	private List<ActionDefinition> actionDefinitions;
	private String columnSeparator;
	private String curAction;

	public ActionDefinitiontTracesManager(File outDir, String prefix,
			String globalTraceName, boolean splitBehavioralSequences, List<ActionDefinition> actionDefinitions, String columnSeparator ) {
		super(outDir, prefix, globalTraceName, splitBehavioralSequences);
		this.actionDefinitions = actionDefinitions;
		this.columnSeparator = columnSeparator;
	}

	@Override
	protected KeyData getKeyElement(String component, String token)
			throws IOException {
		String[] cols = token.split(columnSeparator);
		boolean changed =false;
		for ( ActionDefinition action : actionDefinitions ){
			if ( action.isActionEvent(cols) ){
				curAction = action.getName();
				changed = true;
			}
		}
		return new KeyData(changed,curAction);
	}

}
