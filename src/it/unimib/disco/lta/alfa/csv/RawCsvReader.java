package it.unimib.disco.lta.alfa.csv;

import java.io.IOException;

/**
 * This class implements a csv reader that uses the String.split methods to separate columns.
 * The consequence of this is that quote chars are not interpreted.
 * 
 * @author Fabrizio Pastore
 *
 */
public class RawCsvReader implements CsvReader {

	private char columnSeparator = ',';

	public char getColumnSeparator() {
		return columnSeparator ;
	}

	public char getQuoteChar() {
		return 0;
	}

	public String[] readLine(String csvLine) throws IOException {
		return csvLine.split(String.valueOf(columnSeparator));
	}

	public void setColumnSeparator(char separator) {
		this.columnSeparator = separator;
	}

	public void setQuoteChar(char quoteChar) {
		
	}

}
