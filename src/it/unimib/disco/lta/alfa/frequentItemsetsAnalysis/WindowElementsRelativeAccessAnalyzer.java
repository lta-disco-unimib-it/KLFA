package it.unimib.disco.lta.alfa.frequentItemsetsAnalysis;

import it.unimib.disco.lta.alfa.frequentItemsetsAnalysis.WindowSequenceAnalyzer.Matrix;
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
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.Map.Entry;

import javax.swing.JPopupMenu.Separator;


/**
 * This class analyzes a log file and creates a CSV in which in each line a window of elements is represented.
 * Each window is moved of 1 char with respect to the previous one.
 * The windows is not written by the name of the elemnts, but by true/false values that indicate if an element is present in the window.
 * 
 * @author Fabrizio Pastore fabrizio.pastore AT gmail.com
 *
 */
public class WindowElementsRelativeAccessAnalyzer extends WindowSequenceAnalyzer{

	public class WindowElementsHitMatrixManager extends MatrixManager {
		public int curCount[];
		                
		public WindowElementsHitMatrixManager(String separator,
				List<Integer> eventColumns) {
			super(separator, eventColumns);
			
			
		}

		public String getMatrixLine(List<String> events){
			initRAElements();
			boolean line[] = new boolean[matrix.getWordsNumber()];
			
			int pos = matrix.getEventPos(events.get(events.size()-1));
			
			for ( int i = 0; i < curCount.length; ++i ){
				int value = curCount[i];
				if ( value != -1 ){
					curCount[i]=value+1;
				}
			}
			curCount[pos]=0;
			
			
			
			StringBuffer b = new StringBuffer();
			for(int i = 0; i < line.length; ++i){
				if ( i != 0 ){
					b.append(getSeparator());
				}
				b.append(String.valueOf(curCount[i]));
			}
			
			return b.toString();
		}

		private void initRAElements() {
			if ( curCount != null ){
				return;
			}
			curCount = new int[matrix.getWordsNumber()];
			for ( int i = 0; i < curCount.length; ++i ){
				curCount[i]=-1;
			}
		}
		
	}


	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		WindowElementsRelativeAccessAnalyzer csvTableGenrator = new WindowElementsRelativeAccessAnalyzer();
		
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
			csvTableGenrator.process(input,output);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}



	protected void processHeader(MatrixManager mm, BufferedWriter fw) throws IOException {
		
		Matrix matrix = mm.getMatrix();
		
		SortedMap<Integer, String> sm = new TreeMap<Integer, String>();
		for ( Entry<Integer, String> entry : matrix.getEntries()  ){
			sm.put(entry.getKey(),entry.getValue());	
		}
		
		boolean first = true;
		for ( Entry<Integer, String> entry : sm.entrySet() ){
			if ( ! first ){
			fw.write(mm.getSeparator());
			} else {
				first = false;
			}
			fw.write(entry.getValue());
			
		}
		fw.write("\n");
	}




	@Override
	protected MatrixManager createMatrixManager() {
		return new WindowElementsHitMatrixManager(getSeparator(), getEventColumns());
	}
}