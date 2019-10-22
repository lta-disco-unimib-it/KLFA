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

import java.util.Iterator;

import database.DataLayerException;

/**
 * This interface represent an IO trace
 * 
 * @author Fabrizio Pastore fabrizio.pastore AT gmail.com
 *
 */
public interface IoTrace {
	
		/**
		 * This interface represent an Iterator over the lines of an IoTrace.
		 * Every next() call must return a line of the IoTrace.
		 * 
		 * @author Fabrizio Pastore fabrizio.pastore AT gmail.com
		 *
		 */
		public interface LineIterator extends Iterator<String> {

			public long getCurrentLineNumber();
			
			public boolean hasNext();

			public String next();

			public void remove();
			
		}
	
		/**
		 * Return the name of the method that the Trace refers to
		 * 
		 * @return
		 */
		public String getMethodName();
		
		/**
		 * Returns an Iterator over lines of the IO Trace
		 * @return
		 * @throws TraceException
		 * @throws DataLayerException 
		 */
		public LineIterator getLineIterator() throws TraceException;

		/**
		 * Returns an iterator over added meta data informations.
		 *  
		 * @return
		 * @throws TraceException
		 */
		public MetaDataIterator	getMetaDataIterator()  throws TraceException;
		
		/**
		 * This method is there for optimization purposes. Is called after a trace is readed. 
		 * So the trace can be flushed out from memory.
		 * If no meta data information is available teturns a TrcaeException
		 * 
		 * If not implemented throw an UnsupportedOperationException.
		 *  
		 * @throws TraceException
		 */
		public void release() throws TraceException;
		
}
