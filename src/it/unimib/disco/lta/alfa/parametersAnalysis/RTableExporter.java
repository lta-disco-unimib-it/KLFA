package it.unimib.disco.lta.alfa.parametersAnalysis;

import it.unimib.disco.lta.alfa.csv.CsvParsersFactory;
import it.unimib.disco.lta.alfa.csv.CsvReader;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;


/**
 * This class read a csv file and write a csv file in which certain columns values are grouped together and wrote as the first columns.
 * Other columns are written as they are.
 * 
 * @author Fabrizio Pastore fabrizio.pastore AT gmail.com
 *
 */
public class RTableExporter {
	private HashMap<String,String[]> signaturesRegistry = new HashMap<String, String[]>();
	private List<Integer> signatureElements = new ArrayList<Integer>();
	private String separator=",";
	private int hashThreshold=-1;
	private String ruleSignatureSeparator = "_";
	private CsvReader csvReader;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		RTableExporter exporter = new RTableExporter();
		
		for(int i = 0; i< args.length-2;++i){
			if ( args[i].equals("-signatureElements") ){
				String[] sigElements = args[++i].split(",");
				int[] signatureElements = new int[sigElements.length];
				
				for( int j=0; j < sigElements.length; ++j ){
					signatureElements[j] = Integer.valueOf(sigElements[j]);
				}
				exporter.setSignatureElements(signatureElements);
			} else if ( args[i].equals("-separator") ){
				exporter.setSeparator(args[++i]);
			} else if ( args[i].equals("-hashThreshold") ){
				exporter.setHashThreshold(Integer.valueOf(args[++i]));
			}
		}
		
		File src = new File(args[args.length-2]);
		File dst = new File(args[args.length-1]);
		
		if ( dst.exists() ){
			System.err.println("Destnation file "+dst.getAbsolutePath() +" exists, delete or move it to run the program.");
			System.exit(-1);
		}
		
		try {
			exporter.export(src, dst);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public RTableExporter(){
		signatureElements.add(0);
		signatureElements.add(1);
		csvReader = CsvParsersFactory.createNewCsvReader();
		
	}

	public void setHashThreshold(int value) {
		hashThreshold=value;
	}

	private void export(File src, File dst) throws IOException {
		
		BufferedReader reader = new BufferedReader(new FileReader(src));
		BufferedWriter writer= new BufferedWriter(new FileWriter(dst));
		try{
		String line;
		
		while(( line = reader.readLine())!=null){
			ArrayList<String> parameters = getParameters(line);
			String signature = getRuleSignature(line);
			System.out.println("Exp "+signature);
			
			
			writeEntry(writer,signature,parameters);
		}

		writer.close();
		} finally {
			if ( writer != null ){
				writer.close();
			}
		}
	}
	
	private void writeEntry(BufferedWriter writer, String signature,
			ArrayList<String> parameters) throws IOException {
		writer.write(signature.replace(" ", "_"));
		for(String parameter : parameters ){
			writer.write(separator);
			writer.write(parameter.replace(" ", "_"));
		}
		writer.write("\n");
	}

	/**
	 * Given a csv line return the rule signature by concatenating the signature columns elements
	 * 
	 * @param line
	 * @return
	 */
	public String getRuleSignature(String line) {
		return getRuleSignature(line, false);
	}
	
	public String getRuleSignature(String line,boolean onlyFirst) {
		try{
		String[] columns;
		try {
			columns = csvReader.readLine(line);
		} catch (IOException e) {
			return null;
		}
		StringBuffer sigB = new StringBuffer();
		boolean first = true;
		
		String lineElements[] = new String[signatureElements.size()];
		
		int i = 0;
		for( Integer element : signatureElements ){
			if ( first ){
				first=false;
			} else {
				if ( onlyFirst ){
					break;
				}
				sigB.append(ruleSignatureSeparator );
			}
			
			String elementsValue = columns[element];
			sigB.append(elementsValue);
			lineElements[i++] = elementsValue;
		}
		
		
		
		String signature = sigB.toString();
		if ( hashThreshold>-1 && signature.length()>hashThreshold){
			signature = String.valueOf(signature.hashCode());
		}
		
		signaturesRegistry.put(signature, lineElements);
		
		return signature;
		} catch (RuntimeException e){
			System.out.println(line);
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * Given a csv line returns a list of the parameters only 
	 * 
	 * @param line
	 * @return
	 */
	public ArrayList<String> getParameters(String line) {
		String[] columns;
		try {
			columns = csvReader.readLine(line);
		} catch (IOException e) {
			return null;
		}
		
		ArrayList<String> parameters = new ArrayList<String>(columns.length);
		
		for( int i = 0; i< columns.length; ++i ){
			if ( ! signatureElements.contains(i) ){
				parameters.add(columns[i]);
			}
		}
		
		return parameters;
	}
	
	/**
	 * Set which csv columns have to be considered to build up signatures (usually component and action)
	 * 
	 * @param signatureElements
	 */
	public void setSignatureElements(int[] signatureElements) {
		this.signatureElements.clear();
		for( int value : signatureElements){
			this.signatureElements.add(value);
		}
	}

	public String getSeparator() {
		return separator;
	}

	public void setSeparator(String separator) {
		csvReader.setColumnSeparator(separator.toCharArray()[0]);
		this.separator = separator;
	}

	public int getHashThreshold() {
		return hashThreshold;
	}
	
	/**
	 * Given a parameter rule signature returns the elements from which the rule has been created.
	 * 
	 * @param ruleSignature
	 * @return
	 */
	public String[] getSignatureElements( String ruleSignature ){
		return signaturesRegistry.get(ruleSignature);
	}
	
	public String getRuleSignaturePrefix( String line ){
		return getRuleSignature(line, true);
	}
	
}
