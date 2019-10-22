package it.unimib.disco.lta.alfa.klfa.utils.actionAnalysis;

import it.unimib.disco.lta.alfa.klfa.DistinctTracesManager;
import it.unimib.disco.lta.alfa.klfa.DistinctTracesManager.KeyData;
import it.unimib.disco.lta.alfa.klfa.utils.componentAnalysis.ComponentTracesFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

public class ActionsColumnsTracesManager extends DistinctTracesManager {

	private HashMap<String,HashMap<Integer,String>> actions = new HashMap<String, HashMap<Integer,String>>();
	private String curAction = null;
	private String separator;
	
	public ActionsColumnsTracesManager(File outDir,String prefix, String global, List<String> actions, String separator,boolean splitBehavioralSequences) {
		
		super(outDir,prefix,global,splitBehavioralSequences);
		this.separator = separator;
		for ( String action : actions ){
			String[] split = action.split("=");
			String name = split[0];
			String[] contents = split[1].split(",");
			
			HashMap<Integer,String> rules = new HashMap<Integer, String>();
			for ( String content : contents ){
				String[] elements = content.split(":");
				int pos = Integer.valueOf(elements[0]);
				String rule = elements[1];
				rules.put(pos, rule);
			}
			
			this.actions.put(name,rules);
		}
	}


	
	
	@Override
	protected KeyData getKeyElement(String component, String token) throws IOException {
		String[] splitted = token.split(separator);
		boolean actionChanged = false;
		for ( Entry<String,HashMap<Integer,String>> entry : actions .entrySet() ){
			HashMap<Integer, String> rules = entry.getValue();
			boolean match = true;
			for ( Integer pos : rules.keySet() ){
				if ( pos >= splitted.length || ! splitted [pos].matches(rules.get(pos)) ){
					match = false;
				}
			}
			
			if ( match ){
			
				if ( curAction != null ){
					ComponentTracesFile traceFile = getKeyElementTrace(curAction);
					traceFile.close();
				}
				
				
						
				actionChanged = true;
				curAction = entry.getKey();
				break;
			}
		}
		
		return new KeyData(actionChanged, token);
	}


	public String getCurAction() {
		return curAction;
	}

	public void setCurAction(String curAction) {
		this.curAction = curAction;
	}
	
}
