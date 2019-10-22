package it.unimib.disco.lta.alfa.dataTransformation;

import it.unimib.disco.lta.alfa.dataTransformation.parserRules.ParserFactoryException;
import it.unimib.disco.lta.alfa.dataTransformation.parserRules.ParserRule;
import it.unimib.disco.lta.alfa.dataTransformation.parserRules.ParserRuleException;
import it.unimib.disco.lta.alfa.dataTransformation.parserRules.ParserRulesFactory;
import it.unimib.disco.lta.alfa.klfa.TraceAnalyzerComponent;
import it.unimib.disco.lta.alfa.klfa.TraceAnalyzerException;
import it.unimib.disco.lta.alfa.klfa.TraceManagerProvider;
import it.unimib.disco.lta.alfa.klfa.TraceAnalyzer.MinimizationTypes;
import it.unimib.disco.lta.alfa.klfa.TraceAnalyzerComponent.TraceManagerProviderComponent;
import it.unimib.disco.lta.alfa.klfa.traceFilters.TraceFilterPositional;
import it.unimib.disco.lta.alfa.klfa.traceFilters.TraceFilterRule;
import it.unimib.disco.lta.alfa.klfa.utils.componentAnalysis.ComponentFSABuilderException;
import it.unimib.disco.lta.alfa.klfa.utils.componentAnalysis.ComponentTraceFileMaker;
import it.unimib.disco.lta.alfa.klfa.utils.componentAnalysis.ComponentTracesFile;
import it.unimib.disco.lta.alfa.parametersAnalysis.TraceFilter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;


public class TransformationRulesSeparator {

	public static class TSTraceFileMaker extends ComponentTraceFileMaker {

		private File dest;


		public TSTraceFileMaker(File dest, TraceManagerProvider traceManagerProvider,
				List<ParserRule> parserRules, String columnSeparator,
				int componentColumn, int eventColumn, File outputDir,
				TraceFilter traceFilter) {
			super(traceManagerProvider, parserRules, columnSeparator, componentColumn,
					eventColumn, outputDir, traceFilter);
			this.dest = dest;
			//System.out.println("Rules "+parserRules.size());
		}
		
		
		public Collection<ComponentTracesFile> processInputTrace(File inputTrace, File origTrace, String prefix, String suffix) throws IOException, ComponentFSABuilderException, TraceAnalyzerException {
			BufferedReader fr = new BufferedReader ( new FileReader(inputTrace) );
			BufferedReader fro = new BufferedReader ( new FileReader(inputTrace) );
			String line = null;

			//DistinctTracesManager cm = traceManagerProvider.getTraceManager(outputDir,prefix,dest);
			int curline = 0;
			BufferedWriter w = new BufferedWriter(new FileWriter(dest));
			
			
			while ( ( line = fr.readLine()) != null ){
				String oLine = fro.readLine();
				++curline;
				String cleanline = line.trim();



				if ( cleanline.equals("|")){
					w.write("|\n");
					resetPreprocessors();
				} else {
					try{
						addToken(w,cleanline,suffix,curline,oLine);
					}catch(RuntimeException e){
						System.err.println("Curline "+curline);
						throw e;
					}

				}
			}

			fr.close();
			fro.close();
			w.close();
			return null;
		}

		
		protected void addToken(BufferedWriter w, String cleanline, String suffix, int curline,String oLine) throws ComponentFSABuilderException, IOException {
			String[] columns = csvReader.readLine(columnSeparator);

			if ( ! traceFilter.accept(columns) )
				return;
			
			String component;
			try{
			//Get the parsed value for the component name
				
				
				ParserRule rule;
				try {
					//System.out.println(cleanline);
					rule = getParserRule(columns);
					//System.out.println("Found rule"+rule.getValueTransformersNumber());
					component=rule.processSingleElement(columns,componentColumn);
					



					String token = getParsedLine( columns, curline );
					String orig = getMatchingElements(columns);
					if ( token != null ){
						w.write( curline+"\t"+oLine+"\t"+orig+"\t"+token+"\n");
					}

				} catch (TraceAnalyzerException e) {
					
				}
				
				
			
			} catch (ParserRuleException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
		}

	}
	
	
	public static void main ( String args[] ){
		int MIN_ARGS = 4;
		int eventColumn = 1;
		int componentColumn = 0;
		File outputDir = new File ("TR");
		String columnSeparator=",";
		
		for( int i = 0; i < args.length - MIN_ARGS; ++i ){
			
			if ( args[i].equals("-eventColumn") ){
				eventColumn = Integer.valueOf(args[++i]);
			} else if ( args[i].equals("-componentColumn") ){
				componentColumn = Integer.valueOf(args[++i]);
			} else if ( args[i].equals("-columnSeparator") ){
				columnSeparator = args[++i];
			} else if ( args[i].equals("-outputDir") ){
				outputDir = new File(args[++i]);
			
			} else {
				System.err.println("Unrecognized option : "+args[i]);
				
		
				System.exit(-1);
			}
		}
		File log = new File(args[args.length-4] );
		File transformerRuleFile = new File(args[args.length-3] );
		File preprocessorsFile = new File ( args[args.length-2] );
		File trace = new File(args[args.length-1]);
		
		List<ParserRule> rules = new ArrayList<ParserRule>();
		HashMap<String, ValueTransformer> trasformers;
		
		outputDir.mkdirs();
		
		try {
			trasformers = TransformersFactory.createTrasformers(transformerRuleFile);
		
			for ( Entry<String,ValueTransformer> transformerEntry : trasformers.entrySet() ){
				HashMap<String, ValueTransformer> cleanTransformers = new HashMap<String, ValueTransformer>();
				
				cleanTransformers.put(transformerEntry.getKey(), transformerEntry.getValue());
				//System.out.println("Processing "+transformerEntry.getKey());
				List<ParserRule> trules = new ArrayList<ParserRule>(); 
				for ( ParserRule rule : ParserRulesFactory.createRules(componentColumn,eventColumn,preprocessorsFile,cleanTransformers,true) ){
					if ( rule.getValueTransformersNumber() > 0 ){
						//System.out.println("Apply "+rule.getValueTransformersNumber());
						trules.add(rule);
						
						rule.setValueTrasformer(0, new ValueTransformerSame());
						rule.setValueTrasformer(1, new ValueTransformerSame());
					}
				}
				
				

				TraceManagerProvider traceManagerProvider = new TraceAnalyzerComponent.TraceManagerProviderComponent(false);
				File dest = new File(outputDir,transformerEntry.getKey()+".out");
				TSTraceFileMaker ctfm = new TSTraceFileMaker(dest,traceManagerProvider,trules, columnSeparator, componentColumn, eventColumn, outputDir, new TraceFilterPositional(new ArrayList<TraceFilterRule>()));
				
				try {
					ctfm.processInputTrace(trace, log, "", "");
				} catch (ComponentFSABuilderException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (TraceAnalyzerException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserFactoryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
