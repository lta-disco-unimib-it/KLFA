package it.unimib.disco.lta.alfa.tools;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * This class is used to generate a trace compatible with BCT inference engines.
 * @author Fabrizio Pastore fabrizio.pastore AT gmail.com
 *
 */
public class TraceBuilder {
	
	
	private boolean putSuffix = false;
	private String suffix = "()";
	
	public static class TraceBuilderException extends Exception{

		public TraceBuilderException(String string) {
			super(string);
		}

		public TraceBuilderException(String string, IOException e) {
			super(string,e);
		}
		
	}
	
	public static void main(String[] args){

		
		if ( args.length < 2 ){
			usage();
			System.exit(-1);
		}
		
		boolean putSuffix = false;
		String suffixString = null;
		
		//Check options
		int i;
		for ( i = 0; i < args.length; ++i ){
			if ( ! args[i].startsWith("-") )
				break;
			if ( args[i].equals("-suffix") ){
				putSuffix = true;
			} else {
				//options with argument check
				if ( i >= args.length -2 ){
					usage();
					System.exit(-1);
				}
				if ( args[i].equals("-suffixString")) {
					suffixString = args[++i];
				}
			}
				
		}
		
		TraceBuilder f = new TraceBuilder();
		
		//Set options
		if ( putSuffix || suffixString != null ){
			System.out.println("Setting suffix "+putSuffix);
			f.setPutSuffix(true);
			if ( suffixString != null ){
				System.out.println("Setting suffix string "+suffixString);
				f.setSuffix(suffixString);
			}
		}
		
		int filesN = args.length - i; 
		if ( filesN < 2 ){
			usage();
			System.exit(-1);
		}
		
		String files[] = new String[filesN];
		System.arraycopy(args, i, files, 0, files.length);
		
		
		try {
			f.process(files);
		} catch (TraceBuilderException e) {
			System.out.println("An error occurred: "+e.getMessage());
			
		}
	}
	
	public void process(String[] files) throws TraceBuilderException{	
		int last = files.length-1;
		File output = new File(files[last]);
		
		if ( output.exists() ){
			throw new TraceBuilderException("Output file "+output+" exists.");
		}
		
		FileWriter writer = null;
		try {
			writer = new FileWriter(output);
			for ( int i = 0; i < last; ++i ){
				File input = new File( files[i] );

				append(input,writer);

			}
		} catch (IOException e) {
			throw new TraceBuilderException("Cannot append trace to "+output,e);
		} finally {
			try {
				if ( writer != null )
					writer.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}


	}

	protected static void usage() {
		System.out.println("This program reads traces with event symbols separated by new line characters " +
				"and generates a new trace in the format undertood by the BCT inference engines:\n" +
				" event symbols are separated by a # while traces are separated by a |." +
				"\n");
		System.out.println("\tThe program receives a list of file names, the last must be the name of the file you want to write to (it must not exist).\n");		
		System.out.println("Usage : \n");
		System.out.println("Formatter [options] <inputFileList> outputFile.\n" +
				"\nOptions are:" +
				"\n\t-suffix : add a suffix at the end of each symbol (necessary in case on names taht can cause nondeterminism);" +
				"\n\t-suffixString <string>: specify a custom sring to add as a suffix;");
		
	}
	
	private void append( File input , FileWriter writer ) throws IOException{
		BufferedReader br = new BufferedReader( new FileReader(input) );
		String line;
		boolean first = true;
		while ( ( line = br.readLine() ) != null ){
			if ( ! first ){
				if ( putSuffix )
					writer.append(suffix);
				writer.append("#");
			} else {
				first = false;
			}
			writer.append(line);
			
		}
		if ( putSuffix )
			writer.append(suffix );
		writer.append("|");
		br.close();
		
		
	}

	public boolean isPutSuffix() {
		return putSuffix;
	}

	public void setPutSuffix(boolean putSuffix) {
		this.putSuffix = putSuffix;
	}

	public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}


}
