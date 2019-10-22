package it.unimib.disco.lta.alfa.csv;

import java.io.Writer;
import java.util.List;

import au.com.bytecode.opencsv.CSVWriter;

/**
 * Csv writer that uses opencsv api
 * 
 * @author Fabrizio Pastore
 *
 */
public class OpenCsvWriter implements CsvWriter {

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
	
	public void writeCsvLine(Writer writer, List<String> columns){
		CSVWriter csvWriter = new CSVWriter(writer, columnSeparator, quoteChar);
		csvWriter.writeNext(columns.toArray(new String[columns.size()]));
		
	}
	
	public void writeCsvLines(Writer writer, List<List<String>> csvLines){
		CSVWriter csvWriter = new CSVWriter(writer, columnSeparator, quoteChar);
		for ( List<String> columns : csvLines ){
			csvWriter.writeNext(columns.toArray(new String[columns.size()]));
		}
	}
	
}
