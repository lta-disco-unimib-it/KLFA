package it.unimib.disco.lta.alfa.preprocessing.rawEventsSeparation;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * This class preprocess a generic log, putting one event per line.
 * By default a line is considered a new event description if it starts with a char and not with a blank ( space or tab)
 * 
 * @author Fabrizio Pastore fabrizio.pastore AT gmail.com
 *
 */
public class RegexBasedRawEventsSeparator {
	private String eventStartExpression = "^\\S.*";
	//JBOSS line start [0-9][0-9][0-9][0-9]-[0- 9][0-9]-[0-9][0-9] [0-9][0-9]:[0-9][0-9]:[0-9][0-9],[0-9][0-9][0-9] .*"
	private String lineSeparator = " ";

	
	public static void main(String args[]){
		ArrayList<File> inputs = new ArrayList<File>();
		RegexBasedRawEventsSeparator p = new RegexBasedRawEventsSeparator();
		
		if ( args.length <= 1 ){
			printHelp();
			System.exit(-1);
		}
		
		for( int i = 0; i < args.length-1; ++i){
			if ( args[i].equals("-eventStartExpression") ){
				p.setEventStartExpression(args[++i]);
			} else if ( args[i].equals("-lineSeparator") ){
					p.setLineSeparator(args[++i]);
			} else if ( args[i].equals("-help") || args[i].equals("-h") ) {
				printHelp();
				System.exit(-1);
			}else {
				
				File file = new File(args[i]);
				if ( file.isDirectory() ){
					inputs.addAll( retrieveFilesInDirectory( file ) );
				} else {
					inputs.add(file);
				}
			}
			
		}
		
		File output = new File(args[args.length-1]);
		
		
		try {
			p.process(inputs,output);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}


	/**
	 * Returns a list with all the files in the given directory
	 * @param file
	 * @return
	 */
	private static Collection<? extends File> retrieveFilesInDirectory(File file) {
		File[] files = file.listFiles();
		
		ArrayList<File> list = new ArrayList<File>();
		
		for( File f : files ){
			list.add(f);
		}
		
		return list;
	}



	private static void printHelp() {
		System.out.println("This program takes in input a log file, or a collection of log files and preprocess them, according to user define rules, in order to have" +
				"distinct log messages on single lines. In case multiple files are passed their messages are saparated by a line containing the | char." +
				"\nUsage:\n\t" +
				RegexBasedRawEventsSeparator.class.getCanonicalName()+" [OPTIONS] <inputlog> [<inputlog>+] <outputLog>" +
						"\nOptions: " +
						"\n\t-eventStartExpression <regex>: indicate how logging messages starts, this permit put distinct messages in distinct lines" +
						"\n\t-lineSeparator <string>: when merging two lines into one use <string> to replace the newline char. Default is the empty string." +
						"\n\t-help: print this help and exit" +
						"\n\t-h: print this help and exit" +
						"\n\n\n");
		
		
	}

	public void process(List<File> files, File output) throws IOException {
		
		BufferedWriter wr = new BufferedWriter(new FileWriter(output));
		
		
		try{
			for ( int i = 0; i < files.size(); ++i ){
				try {
					File input = files.get(i);
					
					
					BufferedReader br = new BufferedReader(new FileReader(input));
					
					process(br,wr);
					br.close();
					
					//if ( i>0 ){
						wr.write("|");
						wr.newLine();
						System.out.print("DDD |\n");
					//}
					
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}finally{
			wr.close();
		}
	}

	public void process(File input, File output) throws IOException {
		ArrayList<File> inputs = new ArrayList<File>();
		inputs.add(input);
		process(input,output);
	}
	
	public void process(BufferedReader br, BufferedWriter wr) throws IOException {
		
		
		String line;
		boolean first = true;
		
		while ( ( line = br.readLine() ) != null ){
			
			if ( line.length() == 0 )
				continue;
			
			if ( !first ) {
				if ( isStart(line) ){
					System.out.println("START ");
					wr.newLine();
				} else {
					wr.write( lineSeparator  );
					
				}
			}
			first =false;
			
			wr.write(line);
			System.out.println("DDD "+line);
		}
		wr.newLine();
		
		
		
	}

	protected boolean isStart(String line) {
		return line.matches(eventStartExpression );
	}

	public String getEventStartExpression() {
		return eventStartExpression;
	}

	public void setEventStartExpression(String eventStartExpression) {
		this.eventStartExpression = eventStartExpression;
	}



	public String getLineSeparator() {
		return lineSeparator;
	}



	public void setLineSeparator(String lineSeparator) {
		this.lineSeparator = lineSeparator;
	}
}
