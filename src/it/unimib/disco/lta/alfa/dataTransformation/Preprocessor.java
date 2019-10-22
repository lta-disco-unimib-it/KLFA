package it.unimib.disco.lta.alfa.dataTransformation;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.NoSuchElementException;





/**
 * This class manages the trace transformation actions necessary to succesfully apply FSA engines to log files.
 * 
 * @author Fabrizio Pastore fabrizio.pastore AT gmail.com
 *
 */
public class Preprocessor {
	protected Collection<PreproessorRule>	rules = new ArrayList<PreproessorRule>();
	private String notFound = "x\n";
	private boolean writeNotFound = false;
	
	public void addRule( PreproessorRule rule ){
		rules.add(rule);
	}
	
	
	public void process(File input, File output, File etxLog) throws IOException {
		BufferedReader reader = new BufferedReader( new FileReader ( input ) );
		
		PreprocessorLineIterator iterator = new PreprocessorLineIterator( reader );
		
		BufferedWriter writer = new BufferedWriter( new FileWriter( output ));
		BufferedWriter writerLog = new BufferedWriter( new FileWriter( etxLog));
		int counter=0;
		while ( iterator.hasNext() ){
			++counter;
			String line = iterator.next();
			
			boolean accepted = false;
			for ( PreproessorRule rule : rules ){
				//System.out.println(rule.toString());
				if ( rule.accept( line ) ){
					accepted = true;
					String res = rule.process(line, iterator );
					if ( res != null ){
						writer.write(  res +"\n" );
						writerLog.write( ""+counter);
						writerLog.write( " : " );
						writerLog.write( line );
						writerLog.write( "\n" );
					}
					break;
				}
			}
			if ( ! accepted ){
				if ( writeNotFound ){
					writer.write(  notFound + "\n" );
					writerLog.write( ""+counter);
					writerLog.write( " : " );
					writerLog.write( line );
					writerLog.write( "\n" );
				}
			}
		}
		
		reader.close();
		writer.close();
		writerLog.close();
	}
	
	


	public static void main( String[] args ){
		Preprocessor normalizer = new Preprocessor();
		
		ValueTransformer excKeyDispenser = new ValueTransformerNone( );
		
		//KeyDispenser threadKeyDispenser = new KeyDispenserSame( );
		ValueTransformer threadKeyDispenser = new ValueTransformerNone( );
		
		//KeyDispenser tidMonitorNameKeyDispenser = new KeyDispenserRelative( "TM" );
		ValueTransformer tidMonitorNameKeyDispenser = new ValueTransformerNone( );
		//KeyDispenser tidMonitorNameKeyDispenser = new KeyDispenserSame();
		
		//KeyDispenser monitorTagKeyDispenser = new KeyDispenserNone( );
		//KeyDispenser monitorTagKeyDispenser = new KeyDispenserRelative( "MT" );
		ValueTransformer monitorTagKeyDispenser = new ValueTransformerSame(  );
		//KeyDispenser monitorTagKeyDispenser = new KeyDispenserLastAccess( "MT" );
		
//		normalizer.addRule( new ClassLoaderRule() );
		//normalizer.addRule( new ClassLoaderNoName() );
//		normalizer.addRule( new RuntimeRule( threadKeyDispenser, excKeyDispenser ) );
//		normalizer.addRule( new CompilerRule() );
//		normalizer.addRule( new GCRule() );
//		normalizer.addRule( new MemoryRule() );
//		normalizer.addRule( new ThreadRule( tidMonitorNameKeyDispenser, monitorTagKeyDispenser ) );
//		normalizer.addRule( new VMRule() );
		
		normalizer.run(args);
	}
	
	
	public void run( String[] args ) {
		if ( args.length != 2 || args[0] == "--help"){
			printUsage();
			System.exit(-1);
		}
		
		File input = new File( args[0]);
		File output = new File( args[1]);
		File outputLog = new File( args[1]+".extlog");

		
		try {
			process( input, output, outputLog );
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	protected static void printUsage() {
		String msg = " Usage : \n" +
			Preprocessor.class.getSimpleName()+" <input> <output> \n" +
					"\n" +
					"<input> : input file\n" +
					"<output> : file were to write normalized trace.\n";
		System.out.println(msg);
		
	}


	public String getNotFound() {
		return notFound;
	}


	public void setNotFound(String notFound) {
		this.notFound = notFound;
	}

	
}

