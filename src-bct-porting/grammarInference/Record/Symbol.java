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

/**
 * 
 * @author Leonardo Mariani
 *
 * This class represents a symbol in a trace
 */
public class Symbol {
	private String value=null;
	
	/**
	 * The constructor creates a new symbol that is represented by a string
	 * 
	 * @param val the string value representing the current symbol
	 */
	public Symbol(String val) {
		value=val;
	}

	/**
	 * returns the symbolic value of the current symbol
	 * 
	 * @return symbolic value of hte current symbol
	 */
	public String getValue(){
		return value;
	}
	
	public boolean equals( Object o ){
		if ( o == this ){
			return true;
		}
		if ( ! (o instanceof Symbol ) ){
			return false;
		}
		return value.equals(((Symbol)o).value);
	}
	
	public String toString(){
		return value;
	}
}
