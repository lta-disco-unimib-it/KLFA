package it.unimib.disco.lta.alfa.tools;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map.Entry;

public class ProbabilisticAnalyzerResultWriter {

	private String csvFieldSeparator;

	public ProbabilisticAnalyzerResultWriter(String csvFieldSeparator) {
		this.csvFieldSeparator = csvFieldSeparator;
	}


	public void writeCsv(String file, ProbabilisticAnalyzerResult result) throws IOException {
		FileWriter writer = new FileWriter(new File(file));
		writer.write("Path"+csvFieldSeparator+"count"+csvFieldSeparator+"percentage\n");
		int entries = result.symbolsNumber();
		for ( Entry<KPath, Integer> entry : result.entrySet()){
			Integer value = entry.getValue();
			KPath kpath = entry.getKey();
			
			writer.write(kpath+csvFieldSeparator+value+csvFieldSeparator+(double)value/(double)entries+"\n");
		}
		writer.close();
	}
}
