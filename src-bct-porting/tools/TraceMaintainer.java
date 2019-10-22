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
/*
 * Created on 29-dic-2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package tools;

import java.util.Stack;
import java.util.Vector;

import traceReaders.normalized.NormalizedInteractionTraceHandler;
import traceReaders.normalized.NormalizedTraceHandlerException;
import traceReaders.raw.Token;

/**
 * @author Leonardo Mariani
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class TraceMaintainer {
	NormalizedInteractionTraceHandler tr;
	Stack<Vector<Token>> traceVector;
	Stack<Token> nameVector;

	private final String SEPARATOR = "#";
	//int currentPosition = -1;
	
	/**
	 * 
	 */
	public TraceMaintainer(NormalizedInteractionTraceHandler tr) {
		this.tr=tr;
		traceVector = new Stack<Vector<Token>>();
		nameVector = new Stack<Token>();
		
	}

	public void newTrace(Token token) {
		Vector<Token> vectorToken = new Vector<Token>();
		//currentPosition++;
		nameVector.push( token);
		traceVector.push( vectorToken );
	}
	
	public void addSymbol(Token symbol) {
		if ( traceVector.size() > 0 )
			traceVector.peek().add(symbol);
	}
	
	public void closeTrace(String threadId) {
		try{

//			String traceToAdd = (String)traceVector.get(currentPosition);
//			if (traceToAdd.equals("")) {

//			tr.addInteractionTrace((String)nameVector.get(currentPosition),"|", threadId);

//			} else {
//			tr.addInteractionTrace((String)nameVector.get(currentPosition), traceToAdd.substring(0,traceToAdd.length()-1) + "|", threadId);
//			}
//			currentPosition--;


			String method = nameVector.pop().getMethodName();
			String methodName = method.substring(0, method.length()-1);
			Vector<Token> trace = traceVector.pop();

			if ( trace.isEmpty() ) {
				tr.addInteractionTrace(methodName, null, threadId);
			} else {
				tr.addInteractionTrace(methodName, trace, threadId);
			}

		} catch (NormalizedTraceHandlerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public int getNumberOpenTraces() {
		return traceVector.size();
	}
	
//	public Vector<String> getOpenTraces(){
//		return new Vector<String>(nameVector);
//	}
}
