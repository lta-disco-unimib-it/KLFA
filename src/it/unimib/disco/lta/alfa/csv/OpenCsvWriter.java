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
