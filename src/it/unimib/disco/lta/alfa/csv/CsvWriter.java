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
