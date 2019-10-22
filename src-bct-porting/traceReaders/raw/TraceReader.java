/*
 * Created on 29-dic-2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package traceReaders.raw;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * @author Leonardo Mariani
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class TraceReader {
	private final char SEPARATOR = '#';
	
	private final int BUFFERSIZE=3;
	char buffer[] = new char[BUFFERSIZE];
	int readedCharacters=0;
	int sentChr = 0;
	FileReader fr=null;
	/**
	 * 
	 */
	public TraceReader(FileReader fr) {
		this.fr=fr;
	}

	private void readBlock() throws IOException {
		readedCharacters = fr.read(buffer);
	}
	
	public String getNextToken() throws IOException {
		String tk="";
		if (readedCharacters == sentChr) {
			readBlock();
			sentChr=0;
		}

		
		if (readedCharacters <=0) return null;
		int i=sentChr;
		
		while ((buffer[i]!=SEPARATOR) && (readedCharacters>0) ) {
			i++;
			if (i==readedCharacters) {
				tk = tk + new String(buffer,sentChr,i-sentChr);
				readBlock();
				i=0;
				sentChr=0;
			}
		}
		
		if (readedCharacters == -1) {
			return null;
		} else if (readedCharacters == 0) {
			System.out.println("Incorrect input file!");
			return null;
		} else {
			String returnString = tk + new String(buffer,sentChr,i-sentChr);
			sentChr=i+1;
			return returnString;
		}
	}

	

}
