package it.unimib.disco.lta.alfa.eventsDetection.slct;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


/**
 * This class preprocess generic log files in which each event is recorded in a single line, and it creates a log that can be processed by slct:
 * replaces with white spaces: , | [ ] ; @ = ( ) { } :
 * forces separation of neighbour numerical values (putting a , between them)
 * replaces with one single white space character all sequences of withe space and tabs
 * 		
 * single | in one line are rewritten down		
 * 		
 * 
 * 
 * @author Fabrizio Pastore fabrizio.pastore AT gmail.com
 *
 */
public class GenericLogToSlctPreprocessor {
	private String separator = " ";
	protected int offset=0;
	private String numbersSeparator = ",";
	private String traceSeparator = "|";
	private ArrayList<ReplaceRule> replacementRules = new ArrayList<ReplaceRule>();
	private int offsetPost;
	private int truncate = -1;
	private int tail = -1;
	private ArrayList<ReplaceRule> replacemenRegextRules = new ArrayList<ReplaceRule>();
	
	public static class ReplaceRule{
		private String original;
		private String replacement;

		ReplaceRule(String original,String replacement){
			this.original = original;
			this.replacement = replacement;
		}

		public String getReplacement() {
			return replacement;
		}

		public void setReplacement(String replacement) {
			this.replacement = replacement;
		}

		public String getOriginal() {
			return original;
		}

		public void setOriginal(String original) {
			this.original = original;
		}
	}
	
	public static void main(String args[]){
		String offset = null;
		ArrayList<File> inputs = new ArrayList<File>();
		GenericLogToSlctPreprocessor p = new GenericLogToSlctPreprocessor();
		
		for( int i = 0; i < args.length-1; ++i){
			
			if ( args[i].equals("-offset") ){
				offset=args[++i];
			} else if ( args[i].equals("-offsetPost") ) {
				p.setOffsetPost(Integer.valueOf(args[++i]).intValue());
			} else if ( args[i].equals("-separator") ) {
				p.setSeparator(args[++i]);
			} else if ( args[i].equals("-truncate") ) {
				p.setTruncate(Integer.valueOf(args[++i]));
			} else if ( args[i].equals("-numberSeparator") ) {	//set the string which is but betwee two numbers separated by white spaces
				p.setNumbersSeparator(args[++i]);
			} else if ( args[i].equals("-replacement") ) {	//set the string which is but betwee two numbers separated by white spaces
				p.addReplacementRule(args[++i],args[++i]);
			} else {
				inputs.add(new File(args[i]));
			}
			
		}
		
		File output = new File(args[args.length-1]);
		
		if ( offset != null ){
			p.setOffset(Integer.valueOf(offset));
		}
		
		
		
		
		try {
			p.process(inputs,output);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}



	/**
	 * Set the offset that must be removed after processing user defined rules 
	 * @param offsetPost
	 */
	public void setOffsetPost(int offsetPost) {
		this.offsetPost = offsetPost;
		
	}

	public void addReplacementRule(String string, String replacement) {
		replacementRules.add(new ReplaceRule(string,replacement));
	}

	
	public void addReplacementRegexRule(String string, String replacement) {
		replacemenRegextRules.add(new ReplaceRule(string,replacement));
	}
	
	
	protected void process(ArrayList<File> inputs, File output) throws IOException {
		
		BufferedWriter wr = new BufferedWriter(new FileWriter(output));
		
		
		try{
			for ( int i = 0; i < inputs.size(); ++i ){
				try {
					File input = inputs.get(i);
					
					if ( i>0 ){
						wr.write("|\n");
					}
					BufferedReader br = new BufferedReader(new FileReader(input));
					process(br,wr);

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
	
	protected void process(BufferedReader br, BufferedWriter wr) throws IOException {
		
		
		String line;

		
		while ( ( line = br.readLine() ) != null ){
			processLine(line,wr);
		}
		br.close();
	}
	
	protected void processLine(String line,BufferedWriter wr) throws IOException{
			
			if ( line.length() == 0 )
				return;
			
			String result = getResult(line);
			wr.write(result);
			wr.write("\n");
	}
	
	protected String getResult( String line ){
			
			if ( line.equals(traceSeparator) ){
				return traceSeparator;
			}
			
			if ( truncate > 0 && line.length() > truncate ){
				line = line.substring(0,truncate);
			}
			
//			System.out.println(line.length());
//			System.out.println(line);

			
			try{
				//line = line.substring(offset);
				//System.out.println(" -"+line);
				
				
				
				if ( tail > 0 ){
					line = line.substring(0,line.length()-tail);
				}
				
			} catch ( RuntimeException e ){
				System.out.println(" "+line.length()+" "+line);
				throw e;
			}
			
//			System.out.println("Replacement rules");
			//first apply user defined replacement rules
			for ( ReplaceRule r : replacementRules ){
				//System.out.println("REPLACE "+r.getOriginal()+" "+line);
				line = line.replace(r.getOriginal(), r.getReplacement());
				//System.out.println("REPLACED "+line);
			}
			
			
			for ( ReplaceRule r : replacemenRegextRules ){
				System.out.println("REPLACE "+r.getOriginal()+" with "+r.getReplacement()+" "+line);
				line = line.replaceAll(r.getOriginal(), r.getReplacement());
				//System.out.println("REPLACED "+line);
			}
			
			try {
				line = line.substring(offsetPost);
			} catch ( RuntimeException e ){
				System.err.println("Exception occurred ");
				System.err.println("Line: "+line);
				if ( line != null ){
					System.err.println("Length "+line.length()+" substring "+offsetPost);
				}
				throw e;
			}
			
//			System.out.flush();
//			System.out.println("Default Replacements");
			String result = line
				.replace("|", " ")
				.replace("[", " ")
				.replace("]", " ")
				.replace(";", " ")
				.replace("#", " ")
				.replace("@", " ")
				.replace("=", " ")
				.replace("(", " ")
				.replace(")", " ")
				.replace("\\", "\\\\")
				.replace("{", " ")
				.replace("}", " ")
				.replace(":", " ")
				.replaceAll("\\s+", separator)			//leave after the others, replace every (generated) sequence of white spaces with a single one
				.replaceAll("([0-9]) ([0-9])", "$1 , $2"); //leave last, need to replaces long white space sequences before
			
			
			return result;
		
	}

	protected boolean isStart(String line) {
		return true;
	}

	public String getSeparator() {
		return separator;
	}

	public void setSeparator(String separator) {
		this.separator = separator;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public String getNumbersSeparator() {
		return numbersSeparator;
	}

	public void setNumbersSeparator(String numbersSeparator) {
		this.numbersSeparator = numbersSeparator;
	}



	public String getTraceSeparator() {
		return traceSeparator;
	}



	public void setTraceSeparator(String traceSeparator) {
		this.traceSeparator = traceSeparator;
	}



	public int getTruncate() {
		return truncate;
	}



	public void setTruncate(int truncate) {
		this.truncate = truncate;
	}



	public int getTail() {
		return tail;
	}



	public void setTail(int tail) {
		this.tail = tail;
	}
}
