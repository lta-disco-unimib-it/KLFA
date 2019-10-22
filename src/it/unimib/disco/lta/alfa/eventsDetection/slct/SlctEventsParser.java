package it.unimib.disco.lta.alfa.eventsDetection.slct;

import it.unimib.disco.lta.alfa.eventsDetection.EventTypesDetector;
import it.unimib.disco.lta.alfa.eventsDetection.EventTypesDetectorException;
import it.unimib.disco.lta.alfa.logging.Logger;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.Format;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Formatter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;




/**
 * This class, given a file with rules produced by slct, process a (sinle event per line) log file and generates a csv file in which the original log lines 
 * are replaced by abstract names according to the rule the line match. Plus the words that match * are threated as rule parameters.
 * 
 * @author Fabrizio Pastore fabrizio.pastore AT gmail.com
 *
 */
public class SlctEventsParser implements EventTypesDetector {

	
	private String traceSeparator = "|";
	private List<SlctPattern> slctPatterns = new ArrayList<SlctPattern>();
	private String separator = ",";
	private HashMap<String,String> unmatched = new HashMap<String,String>();
	private int linesProcessedCount = 0;
	//private HashMap<String,Pattern> rulesMap;
	private boolean hashOutliers = false;
	private String componentId = "C";
	private int lineSizeThreashold = -1;
	
	public SlctEventsParser(){
		
	}

	/**
	 * This method load rules from file in the format outputted by slct
	 * 
	 * @param slctRules
	 * @throws IOException
	 */
	public void loadSlctRegex(File slctRules) throws IOException {
		BufferedReader r = new BufferedReader ( new FileReader(slctRules));
		
		setRules( SlctUtils.getRules(r,true) );
		
		r.close();
		
		
	}



	/**
	 * Return the rule name associated to the rule in the given position.
	 * The position is the position in the rule array, not in the file (it is the inverse position with respect to the file).
	 * 
	 * @param rulePosition
	 * @param rule
	 * @return
	 */
	private String getRuleName(int rulePosition, Pattern rule) {
		Formatter f = new Formatter();
		f.format("R%4d", rulePosition);
		return f.toString().replace(" ", "0"); 
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		if ( args.length < 3 ){
			printUsage();
			System.exit(-1);
		}
		SlctEventsParser transformer = new SlctEventsParser();
		
		for ( int i =0; i < args.length; ++i ){
			if ( args[i].equals("-hashOutliers") ){
				transformer.setHashOutliers(true);
			}
		}
		
		File slctRules = new File(args[args.length-3]);
		File src = new File(args[args.length-2]);
		File dst = new File(args[args.length-1]);
		
		if ( dst.exists() ){
			System.err.println("Destnation file "+dst.getAbsolutePath() +" exists, delete or move it to run the program.");
			System.exit(-1);
		}
		
		
		try {
			transformer.loadSlctRegex(slctRules);
		
			System.out.println("Using rules: ");
			 List<SlctPattern> slctPatterns = transformer.getRules();
			for( SlctPattern p : slctPatterns ){
				System.out.println(p.getId()+"="+p.getPattern().pattern());
			}
			
			transformer.process(src,dst);
			
			HashMap<String, String> unmatchedlines = transformer.getUnmatchedLines();
			System.out.println("Non matching lines ("+unmatchedlines.size()+"/"+transformer.getLinesProcessedCount()+")");
			
			for( String line : unmatchedlines.values()){
				System.out.println(line);
			}
		} catch (EventTypesDetectorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		Pattern p = Pattern.compile("my na\\-me is (.+) and I am (.+) years old");
//		Matcher m = p.matcher("my na-me is pippo and I am five years old");
//		int groupC = m.groupCount();
//		System.out.println(groupC);
//		if ( m.find()){
//			for ( int i = 0; i < groupC; ++i ){
//				System.out.println(m.group(i+1));
//			}
//		}
//		
//		
//		p = Pattern.compile("FINEST GlassFish10.0 com.sun.org.apache.commons.modeler.Registry _ThreadID 10 _ThreadName Thread\\-4 ClassName com.sun.org.apache.commons.modeler.Registry MethodName      loadDescriptors Finding descriptor (.*)");
//		m = p.matcher("FINEST GlassFish10.0 com.sun.org.apache.commons.modeler.Registry _ThreadID 10 _ThreadName Thread-4 ClassName com.sun.org.apache.commons.modeler.Registry MethodName               loadDescriptors  Finding descriptor com/sun/enterprise");
//		System.out.println(m.matches());
		
	}

	private static void printUsage() {
		System.out.println("Usage: " +
				SlctEventsParser.class.getName()+" <rulesFile> <logFile> <ouputFile>\n");
	}

	public void process(File src, File dst) throws EventTypesDetectorException  {
		List<File> list = new ArrayList<File>(1);
		list.add(src);
		process(list,dst);
	}
	
	private void processFile(File src, BufferedWriter writer) throws EventTypesDetectorException  {	
		
		Logger.info("processing");
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(src));
		
			
		
		String line;
		
		
			while(( line = reader.readLine())!=null){
				System.out.write('.');
				if(line.length()==0){
					continue;
				}
				++linesProcessedCount ;
				String replacement = processLine(writer,line);
				if ( replacement != null ){
					//System.out.println("!!NOT MATCHED "+replacement);
					unmatched.put(replacement,line);
				}
			}
			reader.close();
		} catch (IOException e) {
			throw new EventTypesDetectorException(e);
		}
		
		
		
	}

	public void process(List<File> srcs, File dst) throws EventTypesDetectorException {
		Logger.info("Generating csv file "+dst.getAbsolutePath());
		
		try{
			BufferedWriter writer= new BufferedWriter(new FileWriter(dst));

			boolean first=true;
			for ( File src : srcs ){

				if ( ! first ){
					appendTraceSeparator(writer);
				} else {
					first = false;
				}

				processFile(src, writer);

			}

			writer.close();
		} catch ( IOException e ){
			throw new EventTypesDetectorException(e);
		}
		
		Logger.info("Generated");
	}
	
	private void appendTraceSeparator(BufferedWriter writer) throws IOException {
		writer.write(traceSeparator);
		writer.newLine();
	}

	/**
	 * Process a line writing the parsed content to the output writer
	 * Returns true if a rule matched the line otherwise returns false
	 * 
	 * @param writer
	 * @param line
	 * @return
	 * @throws IOException
	 */
	private String processLine(BufferedWriter writer, String line) throws IOException {
		//line = line.replace("  ", " ");
		Logger.finer("Processing line: "+line);
		if ( line.equals(traceSeparator) ){
			appendTraceSeparator(writer);
			return null;
		}
		
		for(int i = 0; i < slctPatterns.size(); ++i ){
			
			SlctPattern slctPattern = slctPatterns.get(i);
			Pattern rule = slctPattern.getPattern();
			
			Logger.finest("Processing rule "+rule.pattern());
			
			List<String> parameters = getParameters(rule,line);
			
			if ( rule.toString().trim().equals(line.trim()) && parameters == null){
				Logger.warning("Equals but no match : "+line);
			}
			
			//System.out.println(rule.pattern()+" "+parameters+" "+line+"|");
			if ( parameters != null ){
				String ruleId = slctPattern.getId();
				writeParsedLine(writer,ruleId,parameters);
				//System.out.println("MATCHED "+rule.pattern()+" "+line);
				Logger.finest("Rule matched "+ruleId+" "+rule.pattern());
				return null;
			}
			
		}
		
		Logger.info("No match for : "+line);
		
		Logger.finest("No matching rule found");
		return writeNoRule(writer,line);
		
	}



	private String writeNoRule(BufferedWriter writer, String line) throws IOException {
		writer.write(componentId+separator);
		String content;
		if ( hashOutliers ){
			content=""+line.hashCode();
			
		} else {
			content=line.replace(')', ' ');
		}
		//System.out.println("WRITNG "+content);
		writer.write(content);
		writer.write("\n");
		
		return content;
	}

	private void writeParsedLine(BufferedWriter writer, String ruleId, List<String> parameters) throws IOException {
		StringBuffer sb = new StringBuffer();
		sb.append(componentId +separator);
		sb.append(ruleId);
		for( String par : parameters ){
			sb.append(separator+par);
		}
		
		writer.write(sb.toString());
		writer.newLine();
	}

	/**
	 * returns the parameters corresponding to the subgroups in a egular expression
	 * @param rule
	 * @param line
	 * @return
	 */
	private List<String> getParameters(Pattern rule, String line) {
		
		//Optimization for big lines
		if ( ( lineSizeThreashold > 0 ) && ( line.length() > lineSizeThreashold ) ){
			line = line.substring(0,lineSizeThreashold);
//			String ruleRegex = rule.pattern();
//			String fixedElements[] = ruleRegex.replace("(.*)", "").split(" ");
//			for ( String fixedElement : fixedElements ){
//				if ( !line.contains(fixedElement) ){
//					return null;
//				}
//			}
		}
		
		Matcher m = rule.matcher(line);
		if(m.find()){
			
			int groupC = m.groupCount();
			ArrayList<String> result = new ArrayList<String>(groupC);
			for ( int i = 0; i < groupC; ++i ){
				result.add(m.group(i+1));
			}
			return result;
		}
		return null;
	}


	public void setRules(List<Pattern> list) {
		this.slctPatterns = new ArrayList<SlctPattern>();
		for ( int i = 0; i < list.size(); ++i ){
			Pattern p = list.get(i);
			SlctPattern sp = new SlctPattern(getRuleName(i, p),p);
			slctPatterns.add(sp);
		}
	}

	public String getSeparator() {
		return separator;
	}

	public void setSeparator(String separator) {
		this.separator = separator;
	}

	public HashMap<String, String> getUnmatchedLines() {
		return unmatched;
	}

	public void setUnmatched(HashMap<String, String> unmatched) {
		this.unmatched = unmatched;
	}

	public int getLinesProcessedCount() {
		return linesProcessedCount;
	}

	public void setLinesProcessedCount(int linesProcessedCount) {
		this.linesProcessedCount = linesProcessedCount;
	}

	public boolean isHashOutliers() {
		return hashOutliers;
	}

	public void setHashOutliers(boolean hashOutliers) {
		this.hashOutliers = hashOutliers;
	}

	public String getComponentId() {
		return componentId;
	}

	public void setComponentId(String componentId) {
		this.componentId = componentId;
	}

	public void loadPatterns( File propertiesFile ) throws IOException{
		Properties p = new Properties();
		FileInputStream fis = new FileInputStream( propertiesFile );
		p.load(fis);
		fis.close();
		loadRules(p);
	}
	
	public void loadRules(Properties rules) {
		this.slctPatterns = new ArrayList<SlctPattern>();
		
		SortedMap<String,String> m = new TreeMap<String,String>();
		Enumeration<String> keys = (Enumeration<String>) rules.propertyNames();
		while(keys.hasMoreElements()){
			String ruleId = keys.nextElement();
			String regex= rules.getProperty(ruleId);
			m.put(ruleId,regex);
		}
		
		for ( String ruleId : m.keySet() ){
			String regex = m.get(ruleId);
			this.slctPatterns.add(new SlctPattern(ruleId,Pattern.compile(regex)));
		}
	}

	public List<SlctPattern> getRules() {
		return slctPatterns;
	}

	public void setSlctPattern(List<SlctPattern> rules) {
		this.slctPatterns = rules;
	}

	public int getLineSizeThreashold() {
		return lineSizeThreashold;
	}

	public void setLineSizeThreashold(int lineSizeThreashold) {
		this.lineSizeThreashold = lineSizeThreashold;
	}

}

