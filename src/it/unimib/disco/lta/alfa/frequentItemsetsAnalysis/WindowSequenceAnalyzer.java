package it.unimib.disco.lta.alfa.frequentItemsetsAnalysis;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.Map.Entry;

public abstract class WindowSequenceAnalyzer {

	public static class Matrix{
		private HashMap<String,Integer> words = new HashMap<String, Integer>();
		private HashMap<Integer,String> ids = new HashMap<Integer,String>();
		
		public Integer getEventPos(String event){
			return words.get(event);
		}
		
		public Set<Entry<Integer, String>> getEntries(){
			return ids.entrySet();
		}
		
		public String getEventFromId( int id ){
			return ids.get(id);
		}

		public void put(String event, int pos) {
			words.put(event, pos);
			ids.put(pos,event);
		}

		public int getWordsNumber() {
			return words.size();
		}
	}
	
	public static abstract class MatrixManager{
		protected Matrix matrix = new Matrix();
		private String separator;
		private List<Integer> eventColumns = new ArrayList<Integer>();
		
		public abstract String getMatrixLine(List<String> events);
		
		
		public MatrixManager( String separator, List<Integer> eventColumns ){
			this.separator = separator;
			this.eventColumns.addAll(eventColumns);
		}

		
		public String getEvent(String line) {
			
			String splitted[] = line.split(separator);
			StringBuffer result = new StringBuffer();
			for( int eventColumn : eventColumns){
				
				if ( splitted.length <= eventColumn )
					return null;
				result.append(splitted[eventColumn]);
			}
			
			return result.toString();
		}
		
		public void learn(File input) throws IOException {
			BufferedReader fir = new BufferedReader( new FileReader(input) );
			
			String line;
			Set<String> events = new HashSet<String>();
			while( ( line = fir.readLine() ) != null ){
				String event = getEvent(line);
				
				if ( event != null ){
					events.add(event);
				}
			}
			
			int pos = 0;
			for ( String event : events ){
				matrix.put(event, pos);
				++pos;
			}
			
			fir.close();
		}


		public Matrix getMatrix() {
			return matrix;
		}


		public String getSeparator() {
			return separator;
		}


		public List<Integer> getEventColumns() {
			return eventColumns;
		}
		

	}
	

	private String separator = ",";
	private List<Integer> eventColumns = new ArrayList<Integer>();
	private int windowSize = 10;




	public String getSeparator() {
		return separator;
	}

	public void setSeparator(String separator) {
		this.separator = separator;
	}

	public List<Integer> getEventColumns() {
		ArrayList<Integer> res = new ArrayList<Integer>();
		res.addAll(eventColumns);
		
		return res;
	}

	public void addEventColumn(int eventColumn) {
		this.eventColumns.add(eventColumn);
	}

	public int getWindowSize() {
		return windowSize;
	}

	public void setWindowSize(int windowSize) {
		this.windowSize = windowSize;
	}

	public Matrix process(File input,File output) throws IOException {
		
			
				MatrixManager mm = createMatrixManager();			
				mm.learn(input);
				
				BufferedReader fir = new BufferedReader( new FileReader(input) );
				BufferedWriter fw = new BufferedWriter ( new FileWriter(output) );
				
				LinkedList<String> events = new LinkedList<String>();
				
				processHeader(mm,fw);
				
				processFirstWindow(fw,fir,mm,events);
				
				boolean fileEnd;
				do{
					fileEnd = processTrace(fw,fir,mm,events);
				}while(!fileEnd);
				
				fir.close();
				fw.close();
				return mm.getMatrix();
			
		
	}



	protected void processHeader(MatrixManager mm, BufferedWriter fw) throws IOException {
		
	}

	protected abstract MatrixManager createMatrixManager();

	private boolean processTrace(BufferedWriter fw, BufferedReader fir,
			MatrixManager mm, LinkedList<String> events) throws IOException {
		String line;
		while( ( line = fir.readLine() ) != null ){
			if ( line.equals("|")){
				return false;
			}
			events.remove();
			events.add(mm.getEvent(line));
			//System.out.println(line);
			fw.write(mm.getMatrixLine(events));
			fw.write('\n');
		}
		return true;
	}

	private void processFirstWindow(BufferedWriter fw, BufferedReader fir, MatrixManager mm,
			LinkedList<String> events) throws IOException {
		events.clear();
		for ( int i = 0 ; i < windowSize; ++i ){
			String line = fir.readLine();
			events.add( mm.getEvent(line) );
		}
		
		fw.write(mm.getMatrixLine(events));
		fw.write("\n");
	}
	
}