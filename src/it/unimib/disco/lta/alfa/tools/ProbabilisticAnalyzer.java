package it.unimib.disco.lta.alfa.tools;

import grammarInference.Record.FlyweigthKbhParser;
import grammarInference.Record.Symbol;
import grammarInference.Record.SymbolsFactory;
import grammarInference.Record.Trace;
import grammarInference.Record.VectorTrace;
import grammarInference.Record.TraceParser;
import grammarInference.Record.kbhParser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.Map.Entry;

import automata.fsa.FiniteStateAutomaton;

public class ProbabilisticAnalyzer {
	
	private int threadN = 1;
	private int pathLen;
	private SymbolsFactory symbolsfactory =  new SymbolsFactory();
	private static final String csvFieldSeparator = "\t";
	
	public ProbabilisticAnalyzer(int pathLen) {
		this.pathLen = pathLen;
	}

	
	
	
	
	public static void main (String args[]){
		
		int pathLen = Integer.valueOf(args[0]);
		
		
		try {
			
			ProbabilisticAnalyzer pa = new ProbabilisticAnalyzer(pathLen);
			
			File traceFile = new File(args[1]);
			
			ProbabilisticAnalyzerResult result = pa.analyze(traceFile);
			
			ProbabilisticAnalyzerResultWriter paw = new ProbabilisticAnalyzerResultWriter(csvFieldSeparator);
			
			try {
				paw.writeCsv( traceFile.getName()+".prob.csv",result );
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
			
			if ( args.length > 2 ){
				File traceFileExt = new File(args[2]);
				
				ProbabilisticAnalyzerResult resultExt = pa.analyze(traceFileExt);
				try {
					paw.writeCsv( traceFileExt.getName()+".prob.csv",resultExt );
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				
				Set<KPath> all = new HashSet<KPath>(); 
					
				all.addAll( result.getPaths() );
				
				all.addAll(resultExt.getPaths());
				
				Set<KPath> missing = new HashSet<KPath>();
				
				System.out.println("Path"+csvFieldSeparator+"Original"+csvFieldSeparator+"Ext"+csvFieldSeparator+"Diff");
				for ( KPath path : all ){
					double p = result.getPercentage(path);
					double pext = resultExt.getPercentage(path);
					System.out.println(path+csvFieldSeparator+p+csvFieldSeparator+pext+csvFieldSeparator+(p-pext));
					if ( p == 0 && pext > 0 ){
						missing.add(path);
					}
				}
				
				//Print missing
				System.out.println("Paths not present in the original trace");
				for ( KPath path : missing ){
					System.out.println(path.toString().replace("#", " : *\\n"));
				}
				
				
				
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}




	public ProbabilisticAnalyzerResult analyze( File traceFile ) throws FileNotFoundException {
		TraceParser fileParser = new FlyweigthKbhParser(traceFile.getAbsolutePath());
		Iterator<VectorTrace> it = fileParser.getTraceIterator();
		ProbabilisticAnalyzerResult result = new ProbabilisticAnalyzerResult();
		
		while ( it.hasNext() ){
			Trace trace = it.next();
			ProbabilisticAnalyzerResult traceResult = analyze(trace);
			result.addResults(traceResult);
		}
		
		return result;
	}



	private ProbabilisticAnalyzerResult analyze(Trace trace) {
		ProbabilisticAnalyzerResult r = new ProbabilisticAnalyzerResult();
		Iterator<String> sit = trace.getSymbolIterator();
		KPath kpath = new KPath(pathLen);
		
		while( sit.hasNext() ){
			String symbol = sit.next();
			kpath.nextStep(symbol);
			r.addPath(kpath);
		}
		
		return r;
	}



	public int getThreadN() {
		return threadN;
	}



	public void setThreadN(int threadN) {
		this.threadN = threadN;
	}

	
}
