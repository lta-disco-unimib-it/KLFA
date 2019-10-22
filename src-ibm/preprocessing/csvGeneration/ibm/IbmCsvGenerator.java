package preprocessing.csvGeneration.ibm;

import it.unimib.disco.lta.alfa.eventsDetection.EventTypesDetector;
import it.unimib.disco.lta.alfa.eventsDetection.EventTypesDetectorException;
import it.unimib.disco.lta.alfa.eventsDetection.SingleLineEventTypesDetector;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;


public class IbmCsvGenerator implements EventTypesDetector, SingleLineEventTypesDetector {
	
	
	
	private String csvSeparator = ",";

	public IbmCsvGenerator(){
		
	}
	
	public void process(List<File> inputs, File destTrace)
			throws EventTypesDetectorException {
		
		BufferedWriter w;
		try {
			w = new BufferedWriter(new FileWriter(destTrace));
			boolean first = true;
			for ( File file : inputs ){
				if ( ! first ){
					w.write("|\n");
				} else {
					first = false;
				}
				processFile(file,w);
			}
			w.close();
		} catch (IOException e) {
			throw new EventTypesDetectorException(e);
		}
		
	}

	/*
	 * (non-Javadoc)
	 * @see preprocessing.csvGeneration.ibm.SingleLineEventTypesDetector#processLine(java.lang.String)
	 */
	public String processLine (String line){
		String[] elements = line.split(" ");
		String action;
		int start;
		if ( elements[1].equals("takes") ){
			action = elements[1];
			start = 4;
		} else if ( elements[1].equals("waits") ){
			action = elements[1];
			start = 5;
		} else if ( elements[1].equals("interrupts") ){
			action = elements[1];
			start = 4;
		} else if ( elements[1].equals("tries") ){
			action = elements[1];
			start = 6;
		} else if ( elements[1].equals("releases") ){
			action = elements[1];
			start = 4;
		} else if ( elements[1].equals("returns") ){
			action = elements[1];
			start = 7;
		} else {
			action = elements[1];
			start = 3;
		}
		String newLine = createNewCSVLine(action,elements,start);
		return newLine;
	}
	
	private void processFile(File file, BufferedWriter w) throws IOException {
		BufferedReader fr = new BufferedReader( new FileReader(file) );
		String line;
		
		while ( ( line = fr.readLine() ) != null ){
			String newCsvLine = processLine(line);
			w.write(newCsvLine);
			w.write("\n");	
		}
		fr.close();
	}

	/**
	 * This method takes in input the action name, an array with the words of the raw log line, and 
	 * the starting point from which to copy the elements in the new line. 
	 * The resulting line will be made of the action name plus the elements copied.
	 *   
	 * @param action
	 * @param elements
	 * @param start
	 * @return
	 * @throws IOException
	 */
	private String createNewCSVLine( String action, String[] elements, int start) {
		StringBuffer sb = new StringBuffer();
		sb.append("C");
		sb.append(csvSeparator );
		sb.append(action);
		sb.append(csvSeparator );
		sb.append(elements[0]); //Thread
		for ( int i = start; i < elements.length; ++i ){
			if ( i == start+1 )
				continue;
			sb.append(csvSeparator );
			sb.append(elements[i]);
		}
		
		return sb.toString();
	}

	public void process(File inputs, File destTrace)
			throws EventTypesDetectorException {
		List<File> files = new ArrayList<File>(0);
		process(files, destTrace);
		
	}

	public static void main(String[] args){
		IbmCsvGenerator g = new IbmCsvGenerator();
		
		List<File> files = new ArrayList<File>();
		
		for ( int i = 0 ; i < args.length-1; ++i){
			if ( args[i].equals("-csvSeparator") ){
				g.setCsvSeparator(args[++i]);
			} else {
				files.add(new File(args[i]));
			}
		}
		
		File dest = new File(args[args.length-1]);
		
		if ( dest.exists() ){
			System.err.println(dest.getAbsolutePath()+" exists.");
		}
		
		try {
			g.process(files, dest);
		} catch (EventTypesDetectorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getCsvSeparator() {
		return csvSeparator;
	}

	public void setCsvSeparator(String csvSeparator) {
		this.csvSeparator = csvSeparator;
	}

}
