package it.unimib.disco.lta.alfa.klfa;

import java.io.File;

public class TraceAnalyzerApplication extends TraceAnalyzer{
	
	

	public static class UnifiedTraceManagerProvider implements TraceManagerProvider {
		boolean splitBehavioralSequences = false;
		
		public UnifiedTraceManagerProvider(boolean splitBehavioralSequences) {
			this.splitBehavioralSequences = splitBehavioralSequences;
		}

		public DistinctTracesManager getTraceManager(File outputDir,String prefix, String name) {
			return new UnifiedTracesManager(outputDir,prefix,name,splitBehavioralSequences);
		}

		
	}
	
	
	public TraceAnalyzerApplication(boolean splitBehavioralSequences ) {
		super(new UnifiedTraceManagerProvider(splitBehavioralSequences));
	}
	



	
}
