package it.unimib.disco.lta.alfa.klfa;

import java.io.File;

public interface TraceManagerProvider {
	
	public DistinctTracesManager getTraceManager(File outputDir, String prefix, String name);
	
}
