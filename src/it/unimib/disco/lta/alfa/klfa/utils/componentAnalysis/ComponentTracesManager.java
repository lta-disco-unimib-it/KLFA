package it.unimib.disco.lta.alfa.klfa.utils.componentAnalysis;

import it.unimib.disco.lta.alfa.klfa.DistinctTracesManager;
import it.unimib.disco.lta.alfa.klfa.DistinctTracesManager.KeyData;

import java.io.File;

public class ComponentTracesManager extends DistinctTracesManager {

	public ComponentTracesManager(File outDir,String prefix, String string,boolean splitBehavioralSequences) {
		super(outDir,prefix,string,splitBehavioralSequences);
	}

	@Override
	protected KeyData getKeyElement(String component, String token) {
		return new KeyData(false,component);
	}

}
