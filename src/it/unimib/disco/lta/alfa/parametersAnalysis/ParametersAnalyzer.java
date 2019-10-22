package it.unimib.disco.lta.alfa.parametersAnalysis;

import it.unimib.disco.lta.alfa.dataTransformation.ValueTransformer;
import it.unimib.disco.lta.alfa.dataTransformation.ValueTransformerGlobal;
import it.unimib.disco.lta.alfa.dataTransformation.ValueTransformerRelativeToAccess;
import it.unimib.disco.lta.alfa.dataTransformation.ValueTransformerRelativeToInstantiation;
import it.unimib.disco.lta.alfa.dataTransformation.ValueTransformerSame;
import it.unimib.disco.lta.alfa.dataTransformation.parserRules.ParserRule;
import it.unimib.disco.lta.alfa.eventsDetection.slct.SlctEventsParser;
import it.unimib.disco.lta.alfa.eventsDetection.slct.SlctPattern;
import it.unimib.disco.lta.alfa.inferenceEngines.KBehaviorEngine;
import it.unimib.disco.lta.alfa.klfa.TraceAnalyzerApplication;
import it.unimib.disco.lta.alfa.klfa.TraceAnalyzerComponent;
import it.unimib.disco.lta.alfa.klfa.TraceAnalyzerException;
import it.unimib.disco.lta.alfa.klfa.TraceManagerProvider;
import it.unimib.disco.lta.alfa.klfa.UnifiedTracesManager;
import it.unimib.disco.lta.alfa.klfa.utils.componentAnalysis.ComponentFSABuilderException;
import it.unimib.disco.lta.alfa.klfa.utils.componentAnalysis.ComponentTraceFileMaker;
import it.unimib.disco.lta.alfa.klfa.utils.componentAnalysis.ComponentTracesFile;
import it.unimib.disco.lta.alfa.utils.MathUtil;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.Map.Entry;
import java.util.regex.Pattern;


import automata.fsa.FiniteStateAutomaton;



public class ParametersAnalyzer {
	
	
	private RTableExporter exporter = new RTableExporter();
	
	private int maxLinesToRead = -1;

	private boolean dontSeparateTraces;

	private int maxLinesToAnalyze = -1; 
	
	public int getMaxLinesToRead() {
		return maxLinesToRead;
	}









	public void setMaxLinesToRead(int maxLinesToRead) {
		this.maxLinesToRead = maxLinesToRead;
	}









	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		
		ParametersAnalyzer analyzer = new ParametersAnalyzer();
		
		double intersectionThreshold = 0.85;
		int support = 5;
		
		if ( args.length < 1 ){
			printUsage();
			System.exit(-1);
		}
		
		boolean testPreprocessingRules = false;
		SlctEventsParser g = new SlctEventsParser();
		
		for(int i = 0; i< args.length-3;++i){
			if ( args[i].equals("-testPreprocessingRules") ){
				testPreprocessingRules = true;
			} else if ( args[i].equals("-actionElements") ){
				String[] sigElements = args[++i].split(",");
				int[] signatureElements = new int[sigElements.length];
				
				for( int j = 0; j < sigElements.length; ++j ){
					signatureElements[j] = Integer.valueOf(sigElements[j]);
				}
				analyzer.setSignatureElements(signatureElements);
			} else if ( args[i].equals("-separator") ){
				analyzer.setSeparator(args[++i]);
			} else if ( args[i].equals("-slctPatterns") ||  args[i].equals("-rules")){
				g.loadSlctRegex(new File(args[++i]));
			} else if ( args[i].equals("-patterns") ){
				Properties p = new Properties();
				FileInputStream s = new FileInputStream(new File(args[++i]));
				p.load(s);
				g.loadRules(p);
			} else if ( args[i].equals("-actionNameLimit") ){
				analyzer.setHashThreshold(Integer.valueOf(args[++i]));
			} else if ( args[i].equals("-clusterSupport") ){
				support = Integer.valueOf(args[++i]);
			} else if ( args[i].equals("-intersectionThreshold") ){
				intersectionThreshold = Double.valueOf(args[++i]);
			} else {
				System.err.println("Unrecognized option "+args[i]);
				printUsage();
				System.exit(-1);
			}
		}
		
		
		
		File csvFileToAnalyze = new File(args[args.length-2]);
		
		File output = new File(args[args.length-1]);
		
		
		if ( output.exists() ){
			System.err.println(output.getAbsolutePath()+ " exists aborting");
			System.exit(-1);
		}
		
		BufferedWriter w = new BufferedWriter( new FileWriter (output) );
		
		try {
			AnalysisResult r = analyzer.analyze(csvFileToAnalyze,null);
			ArrayList<TraceData> tracesData = r.getTracesDatas();
			printTraceData(w,tracesData);
						

			ParametersClustersGenerator pcg = new ParametersClustersGenerator(tracesData, intersectionThreshold,support);
			Set<Cluster<RuleParameter>> clusters = pcg.getAllClusters();

			
//			printClusters(w,clusters, csvFileToAnalyze, analyzer.getExporter(), tracesData, testPreprocessingRules, g);
//			printSuggest(w,clusters,analyzer.getExporter());

			ClusterStatisticsGenerator csg = new ClusterStatisticsGenerator();
			List<ClusterStatistics> clustersStats = csg.calculateClustersStatistics(clusters, csvFileToAnalyze, analyzer.getExporter(), tracesData, g.getRules());

			printStatistics( w, clustersStats, analyzer.getExporter(), g.getRules(), null );
			
			TracePreprocessorConfigurationMaker tpcm = new TracePreprocessorConfigurationMaker(  );
			TracePreprocessorConfiguration tpc = tpcm.getSuggestedConfigurations(  clustersStats, analyzer.getExporter() );
			
			System.out.println("Suggested transformers: ");
			System.out.println(tpc.getTransformersFileContent());
			
			System.out.println("Suggested preprocessing rules: ");
			System.out.println(tpc.getPreprocessingRules());
			
			w.flush();


			w.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}







	

	public static void printStatistics(BufferedWriter w, List<ClusterStatistics> clustersStats, RTableExporter rTableExporter, List<SlctPattern> slctPatterns, Map<String, String> additionalData ) throws IOException {
		w.write("\nClusters Statistics:");
		w.write("\n");
		
		w.write("Signature");
		w.write("\t"+"Patterns");
		w.write("\t"+"Traces");
		w.write("\t"+"DistinctValues");
		w.write("\t"+"SharedValues");
		w.write("\t"+"Sharing");
		
		w.write("\t"+"DistinctValuesDistanceMeanMean");
		w.write("\t"+"DistinctValuesDistanceMeanStdDev");
		w.write("\t"+"DistinctValuesDistanceMedianMean");
		w.write("\t"+"DistinctValuesDistancesMedianStdDev");
		w.write("\t"+"DistinctValuesDistancesThirdQuartileMean");
		w.write("\t"+"DistinctValuesDistancesThirdQuartileStdDev");
		w.write("\t"+"DistinctValuesDistanceStdDevMean");
		w.write("\t"+"DistinctValuesDistanceStdDevStdDev");
		
		w.write("\t"+"DistinctValuesMean");
		w.write("\t"+"DistinctValuesOccurrenciesMean");
		w.write("\t"+"DistinctValuesOccurrenciesStdDev");
		w.write("\t"+"DistinctValuesStdDev");
		
		w.write("\t"+"EventDistanceMeanMean");
		w.write("\t"+"EventDistanceMeanStdDev");
		w.write("\t"+"EventDistanceMedianMean");
		w.write("\t"+"EventDistanceMedianStdDev");
		w.write("\t"+"EventDistanceStdDevMean");
		w.write("\t"+"EventDistanceStdDevStdDev");
		
		w.write("\t"+"ValuesMeanOnValuesOccurrenciesMean");
		w.write("\t"+"ValuesNumberOnValuesDistanceMedian");
		w.write("\t"+"ValuesNumberOnValuesOccurrenciesPerDistanceMedian");
		
		w.write("\t"+"GoMedian");
		w.write("\t"+"RiMedian");
		w.write("\t"+"RaMedian");
		
		
		
		w.write("\n");
		
		
		
		for ( ClusterStatistics clusterStats : clustersStats	){
			printStatistics(w,clusterStats,slctPatterns,rTableExporter,additionalData);
			w.write("\n");
		}
		
		w.write("\n");
	}









	public static void printStatistics(BufferedWriter w,
			ClusterStatistics clusterStats, List<SlctPattern> slctPatterns, RTableExporter rTableExporter, Map<String,String> additionalData) throws IOException {

		
		
		w.write(clusterStats.getCluster().getSignature());
		w.write("\t"+getPatterns(clusterStats.getCluster(),slctPatterns,rTableExporter));
		w.write("\t"+clusterStats.getTracesNumber());
		w.write("\t"+clusterStats.getDistinctValues());
		w.write("\t"+clusterStats.getSharedValues());
		w.write("\t"+clusterStats.getSharing());
		
		w.write("\t"+clusterStats.getDistinctValuesDistanceMeanMean());
		w.write("\t"+clusterStats.getDistinctValuesDistanceMeanStdDev());
		w.write("\t"+clusterStats.getDistinctValuesDistanceMedianMean());
		w.write("\t"+clusterStats.getDistinctValuesDistancesMedianStdDev());
		w.write("\t"+clusterStats.getDistinctValuesDistancesThirdQuartileMean());
		w.write("\t"+clusterStats.getDistinctValuesDistancesThirdQuartileStdDev());
		w.write("\t"+clusterStats.getDistinctValuesDistanceStdDevMean());
		w.write("\t"+clusterStats.getDistinctValuesDistanceStdDevStdDev());
		
		w.write("\t"+clusterStats.getDistinctValuesMean());
		w.write("\t"+clusterStats.getDistinctValuesOccurrenciesMean());
		w.write("\t"+clusterStats.getDistinctValuesOccurrenciesStdDev());
		w.write("\t"+clusterStats.getDistinctValuesStdDev());
		
		w.write("\t"+clusterStats.getEventDistanceMeanMean());
		w.write("\t"+clusterStats.getEventDistanceMeanStdDev());
		w.write("\t"+clusterStats.getEventDistanceMedianMean());
		w.write("\t"+clusterStats.getEventDistanceMedianStdDev());
		w.write("\t"+clusterStats.getEventDistanceStdDevMean());
		w.write("\t"+clusterStats.getEventDistanceStdDevStdDev());
		
		w.write("\t"+clusterStats.getValuesMeanOnValuesOccurrenciesMean());
		w.write("\t"+clusterStats.getValuesNumberOnValuesDistanceMedian());
		w.write("\t"+clusterStats.getValuesNumberOnValuesOccurrenciesPerDistanceMedian());
		
		w.write("\t."+clusterStats.getGoMedian());
		w.write("\t"+clusterStats.getRiMedian());
		w.write("\t"+clusterStats.getRaMedian());
		
		
		w.write("\t");
		if ( additionalData != null ){
			String additional = additionalData.get(clusterStats.getCluster().getSignature());
			if ( additional != null ){
				w.write(additional);
			} else {
				System.out.println("Not found "+clusterStats.getCluster().getSignature());
			}
		} else {
			System.out.println("No aditional data");
		}
	}









	private static String getPatterns(Cluster<RuleParameter> cluster, List<SlctPattern> slctPatterns, RTableExporter tableExporter) {
		StringBuffer result = new StringBuffer();
		
		HashMap<String,Pattern> rulesMap = new HashMap<String, Pattern>();
		
		for ( SlctPattern slctPattern :  slctPatterns){
			rulesMap.put(slctPattern.getId(), slctPattern.getPattern());
		}
		
		result.append("[ ");
		
		boolean first = true;
		for ( RuleParameter p : cluster.getElements() ){
			
			if ( first ){
				first = false;
			} else {
				result.append(" - ");
			}

			String ruleSignature = p.getRuleName()+"_"+p.getParameterPos();
			
			System.out.println(ruleSignature+" "+tableExporter);
			//String ruleName = tableExporter.getSignatureElements(ruleSignature)[1];
			
			String ruleName = ruleSignature.split("_")[1];
			
			Pattern pattern = rulesMap.get(ruleName);
			if ( pattern == null ){
				result.append(ruleName);
			} else {
				result.append(pattern.pattern());
			}
			
		}
		
		result.append(" ]");
		
		return result.toString();
	}









	public static void printTraceData(BufferedWriter w, ArrayList<TraceData> tracesData) throws IOException {
		
		
		int c=0;
		for ( int traceIndex = 0; traceIndex < tracesData.size(); ++traceIndex ){
			TraceData traceData = tracesData.get(traceIndex);
			w.write("\nStatistical information for trace "+c+"\n");
			RulesStatistics rs = traceData.getRulesStatistics();
			Collection<RuleStatistics> stats = rs.getStatistics();
			w.write("\nRule Name\tParameter Position\tOccurrencies\tValues #\tValues mean\t");
			for ( RuleStatistics stat : stats ){
				int[] n;
				try {
					n = stat.getParametersTotalOccurrencies();
				} catch (RuleStatisticExceptionNoParameters e) {
					n = new int[]{0};
				}
				double[] avg;
				try {
					avg = stat.getParametersOccurrenciesMean();
				} catch (RuleStatisticExceptionNoParameters e) {
					avg = new double[]{0};
				}

				for( int i = 0 ; i < n.length; ++i ){
					int valuesN;
					try {
						//How many distinct values do we have for the i-th parameter?
						valuesN = stat.getParameterValues(i).size();
					} catch (RuleStatisticsException e) {
						valuesN = -1;
					}
					w.write("\n"+stat.getRuleName()+"\t"+i+"\t"+n[i]+"\t"+valuesN+"\t"+avg[i]);
				}
			}
			w.flush();
			//printAllIntersections(stats);

			
		}

		
	}









	private static void printSuggest(BufferedWriter w, Set<Cluster<RuleParameter>> clusters, RTableExporter ruleExporter) throws IOException {
		w.write("\nSuggestions for preprocessors:");
		
		for(Cluster<RuleParameter> cluster : clusters  ){
			
			for ( RuleParameter p : cluster.getElements()){
				String ruleSignature = p.getRuleName();
				String[] signatureElements = ruleExporter.getSignatureElements(ruleSignature);
				w.write("\n"+signatureElements[0]+","+signatureElements[1]+",0:SAME,1:SAME,"+( 2+p.getParameterPos() )+":"+cluster.getSignature());
			}
			
		}
	}



	private static void printUsage() {
		System.out.println("\nUsage\n" +
				ParametersAnalyzer.class.getName()+" [options] <fileToAnalize> <outputFile>" +
						"\nOptions can be:" +
						"\n\t-actionElements <list>:\tlist of comma separated columns number that should be used to distinguish different actions" +
						"\n\t-separator <separatorChar>:\tcharacter used in <fileToAnalyze> to separate columns" +
						"\n\t-actionNameLimit <value>:\tmaximum length of an action name, in case it is greather than this the program outputs the hash of the action name instead of the full name." +
						"\n\t-clusterSupport <value>:\tthe minimum number of values in common that cluster elements must share" +
						"\n\t-intersectionThreshold <value>:\tthe percentage of elements that two parameter must share to be part of the same cluster. Must be between 0 and 1. It is calculated considering the smallest of the two.");
		
		
	}
	
	private static void printIntersections(BufferedWriter w, List<ParametersIntersection> intersections) throws IOException{
		w.write("\nRules intersections with value > "+intersections);
		for(ParametersIntersection intersection : intersections){
			w.write("\n"+intersection.getRuleParameter1()+" and "+intersection.getRuleParameter2()+" = "+intersection.getIntersectionSize());
		}
	}
	


	
	
	
	public static void printClusters(BufferedWriter w, List<Cluster<RuleParameter>> clusters,File file,RTableExporter tableExporter, int trace, TraceData traceData) throws IOException{
		w.flush();
		w.write("\nClusters for trace "+trace+" : ");
		w.write("\nName:\telements\tvalues\tdistAvg\tdistStdDev\tdistMedian");
		int i=0;
		for ( Cluster<RuleParameter> c : clusters ){
			++i;
			w.write(i+":\t");
			for ( RuleParameter el : c.getElements() ){
				w.write(""+el);
				w.write(" ");
			}
			
			
			//Calculate the values that the parameter can assume
			Set<String> values = new HashSet<String>();
			for ( RuleParameter r : c.getElements()){
				RuleStatistics rs = traceData.getRulesStatistics().get(r.getRuleName());
				Set<String> parvalues;
				try {
					parvalues = rs.getParameterValues(r.getParameterPos());
					values.addAll(parvalues);
				} catch (RuleStatisticsException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
			
			
			ClusterParameterAnalyzer ca = new ClusterParameterAnalyzer(tableExporter,c);
			ArrayList<Integer> tracesToAnalyze = new ArrayList<Integer>(1);
			tracesToAnalyze.add(trace);
			
			ca.setInterestingTraces(tracesToAnalyze);
			try {
				ParameterAnalysisResult res = ca.analyze(file);

				w.write(values.size());
				w.write("\t");
				
					w.write(""+res.getParametersDistanceAvgMean(trace));
				
				w.write("\t");
				
					res.getParametersDistanceMedianMean(trace);
					w.write(""+res.getParametersDistanceStdDevMean(trace));
				
				w.write("\t");
				
					w.write(""+res.getParametersDistanceMedianMean(trace));
				
				w.write("\t");

				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			

			
			w.write("\n");
		}
	}
	
	
	private static FiniteStateAutomaton[] testPreprocessingRules(Cluster<RuleParameter> c,
			File tracefile) {
		
		
		
		//GO
		HashMap<String,ValueTransformer> trasformers = new HashMap<String, ValueTransformer>();
		
		ValueTransformerSame same = new ValueTransformerSame();
		ValueTransformerGlobal go = new ValueTransformerGlobal("_");
		ValueTransformerRelativeToInstantiation ri = new ValueTransformerRelativeToInstantiation("_");
		ValueTransformerRelativeToAccess ra = new ValueTransformerRelativeToAccess("_");
		
		trasformers.put("SAME", same );
		trasformers.put("GO", go );
		trasformers.put("RI", ri );
		trasformers.put("RA", ra);
		
		FiniteStateAutomaton fsaGO = testProcessingRule(c,same,go, tracefile);
		FiniteStateAutomaton fsaRi = testProcessingRule(c,same,ri, tracefile);
		FiniteStateAutomaton fsaRa = testProcessingRule(c,same,ra, tracefile);
		
		return new FiniteStateAutomaton[]{fsaGO,fsaRi,fsaRa};
	}
		private static FiniteStateAutomaton testProcessingRule(Cluster<RuleParameter> c,
				ValueTransformerSame same, ValueTransformer toTest, File traceFile) {
			File tmpDir = new File("/tmp/analysis");
			tmpDir.mkdirs();
		List<ParserRule> rules = new ArrayList<ParserRule>();
		final int componentColumn = 0;
		final int actionColumn = 1;
		
		final HashSet<String> ruleNames = new HashSet<String>();
		
		for ( RuleParameter rp : c.getElements() ){
			ruleNames.add(rp.getRuleName());
			String[] ruleName = rp.getRuleName().split("_");
			String component = ruleName[0];
			String action = ruleName[1];
			ParserRule comp = new ParserRule(componentColumn,actionColumn,component,action);
			comp.setValueTrasformer(componentColumn, same);
			comp.setValueTrasformer(actionColumn, same);
			comp.setValueTrasformer(rp.getParameterPos()+2, toTest);
			rules.add(comp);
		}
		
		
		
		
		TraceFilter tf = new TraceFilter(){
			
			public boolean accept(String[] cols){
				if ( cols.length == 0 ){
					return false;
				}
				
				if ( cols[0].equals("|") )
					return true;
				
				String ruleName = cols[componentColumn]+"_"+cols[actionColumn];
				
				return ruleNames.contains(ruleName);
			}
		};
		
		TraceManagerProvider traceManagerProvider = new TraceAnalyzerApplication.UnifiedTraceManagerProvider(false);
		
		ComponentTraceFileMaker ctfm = new ComponentTraceFileMaker(traceManagerProvider,rules, ",", componentColumn, actionColumn, tmpDir, tf);
		
		
		KBehaviorEngine engine = new KBehaviorEngine(  );
		
		
		
		try {
			Collection<ComponentTracesFile> splittedTraceFiles = ctfm.processInputTrace( traceFile, "_", ")" );
			for ( ComponentTracesFile componentTraceFile : splittedTraceFiles ){
				FiniteStateAutomaton fsa = engine.inferFSAfromFile(componentTraceFile.getFile().getAbsolutePath());
				return fsa;
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ComponentFSABuilderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TraceAnalyzerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}

		
		


	public static void printClusters(BufferedWriter w, Collection<Cluster<RuleParameter>> clusters,File file,RTableExporter tableExporter, ArrayList<TraceData> tracesData, boolean testPreprocessingRules, SlctEventsParser g) throws IOException{
		w.write("\nClusters : ");
		w.write("\nName:" +
				"\tvaluesAvg\t" +
				"\tsharing" +
				"\tvaluesOccurrenciesAvg\t" +
				"\tparDistAvg\t" +
				"\tparDistStdDev\t" +
				"\tparDistMedian\t"+
				"\teventDistAvg\t" +
				"\teventDistStdDev\t" +
				"\teventDistMedian\t" +
				"\tvalues/valuesOcc" +
				"\tvalues(valuesOcc*parDistMedian)"
		);
		int i=0;
		
		List<SlctPattern> slctPatterns = g.getRules();
		
		HashMap<String,Pattern> rulesMap = new HashMap<String, Pattern>();
		
		for ( SlctPattern slctPattern : slctPatterns ){
			rulesMap.put(slctPattern.getId(), slctPattern.getPattern());
		}
		
		for ( Cluster<RuleParameter> c : clusters ){
			++i;
			w.write("\n");
			w.write(i+":\t");
			
			w.write(c.getSignature());
			
			w.write("\t[");
			if ( rulesMap != null ){ 
				for ( RuleParameter p : c.getElements() ){
					String ruleSignature = p.getRuleName();
					String ruleName = tableExporter.getSignatureElements(ruleSignature)[1];
					Pattern pattern = rulesMap.get(ruleName);
					w.write(pattern.pattern());
					w.write(" , ");
				}
			}
			w.write("]");
			
			ClusterParameterAnalyzer ca = new ClusterParameterAnalyzer(tableExporter,c);
			try {
				ParameterAnalysisResult res = ca.analyze(file);
				double distancesMedianMean = -1;
				double valuesOccurrenciesMean = -1;
				double valuesMean = -1;
				
				//Calculate the values that the parameter can assume
				double[] totalValues = new double[tracesData.size()];
				int trace = 0;
				Set<String> sharedValues = null;
				for ( TraceData traceData : tracesData ){
					Set<String> values = new HashSet<String>();
					for ( RuleParameter r : c.getElements()){
						RuleStatistics rs = traceData.getRulesStatistics().get(r.getRuleName());
						Set<String> parvalues;
						try {
							parvalues = rs.getParameterValues(r.getParameterPos());
							values.addAll(parvalues);
						} catch (RuleStatisticsException e) {
							
						}
						
					}
					
					if ( trace == 0 ){
						sharedValues = new HashSet<String>();
						sharedValues.addAll(values);
					} else {
						sharedValues.retainAll(values);
					}
					
					totalValues[trace++] = values.size();
				}
				
				w.write("\t");
				valuesMean = MathUtil.getMean(totalValues); 
				w.write(valuesMean+"\t"+MathUtil.getStdDev(totalValues));
				w.write("\t");
				w.write(""+(double)sharedValues.size()/MathUtil.getMean(totalValues));
				w.write("\t");
				
					valuesOccurrenciesMean = res.getValuesOccurrenciesMeanMean(); 
					w.write(valuesOccurrenciesMean+"\t"+res.getValuesOccurrenciesMeanStdDev());
				
				w.write("\t");
				
					w.write(res.getGlobalParametersDistanceAvgMean()+"\t"+res.getGlobalParametersDistanceAvgStdDev());
				
				w.write("\t");
				
					w.write(res.getGlobalParametersDistanceStdDevMean()+"\t"+res.getGlobalParametersDistanceStdDevStdDev());
				
				w.write("\t");
				
					distancesMedianMean = res.getGlobalParametersDistanceMedianMean();
					w.write(distancesMedianMean+"\t"+res.getGlobalParametersDistanceMedianStdDev());
				
				w.write("\t");
				w.write(res.getEventDistancesMeanMean()+"\t"+res.getEventDistancesMeanStdDev());
				w.write("\t");
				w.write(res.getEventDistancesStdDevMean()+"\t"+res.getEventDistancesStdDevStdDev());
				w.write("\t");
				w.write(res.getEventDistancesMedianMean()+"\t"+res.getEventDistancesMedianStdDev());
				w.write("\t");

				w.write(""+valuesMean/valuesOccurrenciesMean);
				w.write("\t");
				
				w.write(""+valuesMean/(valuesOccurrenciesMean*distancesMedianMean));
				w.write("\t");
				
				if ( testPreprocessingRules ){
					FiniteStateAutomaton[] fsas = testPreprocessingRules(c, file);
					for ( FiniteStateAutomaton fsa : fsas ){
						w.write( ""+(double)fsa.getTransitions().length / (double)fsa.getStates().length);
						w.write("\t");
					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//w.write("\n");
			w.flush();
		}
	}



	private static void printAllIntersections(PrintWriter w,Collection<RuleStatistics> stats) {
		w.write("\nRules intersections");
		
		for ( RuleStatistics stat : stats ){
			w.write("RuleName\t");
			for ( RuleStatistics statInt : stats ){
				for( int i = 0; i  < stat.getParametersNumber(); ++i ){
					for( int j = 0; j  < statInt.getParametersNumber(); ++j ){
						w.write(stat.getRuleName()+"_"+i+"-"+stat.getRuleName()+"_"+j);
						w.write("\t");
					}	
				}
				
			}
			w.write("\n");
		}
		
		for ( RuleStatistics stat : stats ){
			w.write(stat.getRuleName()+"\t");
			for ( RuleStatistics statInt : stats ){
				for( int i = 0; i  < stat.getParametersNumber(); ++i ){
					for( int j = 0; j  < statInt.getParametersNumber(); ++j ){
						w.write(RuleStatisticsUtils.getIntersections(stat,i,statInt,j).size());
						w.write("\t");
					}	
				}
				
			}
			w.write("\n");
		}

	}

	public AnalysisResult analyze(File file) throws IOException {
		return analyze(file,null);
	}

	public AnalysisResult analyze(File file,Set<String> signaturesExpressions) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(file));
		boolean newTrace = false;
		try{

			AnalysisResult result = new AnalysisResult(false);
			//result.setDontSeparateTraces(dontSeparateTraces);
			String line;
			
			int linesCount = 0;
			while(( line = reader.readLine())!=null){
				linesCount++;
				
				if( maxLinesToRead > 0 ){
					if ( linesCount > maxLinesToRead ){
						break;
					}
				}
				
				if ( newTrace ){
					result.newTrace();
					newTrace = false;
				}
				
				if ( line.length() == 0){
					continue;
				}
				
				if ( line.equals("|") ){
					newTrace=true;
					continue;
				}
				
				
				
				result.newLine();
				
				ArrayList<String> parameters = exporter.getParameters(line);
				String signature = exporter.getRuleSignature(line);
				
				boolean add = false;
				if ( signaturesExpressions != null ){
					for ( String expression : signaturesExpressions ){
						if ( signature.matches(expression) ){
							add=true;			
						} 
					}
				} else {
					add=true;
				}
				
				if ( add == true ){
					result.addStat(signature, parameters);
				}
				
			}

			
			
			return result;
		} finally {
			if ( reader != null ){
				reader.close();
			}
		}
	}

	public ArrayList<String> getParameters(String line) {
		return exporter.getParameters(line);
	}

	public String getSeparator() {
		return exporter.getSeparator();
	}

	public void setSeparator(String separator) {
		exporter.setSeparator(separator);
	}

	public void setSignatureElements(int[] signatureElements) {
		exporter.setSignatureElements(signatureElements);
	}

	public int getHashThreshold() {
		return exporter.getHashThreshold();
	}

	public void setHashThreshold(int value) {
		exporter.setHashThreshold(value);
	}

	public RTableExporter getExporter() {
		return exporter;
	}

	public void setDontSeparateTraces(boolean value) {
		this.dontSeparateTraces = value;
	}


	public Set<String> retrieveComponentsNames(File file) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(file));
		
		HashSet<String> components = new HashSet<String>();

		String line;

		int linesCount = 0;
		try {
			while(( line = reader.readLine())!=null){
				linesCount++;

				if( maxLinesToRead > 0 ){
					if ( linesCount > maxLinesToRead ){
						break;
					}
				}



				if ( line.length() == 0){
					continue;
				}

				if ( line.equals("|") ){
					
					continue;
				}





				ArrayList<String> parameters = exporter.getParameters(line);

				components.add(exporter.getRuleSignaturePrefix(line));

			}
		} finally {
			// TODO Auto-generated catch block
			reader.close();
		}



		return components;

	}

}
