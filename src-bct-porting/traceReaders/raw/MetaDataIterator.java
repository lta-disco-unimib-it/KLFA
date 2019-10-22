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
