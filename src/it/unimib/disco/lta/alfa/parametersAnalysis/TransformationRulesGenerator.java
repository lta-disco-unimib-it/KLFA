package it.unimib.disco.lta.alfa.parametersAnalysis;

import it.unimib.disco.lta.alfa.csv.CsvParsersFactory;
import it.unimib.disco.lta.alfa.csv.CsvWriter;
import it.unimib.disco.lta.alfa.eventsDetection.AutomatedEventTypesDetector;
import it.unimib.disco.lta.alfa.eventsDetection.slct.SlctEventsParser;
import it.unimib.disco.lta.alfa.logging.Logger;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;



/**
 * This class is an entry point for the  generation of the rules for the component that generates the final trace for kbehavior
 * 
 * @author Fabrizio Pastore fabrizio.pastore AT gmail.com
 *
 */
public class TransformationRulesGenerator {

	private File csvFile;
	private SlctEventsParser eventsDetector = new SlctEventsParser();
	private boolean testPreprocessingRules = false;
	private ArrayList<TraceData> tracesData;
	private ParametersAnalyzer analyzer = new ParametersAnalyzer();
	private Set<Cluster<RuleParameter>> allClusters;
	private List<ClusterStatistics> allClustersStats;
	private TracePreprocessorConfiguration suggestedConfigurations;
	private double intersectionThreshold = 0.85;
	private int clustersSupport = 5;
	private AnalysisResult parametersAnalysisResult;
	private boolean processed;
	private TracePreprocessorConfigurationMaker tpcm = new TracePreprocessorConfigurationMaker(  );
	private TracePreprocessorConfiguration allConfigurations;
	private boolean dontGroup = false;
	private String separator = ",";
	private String traceSeparator="|";
	private boolean analyzeComponentsSeparately = false;
	private int linesToAnalyzePerCluster = -1;
	
	public int getLinesToAnalyzePerCluster() {
		return linesToAnalyzePerCluster;
	}

	public void setLinesToAnalyzePerCluster(int linesToAnalyzePerCluster) {
		this.linesToAnalyzePerCluster = linesToAnalyzePerCluster;
	}

	public TransformationRulesGenerator(File csvFile){
		this.csvFile = csvFile;
	}
	
	public void setMaxLinesToRead(int limit){
		analyzer.setMaxLinesToRead(limit);
	}
	
	public void setDontSeparateTraces( boolean value ){
		analyzer.setDontSeparateTraces(value);
	}
	
	/**
	 * This method does all the work, it is called when one of the extracted results is needed.
	 * 
	 * @throws TransformationRulesGeneratorException
	 */
	private void process() throws TransformationRulesGeneratorException {
		if ( processed ){
			return;
		}
		
		
		try {
			
			if ( analyzeComponentsSeparately ){
				analyzeSingleComponents();
			} else {
				analyzeWholeTrace();
			}
		
			
			
			
		} catch (IOException e) {
			throw new TransformationRulesGeneratorException(e);
		}
		
		processed = true;
	}

	/**
	 * Analyze the csv file generating clusters that relate only parameters that belong to a same component
	 *  
	 * @throws IOException
	 */
	private void analyzeSingleComponents() throws IOException{
		Set<String> componentExpressions = extractComponentExpressions( csvFile );
		allClusters = new HashSet<Cluster<RuleParameter>>();
		allClustersStats = new ArrayList<ClusterStatistics>();
		suggestedConfigurations = new TracePreprocessorConfiguration();
		
		for ( String componentExpression : componentExpressions ){
			System.out.println("Analyzing component: "+componentExpression);
			Set<String> expressions = new HashSet<String>();
			expressions.add(componentExpression);
			parametersAnalysisResult = analyzer.analyze(csvFile,expressions);
			
			ArrayList<TraceData> allTracesData = parametersAnalysisResult.getTracesDatas();
			ArrayList<TraceData> tracesData = new ArrayList<TraceData>();
			
			for ( TraceData traceData : allTracesData ){
				if ( traceData.getLineCount() != 0){
					tracesData.add(traceData);
				}
			}
			
			ParametersClustersGenerator pcg = new ParametersClustersGenerator(tracesData, intersectionThreshold, clustersSupport);
			pcg.setDontGroup(dontGroup);
			
			Set<Cluster<RuleParameter>> clusters = pcg.getClusters();
			//allClusters.addAll( clusters );

			//generateRules();
			
			System.out.println("Lines to analyze "+linesToAnalyzePerCluster);
			ClusterStatisticsGenerator csg = new ClusterStatisticsGenerator();
			csg.setLinesToAnalyzePerCluster(linesToAnalyzePerCluster);
			allClustersStats = csg.calculateClustersStatistics(clusters, csvFile, analyzer.getExporter(), tracesData, eventsDetector.getRules());
			suggestedConfigurations.mergeConfiguration( tpcm.getSuggestedConfigurations(  allClustersStats, analyzer.getExporter() ) );
			
			System.out.println("Generated "+clusters.size()+" clusters.");
		}
	}
	
	/**
	 * Run an analysis over the whole file.
	 * 
	 * @throws IOException
	 */
	private void analyzeWholeTrace() throws IOException{
		parametersAnalysisResult = analyzer.analyze(csvFile,null);
		
		tracesData = parametersAnalysisResult.getTracesDatas();
		
					
		
		ParametersClustersGenerator pcg = new ParametersClustersGenerator(tracesData, intersectionThreshold,clustersSupport);
		pcg.setDontGroup(dontGroup);
		allClusters = pcg.getAllClusters();

		//generateRules();
		
		ClusterStatisticsGenerator csg = new ClusterStatisticsGenerator();
		allClustersStats = csg.calculateClustersStatistics(allClusters, csvFile, analyzer.getExporter(), tracesData, eventsDetector.getRules());
		
		
		suggestedConfigurations = tpcm.getSuggestedConfigurations(  allClustersStats, analyzer.getExporter() );
	}
	

	/**
	 * This method retrieve the names of the components from the csv log and generates regular expressions that match 
	 * these names.
	 *  
	 * @param csvFile
	 * @return
	 * @throws IOException
	 */
	private Set<String> extractComponentExpressions(File csvFile) throws IOException {
		Set<String> names = new HashSet<String>();
		
		for ( String name : this.analyzer.retrieveComponentsNames(csvFile) ){
			names.add(name.replace(".", "\\.")
					.replace("(", "\\(")
					.replace(")", "\\)")
					.concat(".*"));	
		}
		return names;
	}

	private void generateRules() throws IOException {
		BufferedReader r = new BufferedReader (  new FileReader(csvFile) );
		String line;
		
		ClusterTransformationRulesAnalyzer result = new ClusterTransformationRulesAnalyzer();
		
		while ( null != ( line = r.readLine() ) ){
			for ( Cluster<RuleParameter> cluster : allClusters ){
				 
				
				
				
				
				
				Logger.finest(ClusterParameterAnalyzer.class.getName()+" new line");
				
				
				
				Logger.finest("Analyzing line");
				
				if ( isTraceSeparator (line) ){
					continue;
				}
				
				RTableExporter exporter = analyzer.getExporter();
				String signature = exporter.getRuleSignature(line);
				
				
				
				for ( RuleParameter ruleParameter : cluster.getElements() ){
					if ( signature.equals(ruleParameter.getRuleName()) ){
						Logger.finest(ClusterParameterAnalyzer.class.getName()+" adding parameter ");
						ArrayList<String> parameters = exporter.getParameters(line);
						ArrayList<String> par = new ArrayList<String>(1);
						if ( parameters.size() > ruleParameter.getParameterPos() ){
							par.add(parameters.get(ruleParameter.getParameterPos()));
						}
						
						result.addParameters(cluster,par);
						break;
					}
				}
			}
		}
	}

	private boolean isTraceSeparator(String line) {
		return "|".equals(line);
	}

	private static void printUsage() {
		System.out.println("This programs reads a kLFA CSV file and generates the optimal preprocessing" +
				" configuration files for data transformation." +
				"\n\nUsage: "+TransformationRulesGenerator.class.getName()+" [OPTIONS] kLFAFileToAnalyze.csv" +
						"\n\nOptions:" +
						
						"\n\t-actionNameLimit <value>: if actions are longer than <value> report their hash on the analysis file. <value> must be an int value" +
						"\n\t-analysisStatisticsFile <fileName> : save statistical results about the parameters to file <fileName>, " +
						"Default value is analysisStatistics.csv" +
						"\n\t-clusterIntersectionThreshold <value>: do not cluster together two distinct parameters if they share less than <value> percent of symbols. <value> must be a double between 0 and 1. Default is 0.7 ." +
						"\n\t-clusterSupport <value> : do not cluster together two distinct parameters if they have less than <value> symbols in common. " +
						"Default is 5." +
						"\n\t-dontGroup : do not group parameters in data-clusters" +
						"\n\t-help : print this message" +
						"\n\t-help : print this message" +
						"\n\t-maxLinesToRead <limit> : max number of lines to consider when performing the analysis"+
						
						"\n\t-patterns <fileName>: load regex patterns derived by "+AutomatedEventTypesDetector.class.getCanonicalName()+" from file <fileName>." +
						"\n\t-preprocessingRulesFile <fileName> : save preprocessing configuration to file <fileName>." +
						"Default is preprocessingRules.txt" +
						"\n\t-separator <columnSeparator> : csv columns are separated by the string <columnSeparator>. Default is , " +
						"\n\t-signatureElements <list>: indicate what columns of the csv file correspond to component and action" +
						" and do not be analyzed as parameters. <list> is a comma separated list of numbers. Default is 0,1." +
						"\n\t-slctPatterns <fileName>: load regex patterns derived by slct from the given file." +
						"\n\t-transformersConfigFile <fileName> : save transformers configuration to file <fileName>." +
						"Default is transformesConfig.txt" 
		
		);
		
		
						
	}
	
	
	public static void main(String args[]) throws IOException{
		
		
		
		if ( args.length < 1 ){
			printUsage();
			System.exit(-1);
		}
		
		File csvFileToAnalyze = new File(args[args.length-1]);
		
		TransformationRulesGenerator trg = new TransformationRulesGenerator(csvFileToAnalyze);
		
		
		
		File transformers = new File("transformersConfig.txt");
		File preprocessingRules = new File("preprocessingRules.txt");
		File clustersAnalysisFile = new File("analysisStatistics.csv");
		
		boolean patternsLoaded = false;
		for(int i = 0; i< args.length-1;++i){
			if ( args[i].equals("-testPreprocessingRules") ){
				trg.setTestPreprocessingRules(true);
			} else if ( args[i].equals("-help") || args[i].equals("-h") ){
				printUsage();
				System.exit(-1);
			} else if ( args[i].equals("-signatureElements") ){
				String[] sigElements = args[++i].split(",");
				int[] signatureElements = new int[sigElements.length];
				
				for( int j = 0; j < sigElements.length; ++j ){
					signatureElements[j] = Integer.valueOf(sigElements[j]);
				}
				trg.setSignatureElements(signatureElements);
			} else if ( args[i].equals("-dontGroup") ){
				trg.setDontGroup(true);
			} else if ( args[i].equals("-dontSeparateTraces") ){
				trg.setDontSeparateTraces(true);
			} else if ( args[i].equals("-analyzeComponentsSeparately") ){
				trg.setAnalyzeComponentsSeparately(true);
			} else if ( args[i].equals("-separator") ){
				trg.setSeparator(args[++i]);
			} else if ( args[i].equals("-analysisStatisticsFile") ){
				clustersAnalysisFile = new File(args[++i]);
			} else if ( args[i].equals("-transformersConfigFile") ){
				transformers = new File(args[++i]);
			} else if ( args[i].equals("-preprocessingRulesFile") ){
				preprocessingRules = new File(args[++i]);
			} else if ( args[i].equals("-maxLinesToAnalyzePerCluster") ){
				trg.setLinesToAnalyzePerCluster( Integer.valueOf(args[++i]).intValue() ); 
			} else if ( args[i].equals("-slctPatterns") ||  args[i].equals("-rules")){
				patternsLoaded = true;
				trg.loadSlctRules(new File(args[++i]));
			} else if ( args[i].equals("-patterns") ){
				patternsLoaded = true;
				Properties p = new Properties();
				FileInputStream s = new FileInputStream(new File(args[++i]));
				p.load(s);
				trg.loadRules(p);
			} else if ( args[i].equals("-actionNameLimit") ){
				trg.setHashThreshold(Integer.valueOf(args[++i]));
			} else if ( args[i].equals("-clusterSupport") ){
				trg.setClustersSupport( Integer.valueOf(args[++i]).intValue() );
			} else if ( args[i].equals("-clusterIntersectionThreshold") ){
				trg.setIntersectionThreshold( Double.valueOf(args[++i]) );
			} else if ( args[i].equals("-maxLinesToRead") ){
				trg.setMaxLinesToRead( Integer.valueOf(args[++i]) );
			} else {
				System.err.println("Unrecognized option "+args[i]);
				printUsage();
				System.exit(-1);
			}
		}
		
//		if ( ! patternsLoaded ){
//			System.err.println("You have to specify the slct rules to load with -slctPatterns <file> or -patterns <file>");
//			System.exit(-1);
//		}
		
		if ( transformers.exists() ){
			System.err.println(transformers.getAbsolutePath()+ " exists aborting");
			System.exit(-1);
		}
		
		if ( preprocessingRules.exists() ){
			System.err.println(preprocessingRules.getAbsolutePath()+ " exists aborting");
			System.exit(-1);
		}
		
		if ( clustersAnalysisFile.exists() ){
			System.err.println(clustersAnalysisFile.getAbsolutePath()+ " exists aborting");
			System.exit(-1);
		}
		
		try {
			
			System.out.println("Exporting preprocessing rules to "+preprocessingRules.getAbsolutePath());
			trg.exportSuggestedPreprocessingRulesConfigurationToFile(preprocessingRules);
			
			System.out.println("Exporting transformers to "+transformers.getAbsolutePath());
			trg.exportSuggestedTranformersConfigurationToFile(transformers);
			
			
			System.out.println("Exporting clustersAnalysisFile rules to "+clustersAnalysisFile.getAbsolutePath());
			trg.exportAllClustersStatisticsToFile(clustersAnalysisFile);
			
		} catch (TransformationRulesGeneratorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}



	private void setAnalyzeComponentsSeparately(boolean b) {
		this.analyzeComponentsSeparately = b;
	}

	public boolean isTestPreprocessingRules() {
		return testPreprocessingRules;
	}

	public void setTestPreprocessingRules(boolean testPreprocessingRules) {
		this.testPreprocessingRules = testPreprocessingRules;
	}


	public int getHashThreshold() {
		return analyzer.getHashThreshold();
	}

	public String getSeparator() {
		return analyzer.getSeparator();
	}

	public void setHashThreshold(int value) {
		analyzer.setHashThreshold(value);
	}

	public void setSeparator(String separator) {
		analyzer.setSeparator(separator);
	}

	public void setSignatureElements(int[] signatureElements) {
		analyzer.setSignatureElements(signatureElements);
	}

	public File getCsvFile() {
		return csvFile;
	}

	public ArrayList<TraceData> getTracesData() {
		return tracesData;
	}

	public Set<Cluster<RuleParameter>> getAllClusters() throws TransformationRulesGeneratorException {
		process();
		return allClusters;
	}

	public List<ClusterStatistics> getClustersStats() throws TransformationRulesGeneratorException {
		process();
		return allClustersStats;
	}

	public double getIntersectionThreshold() {
		return intersectionThreshold;
	}

	public int getClustersSupport() {
		return clustersSupport;
	}

	public AnalysisResult getParametersAnalysisResult() throws TransformationRulesGeneratorException {
		process();
		return parametersAnalysisResult;
	}

	public void setIntersectionThreshold(double intersectionThreshold) {
		this.intersectionThreshold = intersectionThreshold;
	}

	public void setClustersSupport(int clustersSupport) {
		this.clustersSupport = clustersSupport;
	}

	public void loadRules(Properties rules) {
		eventsDetector.loadRules(rules);
	}

	public void loadSlctRules(File slctRules) throws IOException {
		eventsDetector.loadSlctRegex(slctRules);
	}
	
	
	public void exportTracesDataToFile(File file) throws IOException, TransformationRulesGeneratorException{
		process();
		BufferedWriter w = new BufferedWriter( new FileWriter (file) );
		ParametersAnalyzer.printTraceData(w,tracesData);
		w.close();
	}
	
	public void exportAllClustersStatisticsToFile(File file) throws IOException, TransformationRulesGeneratorException{
		process();
		BufferedWriter w = new BufferedWriter( new FileWriter (file) );
		
		ParametersAnalyzer.printStatistics( w, allClustersStats, analyzer.getExporter(), eventsDetector.getRules(), suggestedConfigurations.getTransformersMap() );
		w.close();
	}
	
	public void exportSuggestedTranformersConfigurationToFile(File file) throws IOException, TransformationRulesGeneratorException{
		process();
		BufferedWriter w = new BufferedWriter( new FileWriter (file) );
		w.write(suggestedConfigurations.getTransformersFileContent());
		w.close();
	}
	public void exportClusterStatsToFile(File file) throws IOException, TransformationRulesGeneratorException{
		process();
		BufferedWriter w = new BufferedWriter( new FileWriter (file) );
		ParametersAnalyzer.printStatistics(w, allClustersStats, analyzer.getExporter(), eventsDetector.getRules(), suggestedConfigurations.getTransformersMap());
		w.close();
	}
	
	public void exportSuggestedPreprocessingRulesConfigurationToFile(File file) throws IOException, TransformationRulesGeneratorException{
		process();
		CsvWriter csvWriter = CsvParsersFactory.createNewCsvWriter();
		FileWriter writer = new FileWriter (file);
		csvWriter.writeCsvLines(writer, suggestedConfigurations.getPreprocessingRules() );
		writer.close();
	}


	public TracePreprocessorConfiguration getSuggestedConfigurations() throws TransformationRulesGeneratorException {
		process();
		return suggestedConfigurations;
	}
	
	public TracePreprocessorConfiguration getAllConfigurations() throws TransformationRulesGeneratorException {
		process();
		if ( allConfigurations == null ){
			allConfigurations = tpcm.getCompleteConfigurations(allClustersStats, analyzer.getExporter());
		}
		return allConfigurations;
	}

	public void setCsvFile(File csvFile) {
		this.csvFile = csvFile;
	}

	public boolean isDontGroup() {
		return dontGroup;
	}

	public void setDontGroup(boolean dontGroup) {
		this.dontGroup = dontGroup;
	}
	
	
}

