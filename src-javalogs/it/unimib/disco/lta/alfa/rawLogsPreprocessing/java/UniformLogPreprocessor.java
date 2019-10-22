package it.unimib.disco.lta.alfa.rawLogsPreprocessing.java;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class UniformLogPreprocessor {
	private String separator = " ";
	private int offset=0;
	
	public static void main(String args[]){
		String offset = null;
		ArrayList<File> inputs = new ArrayList<File>();
		
		for( int i = 0; i < args.length-1; ++i){
			
			if ( args[i].equals("-offset") ){
				offset=args[++i];
			} else {
				inputs.add(new File(args[i]));
			}
			
		}
		
		File input = new File(args[args.length-2]);
		File output = new File(args[args.length-1]);
	
		
		UniformLogPreprocessor p = new UniformLogPreprocessor();
		
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

	private void process(ArrayList<File> inputs, File output) throws IOException {
		
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

	private void process(File input, File output) throws IOException {
		ArrayList<File> inputs = new ArrayList<File>();
		inputs.add(input);
		
	}
	
	private void process(BufferedReader br, BufferedWriter wr) throws IOException {
		
		
		String line;
		
		while ( ( line = br.readLine() ) != null ){
			if ( line.length() == 0 )
				continue;
			if ( line.startsWith("[") ){
				wr.write("\n");
				line = line.substring(offset);
				
			} else {
				//do nothing we attach nested lines to the end of the previous line
				//	[#|2008-03-27T18:39:16.217+0100|SEVERE|GlassFish10.0|javax.enterprise.system.tools.admin|_ThreadID=19;_ThreadName=Thread-4;|The log message is null.
				// 		java.util.ConcurrentModificationException
		        //		at java.util.HashMap$HashIterator.nextEntry(HashMap.java:793)
		        //		at java.util.HashMap$ValueIterator.next(HashMap.java:822)
				// will become:
				//	[#|2008-03-27T18:39:16.217+0100|SEVERE|GlassFish10.0|javax.enterprise.system.tools.admin|_ThreadID=19;_ThreadName=Thread-4;|The log message is null.	java.util.ConcurrentModificationException	at java.util.HashMap$HashIterator.nextEntry(HashMap.java:793)	at java.util.HashMap$ValueIterator.next(HashMap.java:822)
			
			}
			
			String result = line
				.replace("|", separator)
				.replace("[", separator)
				.replace("]", separator)
				.replace(";", separator)
				.replace("#", separator)
				.replace("@", separator)
				.replace("=", separator)
				.replace("(", separator)
				.replace(")", separator)
				.replaceAll("\\s+", " ");
			wr.write(result);
			
		}
		
		wr.write("\n");
		
		br.close();
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
}
