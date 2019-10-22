package it.unimib.disco.lta.alfa.probabilisticAnalysis;

import it.unimib.disco.lta.alfa.csv.CsvParsersFactory;
import it.unimib.disco.lta.alfa.csv.CsvReader;
import it.unimib.disco.lta.alfa.utils.MathUtil;
import it.unimib.disco.lta.alfa.utils.TreeDB;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.Map.Entry;


/**
 * This class permits to analyze a csv file and calculate the number of times different subsequences appear.
 * Subsequences are identified considering all the sequences of symbols seen in the file with a length
 * between 2 and K.
 *  
 * @author Fabrizio Pastore fabrizio.pastore AT gmail.com
 *
 */
public class SequencesStatsCalculator {
	//private String separator = ",";
	private int[] elementsToConsider;
	
	private int sequenceSize;
	private LinkedList<String> curSequence = new LinkedList<String>();
	private int checkPointWindowSize;
	
	private Set<String> elementsToIgnore = new HashSet<String>();

	private CsvReader csvReader;
	
	public static class SequencesStatsCalculatorResults{
		private TreeDB<String> treeDB = new TreeDB<String>();
		private List<HashMap<List<String>,Long>> checkPoints = new LinkedList<HashMap<List<String>,Long>>();
		
		public List<HashMap<List<String>, Long>> getStatsPoints() {
			return checkPoints;
		}
		
		public double getTotalCount() {
			return treeDB.getTotalCount();
		}

		public HashMap<List<String>, Long> getSequencesOccurrencies() {
			return treeDB.getStats();
		}
		
		public void addSequence(LinkedList<String> curSequence) {
			treeDB.add(curSequence);
		}

		public void addCheckpoint() {
			checkPoints.add(getSequencesOccurrencies());
		}

		
	}
	
	/**
	 * Constructor
	 * 
	 * @param sequenceSize maximum size of the sub-sequence considered
	 * @param _elementsToConsider csv columns to consider when building the symbols of the sequence
	 */
	public SequencesStatsCalculator( int sequenceSize, List<Integer> _elementsToConsider ){
		this.sequenceSize = sequenceSize;
		elementsToConsider = new int[_elementsToConsider.size()];
		
		for ( int i = 0; i < elementsToConsider.length; ++i ){
			elementsToConsider[i] = _elementsToConsider.get(i);
		}
		
		csvReader = CsvParsersFactory.createNewCsvReader();
	}
	
	public void setStatsPoint(int size){
		checkPointWindowSize = size;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		int sequenceSize = 10;
		int sequenceMinSize = 1;
		int checkPoint = -1;
		ArrayList<Integer> elementsToConsider = new ArrayList<Integer>();
	
		if ( args.length < 1 ){
			printUsage();
		}
		
		for ( int i = 0; i < args.length-1; ++i ){
			if ( args[i].equals("-checkPoint") ){
				checkPoint = Integer.valueOf(args[++i]);
			} else if ( args[i].equals("-subSequenceMaxSize") ){
				sequenceSize = Integer.valueOf(args[++i]);
			} else if ( args[i].equals("-subSequenceMinSize") ){
				sequenceMinSize = Integer.valueOf(args[++i]);
			} else if ( args[i].equals("-elementsToConsider") ){
				for ( String pos : args[++i].split(",") ){
					elementsToConsider.add(Integer.valueOf(pos));
				}
			}
		}
		
		
		List<File> filesToCompare = new ArrayList<File>();
		for ( String filePath : args[args.length-1].split(",")){
			filesToCompare.add(new File(filePath));
		}
		
		HashMap<String,Double> percentages[] = new HashMap[filesToCompare.size()];
		HashMap<String,Integer> allElements = new HashMap<String,Integer>();
		
		int fc = 0;
		for ( File file : filesToCompare ){
			SequencesStatsCalculator sc = new SequencesStatsCalculator( sequenceSize, elementsToConsider );
			if ( checkPoint > 0 ){
				sc.setStatsPoint(checkPoint);
			}
			
			percentages[fc]= new HashMap<String, Double>();
			
			String prefix = fc+"_";
			try {
				SequencesStatsCalculatorResults results = sc.process( file );

				HashMap<List<String>,Long> occ = results.getSequencesOccurrencies();

				BufferedWriter w = new BufferedWriter(new FileWriter(prefix+"checkPoints.txt"));
				List<HashMap<List<String>, Long>> statPoints = results.getStatsPoints();
				w.write("Sequence,Size");
				for ( int i = 0; i < statPoints.size(); ++i ){
					w.write(",Occurrencies("+(i+1)*checkPoint+")");
				}
				w.write("\n");
				
				List<HashMap<String,Long>> statsList = getWritableLists(statPoints,sequenceMinSize);
				Set<String>allKeys = collectAllKeys(statsList);
				
				for ( String key : allKeys ){
					w.write(key);
					w.write(",");
					for ( HashMap<String,Long> statMap : statsList ){
						Long value = statMap.get(key);
						if ( value == null ){
							value = (long)0;
						}
						w.write(value.toString());
						w.write(",");
					}
					
					w.write("\n");

				}
				w.close();
				
				
				List<Double> groups[] = new List[sequenceSize];
				for ( int i = 0; i < sequenceSize; ++i){
					groups[i]=new ArrayList<Double>();
				}

				w = new BufferedWriter(new FileWriter(prefix+"sequencesOccurrencies.txt"));
				w.write("Sequence,Size,Occurrencies\n");
				for ( Entry<List<String>,Long> entry : occ.entrySet() ){
					w.write("\"");
					List<String> sequence = entry.getKey();
					StringBuffer elementNameBuf = new StringBuffer();
					for ( String element : sequence ){
						elementNameBuf.append(element);
						elementNameBuf.append(" ");
					}
					String elementName = elementNameBuf.toString();

					w.write(elementName);
					w.write("\",");
					w.write(""+sequence.size());
					w.write(",");
					w.write(entry.getValue().toString());
					w.write("\n");

					allElements.put(elementName,sequence.size());
					percentages[fc].put(elementNameBuf.toString(),entry.getValue().doubleValue() );

					groups[sequence.size()-1].add(entry.getValue().doubleValue());

				}
				w.close();

				w = new BufferedWriter(new FileWriter(prefix+"groupsOccurrencies.txt"));
				w.write("Sequence Size,Occurrencies Mean,1st Percentile,Median,3rd Percentile,Std Dev\n");
				for ( int i = 0; i < sequenceSize; ++i ){
					double[] values = new double[groups[i].size()];
					int c=0;

					for ( Double value : groups[i] ){
						values[c++]=value;
					}

					w.write((i+1)+","+MathUtil.getMean(values)+","+MathUtil.getPercentile(values, 25)+","+MathUtil.getMedian(values)+","+MathUtil.getPercentile(values, 75)+","+MathUtil.getStdDev(values));
					w.write("\n");
				}
				w.close();
				
//				for ( Entry<String,Double> entry : percentages[fc].entrySet() ){
//					Double val = entry.getValue();
//					entry.setValue(val/groups[allElements.get(entry.getKey())-1].size());
//				}
				

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			fc++;
		}
		
		BufferedWriter w;
		try {
			w = new BufferedWriter(new FileWriter("elementsComparison.txt"));
			for( Entry<String,Integer> element :  allElements.entrySet() ){
				String elementName = element.getKey();
				w.write(elementName);
				w.write(",");
				Integer size = element.getValue();
				w.write(size.toString());
				
				for ( int i = 0; i < filesToCompare.size(); ++i){
					w.write(",");
					
					Double perc = percentages[i].get(elementName);
					
					if ( perc == null ){
						w.write("0");
					} else {
						w.write(perc.toString());
					}
				}
				w.write("\n");
			}
			w.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}


	private static void printUsage() {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Given a list of maps returns a set with all the keys in the maps
	 * @param statsList
	 * @return
	 */
	private static <T extends Object> Set<String> collectAllKeys(
			List<HashMap<String, T>> statsList) {
		HashSet<String> keys = new HashSet<String>();
		for ( HashMap<String,?> map : statsList ){
			keys.addAll(map.keySet());
		}
		return keys;
	}

	public static HashMap<String, Long> getStringSequencesMap(HashMap<List<String>, Long> statPoint, int sequenceMinSize){
		HashMap<String, Long> newStat = new HashMap<String,Long>();
		
		//create the key as the concatenation of every symbol, and add to the new map
		for ( Entry<List<String>,Long> entry : statPoint.entrySet() ){

			List<String> sequence = entry.getKey();
			if ( sequence.size() < sequenceMinSize ){
				continue;
			}
			
			StringBuffer elementNameBuf = new StringBuffer();
			elementNameBuf.append("\"");
			for ( String element : sequence ){
				elementNameBuf.append(element);
				elementNameBuf.append(" ");
			}
			elementNameBuf.append("\"");
			String elementName = elementNameBuf.toString();
			newStat.put(elementName, entry.getValue());
		}
		return newStat;
	}
	
	/**
	 * Given a list with Maps that indicate for every subsequence the number of times they occurr, derives a new map containing 
	 * the same information but in which the subsequence is not represented as a list of strings but as a string in which the every element 
	 * is separated by a space.
	 * 
	 * @param statPoints
	 * @param sequenceMinSize 
	 * @return
	 */
	public static List<HashMap<String, Long>> getWritableLists(
			List<HashMap<List<String>, Long>> statPoints, int sequenceMinSize) {
		
		List<HashMap<String,Long>> result = new ArrayList<HashMap<String,Long>>(statPoints.size());

		for ( HashMap<List<String>,Long> statPoint : statPoints ){ //Proces every statPoint
			
			
			result.add(getStringSequencesMap(statPoint, sequenceMinSize));
		}
		return result;
	}



	/**
	 * This method parse a file and update the coverage statistics
	 * @param file
	 * @return 
	 * @throws IOException
	 */
	public SequencesStatsCalculatorResults process(File file) throws IOException {
		BufferedReader r = new BufferedReader(new FileReader(file));
		SequencesStatsCalculatorResults results = new SequencesStatsCalculatorResults();
		
		String line;
		int lineCounter=0;
		while ( ( line = r.readLine() ) != null ){
			if ( line.equals("|") ){
				continue;
			}
			
			String lineElement = getLineElement(line);
			if ( elementsToIgnore.contains(lineElement ) ){
				continue;
			}
			
			newElement(results,lineElement);
			
			lineCounter++;
			if ( checkPointWindowSize>0 && (lineCounter % checkPointWindowSize == 0 )){
				results.addCheckpoint();
				
			}
		}
		
		r.close();
		
		return results;
	}

	private void newElement(SequencesStatsCalculatorResults results, String lineElement) {
		if ( curSequence.size() >= sequenceSize ){
			curSequence.removeFirst();
		}
		
		curSequence.add(lineElement);
		
		if ( curSequence.size() == sequenceSize ){
			results.addSequence(curSequence);
		}
		
	}

	private String getLineElement(String line) {
		String[] lineElements;
		try {
			lineElements = csvReader.readLine(line);
		} catch (IOException e) {
			return null;
		}
		
		StringBuffer bf = new StringBuffer();
		
		for( int i = 0; i < elementsToConsider.length; ++i ){
			int position = elementsToConsider[i];
			if ( i > 0 ){
				bf.append("-");
			}
			bf.append(lineElements[position]);
		}
		
		return bf.toString();
	}



	public boolean addElementToIgnore(String o) {
		return elementsToIgnore.add(o);
	}

	public boolean addElementsToIgnore(Collection<? extends String> c) {
		return elementsToIgnore.addAll(c);
	}

	public List<Integer> getElementsToConsider() {
		List<Integer> res = new ArrayList<Integer>(elementsToConsider.length);
		for ( int i : elementsToConsider ){
			res.add(i);
		}
		return res;
	}

	public void setElementsToConsider(int[] elementsToConsider) {
		this.elementsToConsider = elementsToConsider;
	}

}
