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
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Implementation of InteractionTrace interface to work on files
 * 
 * @author Fabrizio Pastore fabrizio.pastore AT gmail.com
 *
 */
public class FileInteractionTrace implements InteractionTrace {
	private File file;
	private File metaTraceFile;
	private String threadId;
	private TraceReader reader = null;
	private MetaDataIterator metaIterator;
	private FileReader fr;
	
	public FileInteractionTrace( String threadId, File file, File metaFile ){
		this.file = file;
		this.metaTraceFile = metaFile;
		if ( ! file.exists() )
			throw new IllegalArgumentException("Interaction traces file "+file+" does not exists");
		this.threadId = threadId;
	}
	
	public Token/*String*/ getNextToken() throws TraceException {
	
		if ( reader == null ){
			
			try {
				fr = new FileReader(file);
				reader = new TraceReader(fr);
			} catch (FileNotFoundException e) {
				throw new IllegalArgumentException("Interaction traces file " + file + " does not exists");
			}
			
		}
		try {
			//return reader.getNextToken(); 
			return new Token(reader.getNextToken());
		} catch (IOException e) {
			throw new TraceException("problem occurred while reading file. "+e.getMessage());
		}
	}

	public String getThreadId() {
		return threadId;
	}

	public String getNextMetaData() throws TraceException {
		if ( metaTraceFile == null )
			throw new TraceException("No meta information available");
		
		if ( metaIterator == null ){
			try {
				metaIterator = new MetaDataIterator ( new BufferedReader( new InputStreamReader( new FileInputStream (metaTraceFile) ) ) );
			} catch (FileNotFoundException e) {
				throw new TraceException("No meta information available");
			}
		}
		
		return (String) metaIterator.next();
	}
	
	/**
	 * Close thetrace, this is done for optimization purposes
	 * 
	 * @throws IOException
	 */
	public void close() throws IOException{
		fr.close();
		reader = null;
	}
}
