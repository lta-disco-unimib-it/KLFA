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

import java.util.HashMap;

/**
 * This class is a factory used to create flyweigth symbols.
 *  
 * @author Fabrizio Pastore
 *
 */
public class SymbolsFactory {
	
	HashMap<String,Symbol> symbols = new HashMap();
	
	/**
	 * Returns a SYmbol instance for teh given value
	 * 
	 * @param symbolValue
	 * @return
	 */
	public Symbol getSymbol( String symbolValue ){
		Symbol symbol = symbols.get(symbolValue);
		if ( symbol == null ){
			symbol = new Symbol(symbolValue);
			symbols.put(symbolValue,symbol);
		}
		
		return symbol;
	}
}
