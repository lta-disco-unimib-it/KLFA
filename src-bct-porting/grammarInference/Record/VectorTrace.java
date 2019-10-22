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
 * Created on 12-lug-2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package grammarInference.Record;

import java.util.Iterator;
import java.util.Vector;

/**
 * @author Leonardo Mariani
 *
 * The class represents a trace 
 */
public class VectorTrace implements Trace {
	private static final String separator = " ";
	private Vector trace = null;
	
	private boolean positiveTrace;

	/**
	 *  Creation of an empty trace
	 */
	public VectorTrace() {
		trace = new Vector();
	}
	
	/* (non-Javadoc)
	 * @see grammarInference.Record.Trace#addSymbol(grammarInference.Record.Symbol)
	 */
	public void addSymbol(Symbol s) {
		trace.add(s);
	}

	/* (non-Javadoc)
	 * @see grammarInference.Record.Trace#getSymbol(int)
	 */
	public Symbol getSymbol(int i) {
		return (Symbol) trace.elementAt(i);
	}

	/* (non-Javadoc)
	 * @see grammarInference.Record.Trace#getLength()
	 */
	public int getLength() {
		return trace.size();
	}

	/* (non-Javadoc)
	 * @see grammarInference.Record.Trace#getSymbolIterator()
	 */
	public Iterator getSymbolIterator(){
		return trace.listIterator();
	}
	
	/* (non-Javadoc)
	 * @see grammarInference.Record.Trace#getSubTrace(int, int)
	 */
	public Trace getSubTrace(int from, int to) {
		Trace ret = new VectorTrace();
		for (int i =from; i<=to;i++) {
			ret.addSymbol(new Symbol(((Symbol)trace.elementAt(i)).getValue()));
		}
		return ret;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		String rtrString = "";
		
		for (int i=0; i<trace.size(); i++) {
			rtrString = rtrString + ((Symbol)trace.elementAt(i)).getValue() + separator;
		}
		
		return rtrString;
	}
	/* (non-Javadoc)
	 * @see grammarInference.Record.Trace#isPositiveTrace()
	 */
	public boolean isPositiveTrace() {
		return positiveTrace;
	}

	/* (non-Javadoc)
	 * @see grammarInference.Record.Trace#setPositiveTrace(boolean)
	 */
	public void setPositiveTrace(boolean positiveTrace) {
		this.positiveTrace = positiveTrace;
	}

}

