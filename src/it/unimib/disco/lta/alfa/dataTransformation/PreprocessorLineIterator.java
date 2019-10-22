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
