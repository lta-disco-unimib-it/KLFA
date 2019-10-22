/*******************************************************************************
 *    Copyright 2019 Fabrizio Pastore, Leonardo Mariani
 *   
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 *******************************************************************************/
package it.unimib.disco.lta.alfa.klfa;

import it.unimib.disco.lta.alfa.klfa.TraceAnalyzer.MinimizationTypes;
import it.unimib.disco.lta.alfa.klfa.traceFilters.TraceFilterRule;
import it.unimib.disco.lta.alfa.klfa.traceFilters.TraceFilterRuleReject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;


public class LogTraceAnalyzer {

	private static final int MIN_ARGS = 5;
	private static boolean multiLineOutput = true;

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		File actionNamesFile = null;
		ArrayList<String> argsToPass = new ArrayList<String>();
		
		if ( args.length < MIN_ARGS - 1 ){
			printUsage();
			return;
		}
		
		File actionLinesFile = null;
		MinimizationTypes minimizationType = MinimizationTypes.end;
		int kBehaviorK = 2;
		int minimizationLimit = 0;
		String elementsToIgnoreFile = null;
		String columnSeparator = ",";
		boolean ignoreGlobal = false;
		int componentColumn = 0;
		int eventColumn = 1;
		
		File outputDir = new File( "klfaoutput" );
		File inputDir = new File( "klfaoutput" );
		Set<String> elementsToIgnore = new HashSet<String>();
		String engineType = null;
		String suffix = ")";
		boolean cutOff = true;
		String fsaFileFormat = "ser";
		boolean splitBehavioralSequences = false;
		
		int kBehaviorMaxK = -1;
		boolean replaceSkippedParametersWithDash = false;
		
		ArrayList<String> actionNames = new ArrayList<String>();
		
		for( int i = 0; i < args.length - MIN_ARGS; ++i ){
			
			
			if ( args[i].equals("-actionsNames") ){
				actionNamesFile = new File ( args[++i] );
			} else if ( args[i].equals("-actionName") ){
				actionNames.add(args[++i]);
			} else if ( args[i].equals("-actionsLines") ){
				actionLinesFile  = new File ( args[++i] );
			} else if ( args[i].equals("-stepMinimization") ){
				minimizationType = MinimizationTypes.step;
			} else if ( args[i].equals("-disableMinimization") ){
				minimizationType = MinimizationTypes.none;
			} else if ( args[i].equals("-noCutoff") ){
				cutOff = false;
			} else if ( args[i].equals("-ignoreGlobal") ){
				ignoreGlobal=true;
			} else if ( args[i].equals("-singleLineOutput") ){
				multiLineOutput = false;
			} else if ( args[i].equals("-kBehaviorK") ){
				kBehaviorK = Integer.valueOf(args[++i]);
			} else if ( args[i].equals("-minimizationLimit") ){
				minimizationLimit = Integer.valueOf(args[++i]);
			} else if ( args[i].equals("-splitBehavioralSequences") ){
				splitBehavioralSequences = true;
			} else if ( args[i].equals("-replaceSkippedParametersWithDash") ){
				replaceSkippedParametersWithDash = true;
			} else if ( args[i].equals("-elementsToIgnore") ){
				elementsToIgnoreFile = args[++i];
			} else if ( args[i].equals("-separator") ){
				columnSeparator = args[++i];
			} else if ( args[i].equals("-suffix") ){
				suffix  = args[++i];
			} else if ( args[i].equals("-eventColumn") ){
				eventColumn = Integer.valueOf(args[++i]);
			} else if ( args[i].equals("-componentColumn") ){
				componentColumn = Integer.valueOf(args[++i]);
			} else if ( args[i].equals("-outputDir") ){
				outputDir = new File(args[++i]);
			} else if ( args[i].equals("-fsaFileFormat") ){
				 fsaFileFormat = args[++i];
			} else if ( args[i].equals("-inputDir") ){
				inputDir = new File(args[++i]);
			} else if ( args[i].equals("-kBehaviorMaxK") ){
				kBehaviorMaxK = Integer.valueOf(args[++i]);
			} else if ( args[i].equals("-engineType") ){
				engineType = args[++i];
			} else if ( args[i].equals("-ignore") ){
				elementsToIgnore.add(args[++i]);
			} else {
				System.err.println("Unrecognized option : "+args[i]);
				
				printUsage();
				System.exit(-1);
			}
		}
		
		String splitType = args[args.length-5];
		
		TraceAnalyzer analyzer;
		
		System.out.println("Analysis granularity is: "+splitType);
		
		if ( splitType.equals("noSplit")  || splitType.equals("applicationLevel")){
			analyzer = new TraceAnalyzerApplication(splitBehavioralSequences);
		} else if ( splitType.equals("splitComponent") || splitType.equals("componentLevel")){
			analyzer = new TraceAnalyzerComponent(splitBehavioralSequences);
		} else if ( splitType.equals("splitAction") || splitType.equals("actionLevel") ){
			if ( actionNames.size() == 0 && actionNamesFile == null && actionLinesFile == null ){
				System.err.println("You must specify the splitActionFile or the splitActionLines or the acton names");
				printUsage();
				System.exit(-1);
			}
			
			if ( actionNamesFile != null ){
				ArrayList<String> actions = getActions(actionNamesFile);
				analyzer = new TraceAnalyzerAction(actions,splitBehavioralSequences);
			} else if ( actionNames.size() > 0 ) {
				analyzer = new TraceAnalyzerAction(actionNames,splitBehavioralSequences);
			} else {
				Map<Integer,String> actionsLines = getActionLines(actionLinesFile);
				analyzer = new TraceAnalyzerAction ( actionsLines,splitBehavioralSequences);
				//System.out.println(actionsLines.size());
			}
			
		} else {
			System.err.println("Unrecognized analysisType : "+splitType);
			
			printUsage();
			
			return;
		}
		
		//
		//Configure analyzer options
		//
		
		if ( minimizationLimit > 0 ){
			analyzer.setMinimizationLimit(minimizationLimit);
		}
		analyzer.setReplaceSkippedElementsWithDash(replaceSkippedParametersWithDash);
		analyzer.setIgnoreGlobal(ignoreGlobal);
		analyzer.setFsaFileFormat(fsaFileFormat);
		analyzer.setMultiLineOutput(multiLineOutput );
		analyzer.setKBehaviorK(kBehaviorK);
		analyzer.setSuffix(suffix);
		analyzer.setOutputDir(outputDir);
		analyzer.setInputDir(inputDir);
		analyzer.setComponentColumn(componentColumn);
		analyzer.setEventColumn(eventColumn);
		analyzer.setColumnSeparator(columnSeparator);
		analyzer.setMinimizationType(minimizationType);
		analyzer.setCutoffSearch(cutOff);
		if ( kBehaviorMaxK != -1 ){
			analyzer.setKBehaviorMaxK(kBehaviorMaxK);
		}
		
		if ( engineType != null ){
			if ( engineType.equals("KBehaviorWeightCount") ){
				analyzer.setEngineType(TraceAnalyzer.EngineTypes.KBehaviorWeightCount);
			} else if ( engineType.equals("KBehaviorSortNeighbourhood") ){
				analyzer.setEngineType(TraceAnalyzer.EngineTypes.KBehaviorSortNeighbourhood);
			} else if ( engineType.equals("KBehavior") ){
				analyzer.setEngineType(TraceAnalyzer.EngineTypes.KBehavior);
			} else {
				System.err.println("Unrecognized engine type: "+engineType);
				printUsage();
				System.exit(-1);
			}
		}
		
		String mode = args[args.length-4];
		File transformersFile = new File(args[args.length-3] );
		File preprocessorsFile = new File ( args[args.length-2] );
		File trace = new File(args[args.length-1]);
		
		
		String[] args_ = new String[argsToPass.size()];
		args_ = argsToPass.toArray(args_);
		
		
		if ( elementsToIgnoreFile != null ){
			try {
				analyzer.loadElelementsToIgnore(new File(elementsToIgnoreFile));
			} catch (IOException e) {
				System.err.println("Cannot load elements to ignore from "+elementsToIgnoreFile+" : "+e.getMessage());
				printUsage();
				System.exit(-1);
			}
		}
		
		if ( elementsToIgnore.size() > 0 ){
			analyzer.addTraceFilterRules(createTraceFilterRules(elementsToIgnore));
		}
		
		try {
			if ( mode.equals("training") ){
				analyzer.runTraining(trace, transformersFile, preprocessorsFile);
			} else if ( mode.equals("checking") ){
				analyzer.setMinimizationType(TraceAnalyzer.MinimizationTypes.none);
				analyzer.runChecking(trace, transformersFile, preprocessorsFile);
			} else {
				System.err.println("Analysis type should be one of training or checking");
				printUsage();
				System.exit(-1);
			}
		} catch (TraceAnalyzerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	

	private static List<TraceFilterRule> createTraceFilterRules(
			Set<String> elementsToIgnore) {
		
		List<TraceFilterRule> rules = new ArrayList<TraceFilterRule>();
		
		for ( String elementToIgnore : elementsToIgnore ){
			TraceFilterRule rule = new TraceFilterRuleReject();
			String[] elements = elementToIgnore.split(",");
			for ( int i = 0; i < elements.length; ++i ){
				rule.putColumnRule(i, elements[i]);
			}
			rules.add(rule);
		}
		
		return rules;
		
	}



	private static Map<Integer, String> getActionLines(File actionLinesFile) throws IOException {
		FileInputStream fis = new FileInputStream(actionLinesFile);
		Properties p = new Properties();
		p.load(fis);
		HashMap<Integer,String> map = new HashMap<Integer, String>();
		
		for ( Object property : p.keySet() ){
			int line = Integer.valueOf((String)property);
			String action = p.getProperty((String) property).replace("/", "_");
			map.put(line, action);
		}
		fis.close();
		return map;
	}

	private static void printUsage() {
		// TODO Auto-generated method stub
		
		System.out.println("This program builds models of the application behavior by analyzing a trace file. " +
				"The trace file must be a collection of lines, each one in the format COMPONENT,EVENT[,PARAMETER]. Multiple traces can be defined in a file, to separate a trace from another put a line with the | symbol." +
				"\nUsage :" +
				"\n\t"+LogTraceAnalyzer.class.getName()+" [options] <analysisType> <phase> <valueTranformersConfigFile> <preprocessingRules> <traceFile>" +
						"\nWhere:" +
						"\n\t <analysisType> is the way in which data can be separated to create separated models, can be one of:" +
						"\n\t\tapplicationLevel: generates a single model for the whole trace" +
						"\n\t\tcomponentLevel: generates a model for every component of the system" +
						"\n\t\tactionLevel: generates a different model for every different user actions"  +
						"\n\t <phase> is the technique phase, can be \"training\" (for the generation of the fsa of the valid behavior) or \"checking\" (to check behavioral changes in other executions)" +
						"\n\t <valueTranformersConfigFile> is the path to the file containing the configurations of the different value tranformers that will be used during trace preprocessing" +
						"\n\t <preprocessingRules> is the path to the file containing the preprocessing rules to follow in the preprocessing phase" +
						"\n\t <configFile> is the KBehavior configuration File" +
						"\n\t <traceFile> is the trace file to be read." +
						"\n\tOptions:" +
						"\n\t-fsaFileFormat <format> : format of the file to generate (one of: jff,ser,fsa)" +
						"\n\t-actionsNames <file> : path to the file containing the name of the different actions" +
						"\n\t-actionsLines <file> : path to the properties file indicating the lines in which actions start. File must be i the format lineNumber = action." +
						"\n\t-stepMinimization : do step minimization when launching kbehavior" +
						"\n\t-disableMinimization : do not minimize models inferred with kbehavior(default when checking)" +
						
						
						"\n\t-kBehaviorK <value>: k value for the kBehavior algorithm"+
						"\n\t-kBehaviorMaxK <value>: k max value for the kBehavior algorithm (default is equan to kBehaviorK)"+
						"\n\t-minimizationLimit <value>: when running kBehavior minimize only FSA with less than <value> states"+
						"\n\t-elementsToIgnore <file>: load elements to ignore from file"+
						"\n\t-separator <string>: the csv columns are separated by <string>"+
						"\n\t-suffix <string>: specifies the suffix to add to fsa symbols. Default is )."+
						"\n\t-eventColumn <value>: specifies the csv column containing the event name "+
						"\n\t-componentColumn <value>: specifies the csv column containing the component name "+
						"\n\t-outputDir <path>: specifies the path to the dir where to save FSA and temporary data. Default is klfaoutput."+
						"\n\t-inputDir <path>: specifies the path to the dir where to load FSA from when checking. Default is klfaoutput."+
						"\n\t-ignore <name>: ignore the element named <name> during analysis. Name can indicate a component or an action." +
						"\n\t-ignoreGlobal: do not consider GLOBAL interactions" +
						"\n\t-replaceSkippedParametersWithDash: replace parameters not considered by a rule with dashes" +
						"\n\n"
						);
		
	}

	private static ArrayList<String> getActions(File actionNamesFile) throws IOException {
		ArrayList<String> actions = new ArrayList<String>();
		
		if ( actionNamesFile == null ){
			return actions;
		}
		
		BufferedReader r = new BufferedReader(new FileReader(actionNamesFile) );
		String line;
		while ( ( line = r.readLine() ) != null ){
			actions.add(line);
		}
		r.close();
		
		return actions;
	}

}
