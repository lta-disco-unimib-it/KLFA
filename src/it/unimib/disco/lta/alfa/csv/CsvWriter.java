package it.unimib.disco.lta.alfa.csv;

import java.io.Writer;
import java.util.List;

/**
 * Interface for csv writers
 * 
 * @author Fabrizio Pastore
 *
 */
public interface CsvWriter {
	
	/**
	 * Write a csv line.
	 * 
	 * @param writer
	 * @param columns columns contents
	 */
	public void writeCsvLine(Writer writer, List<String> columns);
	
	/**
	 * Writes all the lines passed.
	 * 
	 * @param writer
	 * @param csvLines
	 */
	public void writeCsvLines(Writer writer, List<List<String>> csvLines);

	/**
	 * Returns the quote char
	 * 
	 * @return
	 */
	public char getQuoteChar();

	/**
	 * Sets the quote char
	 * 
	 * @param quoteChar
	 */
	public void setQuoteChar(char quoteChar);

	/**
	 * Returns the columns separator char
	 * 
	 * @return
	 */
	public char getColumnSeparator();

	/**
	 * Sets the columns separator char
	 * 
	 * @param columnSeparator
	 */
	public void setColumnSeparator(char columnSeparator);
}
