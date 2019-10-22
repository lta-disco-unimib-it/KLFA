package it.unimib.disco.lta.alfa.klfa.utils.actionAnalysis;

import it.unimib.disco.lta.alfa.klfa.DistinctTracesManager;
import it.unimib.disco.lta.alfa.klfa.DistinctTracesManager.KeyData;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class ActionLineTraceManager extends DistinctTracesManager {

	private Map<Integer, String> actionsLines;
	private String curAction = null;
	private boolean actionChanged;

	public ActionLineTraceManager(File outputDir, String prefix, String global, Map<Integer, String> actionsLines, boolean splitBehavioralSequences ) {
		super(outputDir,prefix,global,splitBehavioralSequences);
		this.actionsLines = actionsLines;
	}

	@Override
	protected KeyData getKeyElement(String component, String token)
			throws IOException {
		
		return new KeyData(actionChanged,component);
	}

	public void addToken(String component,String token,int curline) throws IOException{
		
		if ( actionsLines.containsKey(curline) ){	//in case we use actions specified not inside the log we add also the log line  
			curAction  = actionsLines.get(curline);
			actionChanged = true;
			super.addToken("", curAction,curline);
		} else {
			actionChanged = false;
		}
		super.addToken(component, token, curline);
	}
}
