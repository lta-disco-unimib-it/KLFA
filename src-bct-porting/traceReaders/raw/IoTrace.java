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
