package it.unimib.disco.lta.alfa.frequentItemsetsAnalysis;

import it.unimib.disco.lta.alfa.frequentItemsetsAnalysis.WindowSequenceAnalyzer.MatrixManager;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.Map.Entry;


/**
 * This class generates a CSV file in which in each line there is a window of elements of the log file.
 * Each window is moved of 1 char.
 * 
 * 
 * @author Fabrizio Pastore fabrizio.pastore AT gmail.com
 *
 */
public class WindowElementsAnalyzer extends WindowSequenceAnalyzer {

	public static class WindowElementsMatrixManager extends WindowSequenceAnalyzer.MatrixManager {
		private String prefix = null;
		
		public WindowElementsMatrixManager(String separator, List<Integer> eventColumns) {
			super(separator, eventColumns);
		}

		public WindowElementsMatrixManager(String prefix2, String separator,
				List<Integer> eventColumns) {
			this(separator,eventColumns);
			this.prefix = prefix2;
		}

		public String getMatrixLine(List<String> events){
			
			StringBuffer b = new StringBuffer();
			for(int i = 0; i < events.size(); ++i){
				String event = events.get(i);
				//System.out.println(event);
				if ( i != 0 ){
					b.append(getSeparator());
				}
				if ( prefix != null ){
					b.append(prefix);
				}
				b.append(matrix.getEventPos(event));
			}
			
			return b.toString();
		}


		
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		WindowElementsAnalyzer csvTableGenrator = new WindowElementsAnalyzer();
		
		for (int i=0;i< args.length-2;++i){
			if ( i >= args.length-3 ){
				System.err.println("Parameter needed for option: "+args[i]);
				System.exit(-1);
			}
			if ( args[i].equals("-eventColumns") ){
				for ( String column : args[++i].split(",") ){
					Integer cn = Integer.valueOf(column);
					csvTableGenrator.addEventColumn(cn);
				}
			} else if ( args[i].equals("-windowSize") ){
				csvTableGenrator.setWindowSize(Integer.valueOf(args[++i]));
			} else if ( args[i].equals("-separator") ){
				csvTableGenrator.setSeparator(args[++i]);
			} else if ( args[i].equals("-prefix") ){
				csvTableGenrator.setPrefix(args[++i]);
			} else {
				System.err.println("Unknown option: "+args[i]);
				System.exit(-1);
			}
		}
		
		
		File input = new File(args[args.length-2]);
		File output = new File(args[args.length-1]);
		
		if ( output.exists() ){
			System.err.println("File exists "+output.getAbsolutePath());
			System.exit(-1);
		}
		
		
		try {
			
			Matrix mm = csvTableGenrator.process(input,output);
			
			for ( Entry<Integer,String> entry : mm.getEntries()){
				System.out.println(entry.getKey()+" = "+entry.getValue());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	private String prefix = null;

	private void setPrefix(String value) {
		this.prefix = value;
	}

	@Override
	protected it.unimib.disco.lta.alfa.frequentItemsetsAnalysis.WindowSequenceAnalyzer.MatrixManager createMatrixManager() {
		return new WindowElementsMatrixManager(prefix, getSeparator(),getEventColumns());
	}
	




}