package it.unimib.disco.lta.alfa.klfa.utils.componentAnalysis;

import grammarInference.Record.Trace;
import grammarInference.Record.kbhParser;

import it.unimib.disco.lta.alfa.utils.FileUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.Iterator;



public class ComponentFile implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private BufferedWriter traceFileWriter;
	private String name;
	private boolean hasPredecessor;
	private String separator = "#";
	private File file;
	private BufferedWriter transFileWriter;
	private File translationFile;
	private File outputDir;
	private String traceSeparator;

	public ComponentFile(File outDir, String component, String filePrefix,
			String resultFileExtension, String transFileExtension, String separator, String traceSeparator) {
		name = component;
		this.outputDir = outDir;
		this.separator = separator;
		file = new File ( outputDir,getComponentFileName(filePrefix,name,resultFileExtension));
		translationFile = new File ( outputDir, getComponentFileName(filePrefix,name,transFileExtension));
		this.traceSeparator = traceSeparator;
	}
	
	public String getComponentFileName ( String filePrefix, String name, String extension ){
		return filePrefix+name.replace('"','_')+extension;
	}

	public Trace getSubTrace(int start, int end) throws FileNotFoundException {
		kbhParser p = new kbhParser(file.getAbsolutePath());
		Iterator<Trace> it = p.getTraceIterator();
		Trace trace;
		int totLen = 0;
		do {
			trace =  it.next();
			totLen = trace.getLength()+1;
		} while (totLen <= end && it.hasNext());
		int tLen = trace.getLength();
		int delta = totLen-end;
		int sequenceLen = end-start;
		int endPos = tLen-delta;
		
		return trace.getSubTrace(endPos-sequenceLen,endPos );
		
		
	}
	
	
	public void addToken(String token,int line) throws IOException {
		init();
		
		if ( hasPredecessor ){

				traceFileWriter.write(this.separator);
			
		}
		hasPredecessor = true;
		
		traceFileWriter.write(token);
		transFileWriter.write(token+" : "+line);
		transFileWriter.write(FileUtils.lineSeparator);
		
		//close();
	}

	private void init() throws IOException {
		if ( traceFileWriter == null ){
			
			System.out.println("Init "+file);
			traceFileWriter = new BufferedWriter( new FileWriter( file, true ) ) ;
			
			transFileWriter = new BufferedWriter( new FileWriter(translationFile,true) );
		}
	}

	/**
	 * Delete the content of the generated files
	 * 
	 */
	public void clear(){
		file.delete();
		translationFile.delete();
	}
	
	public void closeTrace(int line) throws IOException {
		init();
		
		traceFileWriter.write(this.traceSeparator);
//			fw.newLine();
//			fw.write("|");
//			fw.newLine();

		
		transFileWriter.write(" : "+line);
		transFileWriter.newLine();
		hasPredecessor = false;
		//close();
	}
	
	public void close() throws IOException{
		if ( traceFileWriter != null ){
			traceFileWriter.close();
			transFileWriter.close();
		}
		traceFileWriter = null;
		transFileWriter =  null;
	}

	public String getComponentName() {
		return name;
	}


	public File getFile() {
		return file;
	}

	public File getTranslationFile() {
		return translationFile;
	}
}
