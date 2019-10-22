package it.unimib.disco.lta.alfa.csv;

import java.util.ArrayList;
import java.util.List;

/**
 * This class implement a simple csv parser
 * 
 * @author Fabrizio Pastore
 *
 */
public class CsvFileParser {

	private char columnSeparator = ',';
	private char stringDelimiter = '"';
	private char escape = '\\';
	
	public CsvFileParser(){
		
	}
	
	/**
	 * Creates a csv parser with the given configuration
	 * 
	 * @param columnSeparator	separator for column
	 * @param stringDelimiter	delimiter of string fields
	 */
	public CsvFileParser(char columnSeparator, char stringDelimiter) {
		super();
		this.columnSeparator = columnSeparator;
		this.stringDelimiter = stringDelimiter;
	}

	/**
	 * Return the columns in a csv line
	 * @param line
	 * @return
	 */
	public List<String> getColumns(String line){
		char[] chars = line.toCharArray();
		List<String> columns = new ArrayList<String>();
		
		StringBuffer column = new StringBuffer();
		boolean notInString = true;
		
		for ( int i = 0; i < chars.length; ++i ){
			char curChar = chars[i];
			if ( notInString && curChar == columnSeparator ){
				columns.add(column.toString());
				column = new StringBuffer();
			//} else if ( curChar == escape  ){
				
			} else if ( curChar == stringDelimiter ){
				notInString = ! notInString;
			} else {
				column.append(chars[i]);
			}
		}
		
		if ( column.length() > 0 ){
			columns.add(column.toString());
		}
		
		return columns;
	}

	public String[] getColumnsAsArray(String line) {
		return getColumns(line).toArray(new String[0]);
	}
}
