package traceReaders.raw;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Writer;
import java.util.NoSuchElementException;

import util.FileIndex;

/**
 * Wrapper for io trace files
 * @author Fabrizio Pastore fabrizio.pastore AT gmail.com
 *
 */
public class FileIoTrace implements IoTrace {
	private String methodName;
	private BufferedReader reader = null;
	private BufferedReader metaReader = null;
	private File traceFile;
	private MetaDataIterator metaIterator;
	private File metaTraceFile;
	
	
	public static class FileLineIterator implements LineIterator {
		//FIXME: the reader is never closed
		
		private BufferedReader reader;
		private long curLine = 0;
		
		public FileLineIterator(BufferedReader reader) {
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
			++curLine;
			try {
				String line = reader.readLine();
				return line;
			} catch (IOException e) {
				throw new NoSuchElementException();
			}
		}

		public void remove() {
			throw new UnsupportedOperationException();
		}

		public long getCurrentLineNumber() {
			return curLine;
		}
		
	}
	
	public FileIoTrace( String methodName, File trace, File metaTrace ){
		this.methodName = methodName;
		traceFile = trace;
		metaTraceFile = metaTrace;
	}
	
	public FileIoTrace(String methodName, File trace) {
		this(methodName,trace,null);
	}

	public String getMethodName() {
		return methodName;
	}

	public String nextLine()  throws TraceException {

		try {
			String line = reader.readLine();
			System.out.println(line);
			return line;
		} catch (IOException e) {
			e.printStackTrace();
			throw new TraceException("Problem reading file "+traceFile+" ");
		}
	}


	public void release() throws TraceException {
		try {
			if ( reader != null )
				reader.close();
			
			if ( metaReader != null )
				metaReader.close();
			
		} catch (IOException e) {
			throw new TraceException("Cannot release "+e.getMessage());
		} finally {
			reader = null;
			metaReader = null;
		}
	}

	public LineIterator getLineIterator() throws TraceException {
		try {
			return new FileLineIterator( new BufferedReader( new InputStreamReader( new FileInputStream (traceFile) ) ) );
		} catch (FileNotFoundException e) {
			throw new TraceException(e.getMessage());
		}
	}

	public MetaDataIterator getMetaDataIterator() throws TraceException {
		
		if ( metaTraceFile == null)
			throw new TraceException("Metadata file is set to null, cannot read");
		try {
			if ( metaReader == null ){
				metaReader = new BufferedReader( new InputStreamReader( new FileInputStream (metaTraceFile) ) );
			}else{
				metaReader.close();
				metaReader = new BufferedReader( new InputStreamReader( new FileInputStream (metaTraceFile) ) );
			}
		} catch (FileNotFoundException e) {
			throw new TraceException("Cannot read metadata file");
		} catch (IOException e) {
			throw new TraceException("Cannot read metadata file");
		}
	
		return new MetaDataIterator( metaReader );
	}

	public File getMetaTraceFile() {
		return metaTraceFile;
	}

	public File getTraceFile() {
		return traceFile;
	}
}
