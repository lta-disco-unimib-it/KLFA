package grammarInference.Record;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;


public class FlyweigthKbhParser extends kbhParser {
	private SymbolsFactory symbolsFactory = new SymbolsFactory();
	
	private static final char chSeparator = '#';
	private static final char traceSeparator = '|';

	private FileReader fr = null;
	private int bufferSize = 100;
	private int pos = bufferSize;
	private char[] buffer = null;
	private int nChar=bufferSize+1;

	/**
	 * it instantiates a parser associated to an existing file. If the
	 * file is not foudn an exception is generated. The internal syntax
	 * of the file must be compliant with the following syntax:
	 * 
	 * Traces are separated by the | symbol without involving any carriage
	 * return or other blank characters. Each single symbol inside a trace is
	 * separated by the # charactes. Therefore a#fd#b|f#g| is an example of 
	 * a trace file that stores two traces. The fist one with three symbols and
	 * the second one with two symbols.
	 * 
	 * @param fileName the name of the file storing the set or recorded traces
	 * @throws FileNotFoundException the exception is generated if the file
	 * does not exist
	 */
	public FlyweigthKbhParser(String fileName) throws FileNotFoundException {
		super(fileName);
		fr = new FileReader(fileName);
		buffer = new char[bufferSize];
	}

	/**
	 * It returns an iterator spanning all traces of the trace file.
	 * 
	 * @return a TraceIterator
	 */
	public Iterator getTraceIterator() {
		return new TraceIterator(this);
	}

	/**
	 * The method reads a trace from the trace file. Traces are incrementally
	 * read when necessary and stored in an internal buffer.
	 * If the trace is empty a symbol, "", with 0 length is returned 
	 * @return the next trace
	 * @throws IOException the exception is generated if there are problems
	 * when accessing the file.
	 */
	private VectorTrace readTrace() throws IOException {
		//int nChar;
		String currentSymbol = "";

		int posEndSymbol = 0;
		boolean endTrace = false;

		VectorTrace trace = new VectorTrace();

		while (!endTrace) {
			if (pos >= buffer.length) {
				nChar = fr.read(buffer);
				pos = 0;
				if (nChar == -1)
					return null;
			}
			if (pos>=nChar) return null;

			DataBuffer db = getPosSymbol(buffer, pos);
			posEndSymbol = db.pos;
			endTrace = db.endTrace;

			if (posEndSymbol == -1) {
				currentSymbol =
					currentSymbol
						+ new String(buffer, pos, buffer.length - pos);
				pos = buffer.length;
			} else {
				currentSymbol =
					currentSymbol + new String(buffer, pos, posEndSymbol - pos);
				pos = posEndSymbol + 1;

	
				trace.addSymbol(symbolsFactory.getSymbol(currentSymbol));
				currentSymbol = "";
			}
		}

		return trace;
	}

	/**
	 * This function returns the position of next either end character symbol
	 * or end trace symbol. 
	 * 
	 * @param str the vector of characters where the particular characters must be found
	 * @param start the position where beginning to search
	 * @return the return data structure stores two public fields. The pos
	 * value is used to store the position of the special character. The
	 * endTrace field is used to show if the found character denotes the
	 * end of the trace or not. -1 is returned in the position if no 
	 * characters are found.
	 */
	private DataBuffer getPosSymbol(char[] str, int start) {

		DataBuffer db = new DataBuffer();
		db.endTrace = false;
		db.pos = -1;

		int i = start;

		while ((i < str.length) && (db.pos == -1)) {
			if (str[i] == chSeparator) {
				db.pos = i;
				db.endTrace = false;
			}
			if (str[i] == traceSeparator) {
				db.pos = i;
				db.endTrace = true;
			}
			i++;
		}

		return db;
	}




}
