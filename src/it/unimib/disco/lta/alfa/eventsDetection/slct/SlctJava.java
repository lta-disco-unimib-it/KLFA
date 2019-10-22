package it.unimib.disco.lta.alfa.eventsDetection.slct;

import it.unimib.disco.lta.alfa.logging.Logger;
import it.unimib.disco.lta.alfa.utils.MathUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;

public class SlctJava {
	
	private int support;
	private boolean determineSupport = false;

	public SlctJava ( int support ){
		this.support = support;
	}
	
	public class Value<T> {
		T value;
		
		
		Value ( T value ){
			this.value = value;
		}
	}
	
	public class PatternsTable {
		
		Map<String, Value<Long>> table = new HashMap<String, Value<Long>>();
		private int support;
		
		public PatternsTable(int support) {
			this.support = support;
		}

		public void addPattern( String word ){
			
			
			Value<Long> value = table.get(word);
			
			if ( value == null ){
				value = new Value<Long>(0L);
				table.put(word, value);
			}
			
			value.value++;
			
		}
		
		public long getOccurrenciesForPattern(String word){
			Value<Long> value = table.get(word);
			
			if ( value == null ){
				return 0;
			}
			
			return value.value;
		}
		
		public List<String> getPatternsAboveSupport(){
			List<String> result = new ArrayList<String>();
			
			for ( Entry<String,Value<Long>> entry : table.entrySet() ){
				System.out.println(entry.getValue().value);
				if ( entry.getValue().value >= support ){
					result.add(entry.getKey());
				} else {
					System.out.println("NOT above: "+entry.getKey()+" "+entry.getValue().value);
				}
			}
			
			return result;
		}
		
	}
	
	public class FrequentWordsTable {
		
		private Map<Integer,Map<String,Value<Long>>> tables = new HashMap<Integer, Map<String,Value<Long>>>();
		private int support;
		
		public FrequentWordsTable ( int support ){
			this.support = support;
		}
		
		public List<Entry<String, Value<Long>>> getAllWords(){
			List<Entry<String,Value<Long>>> words = new ArrayList<Entry<String,Value<Long>>>();
			for ( Entry<Integer,Map<String,Value<Long>>> entry : tables.entrySet() ){
				Map<String, Value<Long>> wordsForColumn = entry.getValue();
				for ( Entry<String,Value<Long>> wordEntry : wordsForColumn.entrySet() ){
					words.add(wordEntry);
				}
			}
			return words;
		}
		
		public void addWord( int column, String word ){
			Map<String, Value<Long>> table = getTableForColumn( column );
			
			Value<Long> value = table.get(word);
			
			if ( value == null ){
				value = new Value<Long>(0L);
				table.put(word, value);
			}
			
			value.value++;
			
		}

		private Map<String, Value<Long>> getTableForColumn(int column) {
			Map<String,Value<Long>> table = tables.get(column);
			
			if ( table == null ){
				table = new HashMap<String, Value<Long>>();
				tables.put(column, table);
			}
			
			return table;
		}
		
		public boolean containsInColumnAndAboveThreshold( String word, int column  ){
			Map<String, Value<Long>> table = getTableForColumn(column);
			
			Value<Long> occurrencies = table.get(word);
			
			if ( occurrencies == null ){
				return false;
			}
			
			return occurrencies.value >= support;
		}
		
		public boolean containsInColumn( String word, int column  ){
			Map<String, Value<Long>> table = getTableForColumn(column);
			
			return table.containsKey(word);
		}
	}

	
	public class Processor {
		
		FrequentWordsTable frequentWords;
		PatternsTable patterns;
		
		public Processor( int support ){
			frequentWords = new FrequentWordsTable(support);
			patterns = new PatternsTable(support);
		}
		
		public void buildOneDenseRegions ( File file ){
			FileLinesTokenizer tk = null;
			try {
				tk = new FileLinesTokenizer(file);
				
				List<String> tokens;
				
				
				
				while ( ( tokens = tk.nextLine() ) != null ){
					int len = tokens.size();
					for ( int i = 0; i < len; i++ ){
						
						frequentWords.addWord(i, tokens.get(i));
					}
				}
				
				
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				if ( tk != null ){
					try {
						tk.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}	
		}
		
		
		public void buildPatterns ( File file ){
			FileLinesTokenizer tk = null;
			try {
				tk = new FileLinesTokenizer(file);
				
				List<String> tokens;
				
				
				
				while ( ( tokens = tk.nextLine() ) != null ){
					int len = tokens.size();
					
					StringBuffer patternBuilder = new StringBuffer();
					
					for ( int i = 0; i < len; i++ ){
						
						
						
						String token = tokens.get(i);
						if ( frequentWords.containsInColumnAndAboveThreshold(token, i) ){
							patternBuilder.append(token);
						} else {
							patternBuilder.append("*");
						}
						
						patternBuilder.append(" ");
						
						
					}
					
					patterns.addPattern( patternBuilder.toString().trim() );
				}
				
				
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				if ( tk != null ){
					try {
						tk.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}	
		}

		public List<String> getValidPatterns() {
			// TODO Auto-generated method stub
			return patterns.getPatternsAboveSupport();
		}

		/**
		 * Determine the support
		 * 
		 * 
		 * We could use different strategies:
		 * 
		 * 		average of the occurrencies of the frequentWords
		 * 
		 * 		median  of the occurrencies of the frequentWords
		 * 
		 * 		
		 */
		public void determineSupport() {
			Logger.info("Calculating new support, old support was: "+support);
			
//			List<Double> occurrencies = new LinkedList<Double>();
//			for ( Entry<String, Value<Long>> word : frequentWords.getAllWords() ){
//				occurrencies.add(word.getValue().value.doubleValue());
//			}
			
			
			List<Long> occurrencies = new LinkedList<Long>();
			for ( Entry<String, Value<Long>> word : frequentWords.getAllWords() ){
				occurrencies.add(word.getValue().value);
			}
			support = (int) MathUtil.getMean(occurrencies);
			
			Logger.info("New support is "+support);
		}
		
	}
	
	public List<String> getRules(File fileToProcess)
			throws SlctRunnerException {
		
		Logger.info("Running "+SlctJava.class.getName()+" on "+fileToProcess.getAbsolutePath()+", support: "+support);
		
		Processor processor = new Processor(support);
		
		processor.buildOneDenseRegions(fileToProcess);
		
		if ( determineSupport ){
			processor.determineSupport();
		}
		processor.buildPatterns(fileToProcess);
		
		return processor.getValidPatterns();

	}
	
	public List<Pattern> getPatterns( File fileToProcess ) throws SlctRunnerException{
		List<String> rules = getRules(fileToProcess);
		
		ArrayList<Pattern> result = new ArrayList<Pattern>();
		
		for ( String rule : rules ){
			System.out.println("RULE "+rule);
			result.add(Pattern.compile(rule.replaceAll("\\*", ".*")));
		}
		
		return result;
	}


	

}
