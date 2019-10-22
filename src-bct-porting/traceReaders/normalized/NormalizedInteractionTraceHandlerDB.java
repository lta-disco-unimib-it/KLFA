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
package traceReaders.normalized;

import java.io.File;
import java.util.Vector;

import tools.TraceRepository;
import traceReaders.raw.DBInteractionTrace;
import traceReaders.raw.Token;
import util.FileIndex.FileIndexException;
import database.DataLayerException;
import database.Datum;
import database.InteractionTrace;
import database.MethodCall;

public class NormalizedInteractionTraceHandlerDB implements NormalizedInteractionTraceHandler {
	
	
	private File outputfolder;
	private TraceRepository repository;
	
	public NormalizedInteractionTraceHandlerDB(File outputFolder ) {
		this.outputfolder = outputFolder;
		repository = new TraceRepository( this.outputfolder );
	}
	
	/**
	 * Add a method call sequence for the passed method
	 * 
	 * @param methodName	name of the method
	 * @param trace			sequence of methods called
	 * @throws NormalizedTraceHandlerException 
	 */
	public void addInteractionTrace(String methodName, Vector trace, String threadId) throws NormalizedTraceHandlerException {
		try{
		if(trace == null) {
			try {
				InteractionTrace.insert(methodName , trace, threadId);
			} catch (DataLayerException e) {
				e.printStackTrace();
			}
			//questo metodo deve essere chiamato nel metodo sotto
			repository.addTrace(methodName, "|");
		} else {
			try {
				InteractionTrace.insert(methodName, trace, threadId);
			} catch (DataLayerException e) {
				e.printStackTrace();
			}
			
			String methodCalls = "";
			for (int currentPosition = 0; currentPosition < trace.size(); currentPosition++) {
				methodCalls = methodCalls.concat(((Token)trace.get(currentPosition)).getMethodName().substring(0, ((Token)trace.get(currentPosition)).getMethodName().length()-1) + "#");
			}
			methodCalls = methodCalls.substring(0, methodCalls.length()-1) + "|";
		
			//questo metodo deve essere chiamato nel metodo sotto
			repository.addTrace(methodName, methodCalls);
		}
		} catch ( FileIndexException e ){
			throw new NormalizedTraceHandlerException(e.getMessage());
		}
	}

	/**
	 * Returns an iterator over the normalized traces.
	 * 
	 * @return an iterator over the normalized interaction traces 
	 */
	public NormalizedInteractionTraceIterator getInteractionTracesIterator() {
		//FIXME: why this works on file???
		return new FileInteractionTracesIterator ( repository );
	}

}
