/**
 * 
 */
package it.unimib.disco.lta.alfa.klfa;

import it.unimib.disco.lta.alfa.klfa.utils.componentAnalysis.ComponentTracesFile;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;


public abstract class DistinctTracesManager {
	private HashMap <String,ComponentTracesFile> traces = new HashMap<String, ComponentTracesFile>();
	private String prefix;
	protected String global;
	private String previousKeyElement = null;
	private File outDir;
	private boolean splitBehavioralSequences;
	
	public static class KeyData {
		
		protected String componentName;
		protected boolean changed;
		
		public KeyData(boolean changed, String componentName) {
			super();
			this.changed = changed;
			this.componentName = componentName;
		} 
		
	}
	
	public DistinctTracesManager( File outDir, String prefix, String globalTraceName, boolean splitBehavioralSequences ){
		this.prefix = prefix;
		this.global = globalTraceName;
		this.outDir = outDir;
		this.splitBehavioralSequences = splitBehavioralSequences;
		forceGlobalComponentCreation();
	}
	
	private void forceGlobalComponentCreation() {
		getKeyElementTrace(global);
	}

	public void addToken(String component,String token,int line) throws IOException{
		
		KeyData keyData = getKeyElement(component,token);
		
		String keyElement = keyData.componentName;
		if ( keyElement == null ){
			return;
		}
		
		if ( ! keyElement.equals( previousKeyElement) || keyData.changed ){
			if ( previousKeyElement != null && splitBehavioralSequences ){
				ComponentTracesFile componentTrace = getKeyElementTrace(previousKeyElement);
				componentTrace.closeTrace(line);
			}
			ComponentTracesFile componentTrace = getKeyElementTrace(global);
			componentTrace.addToken(token,line);
		}
		
		ComponentTracesFile componentTrace = getKeyElementTrace(keyElement);
		componentTrace.addToken(token,line);
		previousKeyElement = keyElement;
	}
	
	public boolean isSplitBehavioralSequences() {
		return splitBehavioralSequences;
	}

	public void setSplitBehavioralSequences(boolean splitBehavioralSequences) {
		this.splitBehavioralSequences = splitBehavioralSequences;
	}

	protected abstract KeyData getKeyElement(String component, String token) throws IOException;

	protected ComponentTracesFile getKeyElementTrace(String component) {
		ComponentTracesFile trace = traces.get(component);
		if ( trace == null ){
			trace = new ComponentTracesFile(outDir,component,prefix);
			traces.put(component, trace);
		}
		
		return trace;
	}

	public void closeTraces(int curline) throws IOException{
		for ( ComponentTracesFile trace : traces.values() ){
			trace.closeTrace(curline);
		}
		previousKeyElement = null;
	}
	
	public void closeFiles() throws IOException{
		for ( ComponentTracesFile trace : traces.values() ){
			trace.closeTrace(-1);
			trace.close();
		}
	}

	public Collection<ComponentTracesFile> getTraceFiles() {
		return traces.values();
	}

}