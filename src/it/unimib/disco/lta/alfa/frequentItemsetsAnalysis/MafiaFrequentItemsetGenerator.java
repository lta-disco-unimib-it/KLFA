package it.unimib.disco.lta.alfa.frequentItemsetsAnalysis;

import it.unimib.disco.lta.alfa.frequentItemsetsAnalysis.WindowSequenceAnalyzer.Matrix;
import it.unimib.disco.lta.alfa.frequentItemsetsAnalysis.WindowSequenceAnalyzer.MatrixManager;
import it.unimib.disco.lta.alfa.parametersAnalysis.Cluster;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;



public class MafiaFrequentItemsetGenerator {
	private String mafiaCommand = "/usr/local/bin/mafia";
	private String tabletempFile = "mafiaTable.txt.tmp";
	private String resultTempFile = "mafiaResult.txt.tmp";
	private String temporaryDir = "/tmp";
	private String minSupport = ".01";
	private WindowElementsAnalyzer mf = new WindowElementsAnalyzer();
	
	public List<Itemset<String>> run( File csvInput ) throws IOException {
		
		
		//export to temp
		Matrix mm = mf.process(csvInput,getTableFile());
		
		runMafia();
				
		//read result and report them
		return getResults(mm);
		
	}
	
	private List<Itemset<String>> getResults(Matrix mm) throws IOException {
		File file = getResultFile();
		BufferedReader br = new BufferedReader ( new FileReader(file) );
		String line;
		ArrayList<Itemset<String>> result = new ArrayList<Itemset<String>>();
		
		while( ( line = br.readLine() ) != null ){
			
			//System.out.println(line);
			
			if ( line.length() == 0 )
				continue;
			
			String[] elements = line.split("\\(");
			String[] els = elements[0].split(" ");
			Set<String> events = new HashSet<String>();
			for ( String el : els ){
				String event = mm.getEventFromId(Integer.valueOf(el));
				events.add(event);
			}
			String stringSupport = elements[1].split("\\)")[0];
			
			int support = Integer.valueOf(stringSupport);
			Itemset i = new Itemset(events,support);
			result.add(i);
		}
		
		Collections.sort(result, new Comparator<Itemset<String>>(){

			public int compare(Itemset<String> o1, Itemset<String> o2) {
				return o2.getFrequence()-o1.getFrequence();
			}
			
		});
		
		return result;
		
	}

	private File getResultFile() {
		File tmpDir = new File ( temporaryDir );
		return new File(tmpDir,resultTempFile);
	}
	
	private File getTableFile() {
		File tmpDir = new File ( temporaryDir );
		return new File(tmpDir,tabletempFile);
	}

	private void runMafia() throws IOException {
		String command = mafiaCommand+" -mfi "+minSupport+" -ascii "+getTableFile().getAbsolutePath()+" "+getResultFile().getAbsolutePath();
		System.out.println(command);
		Process process = Runtime.getRuntime().exec( command );
		final InputStream es = process.getErrorStream();
		final InputStream is = process.getInputStream();
		
		Thread tis = new Thread(){
			public void run(){
				int res = 0;
				while ( res != -1){
					try {
						res = is.read();
						//System.out.print(res);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		};
		
		Thread tes = new Thread(){
			public void run(){
				int res = 0;
				while ( res != -1){
					try {
						res = es.read();
						//System.out.print(res);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		};
		
		tis.start();
		tes.start();

		try {
			process.waitFor();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		MafiaFrequentItemsetGenerator ig = new MafiaFrequentItemsetGenerator();
		
		for (int i=0;i< args.length-1;++i){
			if ( args[i].equals("-eventColumns") ){
				for ( String column : args[++i].split(",") ){
					Integer cn = Integer.valueOf(column);
					ig.addEventColumn(cn);
				}
			} else if ( args[i].equals("-windowSize") ){
				ig.setWindowSize(Integer.valueOf(args[++i]));
			} else if ( args[i].equals("-separator") ){
				ig.setSeparator(args[++i]);
			} else {
				System.err.println("Unknown option: "+args[i]);
				printUsage();
			}
		}
		
		File input = new File(args[args.length-1]);
		
		List<Itemset<String>> itemsets;
		try {
			itemsets = ig.run(input);
			System.out.println("Maximal frequent itemsets");
			for ( Itemset<String> i : itemsets ){
				for ( String item : i.getItems() ){
					System.out.print(item);
					System.out.print(" ");
				}
				System.out.print(i.getFrequence());
				System.out.print(" ");
				System.out.print("\n");
			}
			
			ItemsetClusterGenerator<String> icg = new ItemsetClusterGenerator<String>();
			ArrayList<Cluster<String>> merged = icg.getClusters( itemsets);
			
			System.out.println("Merged clusters");
			for ( Cluster<String> cluster : merged ){
				System.out.println( cluster.toString().replace(' ', '|') );
			}

			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	private static void printUsage() {
		
	}

	public void addEventColumn(int eventColumn) {
		mf.addEventColumn(eventColumn);
	}

	public void setSeparator(String separator) {
		mf.setSeparator(separator);
	}

	public void setWindowSize(int windowSize) {
		mf.setWindowSize(windowSize);
	}

}
