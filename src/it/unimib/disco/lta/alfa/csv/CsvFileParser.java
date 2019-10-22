/*******************************************************************************
 *    Copyright 2019 Fabrizio Pastore, Leonardo Mariani
 *   
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 *******************************************************************************/
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
