package it.unimib.disco.lta.alfa.eventsDetection.slct;

import it.unimib.disco.lta.alfa.eventsDetection.ComponentLogFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;




public class GenericLogToComponentSlctPreprocessor extends GenericLogToSlctPreprocessor {
	private HashMap<String,ComponentLogFile> logFiles = new HashMap<String, ComponentLogFile>() ;
	private Properties componentIds = new Properties();
	private File workingDir = new File(".");
	private String componentsOrderFileName = "componentsOrder.txt";
	private ComponentProcessingSequence componentsProcessingSequence;


	
	public static class ComponentProcessingSequence implements Serializable {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		
		private File componentsOrderFile;
		
		private transient FileWriter componentsOrderWriter;
		private transient BufferedReader componentsReader;
		private transient boolean initialized = false;
		
		public ComponentProcessingSequence(File file){
			componentsOrderFile = file;	
		}
		
		public void add( String componentId ) throws IOException{
			checkInitWrite();
			componentsOrderWriter.write(componentId);
			componentsOrderWriter.write("\n");
		}
		
		public String nextId() throws IOException{
			if ( componentsReader == null ){
				componentsReader = new BufferedReader( new FileReader(componentsOrderFile) );
			}
			return componentsReader.readLine();
		}

		public void close() throws IOException {
			if ( componentsOrderWriter != null ){
				componentsOrderWriter.close();
			}
		}

		public void newTrace() throws IOException {
			checkInitWrite();
			componentsOrderWriter.write("|");
			componentsOrderWriter.write("\n");
		}

		private void checkInitWrite() throws IOException {
			if ( ! initialized ){
				componentsOrderWriter = new FileWriter(componentsOrderFile);
				initialized = true;
			}
		}
	}
	
	public GenericLogToComponentSlctPreprocessor() {
		
	}
	
	public static void main(String args[]){
		String offset = null;
		ArrayList<File> inputs = new ArrayList<File>();
		
		GenericLogToComponentSlctPreprocessor p = new GenericLogToComponentSlctPreprocessor();
		
		for( int i = 0; i < args.length; ++i){
			
			if ( args[i].equals("-offset") ){
				offset=args[++i];
			} else if ( args[i].equals("-offsetPost") ) {
				p.setOffsetPost(Integer.valueOf(args[++i]).intValue());
			} else if ( args[i].equals("-componentExpression") ) {
				p.setComponentExpression(args[++i]);
			} else if ( args[i].equals("-dataExpression") ) {
				p.setDataExpression(args[++i]);
			} else if ( args[i].equals("-separator") ) {
				p.setSeparator(args[++i]);
			} else if ( args[i].equals("-numberSeparator") ) {	//set the string which is but betwee two numbers separated by white spaces
				p.setNumbersSeparator(args[++i]);
			} else if ( args[i].equals("-replacement") ) {	//set the string which is but betwee two numbers separated by white spaces
				p.addReplacementRule(args[++i],args[++i]);
			} else {
				inputs.add(new File(args[i]));
			}
			
		}
		
		
		
		if ( offset != null ){
			p.setOffset(Integer.valueOf(offset));
		}
		
		
		
		
		try {
			p.process(inputs);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	public void setDataExpression(String daatExpression) {
		this.data.setDataPattern(Pattern.compile(daatExpression));
	}

	public void process(List<File> inputs) throws IOException {
		
		componentsProcessingSequence = new ComponentProcessingSequence(new File(workingDir,componentsOrderFileName));
		
		try{
			for ( int i = 0; i < inputs.size(); ++i ){
				try {
					File input = inputs.get(i);
					
					
					BufferedReader br = new BufferedReader(new FileReader(input));
					process(br);
					br.close();
					
					
					
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			

			
			if ( componentsProcessingSequence != null )
				componentsProcessingSequence.close();

		}finally{
			
			closeAllTraces();
		}
	}

	public void exportComponentsDefinition( File dest ) throws IOException{
		FileOutputStream out = new FileOutputStream(dest);
		componentIds.store(out, "");
		out.close();
	}
	
	public Properties getComponentsDefinitions () {
		Properties p = new Properties();
		p.putAll(componentIds);
		return p;
	}
	
	public void setComponentsDefinitions (Properties p) {
		componentIds = new Properties();
		componentIds.putAll(p);
	}
	
	private void process(BufferedReader br) throws IOException {

		String line;
		int lines = 0;
		String traceSeparator = getTraceSeparator();
		try {
			int lastClosedTrace=-1;
		while (( line = br.readLine() ) != null ){
			lines++;
			System.out.print(".");
			if ( line.length() != 0 ){
				if ( line.equals( traceSeparator ) ){
					lastClosedTrace=lines;
					newTrace(lines);
					continue;
				}
				
				line = line.substring(offset);
				
				String componentName = data.getComponentName( line );
				if ( componentName == null ){
					continue;
				}
				
				//System.out.println("expr: "+componentPattern.pattern());
				//System.out.println("CompName: "+componentName);
				ComponentLogFile log = getComponentLog( componentName );
				addToComponentProcessingSequence(componentName);
				String lineData = data.getLineData(line);
				if ( lineData == null ){
					continue;
				}
				//System.out.println("Line: "+line);
//				
				
				
				//System.out.println("DataPattern "+dataPattern.pattern());
				//System.out.println("Data: "+lineData);
				
				
				
				String result = getResult(lineData);
				//System.out.println(componentName+"\t"+lineData);
				//System.out.println("Adding to log "+log.getFile().getAbsolutePath());
				log.addToken(result, lines);
			}
		}

		if ( lines != lastClosedTrace ){
			newTrace(-1);
		}

		} catch ( RuntimeException e ){
			System.err.println("Exception occurred at line "+lines);
			e.printStackTrace();
			throw e;
		}
	}

	private void addToComponentProcessingSequence(String componentName) throws IOException {

		
		
		String componentId = getComponentId(componentName);
		
		componentsProcessingSequence.add(componentId);
		
	}


	
//	Retrieves all the chars that are not matched in the component name pattern
//	private String getLineData(String line) {
//		Matcher m = componentPattern.matcher(line);
//		if(m.find()){
//			
//			int groupC = m.groupCount();
//			StringBuffer result = new StringBuffer();
//			int lastPos = 0;
//			
//			for ( int i = 0; i < groupC; ++i ){
//				String el = m.group(i+1);
//				int groupStart =m.toMatchResult().start(i+1);
//				 //m.start(i+i);
//				int groupEnd = m.toMatchResult().end(i+1);
//				//System.out.println(groupStart+" "+groupEnd);
//				
//				result.append(line.substring(lastPos, groupStart));
//				
//				lastPos = groupEnd;
//			}
//			result.append(line.substring(lastPos, line.length()));
//			
//			return result.toString();
//		}
//		return null;
//	}

	

	
	private ComponentLogFile getComponentLog(String componentName) {
		String componentId = getComponentId( componentName );
		ComponentLogFile componentLog = logFiles.get(componentId);
		if ( componentLog == null ){
			componentLog = new ComponentLogFile(workingDir,componentId,"component_");
			logFiles.put(componentId, componentLog);
		}
		return componentLog;
	}

	private String getComponentId(String componentName) {
		String id = componentIds.getProperty(componentName);
		if ( id == null ){
			id = ""+componentIds.size();
			componentIds.setProperty(componentName, id);
		}
		return id;
	}

	private void closeAllTraces() throws IOException {
		for ( ComponentLogFile lf : logFiles.values() ){
			lf.close();
		}
	}

	private void newTrace(int line) throws IOException {
		for ( ComponentLogFile lf : logFiles.values() ){
			lf.closeTrace(line);
		}
		componentsProcessingSequence.newTrace();
	}

	private LogMessageDataExtractor data = new LogMessageDataExtractor(
			Pattern.compile("(.*)"));

	public void setComponentExpression(String string) {
		data.setComponentPattern(Pattern.compile(string));
	}

	public Pattern getDataPattern() {
		return data.getDataPattern();
	}

	public HashMap<String, ComponentLogFile> getLogFiles() {
		HashMap<String,ComponentLogFile> map = new HashMap();
		
		map.putAll(logFiles);
		
		return map;
	}

	public Properties getComponentIds() {
		return componentIds;
	}

	public File getOutDir() {
		return workingDir;
	}

	public String getComponentsOrderFileName() {
		return componentsOrderFileName;
	}

	

	public Pattern getComponentPattern() {
		return data.getComponentPattern();
	}

	public ComponentProcessingSequence getComponentsProcessingSequence() {
		return componentsProcessingSequence;
	}

	public void loadComponentsDefinitions(File file) throws IOException {
		componentIds.clear();
		FileInputStream fis = new FileInputStream(file);
		componentIds.load(fis);
		fis.close();
		
	}

	public File getWorkingDir() {
		return workingDir;
	}

	public void setWorkingDir(File workingDir) {
		this.workingDir = workingDir;
		workingDir.mkdirs();
	}

	public boolean isDontSplitComponents() {
		return data.isDontSplitComponents();
	}

	public void setDontSplitComponents(boolean dontSplitComponents) {
		data.setDontSplitComponents(dontSplitComponents);
	}

	public String getGlobalComponentName() {
		return data.getGlobalComponentName();
	}

	public void setGlobalComponentName(String globalComponentName) {
		data.setGlobalComponentName(globalComponentName);
	}

}
