package it.unimib.disco.lta.alfa.klfa.utils.componentAnalysis;

import it.unimib.disco.lta.alfa.csv.CsvReader;
import it.unimib.disco.lta.alfa.csv.OpenCsvReader;
import it.unimib.disco.lta.alfa.dataTransformation.parserRules.ParserRule;
import it.unimib.disco.lta.alfa.dataTransformation.parserRules.ParserRuleException;
import it.unimib.disco.lta.alfa.klfa.DistinctTracesManager;
import it.unimib.disco.lta.alfa.klfa.TraceAnalyzerException;
import it.unimib.disco.lta.alfa.klfa.TraceManagerProvider;
import it.unimib.disco.lta.alfa.parametersAnalysis.TraceFilter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.Collection;
import java.util.List;

import au.com.bytecode.opencsv.CSVReader;



/**
 * This class creates the component traces files and report the wrapping objects
 * 
 * @author Fabrizio Pastore fabrizio.pastore AT gmail.com
 *
 */
public class ComponentTraceFileMaker {
	
	protected TraceManagerProvider traceManagerProvider;
	private List<ParserRule> rules;
	protected String columnSeparator;
	protected int componentColumn;
	private int eventColumn;
	protected File outputDir;
	protected TraceFilter traceFilter;
	protected CsvReader csvReader;
	
	/**
	 * Create a trace file maker for component analysis
	 * @param traceManagerProvider
	 * @param parserRules
	 * @param columnSeparator
	 * @param componentColumn
	 * @param eventColumn
	 * @param outputDir
	 * @param traceFilter
	 */
	public ComponentTraceFileMaker( TraceManagerProvider traceManagerProvider, List<ParserRule> parserRules, String columnSeparator, int componentColumn, int eventColumn, File outputDir, TraceFilter traceFilter ){
		this.traceManagerProvider = traceManagerProvider;
		rules = parserRules;
		this.componentColumn = componentColumn;
		this.eventColumn = eventColumn;
		this.columnSeparator = columnSeparator;
		this.outputDir = outputDir;
		this.traceFilter = traceFilter;
		
		this.csvReader = new OpenCsvReader();
		this.csvReader.setColumnSeparator(columnSeparator.toCharArray()[0]);
	}


	public Collection<ComponentTracesFile> processInputTrace(File inputTrace, String prefix, String suffix) throws IOException, ComponentFSABuilderException, TraceAnalyzerException {
		BufferedReader fr = new BufferedReader ( new FileReader(inputTrace) );
		String line = null;
		
		DistinctTracesManager cm = traceManagerProvider.getTraceManager(outputDir,prefix,"GLOBAL");
		int curline = 0;
		while ( fr.ready() && ( line = fr.readLine()) != null ){
			++curline;
			String cleanline = line.trim();
			
			if ( line.length() == 0 )
				continue;
			

			
			if ( cleanline.equals("|")){
				cm.closeTraces(curline);
				resetPreprocessors();
			} else {
				try{
					addToken(cm,cleanline,suffix,curline);
				}catch(RuntimeException e){
					System.err.println("Curline "+curline);
					throw e;
				}

			}
		}
		
		fr.close();
		cm.closeFiles();
		return cm.getTraceFiles();
	}
	
	
	protected void resetPreprocessors() {
		for( ParserRule rule : this.rules ){
			rule.reset();
		}
	}
	
	protected void addToken(DistinctTracesManager cm, String cleanline, String suffix, int curline) throws ComponentFSABuilderException, IOException, TraceAnalyzerException {
		//System.out.println(cleanline);
		//System.out.println(columnSeparator);
		
		//String[] columns = cleanline.split(columnSeparator);
//		if ( columns.length > 2 ){
//			throw new ComponentFSABuilderException("Wrong File format: too much columns");
//		}
		
		String[] columns = csvReader.readLine(cleanline);
		
		if ( ! traceFilter.accept(columns) )
			return;
		
		String component;
		try{
		//Get the parsed value for the component name
			component = getParsedElement(columns, componentColumn);
		} catch (TraceAnalyzerException e ){
			component = columns[componentColumn];
		}
		
		
		String token = getParsedLine( columns, curline );
		if ( token != null ){
			cm.addToken(component, token+suffix,curline);
		}
	}
	
	protected String getParsedElement(String[] columns,int elementIdx) throws TraceAnalyzerException{
		
		if ( columns.length < elementIdx ){
			throw new TraceAnalyzerException("Anomalous line: not enough arguments, event and component are missing.");
		}
		ParserRule rule = getParserRule(columns);
		try {
			return rule.processSingleElement(columns,elementIdx);
		} catch (ParserRuleException e) {
			throw new TraceAnalyzerException(e);
		}
		
	}
	
	protected ParserRule getParserRule(String[] columns) throws TraceAnalyzerException{
		//System.out.println(columns.length);
		for ( ParserRule rule : rules ){
			if ( rule.match(columns)){
				return rule;
			}
		}
		throw new TraceAnalyzerException("No rule for component "+columns[componentColumn]+" EVENT: "+columns[eventColumn]);
	}
	
	protected String getParsedLine(String[] columns, int curline) throws TraceAnalyzerException{
		
		if ( columns.length < 2 ){
			throw new TraceAnalyzerException("Anomalous line ("+curline+") : not enough arguments, event and component are missing.");
		}
		ParserRule rule = getParserRule(columns);
		return rule.process(columns);
		
	}
	
	protected String getMatchingElements(String[] columns) throws TraceAnalyzerException{
		
		if ( columns.length < 2 ){
			throw new TraceAnalyzerException("Anomalous line: not enough arguments, event and component are missing.");
		}
		ParserRule rule = getParserRule(columns);
		return rule.getMatchingElements(columns);
		
	}
	
}
