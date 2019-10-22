package it.unimib.disco.lta.alfa.klfa.utils.actionAnalysis;

import it.unimib.disco.lta.alfa.klfa.DistinctTracesManager;
import it.unimib.disco.lta.alfa.klfa.DistinctTracesManager.KeyData;
import it.unimib.disco.lta.alfa.klfa.utils.componentAnalysis.ComponentTracesFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class ActionTracesManager extends DistinctTracesManager {

	private List<String> actions;
	private String curAction = null;
	
	public ActionTracesManager(File outDir,String prefix, String global, List<String> actions,boolean splitBevaioralSequences) {
		super(outDir,prefix,global,splitBevaioralSequences);
		this.actions = actions;
	}


	
	
	@Override
	protected KeyData getKeyElement(String component, String token) throws IOException {
		boolean actionChanged = false;
		for ( String action : actions ){
			if ( token.matches(action) ){
				if ( curAction != null ){
					ComponentTracesFile traceFile = getKeyElementTrace(curAction);
					traceFile.close();
				}
				curAction = token;
				actionChanged = true;
				break;
			}
		}
		return new KeyData(actionChanged,curAction);
	}

	public String getCurAction() {
		return curAction;
	}

	public void setCurAction(String curAction) {
		this.curAction = curAction;
	}

}
