package it.unimib.disco.lta.alfa.klfa;

import it.unimib.disco.lta.alfa.inferenceEngines.KBehaviorEngine;
import it.unimib.disco.lta.alfa.klfa.utils.actionAnalysis.ActionLineTraceManagerProvider;
import it.unimib.disco.lta.alfa.klfa.utils.actionAnalysis.ActionTracesManager;
import it.unimib.disco.lta.alfa.klfa.utils.componentAnalysis.ComponentFSABuilderException;
import grammarInference.Log.FileLogger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import automata.fsa.FiniteStateAutomaton;

/**
 * This class read a csv trace file and builds separated FSA for different components. Components are selected on the basis of their name.
 * Trace files must be in th eform COMPONENT,Symbol.
 * 
 * @author Fabrizio Pastore fabrizio.pastore AT gmail.com
 *
 */
public class TraceAnalyzerAction extends TraceAnalyzer{

	private static final int componentColumn = 0;
	private static final int tokenColumn = 1;
	private ArrayList<String> actions = new ArrayList<String>();
	private Map<Integer,String> actionsLines = new HashMap<Integer,String>();
	
	public static class ActionTraceManagerProvider implements TraceManagerProvider {


		private List<String> actions;
		private boolean splitBehavioralSequences;

		public ActionTraceManagerProvider(List<String> actions, boolean splitBehavioralSequences ) {
			this.splitBehavioralSequences = splitBehavioralSequences;
			this.actions = actions;
		}

		public DistinctTracesManager getTraceManager(File outputDir,String prefix, String name) {
			return new ActionTracesManager(outputDir,prefix,"GLOBAL",actions,splitBehavioralSequences);
		}
	};
	
	public TraceAnalyzerAction ( List<String> actions, boolean splitBehavioralSequences ){
		super( new ActionTraceManagerProvider(actions,splitBehavioralSequences) );
		this.actions.addAll(actions);
	}
	
	public TraceAnalyzerAction ( Map<Integer,String> actionsLines, boolean splitBehavioralSequences  ){
		super( new ActionLineTraceManagerProvider(actionsLines,splitBehavioralSequences) );
	}
	
	public TraceAnalyzerAction ( TraceManagerProvider p ){
		super( p );
		
	}
	


	
	protected void addToken(DistinctTracesManager cm, String cleanline, String suffix, int curline) throws ComponentFSABuilderException, IOException, TraceAnalyzerException {
		//System.out.println("TKN");

		
		if ( isAction(cleanline)){
			cm.addToken("", cleanline,curline);
		} else {
			cm.addToken(cleanline, suffix, curline);
		}
		
	}
	
	
	public boolean isAction ( String token ){
		for ( String action : actions ){
			if ( token.matches(action) ){
				return true;
			}
		}
		return false;
	}


}
