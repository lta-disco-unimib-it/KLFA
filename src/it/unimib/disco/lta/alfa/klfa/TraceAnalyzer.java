package it.unimib.disco.lta.alfa.klfa;

import file.ParseException;
import grammarInference.Log.ConsoleLogger;
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
import java.util.Set;


import automata.State;
import automata.Transition;
import automata.fsa.FSATransition;
import automata.fsa.FiniteStateAutomaton;

import tools.fsa2xml.LazyFSALoader;
import tools.fsa2xml.LazyFSALoader.LazyFSALoaderException;
import tools.fsa2xml.codec.api.FSACodec;
import tools.fsa2xml.codec.factory.FSA2FileFactory;
import it.unimib.disco.lta.alfa.dataTransformation.TransformersFactory;
import it.unimib.disco.lta.alfa.dataTransformation.ValueTransformer;
import it.unimib.disco.lta.alfa.dataTransformation.parserRules.ParserFactoryException;
import it.unimib.disco.lta.alfa.dataTransformation.parserRules.ParserRule;
import it.unimib.disco.lta.alfa.dataTransformation.parserRules.ParserRulesFactory;
import it.unimib.disco.lta.alfa.inferenceEngines.KBehaviorEngine;
import it.unimib.disco.lta.alfa.inferenceEngines.KBehaviorEngineSearchNeighbourhood;
import it.unimib.disco.lta.alfa.klfa.traceFilters.TraceFilterAcceptAll;
import it.unimib.disco.lta.alfa.klfa.traceFilters.TraceFilterPositional;
import it.unimib.disco.lta.alfa.klfa.traceFilters.TraceFilterRule;
import it.unimib.disco.lta.alfa.klfa.traceFilters.TraceFilterRuleReject;
import it.unimib.disco.lta.alfa.klfa.utils.componentAnalysis.ComponentFSABuilderException;
import it.unimib.disco.lta.alfa.klfa.utils.componentAnalysis.ComponentTraceFileMaker;
import it.unimib.disco.lta.alfa.klfa.utils.componentAnalysis.ComponentTracesFile;
import it.unimib.disco.lta.alfa.parametersAnalysis.TraceFilter;
import it.unimib.disco.lta.alfa.tools.LogMapper;

public abstract class TraceAnalyzer {
	
	
	public boolean isMultiLineOutput() {
		return multiLineOutput;
	}

	public void setMultiLineOutput(boolean multiLine) {
		this.multiLineOutput = multiLine;
	}

	public enum MinimizationTypes{none,step,end};
	private int componentColumn = 0;
	private int eventColumn = 1;
	private List<ParserRule> parsingRules = new ArrayList<ParserRule>();
	
	//private static String prefix;
	private String columnSeparator=",";
	private File outputDir = new File("klfaOutput");
	private File inputDir = new File("klfaOutput");
	private List<TraceFilterRule> traceFilterRules = new ArrayList<TraceFilterRule>();
	private TraceManagerProvider traceManagerProvider;
	private String fsaFileFormat = "ser";
	private String  suffix = ")";	//We add this after each trace to not have non-determinism from events like THREAD_START and THEAD_STARTED
	
	//Kbehavior options
	int kBehaviorK = 2;
	private boolean cutoffSearch;
	private MinimizationTypes minimizationType = TraceAnalyzer.MinimizationTypes.end;
	int minimizationLimit = -1;
	
	public enum EngineTypes { KBehavior, KBehaviorWeightCount, KBehaviorSortNeighbourhood};
	private EngineTypes engineType = EngineTypes.KBehavior;
	private Integer kBehaviorMaxK = -1;
	private boolean multiLineOutput = true;
	private HashMap<String,FSACodec> formatCodecs = new HashMap<String, FSACodec>();
	private boolean ignoreGlobal = false;
	private boolean replaceSkippedElementsWithDash = false;
	private HashMap<String, ValueTransformer> valueTrasformers;
	


	{
		formatCodecs.put("ser", FSA2FileFactory.getFSASer());
		formatCodecs.put("jff", FSA2FileFactory.getFSAXml());
		formatCodecs.put("fsa", FSA2FileFactory.getFSABctXml());
	}
	
	public TraceAnalyzer( TraceManagerProvider traceManagerProvider ){
		this.traceManagerProvider = traceManagerProvider;
	}

	public List<ParserRule> getParsingRules() {
		return parsingRules;
	}

	public void setParsingRules(List<ParserRule> parsingRules) {
		this.parsingRules = parsingRules;
	}
	
	public HashMap<String, ValueTransformer> getValueTrasformers() {
		return valueTrasformers;
	}

	public void setValueTrasformers(
			HashMap<String, ValueTransformer> valueTrasformers) {
		this.valueTrasformers = valueTrasformers;
	}
	
	public boolean isReplaceSkippedElementsWithDash() {
		return replaceSkippedElementsWithDash;
	}

	/**
	 * Set to true if you want to replace the elements not matched by a preprocessing-rule with a dash.
	 *  
	 * @param replaceSkippeElementsWithDash
	 */
	public void setReplaceSkippedElementsWithDash(
			boolean replaceSkippeElementsWithDash) {
		this.replaceSkippedElementsWithDash = replaceSkippeElementsWithDash;
	}
	
	protected void addParserRule( ParserRule rule, boolean head ){
		int idx;
		if ( head ){
			idx = 0;
		} else {
			idx = parsingRules.size();
		}
		parsingRules.add(idx,rule);
	}


	/**
	 * Infer an FSA from the given input trace using the transformers as configured in transformerRuleFile and parserRuleFile
	 * 
	 * @param inputTrace
	 * @param traformerRuleFile
	 * @param parserRuleFile
	 * @return 
	 * @throws TraceAnalyzerException
	 */
	public List<KLFALogAnomaly> runTraining( File inputTrace, File traformerRuleFile, File parserRuleFile ) throws TraceAnalyzerException{
		return run(inputTrace, traformerRuleFile, parserRuleFile, false);
	}
	
	/**
	 * Compares the given trace with the models previously built and stored, using the transformers as configured in transformerRuleFile and parserRuleFile
	 * @param inputTrace
	 * @param traformerRuleFile
	 * @param parserRuleFile
	 * @return 
	 * @throws TraceAnalyzerException
	 */
	public List<KLFALogAnomaly> runChecking( File inputTrace, File traformerRuleFile, File parserRuleFile ) throws TraceAnalyzerException{
		return run(inputTrace, traformerRuleFile, parserRuleFile, true);
	}
	
	private List<KLFALogAnomaly> run( File inputTrace, File traformerRuleFile, File parserRuleFile, boolean extend ) throws TraceAnalyzerException{
		checkDirs(extend);

		try {
			valueTrasformers = TransformersFactory.createTrasformers(traformerRuleFile);


			parsingRules.addAll( ParserRulesFactory.createRules(componentColumn,eventColumn,parserRuleFile,valueTrasformers));


		} catch (IOException e) {
			throw new TraceAnalyzerException(e);
		} catch (ParserFactoryException e) {
			throw new TraceAnalyzerException(e);
		}
		
		return run(inputTrace, extend);
		
	}
	
	/**
	 * Infer a set of FSA from the given trace.
	 * 
	 * @param inputTrace
	 * @throws TraceAnalyzerException
	 */
	public void inferFSA( File inputTrace ) throws TraceAnalyzerException{
		run(inputTrace, false);
	}
	
	/**
	 * Return a list of anomalies identified by comparing the given input trace with the models.
	 * 
	 * @param inputTrace
	 * @return
	 * @throws TraceAnalyzerException
	 */
	public List<KLFALogAnomaly> identifyAnomalies( File inputTrace ) throws TraceAnalyzerException{
		return run(inputTrace, true);
	}

	private List<KLFALogAnomaly> run( File inputTrace, boolean extend ) throws TraceAnalyzerException{
		
		try {
			String prefix = "";
			if ( extend ){
				prefix = "checking_";
			}





			if ( replaceSkippedElementsWithDash ){
				for(  ParserRule rule : parsingRules ){
					rule.setReplaceSkippedElementsWithDash(true);
				}
			}
			
			
			

			TraceFilter filter;
			if ( traceFilterRules.size() > 0 ){
				
				filter = new TraceFilterPositional(traceFilterRules);
			} else {
				filter = new TraceFilterAcceptAll();
			}
			
			ComponentTraceFileMaker ctfm = new ComponentTraceFileMaker(traceManagerProvider,parsingRules, columnSeparator, componentColumn, eventColumn, outputDir, filter );
			Collection<ComponentTracesFile> splittedTraceFiles = ctfm.processInputTrace( inputTrace, prefix, suffix );


			List<KLFALogAnomaly> anomalies = performFSAExtensions(inputTrace,prefix,extend,splittedTraceFiles);

			if ( extend ){
				File violations = new File(outputDir ,"anomalies.csv");
				KLFALogAnomalyCsvExporter exporter = new KLFALogAnomalyCsvExporter();
				exporter.exportToCsv(violations, anomalies);
			}
			return anomalies;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ComponentFSABuilderException e) {
			e.printStackTrace();
		} catch (LazyFSALoaderException e) {
			throw new TraceAnalyzerException(e);
		}
		
		return null;
	}
		
		private List<KLFALogAnomaly> performFSAExtensions(File inputTrace,String prefix,boolean extend,Collection<ComponentTracesFile> splittedTraceFiles) throws IOException, LazyFSALoaderException{
			
			List<KLFALogAnomaly> anomalies = new ArrayList<KLFALogAnomaly>();
			
			for ( ComponentTracesFile traceFile : splittedTraceFiles ){

				if ( ignoreGlobal  && traceFile.getComponentName().equals("GLOBAL") ){
					continue;
				}
				
				if ( traceFilterRules.contains(traceFile.getComponentName() ) ){
					System.out.println("Skipping "+traceFile.getComponentName());
					continue;
				}

				System.out.println("Building model for component "+traceFile.getComponentName());

				KBehaviorEngine engine;

				int _kBehaviorMaxK;

				if ( kBehaviorMaxK > -1 ){
					_kBehaviorMaxK = kBehaviorMaxK;
				} else {
					_kBehaviorMaxK = kBehaviorK;
				}

				if  ( engineType == EngineTypes.KBehaviorSortNeighbourhood ){
					engine = new KBehaviorEngineSearchNeighbourhood( kBehaviorK, _kBehaviorMaxK, cutoffSearch, minimizationType.name(), new ConsoleLogger(grammarInference.Log.Logger.infoLevel) );
				}

				else {
					engine = new KBehaviorEngine( kBehaviorK, _kBehaviorMaxK, cutoffSearch, minimizationType.name(), new ConsoleLogger(grammarInference.Log.Logger.debuginfoLevel) );
				}

				if ( minimizationLimit > 0 ){
					engine.setMinimizationLimit(minimizationLimit);
				}


				FileLogger logger = new FileLogger(engine.getVerboseLevel(),getLogFileFullPath(prefix,traceFile,extend));
				engine.setLogger( logger );


				File file = traceFile.getFile();

				FiniteStateAutomaton fsa = null;


				if ( ! extend ){
					fsa = engine.inferFSAfromFile(file.getAbsolutePath());
					saveFSA(fsa, getTrainingFSAOutputFilePath(traceFile));
				}else {
					

					engine.setEnableMinimization(MinimizationTypes.none.name());

					engine.setRecordExtensions(true);

					File trainingFsaFile = new File ( getTrainingFSAFilePath(traceFile) );


					FiniteStateAutomaton trainingFSA;

					if ( trainingFsaFile.exists() ){
						trainingFSA = loadFSA(trainingFsaFile.getAbsolutePath());
						
						
						
						State initial = trainingFSA.getInitialState();
						for ( Transition ts : trainingFSA.getTransitionsFromState(initial) ){
							System.out.println("TS "+file.getAbsolutePath()+" "+ts.getDescription());
						}
						
						fsa = engine.inferFSAfromFile(file.getAbsolutePath(),trainingFSA);
						//reload the original FSA because we modified it
						trainingFSA = loadFSA(trainingFsaFile.getAbsolutePath());
					} else {
						KLFALogAnomaly anomaly = new KLFALogAnomaly();
						anomaly.setComponent(traceFile.getComponentName());
						anomaly.setAnomalyType(KLFALogAnomaly.AnomalyType.NewComponent);
						anomaly.setStateType(it.unimib.disco.lta.alfa.klfa.KLFALogAnomaly.StateType.New);
						anomalies.add(anomaly);
						continue;
					}

					saveFSA(fsa, getExtendedFSAFilePath(traceFile));


					LogMapper m = new LogMapper(inputTrace,traceFile.getTranslationFile());

					anomalies.addAll( KLFALogAnomalyUtil.createKlfaAnomalies(traceFile.getComponentName(),engine.getFSAExtensions(), m, trainingFSA, fsa) );

				}

			}
			
			return anomalies;

		
	}




	private FiniteStateAutomaton loadFSA(String path) throws IOException, LazyFSALoaderException {
		//return LazyFSALoader.loadFSA(path);
		FSACodec preferredCodec = getFSACodec();
		FiniteStateAutomaton fsa;
		try {
			fsa = preferredCodec.loadFSA(path);
		} catch ( ParseException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		fsa = LazyFSALoader.loadFSA(path);
		
		//BUG FIX FOR SER LOADER
		for ( Transition t : fsa.getTransitions() ){
			if ( t instanceof FSATransition ){
				FSATransition f = (FSATransition) t;
				if ( f.getDescription().equals("\u03BB") ){
					f.setLabel("");
				}
			}
		}
		
		return fsa;
	}

	private void saveFSA(FiniteStateAutomaton fsa,
			String file) throws FileNotFoundException, IOException {
		FSACodec codec = getFSACodec();
		codec.saveFSA(fsa, file);
	}

	private FSACodec getFSACodec() {
		return formatCodecs.get(fsaFileFormat);
	}

	/**
	 * Load the list of elements to be ignored from file.
	 * Each line of the file must contain the name of an element to be ignored.
	 * 
	 * @param elementsToIgnoreFile
	 * @throws IOException
	 */
	public void loadElelementsToIgnore(File elementsToIgnoreFile) throws IOException {
		
		BufferedReader r = new BufferedReader( new FileReader ( elementsToIgnoreFile) );
		String line;
		
		while( (line = r.readLine()) != null ){
			String[] cols = line.split(",");
			
			TraceFilterRule rule = new TraceFilterRuleReject();
			for ( int i=0; i < cols.length; ++i ){
				if ( cols[i].length() > 0 ){
					rule.putColumnRule( i, cols[i] );
				}
			}
			
			traceFilterRules.add(rule);
		}
		
		r.close();
	}



	private void checkDirs(boolean extend) {
		
		if ( extend ){
			if ( ! inputDir.exists() ){
				System.err.println("The input dir "+inputDir.getAbsolutePath()+" does not exists, we expect an inpput dir with training analysis results.");
				System.exit(-1);
			}
			if ( ! inputDir.equals(outputDir) ){
				checkOuputDir();
			}
			
		} else {
			checkOuputDir();
		}

	}

	private void checkOuputDir() {
		if ( outputDir.exists() ){
			System.err.println("The output dir "+outputDir.getAbsolutePath()+" exists, delete or rename it before running the program.");
			System.exit(-1);
		}

		if ( ! outputDir.mkdirs() ){
			System.err.println("Cannot create "+outputDir.getAbsolutePath()+" exiting");
			System.exit(-1);
		}
	}

	private String getLogFileFullPath(String prefix, ComponentTracesFile traceFile, boolean extend) {
		
		String logFile = traceFile.getComponentName().replace('"', '_')+".log";
		
		if ( extend){
			logFile = prefix+logFile;
		}
		
		return new File(outputDir,logFile).getAbsolutePath();
	}

	private String getExtendedFSAFilePath(ComponentTracesFile traceFile) {
		return new File(outputDir,getExtendedFSAFileName(traceFile,fsaFileFormat)).getAbsolutePath();
	}

	private String getTrainingFSAFilePath(ComponentTracesFile traceFile) {
		return new File(inputDir,getFSAFileName(traceFile,fsaFileFormat)).getAbsolutePath();
	}
	
	private String getTrainingFSAOutputFilePath(ComponentTracesFile traceFile) {
		return new File(outputDir, getFSAFileName(traceFile,fsaFileFormat)).getAbsolutePath();
	}


	private static String getFSAFileName(ComponentTracesFile traceFile,String format) {
		return traceFile.getComponentName().replace('"', '_')+"."+format;
	}
	
	private static String getExtendedFSAFileName(ComponentTracesFile traceFile,String format) {
		return "ext_"+traceFile.getComponentName().replace('"', '_')+"."+format;
	}

	public String getColumnSeparator() {
		return columnSeparator;
	}

	public void setColumnSeparator(String columnSeparator) {
		this.columnSeparator = columnSeparator;
	}

	public int getComponentColumn() {
		return componentColumn;
	}

	public void setComponentColumn(int componentColumn) {
		this.componentColumn = componentColumn;
	}

	public int getEventColumn() {
		return eventColumn;
	}

	public void setEventColumn(int eventColumn) {
		this.eventColumn = eventColumn;
	}

	public File getOutputDir() {
		return outputDir;
	}

	public void setOutputDir(File outDir) {
		this.outputDir = outDir;
	}

	public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	public int getKBehaviorK() {
		return kBehaviorK;
	}

	public void setKBehaviorK(int behaviorK) {
		kBehaviorK = behaviorK;
	}

	public boolean isCutoffSearch() {
		return cutoffSearch;
	}

	public void setCutoffSearch(boolean cutoffSearch) {
		this.cutoffSearch = cutoffSearch;
	}

	public int getMinimizationLimit() {
		return minimizationLimit;
	}

	public void setMinimizationLimit(int minimizationLimit) {
		this.minimizationLimit = minimizationLimit;
	}

	public File getInputDir() {
		return inputDir;
	}

	public void setInputDir(File inputDir) {
		this.inputDir = inputDir;
	}

	public MinimizationTypes getMinimizationType() {
		return minimizationType;
	}

	public void setMinimizationType(MinimizationTypes minimizationType) {
		this.minimizationType = minimizationType;
	}

	public EngineTypes getEngineType() {
		return engineType;
	}

	public void setEngineType(EngineTypes engineType) {
		this.engineType = engineType;
	}

	public void setKBehaviorMaxK(Integer behaviorMaxK) {
		this.kBehaviorMaxK = behaviorMaxK;
	}

	public String getFsaFileFormat() {
		return fsaFileFormat;
	}

	public void setFsaFileFormat(String fsaFileFormat) {
		if ( ! formatCodecs.containsKey(fsaFileFormat) ){
			throw new IllegalArgumentException("Unknown format : "+fsaFileFormat);
		}
		this.fsaFileFormat = fsaFileFormat;
	}
	
	public void setTraceFilterRules( List<TraceFilterRule> traceFilterRules ){
		this.traceFilterRules.clear();
		this.traceFilterRules.addAll(traceFilterRules);
	}
	
	public void addTraceFilterRules( List<TraceFilterRule> traceFilterRules ){
		this.traceFilterRules.addAll(traceFilterRules);
	}

	public void addTraceFilterRule( TraceFilterRule traceFilterRule ){
		traceFilterRules.add(traceFilterRule);
	}
	
	public List<TraceFilterRule> getTraceFilterRules(){
		return traceFilterRules;
	}

	public boolean isIgnoreGlobal() {
		return ignoreGlobal;
	}

	public void setIgnoreGlobal(boolean ignoreGlobal) {
		this.ignoreGlobal = ignoreGlobal;
	}
	
	
}
