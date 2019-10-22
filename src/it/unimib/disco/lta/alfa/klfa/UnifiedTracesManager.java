package it.unimib.disco.lta.alfa.klfa;

import it.unimib.disco.lta.alfa.klfa.utils.componentAnalysis.ComponentTracesFile;

import java.io.File;
import java.io.IOException;

public class UnifiedTracesManager extends DistinctTracesManager {

	public UnifiedTracesManager(File outDir,String prefix, String globalTraceName, boolean splitBehavioralSequences ) {
		super(outDir,prefix, globalTraceName, splitBehavioralSequences);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected KeyData getKeyElement(String component, String token)
			throws IOException {
		return new KeyData(false,component);
	}
	
	@Override
	public void addToken(String component,String token,int line) throws IOException{
	
		String keyElement = getKeyElement(component,token).componentName;
		
		if ( keyElement == null ){
			return;
		}
		
		
		ComponentTracesFile componentTrace = getKeyElementTrace(global);
		componentTrace.addToken(token,line);
		
	}
}
