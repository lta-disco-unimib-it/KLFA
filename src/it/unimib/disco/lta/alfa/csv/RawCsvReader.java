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
