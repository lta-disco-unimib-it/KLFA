package it.unimib.disco.lta.alfa.eventsDetection;

import it.unimib.disco.lta.alfa.eventsDetection.slct.GenericLogToComponentSlctPreprocessor;
import it.unimib.disco.lta.alfa.eventsDetection.slct.SlctEventsParser;
import it.unimib.disco.lta.alfa.eventsDetection.slct.SlctPattern;
import it.unimib.disco.lta.alfa.eventsDetection.slct.SlctRunnerException;
import it.unimib.disco.lta.alfa.eventsDetection.slct.GenericLogToComponentSlctPreprocessor.ComponentProcessingSequence;
import it.unimib.disco.lta.alfa.logging.Logger;
import it.unimib.disco.lta.alfa.utils.RegexUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.Map.Entry;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;



/**
 * This class generate a csv file in which events are detected by applying the slct clustering algorithm not to the  complete trace but only to 
 * the traces relative the distinct components.
 * To do this it splits the original trace per components and then apply slct on them to detect events. 
 * A unique trace is then reconstructed by parsing the original trace again and building the final preprocessed csv.  
 *  
 * @author Fabrizio Pastore fabrizio.pastore AT gmail.com
 *
 */
public class AutomatedEventTypesDetector implements EventTypesDetector, AutomatedEventTypesDetectorInterface {


	



	private int truncate = -1;
	private GenericLogToComponentSlctPreprocessor p = new GenericLogToComponentSlctPreprocessor();
	private File workingDir = new File(".");
	private List<SlctPattern> eventPatterns = new ArrayList<SlctPattern>();
	private File patternsDir;
	private boolean skipEventPatternsDetection = false;
	private double slctSupportPercentage = 0.05;
	private String slctExecutable = null;
	private HashMap<String, String> unmatchedLines = new HashMap<String, String>();
	
	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {




		String offset = null;
		ArrayList<File> inputs = new ArrayList<File>();
		
		
		
		
		File exportRulesFile = null;
		String componentsDefinitionFileName = "components.properties";
		AutomatedEventTypesDetector componentsCsvGenerator = new AutomatedEventTypesDetector();
		
		for( int i = 0; i < args.length - 1; ++i){
			if ( args[i].equals("-help") ){
				printUsage();
				System.exit(-1);
			} else if ( args[i].equals("-offset") ){
				offset=args[++i];
			} else if ( args[i].equals("-slctExecutablePath") ) {
				componentsCsvGenerator.setSlctExecutable(args[++i]);
			} else if ( args[i].equals("-slctExecutableFolder") ) {
				File slctFolder = new File ( args[++i] );
				String exec = getSlctExecutablePath(slctFolder);
				if ( exec == null ){
					System.out.println("Using JAVA slct");
				}
				componentsCsvGenerator.setSlctExecutable(exec);
			} else if ( args[i].equals("-offsetPost") ) {
				componentsCsvGenerator.setOffsetPost(Integer.valueOf(args[++i]).intValue());
			} else if ( args[i].equals("-componentsDefinitionFile") ) {
				componentsDefinitionFileName=args[++i];
			} else if ( args[i].equals("-componentExpression") ) {
				String expr = args[++i];
				System.out.println("Component expression: "+expr);
				componentsCsvGenerator.setComponentExpression(expr);
			} else if ( args[i].equals("-exportRules") ) {
				exportRulesFile = new File( args[++i] );
			} else if ( args[i].equals("-workingDir") ) {
				componentsCsvGenerator.setWorkingDir(new File(args[++i]));
			} else if ( args[i].equals("-loadComponents") ) {
				componentsCsvGenerator.loadComponentsDefinitions( new File( args[++i] ) );
			} else if ( args[i].equals("-loadEventPatterns") ) {
				componentsCsvGenerator.setSkipEventPatternsDetection(true);
			} else if ( args[i].equals("-patternsDir") ) {
				componentsCsvGenerator.setPatternsDir(new File(args[++i]));
			} else if ( args[i].equals("-dataExpression") ) {
				String expr = args[++i];
				System.out.println("Data expression: "+expr);
				componentsCsvGenerator.setDataExpression(expr);
			} else if ( args[i].equals("-separator") ) {
				componentsCsvGenerator.setSeparator(args[++i]);
			} else if ( args[i].equals("-slctSupportPercentage") ) {
				componentsCsvGenerator.setSlctSupportPercentage( Double.valueOf(args[++i]) );
			} else if ( args[i].equals("-numberSeparator") ) {	//set the string which is but betwee two numbers separated by white spaces
				componentsCsvGenerator.setNumbersSeparator(args[++i]);
			} else if ( args[i].equals("-replacement") ) {	
				componentsCsvGenerator.addReplacementRule(args[++i],args[++i]);
			} else if ( args[i].equals("-replacementRegex") ) {	
				componentsCsvGenerator.addReplacementRegexRule(args[++i],args[++i]);	
			} else if ( args[i].equals("-truncate") ) {
				componentsCsvGenerator.setTruncate(Integer.valueOf(args[++i]));
			} else if ( args[i].equals("-dontSplitComponents") ) {
				componentsCsvGenerator.setDontSplitComponents(true);
			} else {
				inputs.add(new File(args[i]));
			}

		}

		File finalTrace = new File( args[args.length-1]);
		
		if ( offset != null ){
			componentsCsvGenerator.setOffset(Integer.valueOf(offset));
		}
		
		try {
			componentsCsvGenerator.process(inputs,finalTrace);
			componentsCsvGenerator.exportComponentsDefinition(new File(componentsDefinitionFileName));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (EventTypesDetectorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if ( exportRulesFile != null ){
			try {
				exportEventPatterns( componentsCsvGenerator.getAllPatterns(), exportRulesFile );
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
	}
	
	
	
	private List<SlctPattern> getAllPatterns() {
		ArrayList<SlctPattern> result = new ArrayList<SlctPattern>();
		result.addAll(getEventPatterns());
		
		for ( Entry<String,String> entry : unmatchedLines.entrySet() ){
			String value = entry.getValue();
			int limit = Math.min(50,value.length() );
			
			String escapedString = RegexUtil.getEscapedString( value.substring(0,limit) );
			try {
				
				result.add( new SlctPattern(entry.getKey(), Pattern.compile( escapedString )) );
			} catch (PatternSyntaxException t ){
				System.err.println("Error processing string "+escapedString+" original "+value);
				throw t;
			}
			
		}
		
		return result;
	}





	private static String getSlctExecutablePath(File slctFolder) {
		try{
			String osArch = System.getProperty("os.arch").toLowerCase();
			String osName = System.getProperty("os.name").toLowerCase().replaceAll(" ", "");
			
			String osType = "";
			String extension = "";
			if ( osName.contains("windows") ){
				osType = "win";
				extension = ".exe";
			} else if ( osName.contains("osx") ){
				osType = "osx";
			} else if ( osName.contains("linux") ){
				osType = "linux";
			} else {	
				throw new RuntimeException("Unknown OS "+osType);
			}
			
			File executableOsDir = new File( slctFolder, osType);
			File execOsArch = new File(executableOsDir, osArch);
			
			File slctExec = new File(execOsArch, "slct"+extension);
			
			if ( ! slctExec.exists() ){
				return null;
				//throw new RuntimeException("Slct Executable undefined for your os "+slctExec.getAbsolutePath());
			}
			
			return slctExec.getAbsolutePath();
			//System.out.println("Operating system type =>"+ osName+ " "+osArch);
		}catch (Exception e){
			System.out.println("Exception caught ="+e.getMessage());
		}
		return "";
	}

	private static void printUsage() {
		System.out.println("This program reads log files and generates a klfa csv file in which events and components are separated. " +
				"\n\tThe program automatically detects regular expressions that match all the strings that correspond to a particular event type." +
				"\n\tUsage:" +
				AutomatedEventTypesDetector.class.getCanonicalName()+" [OPTIONS] <logFile> [<logFile>]+ <resultCsvFile>\n" +
						"\nOptions:" +
						"\n\t-offset <value>: discard the first <value> chars of every line" +
						"\n\t-offsetPost <value>: discard <value> chars from every line after having done all the replacements" +
						"\n\t-componentsDefinitionFile <file>: save components ids to <file>. File must be a properties file, " +
						"with the real component name as key, and the component id as value." +
						"-\n\tcomponentExpression <regex>: define with a regular expression how to catch the component name from the log line. " +
						"The component name is retrieved using regex grouping functionality. " +
						"Eg \"^[0-9]{4} (\\S+) .*\" for messages like \"2008 myComponent event with paramaters\"" +
						"-\n\tdataExpression <regex>: define with a regular expression what part of the log contains the raw event data (other lines are discarded)" +
						"The event is retrieved using regex grouping functionality. " +
						"Eg \"^[0-9]{4} \\S+ (.*)\" for messages like \"2008 myComponent event with paramaters\"" +
						"-\n\texportRules <file>: save the generated slct rules to <file>" +
						"-\n\tworkingDir <dir>: save temporary files to <dir>" +
						"-\n\tloadComponents <file>: read components ids from <file>. File must be a properties file, " +
						"with the real component name as key, and the component id as value." +
						"-\n\tloadEventPatterns : load event patterns instead of creating them (you need to specify -loadPatternsDir )" +
						"-\n\tpatternsDir <dir> : load event patterns from dir. This is used to load patterns separated per component." +
						"-\n\tseparator <string> : use <string> as csv column separator" +
						"-\n\tslctSupportPercentage <value> : use <value> as the initial slct support percentage. <value> must be a number between 0 and 1. Default is 0.05." +
						"-\n\tnumberSeparator <string> : separate numeric parameters with <string>" +
						"-\n\treplacement <regex> <replacement> : replace every string in the log event that matches <regex> with <replcament>. <regex> must be a valid java regular expression." +
						"-\n\ttruncate <value> : truncate event string if they are longer than <value>" +
						"-\n\tdontSplitComponents : do not split events per component. Used when detecting event types for application level analysis or action level analysis." +
						"\n" 
						
		);
	}

	private List<SlctPattern> loadEventsPatterns(File file) throws IOException {
		Properties p = new Properties();
		FileInputStream fis = new FileInputStream(file);
		p.load(fis);
		fis.close();
		SortedMap<String,SlctPattern> patterns = new TreeMap<String,SlctPattern>();

		for ( Entry<Object,Object> e : p.entrySet() ){
			String ruleName = (String)e.getKey();
			
			if ( ! ruleName.startsWith("R") ){
				System.out.println("Discarding rule "+ruleName);
			}
			
			Pattern pattern = Pattern.compile((String)e.getValue());
			patterns.put(ruleName,new SlctPattern(ruleName,pattern));
		}


		ArrayList<SlctPattern> list = new ArrayList<SlctPattern>();
		list.addAll(patterns.values());
		return list;
	}
	
	public void process(File src, File dst) throws EventTypesDetectorException {
		List<File> list = new ArrayList<File>(0);
		process(list,dst);
	}

	/* (non-Javadoc)
	 * @see it.unimib.disco.lta.alfa.eventsDetection.slct.CsvGenerator#process(java.util.List, java.io.File)
	 */
	public void process (List<File> inputs, File destTrace) throws EventTypesDetectorException{
				
		Logger.info("Splitting log file!!");
		
		ComponentsTracesManager ctm = new ComponentsTracesManager(  );
		try {
			p.process(inputs);
		} catch (IOException e1) {
			throw new AutomatedEventTypesDetectorException(e1);
		}
		HashMap<String, ComponentLogFile> logFiles = p.getLogFiles();
		

		SlctEventTypesDetector slctEventTypesDetector = new SlctEventTypesDetector(this.workingDir);
		
		if ( slctExecutable != null ){
			System.out.println("Setting slct executable path "+slctExecutable);
			slctEventTypesDetector.setSlctExecutablePath(slctExecutable);
		}
		
		slctEventTypesDetector.setSupportPercentage(slctSupportPercentage);

		
		for( Entry<String,ComponentLogFile> entry: logFiles.entrySet() ){
			String componentId = entry.getKey();
			ComponentLogFile logFile = entry.getValue();
			File file = logFile.getFile();
			
			Logger.info("Starting processing componet "+componentId);
			
			try {
				List<SlctPattern> slctPatterns;
				
				if ( ! skipEventPatternsDetection ){
					
					Logger.info("Detecting events for componet "+componentId);
					slctPatterns = slctEventTypesDetector.getSlctPatterns(file);
					
					//Save the slct patterns for the given component
					Logger.info("Saving patterns for component "+componentId+" to file.");
					exportEventPatterns(slctPatterns, new File(workingDir,getEventPatternFileName(componentId)) );
					
					
				} else {
					if ( patternsDir != null ){
						//System.out.println("LOAD");
						Logger.info("Loading patterns for componet "+componentId);
						File patternFile = new File( patternsDir, getEventPatternFileName(componentId) );
						if ( patternFile.exists() ){
						slctPatterns = loadEventsPatterns(patternFile);
						} else {
							slctPatterns = new ArrayList<SlctPattern>(0);
						}
					} else {
						throw new AutomatedEventTypesDetectorException("Not specified dir or file to load patterns from");
					}
				}
				
				eventPatterns.addAll(slctPatterns);
				
				
				SlctEventsParser eventsParser = new SlctEventsParser();
				
				eventsParser.setSlctPattern(slctPatterns);
				if ( truncate > 0 ){
					eventsParser.setLineSizeThreashold(truncate);
				}
				eventsParser.setComponentId(componentId);
				eventsParser.setHashOutliers(true);
				File csvFile = new File(workingDir,componentId+".csv");
				
				Logger.info("Generating the csv file for component "+componentId);
				eventsParser.process(file, csvFile);
				
				
				
				HashMap<String, String> notMatched = eventsParser.getUnmatchedLines();
				unmatchedLines.putAll( notMatched );
				
				Logger.info("Unmatched lines for component "+componentId+" : "+notMatched.size());
				Logger.info("Unmatched lines: ");
				for ( Entry<String,String> notMatchedEntry : notMatched.entrySet() ){
					Logger.info(notMatchedEntry.getKey()+"   "+notMatchedEntry.getValue());
				}
				Logger.info("Unmatched lines end.");
				
				ctm.put(componentId, csvFile);

				Logger.info("Finished processing component "+componentId);
			} catch (SlctRunnerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}


		}
		

		ComponentTracesMerger merger = new ComponentTracesMerger();
		try {
			merger.merge(destTrace,p.getComponentsProcessingSequence(),ctm);
		} catch (IOException e) {
			throw new AutomatedEventTypesDetectorException(e);
		} catch (ComponentsTracesManagerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new AutomatedEventTypesDetectorException(e);
		}
		
				
	}

	private String getEventPatternFileName(String componentId) {
		return componentId+".rules.properties";
	}

	public static void exportEventPatterns(List<SlctPattern> allPatterns, File exportRulesFile) throws IOException {
		Properties p = new Properties();
		
		for ( SlctPattern pattern : allPatterns ){
			
			p.setProperty(pattern.getId(), pattern.getPattern().pattern() );
		}
		
		FileOutputStream fos = new FileOutputStream(exportRulesFile);
		p.store(fos, "");
		fos.close();
		
	}





	public void exportComponentsDefinition(File dest) throws IOException {
		p.exportComponentsDefinition(dest);
	}

	public Properties getComponentIds() {
		return p.getComponentIds();
	}

	/* (non-Javadoc)
	 * @see it.unimib.disco.lta.alfa.eventsDetection.slct.SlctComponentCsvGeneratorInterface#getComponentPattern()
	 */
	public Pattern getComponentPattern() {
		return p.getComponentPattern();
	}

	public String getComponentsOrderFileName() {
		return p.getComponentsOrderFileName();
	}

	public ComponentProcessingSequence getComponentsProcessingSequence() {
		return p.getComponentsProcessingSequence();
	}

	/* (non-Javadoc)
	 * @see it.unimib.disco.lta.alfa.eventsDetection.slct.SlctComponentCsvGeneratorInterface#getDataPattern()
	 */
	public Pattern getDataPattern() {
		return p.getDataPattern();
	}

	public HashMap<String, ComponentLogFile> getLogFiles() {
		return p.getLogFiles();
	}

	/* (non-Javadoc)
	 * @see it.unimib.disco.lta.alfa.eventsDetection.slct.SlctComponentCsvGeneratorInterface#getNumbersSeparator()
	 */
	public String getNumbersSeparator() {
		return p.getNumbersSeparator();
	}

	/* (non-Javadoc)
	 * @see it.unimib.disco.lta.alfa.eventsDetection.slct.SlctComponentCsvGeneratorInterface#getOffset()
	 */
	public int getOffset() {
		return p.getOffset();
	}

	/* (non-Javadoc)
	 * @see it.unimib.disco.lta.alfa.eventsDetection.slct.SlctComponentCsvGeneratorInterface#getOutDir()
	 */
	public File getOutDir() {
		return p.getOutDir();
	}

	/* (non-Javadoc)
	 * @see it.unimib.disco.lta.alfa.eventsDetection.slct.SlctComponentCsvGeneratorInterface#getSeparator()
	 */
	public String getSeparator() {
		return p.getSeparator();
	}

	/* (non-Javadoc)
	 * @see it.unimib.disco.lta.alfa.eventsDetection.slct.SlctComponentCsvGeneratorInterface#getTraceSeparator()
	 */
	public String getTraceSeparator() {
		return p.getTraceSeparator();
	}

	public void loadComponentsDefinitions(File file) throws IOException {
		p.loadComponentsDefinitions(file);
	}

	public void setComponentExpression(String string) {
		p.setComponentExpression(string);
	}

	public void setNumbersSeparator(String numbersSeparator) {
		p.setNumbersSeparator(numbersSeparator);
	}

	public void setOffset(int offset) {
		p.setOffset(offset);
	}
	
	public void setOffsetPost(int offset) {
		p.setOffsetPost(offset);
	}

	public void setSeparator(String separator) {
		p.setSeparator(separator);
	}

	public void setTraceSeparator(String traceSeparator) {
		p.setTraceSeparator(traceSeparator);
	}

	/* (non-Javadoc)
	 * @see it.unimib.disco.lta.alfa.eventsDetection.slct.SlctComponentCsvGeneratorInterface#getWorkingDir()
	 */
	public File getWorkingDir() {
		return workingDir;
	}

	public void setWorkingDir(File workingDir) {
		this.workingDir = workingDir;
		p.setWorkingDir(workingDir);
	}

	public void setDataExpression(String daatExpression){
		p.setDataExpression(daatExpression);
	}
	
	public void addReplacementRule(String string,String replacement){
		p.addReplacementRule(string, replacement);
	}

	public void addReplacementRegexRule(String string,String replacement){
		p.addReplacementRegexRule(string, replacement);
	}
	
	/* (non-Javadoc)
	 * @see it.unimib.disco.lta.alfa.eventsDetection.slct.SlctComponentCsvGeneratorInterface#getEventPatterns()
	 */
	public List<SlctPattern> getEventPatterns() {
		return eventPatterns;
	}

	/* (non-Javadoc)
	 * @see it.unimib.disco.lta.alfa.eventsDetection.slct.SlctComponentCsvGeneratorInterface#getPatternsDir()
	 */
	public File getPatternsDir() {
		return patternsDir;
	}

	public void setPatternsDir(File patternsDir) {
		this.patternsDir = patternsDir;
	}

	/* (non-Javadoc)
	 * @see it.unimib.disco.lta.alfa.eventsDetection.slct.SlctComponentCsvGeneratorInterface#isSkipEventPatternsDetection()
	 */
	public boolean isSkipEventPatternsDetection() {
		return skipEventPatternsDetection;
	}

	public void setSkipEventPatternsDetection(boolean skipEventPatternsDetection) {
		this.skipEventPatternsDetection = skipEventPatternsDetection;
	}

	/* (non-Javadoc)
	 * @see it.unimib.disco.lta.alfa.eventsDetection.slct.SlctComponentCsvGeneratorInterface#getSlctSupportPercentage()
	 */
	public double getSlctSupportPercentage() {
		return slctSupportPercentage;
	}

	public void setSlctSupportPercentage(double slctSupportPercentage) {
		this.slctSupportPercentage = slctSupportPercentage;
	}

	/* (non-Javadoc)
	 * @see it.unimib.disco.lta.alfa.eventsDetection.slct.SlctComponentCsvGeneratorInterface#getTruncate()
	 */
	public int getTruncate() {
		return p.getTruncate();
	}

	public void setTruncate(int truncate) {
		this.truncate=truncate;
		p.setTruncate(truncate);
	}

	/* (non-Javadoc)
	 * @see it.unimib.disco.lta.alfa.eventsDetection.slct.SlctComponentCsvGeneratorInterface#isDontSplitComponents()
	 */
	public boolean isDontSplitComponents() {
		return p.isDontSplitComponents();
	}

	public void setDontSplitComponents(boolean dontSplitComponent) {
		p.setDontSplitComponents(dontSplitComponent);
	}

	public String getComponentRegex() {
		return p.getComponentPattern().pattern();
	}

	public String getDataRegex() {
		return p.getDataPattern().pattern();
	}

	public String getSlctExecutable() {
		return slctExecutable;
	}

	public void setSlctExecutable(String slctExecutable) {
		this.slctExecutable = slctExecutable;
	}
}

