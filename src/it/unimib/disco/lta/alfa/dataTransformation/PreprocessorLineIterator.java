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
package it.unimib.disco.lta.alfa.dataTransformation;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.NoSuchElementException;



public class PreprocessorLineIterator implements LineIterator {

	private BufferedReader reader;
	private String lastLine;

	public PreprocessorLineIterator(BufferedReader reader) {
		this.reader = reader; 
	}

	public boolean hasNext() {
		try {
			return reader.ready();
		} catch (IOException e) {
			return false;
		}
	}

	public String next() {

		try {
			lastLine = reader.readLine();
			return lastLine;
		} catch (IOException e) {
			throw new NoSuchElementException();
		}
	}

	public void remove() {
		throw new UnsupportedOperationException();
	}

}
