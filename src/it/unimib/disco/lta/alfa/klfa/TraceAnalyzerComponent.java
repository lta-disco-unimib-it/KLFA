package it.unimib.disco.lta.alfa.klfa;


import it.unimib.disco.lta.alfa.klfa.utils.componentAnalysis.ComponentTracesManager;

import java.io.File;


/**
 * This class read a csv trace file and builds separated FSA for different components. Components are selected on the basis of their name.
 * Trace files must be in th eform COMPONENT,Symbol.
 * 
 * @author Fabrizio Pastore fabrizio.pastore AT gmail.com
 *
 */
public class TraceAnalyzerComponent extends TraceAnalyzer {

	public static class TraceManagerProviderComponent implements TraceManagerProvider{

		private boolean splitBehavioralSequences;

		public TraceManagerProviderComponent(boolean splitBehavioralSequences) {
			this.splitBehavioralSequences = splitBehavioralSequences;
		}

		public DistinctTracesManager getTraceManager(File outputDir,String prefix, String name) {
			return new ComponentTracesManager(outputDir,prefix,name,splitBehavioralSequences);
		}
	}
	
	public TraceAnalyzerComponent(boolean splitBehavioralSequences) {
		super(new TraceManagerProviderComponent(splitBehavioralSequences));
	}







}
