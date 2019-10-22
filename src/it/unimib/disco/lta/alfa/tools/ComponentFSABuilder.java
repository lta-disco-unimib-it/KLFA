package it.unimib.disco.lta.alfa.tools;


import grammarInference.Log.FileLogger;

import it.unimib.disco.lta.alfa.csv.CsvParsersFactory;
import it.unimib.disco.lta.alfa.csv.CsvReader;
import it.unimib.disco.lta.alfa.inferenceEngines.FSAExtensionsRecorder;
import it.unimib.disco.lta.alfa.inferenceEngines.KBehaviorEngine;
import it.unimib.disco.lta.alfa.inferenceEngines.FSAExtensionsRecorder.FSAExtension;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;

import automata.fsa.FiniteStateAutomaton;

/**
 * This class read a csv trace file and builds separated FSA for different components. Components are selected on the basis of their name.
 * Trace files must be in the form COMPONENT,Symbol.
 * 
 * @author Fabrizio Pastore fabrizio.pastore AT gmail.com
 *
 */
public class ComponentFSABuilder {

	public static class ComponentFSABuilderException extends Exception{

		public ComponentFSABuilderException(String string) {
			super(string);
		}
		
	}
	
	
	public static class ComponentTracesFile {
		private FileWriter fw;
		private String name;
		private boolean hasPredecessor;
		private static final String separator = "#";
		private File file;
		private BufferedWriter lineWriter;
		private File lineFile;
		
		
		public ComponentTracesFile(String component,String filePrefix) {
			name = component;
			file = new File ( filePrefix+name+".trace");
			lineFile = new File ( filePrefix+name+".trans");
		}

		public void addToken(String token,int line) throws IOException {
			init();
			
			if ( hasPredecessor ){
				fw.write(separator );
			}
			hasPredecessor = true;
			
			fw.write(token);
			lineWriter.write(token+" : "+line+"\n");
		}

		private void init() throws IOException {
			if ( fw == null ){
				
				System.out.println("Init "+file);
				fw = new FileWriter( file, true );
				
				lineWriter = new BufferedWriter( new FileWriter(lineFile,true) );
			}
		}

		public void closeTrace(int line) throws IOException {
			init();
			fw.write("|");
			lineWriter.write(" | : "+line+"\n");
			hasPredecessor = false;
		}
		
		public void close() throws IOException{
			if ( fw != null ){
				fw.close();
				lineWriter.close();
			}
			fw = null;
			lineWriter =  null;
		}

		public String getComponentName() {
			return name;
		}


		public File getFile() {
			return file;
		}
	}
	
	public static class ComponentsTracesManager {
		private HashMap <String,ComponentTracesFile> traces = new HashMap<String, ComponentTracesFile>();
		private String prefix;
		private String global;
		private String previousComponent = null;
		private boolean fineLog;
		private String globalFine;
		private String previousToken = null;
		
		public ComponentsTracesManager( String prefix, String globalComponentName, boolean fineLog ){
			this.prefix = prefix;
			this.global = globalComponentName;
			this.globalFine = global+"fine";
			this.fineLog = fineLog;
		}
		
		public void addToken(String component,String token,int line) throws IOException{
			
			if ( ! component.equals( previousComponent) ){
				ComponentTracesFile globalComponentTrace = getComponentTrace(global);
				globalComponentTrace.addToken(token,line);
				
				if ( fineLog ){
					globalComponentTrace = getComponentTrace(globalFine);
					

					if ( previousToken != null ){
						globalComponentTrace.addToken(previousToken, line);
						
					}

					//add the changing symbol to the previous component 
					if ( previousComponent != null ){
						ComponentTracesFile previousComponentTraceFine = getComponentTraceFine(previousComponent);
						previousComponentTraceFine.addToken(token,line);
					}
					
					globalComponentTrace.addToken(token,line);
							
				}
				
				
				previousToken = null;
			} else {
				previousToken = token;
			}
			
			
			
			//add current symbol to current component trace
			ComponentTracesFile componentTrace = getComponentTrace(component);
			componentTrace.addToken(token,line);
			
			//add current symbol to current fine component trace
			ComponentTracesFile componentTraceFine = getComponentTraceFine(component);
			componentTraceFine.addToken(token,line);
			
			
			previousComponent = component;
			
		}
		
		private ComponentTracesFile getComponentTraceFine(String component) {
			return getComponentTrace(getFineComponentName(component));
		}

		private String getFineComponentName(String component) {
			return component+"-fine";
		}

		private ComponentTracesFile getComponentTrace(String component) {
			ComponentTracesFile trace = traces.get(component);
			if ( trace == null ){
				trace = new ComponentTracesFile(component,prefix);
				traces.put(component, trace);
			}
			
			return trace;
		}

		public void closeTraces(int curline) throws IOException{
			for ( ComponentTracesFile trace : traces.values() ){
				trace.closeTrace(curline);
			}
			previousComponent = null;
		}
		
		public void closeFiles() throws IOException{
			for ( ComponentTracesFile trace : traces.values() ){
				trace.closeTrace(-1);
				trace.close();
			}
		}

		public Collection<ComponentTracesFile> getTraceFiles() {
			return traces.values();
		}

	}

	private static final int componentColumn = 0;
	private static final int tokenColumn = 1;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		if ( args.length < 2 ){
			printUsage();
			System.exit(-1);
		}
		boolean  extend = false;
		String  suffix = "";
		boolean fine = false;
		boolean traceOnly = false;
		
		for ( int i = 0 ; i < args.length-2; ++i ){
			if ( args[i].equals("-traceOnly") ){
				traceOnly = true;
			} else if ( args[i].equals("-extend") ){
				extend = true;
			} else if ( args[i].equals("-suffix") ){
				suffix = ")";
			} else if ( args[i].equals("-fine") ){
				fine = true;
			} else {
				printUsage();
				System.exit(-1);
			}
		}
		
		
		String configFile = args[args.length-2];
		File inputTrace = new File(args[args.length-1]);
		
		
		try {
			String prefix = "";
			if ( extend ){
				prefix = "ext_";
			}
				
			Collection<ComponentTracesFile> splittedTraceFiles = processInputTrace( inputTrace, prefix, suffix, fine );
			
			
			if ( ! traceOnly){
				inferModels(splittedTraceFiles,extend,configFile);
			}
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ComponentFSABuilderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void inferModels(Collection<ComponentTracesFile> splittedTraceFiles, boolean extend, String configFile) throws IOException {
		for ( ComponentTracesFile traceFile : splittedTraceFiles ){
			KBehaviorEngine engine = new KBehaviorEngine( configFile );
			
			String logFile = traceFile.getComponentName()+".log";
			
			
			
			if ( extend){
				logFile = "ext_"+logFile;
			}
			FileLogger logger = new FileLogger(engine.getVerboseLevel(),logFile);
			engine.setLogger( logger );
			
			
			File file = traceFile.getFile();
			
			FiniteStateAutomaton fsa = null;
			
			if ( extend ){
				try {
					fsa = engine.inferFSAfromFile(file.getAbsolutePath(),getFSAFileName(traceFile));
					KBehaviorEngine.serializeFSA(fsa, getExtendedFSAFileName(traceFile));
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				System.out.println("Inferring FSA for trace "+traceFile.getFile());
				fsa = engine.inferFSAfromFile(file.getAbsolutePath());
				System.out.println("FSA states "+fsa.getStates().length);
				KBehaviorEngine.serializeFSA(fsa, getFSAFileName(traceFile));
			}
			
			
			logger.close();

			System.out.println("Changes ");


			FileWriter changesWriter = new FileWriter(new File("changes_"+getFSAFileName(traceFile)+".txt"));


			
			for ( FSAExtension data : engine.getFSAExtensions() ){
				changesWriter.write(data.getExtensionType()+" "
						+data.getStartPosition()+" "
						+data.getStartState().getName()+" "
						+data.getAnomalousTrace()+"\n");
			}

			changesWriter.close();

		}
	}

	private static String getFSAFileName(ComponentTracesFile traceFile) {
		return traceFile.getComponentName()+".fsa";
	}
	
	private static String getExtendedFSAFileName(ComponentTracesFile traceFile) {
		return "ext_"+traceFile.getComponentName()+".fsa";
	}

	private static void printUsage() {
		System.out.println("This program split a trace file by component name and build fsa using KBahavior (pay attention KBehaviorEngine must be in the classpath." +
				"The trace file must be a collection of lines, each one in the format COMPONENT,SYMBOL. Multiple traces can be defined in a file, to separate a trace from another put a line with the | symbol." +
				"\nUsage :" +
				"\n\t"+ComponentFSABuilder.class.getName()+" [options] <configFile> <traceFile>" +
						"\nWhere:" +
						"\n\t <configFile> is the KBehavior configuration File" +
						"\n\t <traceFile> is the trace file to be read." +
						"\nOptions can be:" +
						"\n\t -traceOnly : do not infer FSA, just create trace files" +
						"\n\t -extend : extend existing FSA instead of creating new ones" +
						"\n\t -suffix : put ')' after all the symbols in the trace" +
						"\n\t -fine : do fine trace generation, i.e. generates also traces with the name of the switching event");
	}

	private static Collection<ComponentTracesFile> processInputTrace(File inputTrace, String prefix, String suffix, boolean fine) throws IOException, ComponentFSABuilderException {
		BufferedReader fr = new BufferedReader ( new FileReader(inputTrace) );
		String line = null;
		CsvReader csvReader = CsvParsersFactory.createNewCsvReader();
		//csvReader.setColumnSeparator(separator);
		
		ComponentsTracesManager cm = new ComponentsTracesManager(prefix,"GLOBAL",fine);
		int curline = 0;
		while ( ( line = fr.readLine()) != null ){
			++curline;
			String cleanline = line.trim();
			
			if ( cleanline.equals("|")){
				cm.closeTraces(curline);
			} else {
				String[] columns = csvReader.readLine(cleanline);
				if ( columns.length != 2 ){
					throw new ComponentFSABuilderException("Wrong File format: line "+curline+", 2 columns expected, found "+columns.length );
				} 
				String component = columns[componentColumn];
				String token = columns[tokenColumn];
				
				cm.addToken(component, token+suffix,curline);
			}
		}
		
		fr.close();
		cm.closeFiles();
		return cm.getTraceFiles();
	}

}
