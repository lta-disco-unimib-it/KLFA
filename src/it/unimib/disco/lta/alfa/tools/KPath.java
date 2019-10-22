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
package it.unimib.disco.lta.alfa.tools;

import grammarInference.Record.Symbol;

import java.util.LinkedList;
import java.util.Queue;

public class KPath {

	private int k;
	private Queue<String> path = new LinkedList<String>();

	public KPath(int pathLen) {
		this.k = pathLen;
	}

	
	public KPath(KPath rhs){
		this.k = rhs.k;
		this.path = new LinkedList<String>(rhs.path);
	}
	
	/**
	 * Modify the current path by removing the first token stored and pushing the passed one
	 * @param symbol
	 * @return the removed token
	 */
	public String nextStep( String symbol ){
		
		path.add(symbol);
		if ( path.size() > k ){
			return path.remove();
		} else {
			return null;
		}
		
	}
	
	public boolean equals( Object o ){
		if ( o == this ) {
			return true;
		}
		
		if ( ! ( o instanceof KPath ) ){
			return false;
		}
		
		KPath passed = (KPath)o;
		
		if ( passed.k != this.k ){
			return false;
		}
		
		return passed.path.equals(this.path);
	}
	
	public int hashCode(){
		return toString().hashCode();
	}
	
	public String toString(){
		StringBuffer msgb = new StringBuffer();
		boolean first = true;
		for ( String s : path ){
			if ( first ){
				first = false;
			} else {
				msgb.append("#");
			}
			msgb.append(s);
		}
		return msgb.toString();
	}
	
	
	
}
