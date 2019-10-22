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

import database.DataLayerException;
import database.InteractionTrace;

import tools.TraceRepository;
import traceReaders.raw.Token;
import util.FileIndex.FileIndexException;

public class NormalizedInteractionTraceHandlerFile implements
NormalizedInteractionTraceHandler {

	private File outputfolder;
	private TraceRepository repository;

	/**
	 * Constructor 
	 * 
	 * @param outputFolder	directory that contain (or will contain) all normalized traces
	 */
	public NormalizedInteractionTraceHandlerFile( File outputFolder ){
		this.outputfolder = outputFolder;
		repository = new TraceRepository( this.outputfolder );
	}


	public void addInteractionTrace(String methodName, /*String*/Vector trace, String threadId) throws NormalizedTraceHandlerException {
		//repository.addTrace(methodName, trace);
		try {
			if(trace == null) {
				repository.addTrace(methodName, "|");
			} else {
				String methodCalls = "";
				for (int currentPosition = 0; currentPosition < trace.size(); currentPosition++) {
					methodCalls = methodCalls.concat(((Token)trace.get(currentPosition)).getMethodName().substring(0, ((Token)trace.get(currentPosition)).getMethodName().length()-1) + "#");
				}
				methodCalls = methodCalls.substring(0, methodCalls.length()-1) + "|";
				repository.addTrace(methodName, methodCalls);
			}
		} catch (FileIndexException e) {
			// TODO Auto-generated catch block
			throw new NormalizedTraceHandlerException(e.getMessage());
		}
	}



	public NormalizedInteractionTraceIterator getInteractionTracesIterator() {
		return new FileInteractionTracesIterator(repository);
	}
}
