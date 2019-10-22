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
package traceReaders.raw;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.NoSuchElementException;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class MetaDataIterator implements Iterator {
	private BufferedReader reader;
	private String	next = null;
	private Boolean hasNext = null;
	private static final String separator = "#";
	
	public MetaDataIterator(BufferedReader reader) {
		this.reader = reader;
	}

	public boolean hasNext() {
		if ( hasNext == null){
			try {
				hasNext = readNext();
			} catch (IOException e) {
				hasNext = Boolean.FALSE;
			}
		}
		return hasNext.booleanValue();
	}
	
	private Boolean readNext() throws IOException {
			String line = reader.readLine();
			if ( line == null )
				return Boolean.FALSE;
			next = null;
			while ( line != null && ! line.equals(separator ) ){
				if ( next == null)
					next = "";
				next += line;
				line = reader.readLine();
			}
			return Boolean.TRUE;
	}

	public Object next() {
		if ( hasNext == null )
			hasNext();
		if ( hasNext.equals(Boolean.FALSE) )
			throw new NoSuchElementException();
		hasNext = null;
		return next;
	}

	public void remove() {
		throw new NotImplementedException();
	}


}
