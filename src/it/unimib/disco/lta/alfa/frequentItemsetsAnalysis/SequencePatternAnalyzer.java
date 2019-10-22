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
import java.util.Queue;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.Map.Entry;

import javax.swing.JPopupMenu.Separator;


/**
 * This class analyzes a log file and search how many times given pattern is present
 * @author Fabrizio Pastore fabrizio.pastore AT gmail.com
 *
 */
public class SequencePatternAnalyzer {
	private String separator = ",";
	private ArrayList<Integer> eventColumns = new ArrayList<Integer>();
	private List<String> eventsPattern;
	private String firstPatternElement;
	private int windowSize = 10;
	private boolean onlyPatternEvents = false;
		
	public static class SequencePatternMatcher{
		private List<String> eventsPattern;
		private int curPos = 0;
		private int notMatched = 0;
		private int startingLine;
		private List<String> currentlyNotMatchedEvents = new LinkedList<String>();
		private List<List<String>> notMatchedEvents = new LinkedList<List<String>>();
		
		public List<List<String>> getNotMatchedEvents() {
			return notMatchedEvents;
		}

		public SequencePatternMatcher(List<String> eventsPattern, int startingLine){
			this.eventsPattern = eventsPattern;
			this.startingLine = startingLine;
		}
		
		public int getStartingLine() {
			return startingLine;
		}

		public void setStartingLine(int startingLine) {
			this.startingLine = startingLine;
		}

		public boolean matchEvent( String event ){
			if ( event.equals(eventsPattern.get(curPos)) ){
				if ( currentlyNotMatchedEvents.size() > 0 ){
					notMatchedEvents.add(currentlyNotMatchedEvents);
				}
				currentlyNotMatchedEvents = new LinkedList<String>();
				curPos++;
				return true;
			}
			currentlyNotMatchedEvents.add(event);
			notMatched++;
			return false;
		}
		
		public boolean matchDone(){
			return eventsPattern.size() == curPos;
		}

		public int getNotMatched() {
			return notMatched;
		}
		
		public int getProcessed(){
			return notMatched+curPos;
		}
	}
	
	public static class ItemsetsMatcher extends MatrixManager {
		public int curCount[];
		                
		public ItemsetsMatcher(String separator,
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
		
		SequencePatternAnalyzer csvTableGenrator = new SequencePatternAnalyzer();
		File patternFile = null;
		File nonMatchedSequencesOutput = null;
		
		File taggedLog = null;
		
		for (int i=0;i< args.length-2;++i){
			if ( args[i].equals("-patternOnly") ){
				csvTableGenrator.setOnlyPatternEvents(true);
			}
			else if ( i >= args.length-3 ){
				System.err.println("Parameter needed for option: "+args[i]);
				System.exit(-1);
			}
			else if ( args[i].equals("-eventColumns") ){
				for ( String column : args[++i].split(",") ){
					Integer cn = Integer.valueOf(column);
					csvTableGenrator.addEventColumn(cn);
				}
			} else if ( args[i].equals("-patternFile") ){
				patternFile = new File( args[++i] ); 
			} else if ( args[i].equals("-generateTaggedLog") ){
				taggedLog = new File(args[++i]);
			} else if ( args[i].equals("-nonMatchedSequencesOutput") ){
				nonMatchedSequencesOutput = new File( args[++i] );
			} else if ( args[i].equals("-separator") ){
				csvTableGenrator.setSeparator(args[++i]);
			} else if ( args[i].equals("-windowSize") ){
				csvTableGenrator.setWindowSize(Integer.valueOf(args[++i]));
			} else {
				System.err.println("Unknown option: "+args[i]);
				System.exit(-1);
			}
		}
		
		if ( patternFile == null ){
			System.err.println("-patternFile must be set ");
			System.exit(-1);
		}
		
		File input = new File(args[args.length-2]);
		File output = new File(args[args.length-1]);
		
		if ( output.exists() ){
			System.err.println("File exists "+output.getAbsolutePath());
			System.exit(-1);
		}
		
		
		try {
			csvTableGenrator.loadPattern(patternFile);
			List<SequencePatternMatchingResult> result = csvTableGenrator.process(input);
			
			writeResults( result, output);
			writeNonMatchedSequences( result, nonMatchedSequencesOutput, csvTableGenrator.getEventsPattern());
			
			if ( taggedLog != null ){
				csvTableGenrator.generateTaggedLog( result, input, taggedLog );
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public void generateTaggedLog(
			List<SequencePatternMatchingResult> result, 
			File input,
			File taggedLog) throws IOException {
		
		MatrixManager mm = new ItemsetsMatcher(separator,eventColumns);			
		mm.learn(input);
		
		
		HashMap<Integer,SequencePatternMatchingResult> matchersMap = new HashMap<Integer,SequencePatternMatchingResult>();
		for ( SequencePatternMatchingResult matchResult : result ){
			matchersMap.put(matchResult.getStartingLine(), matchResult);
		}
		
		
		BufferedReader fir = new BufferedReader( new FileReader(input) );
		BufferedWriter wb = new BufferedWriter( new FileWriter(taggedLog));
		
		HashSet<String> keyEvents = new HashSet<String>();
		keyEvents.addAll(eventsPattern);
		
		
		String line;
		List<SequencePatternMatcher> matchers = new LinkedList<SequencePatternMatcher>();
		
		
		int curLine = 0;
		while ( ( line = fir.readLine() ) != null ){
			curLine++;
			
			//check if from this line a pattern matcher works 
			SequencePatternMatchingResult newmatcher = matchersMap.get(curLine);
			if ( newmatcher != null ){
				matchers.add(new SequencePatternMatcher(eventsPattern,curLine));
			}
			
			if ( line.length() == 1 && line.equals("|") ){ //New trace, reset
				matchers.clear();
			}
			
			
			
			String event = mm.getEvent(line);
			
			
			if ( onlyPatternEvents  && ( ! keyEvents.contains(event) ) ){
				continue;
			}
			
			StringBuffer tags = new StringBuffer();
			List<Integer> toRemove = new LinkedList<Integer>();
			
			boolean first = true;
			for ( int i = 0; i < matchers.size(); ++i ){
				SequencePatternMatcher matcher = matchers.get(i);
				if ( matcher.matchEvent(event) ){
					if ( first ){
						first = false;
					} else {
						tags.append(":");
					}
					tags.append("M"+matcher.getStartingLine());
				}
				
				if ( matcher.matchDone() ){
					toRemove.add(i);
				}
				
			}
			
			
			//Remove elements to be removed
			for ( int i = toRemove.size()-1; i >=0 ; i--){
				matchers.remove(toRemove.get(i).intValue());
			}
			
			
			wb.write(tags.toString());
			wb.write(separator);
			wb.write(line);
			wb.write("\n");
		}
		
		wb.close();
		
	}

	private static void writeNonMatchedSequences(
			List<SequencePatternMatchingResult> results,
			File nonMatchedSequencesOutput, List<String> eventsPattern) throws IOException {
		
		HashMap<String,Integer> anomalousSequences = new HashMap<String, Integer>();
		
		for ( SequencePatternMatchingResult result : results ){
			
			for( Entry<String,Integer> notMatched : result.getNotMatchedSequences().entrySet() ){
				Integer value = anomalousSequences.get(notMatched.getKey());
				if ( value == null ){
					anomalousSequences.put(notMatched.getKey(), 1);
				} else {
					anomalousSequences.put(notMatched.getKey(), value+notMatched.getValue());
				}
			}
			
			
		}
		
		Set<String> patternElements = new HashSet<String>();
		patternElements.addAll(eventsPattern);
		
		BufferedWriter bw = new BufferedWriter( new FileWriter(nonMatchedSequencesOutput) );
		bw.write("Non Matching Sequence,Times of occurrence,Events in The Pattern,New Events\n");
		for ( Entry<String,Integer> notMatched : anomalousSequences.entrySet() ){
			String sequence = notMatched.getKey();
			HashSet<String> sequenceKeys = new HashSet<String>();
			
			for ( String key : sequence.split(" ")){
				sequenceKeys.add(key);
			}
			
			int allEvents = sequenceKeys.size();
			
			
			sequenceKeys.retainAll(patternElements);
			
			int patternEvents = sequenceKeys.size();
			int nonPatternEvents = allEvents - patternEvents;
			
			
			bw.write(sequence+","+notMatched.getValue()+","+patternEvents+","+nonPatternEvents+"\n");
		}
		bw.close();
	}

	private static void writeResults(
			List<SequencePatternMatchingResult> results, File output) throws IOException {
		BufferedWriter bw = new BufferedWriter( new FileWriter(output) );
		bw.write("Starting Line, Window size, Overlapped by, Overlapping,  Non Matching Sequences\n");
		for ( SequencePatternMatchingResult result : results ){
			
			HashMap<String, Integer> notMatched = result.getNotMatchedSequences();
			
			bw.write(result.getStartingLine()+","+
					result.getWindowSize()+","+
					result.getOverlappingSequences()+","+
					result.getOverlappedSequences()+","+
					notMatched.size()+
					"\n");
		}
		bw.close();
	}

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
	
	public void loadPattern (File patternFile) throws IOException{
		MatrixManager mm = new ItemsetsMatcher(separator,eventColumns);			
		mm.learn(patternFile);
		
		BufferedReader fir = new BufferedReader( new FileReader(patternFile) );
		eventsPattern = new ArrayList<String>();
		 
		String line;
		while ( ( line = fir.readLine() ) != null ){
			String event = mm.getEvent(line);
			eventsPattern.add(event);
		}
		
		firstPatternElement = eventsPattern.get(0);
	}

	public List<SequencePatternMatchingResult> process(File input) throws IOException {
			
			MatrixManager mm = new ItemsetsMatcher(separator,eventColumns);			
			mm.learn(input);
			
			BufferedReader fir = new BufferedReader( new FileReader(input) );
			
			Set<String> keyEvents = new HashSet<String>();
			keyEvents.addAll(eventsPattern);
			
			String line;
			List<SequencePatternMatcher> matchers = new LinkedList<SequencePatternMatcher>();
			List<SequencePatternMatchingResult> matchingResult = new LinkedList<SequencePatternMatchingResult>();
			
			int curLine = 0;
			while ( ( line = fir.readLine() ) != null ){
				curLine++;
				
				if ( line.length() == 1 && line.equals("|") ){ //New trace, reset
					matchers.clear();
					
				}
				
				String event = mm.getEvent(line);
				
				if ( onlyPatternEvents  && ( ! keyEvents.contains(event) ) ){
					continue;
				}
				
				
				
				
				if ( isStartOfPattern(event) ){
					SequencePatternMatcher spm = new SequencePatternMatcher(eventsPattern,curLine);
					matchers.add(spm);
				}
				
				List<Integer> toRemove = new ArrayList<Integer>();
				
				int cur = 0;
				for ( SequencePatternMatcher spm : matchers ){
					spm.matchEvent(event);
					
					
					if ( spm.matchDone() ){
						SequencePatternMatchingResult spmr = new SequencePatternMatchingResult(spm.getStartingLine(),spm.getProcessed());
						matchingResult.add(spmr);
						
						for ( List<String> sequence : spm.getNotMatchedEvents() ){
							
							String seqString = transformSequenceToString(sequence);
							
							spmr.addNotMatchedSequence( seqString );
						}
						
						toRemove.add(cur);
					} else if ( spm.getProcessed() > windowSize ){
						toRemove.add(cur);
					}
					
					cur++;	
				}
				
				
				//Remove elements to be removed
				for ( int i = toRemove.size()-1; i >=0 ; i--){
					matchers.remove(toRemove.get(i).intValue());
				}
			}
			
			
			List<SequencePatternMatchingResult> active = new LinkedList<SequencePatternMatchingResult>();
			
			for ( SequencePatternMatchingResult result : matchingResult ){
				int curStarting = result.getStartingLine();
				
				
				for ( int i = active.size()-1; i >= 0 ; i-- ){
					SequencePatternMatchingResult element = active.get(i);
				
					if ( element.getEndingLine() < curStarting ){
						active.remove(i);
					} else {
				
						element.setOverlappedSequences(element.getOverlappedSequences()+1);
					}
				}
				
				
				
				result.setOverlappingSequences(active.size());
				
				active.add(result);
				
			}
			
			

			return matchingResult;
			
	}



	private String transformSequenceToString(List<String> sequence) {
		StringBuffer sb = new StringBuffer();
		for ( String event : sequence ){
			sb.append(event);
			sb.append(" ");
		}
		return sb.toString();
	}

	private boolean isStartOfPattern(String event) {
		return firstPatternElement.equals(event);
	}

	public int getWindowSize() {
		return windowSize;
	}

	public void setWindowSize(int windowSize) {
		this.windowSize = windowSize;
	}

	public List<String> getEventsPattern() {
		return eventsPattern;
	}

	public void setEventsPattern(List<String> eventsPattern) {
		this.eventsPattern = eventsPattern;
	}

	public boolean isOnlyPatternEvents() {
		return onlyPatternEvents;
	}

	public void setOnlyPatternEvents(boolean onlyPatternEvents) {
		this.onlyPatternEvents = onlyPatternEvents;
	}
}