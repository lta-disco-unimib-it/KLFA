package it.unimib.disco.lta.alfa.csv;

import java.io.IOException;
import java.io.StringReader;

import au.com.bytecode.opencsv.CSVReader;

/**
 * This class implements the CsvReader interface by using the OpenCsvLibrary
 * 
 * @author Fabrizio Pastore
 *
 */
public class OpenCsvReader implements CsvReader {

	private char columnSeparator = ',';
	private char quoteChar = '"';

	public char getQuoteChar() {
		return quoteChar;
	}

	public void setQuoteChar(char quoteChar) {
		this.quoteChar = quoteChar;
	}

	public char getColumnSeparator() {
		return columnSeparator;
	}

	public void setColumnSeparator(char columnSeparator) {
		this.columnSeparator = columnSeparator;
	}

	public String[] readLine(String csvLine) throws IOException {
		CSVReader csvReader = new CSVReader(new StringReader(csvLine), columnSeparator, quoteChar   );
		
		return csvReader.readNext();
	}

}
