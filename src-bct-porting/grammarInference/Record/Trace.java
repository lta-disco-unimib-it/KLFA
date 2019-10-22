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
package grammarInference.Record;

import java.util.Iterator;

public interface Trace {

	/**
	 * addition of a symbol to the end position of the trace
	 * 
	 * @param s the symbol that must be added
	 */
	public abstract void addSymbol(Symbol s);

	/**
	 * Return the symbol stored at a given position
	 * 
	 * @param i the position storing the symbol
	 * @return the value of the symbol
	 */
	public abstract Symbol getSymbol(int i);

	/**
	 * Return the length of the trace
	 * @return length of the trace
	 */
	public abstract int getLength();

	/**
	 * Return an iterator over all symbols stored in the trace
	 * @return iterator of symbols in the trace
	 */
	public abstract Iterator getSymbolIterator();

	/**
	 * Return a subtrace of the current trace
	 * 
	 * @param from first symbol of the substrace (included)
	 * @param to last symbol of the subtrace (included)
	 * @return the subtrace
	 */
	public abstract Trace getSubTrace(int from, int to);

	/**
	 * @return
	 */
	public abstract boolean isPositiveTrace();

	/**
	 * @param positiveTrace
	 */
	public abstract void setPositiveTrace(boolean positiveTrace);

}