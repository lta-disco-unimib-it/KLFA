package it.unimib.disco.lta.alfa.csv;

import java.io.IOException;

public interface CsvReader {
	
	/**
	 * Reads a csv line and returns the corresponding columns
	 * 
	 * @param csvLine
	 * @return
	 * @throws IOException 
	 */
	public String[] readLine(String csvLine) throws IOException;

	/**
	 * Returns the character used in the CSV to separate columns
	 * 
	 */
	public char getColumnSeparator();
	
	/**
	 * Sets the character used in the CSV to separate columns
	 * 
	 * @param separator
	 */
	public void setColumnSeparator(char separator);

	/**
	 * Returns the quote char currently used
	 * 
	 * @return
	 */
	public char getQuoteChar();

	/**
	 * Sets the passed char as the quote char
	 * 
	 * @param quoteChar
	 */
	public void setQuoteChar(char quoteChar) ;
}
