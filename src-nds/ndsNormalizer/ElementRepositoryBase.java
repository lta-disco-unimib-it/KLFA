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
package ndsNormalizer;

import java.util.HashMap;	
import java.util.Iterator;
import java.util.Set;
import java.util.Stack;
import java.util.TreeSet;

import javax.sound.sampled.LineEvent;


public class ElementRepositoryBase<T extends LineData> implements ElementRepository<T>{
	protected HashMap<String,T> elements = new HashMap<String,T>();
	protected HashMap<String,Integer> counters = new HashMap<String, Integer>();
	protected Integer startLevel;
	private	  static final int keySize = 20;
	protected boolean[] keys = new boolean[keySize];
	private int reservedKeys;
	
	
	ElementRepositoryBase( Integer startLevel, int reservedKeys ){
		this.reservedKeys = reservedKeys;
		this.startLevel = startLevel;
		for ( int x = 0; x < keySize; x++ )
			keys[x]=false;
	}
	
	public void nextCycle(){
		Set set = new TreeSet ( counters.keySet() );
		Iterator<String> it = set.iterator();
		
		while ( it.hasNext() ){
			String elementName = it.next();
			Integer count = counters.get( elementName );
			
			if ( count > 1 ){
				--count;
				counters.put(elementName, count);
			} else{
				counters.remove(elementName);
				T el = elements.remove(elementName);
				int pos = Integer.valueOf( el.getData() )-reservedKeys;
				keys[pos] = false;
			}
		}
		
	}

	public T get(String value) {
		if ( elements.containsKey(value) ){
			//counters.put(value, startLevel);
			return elements.get(value);
		}
		return null;
		
	}

	public void put(String key, T data) {
		counters.put(key, startLevel);
		elements.put(key, data);
	}
	
	public String nextKey(){
		for ( int i = 0; i < keySize; ++i ){
			if ( ! keys[i] ){
				String res = ""+(i+reservedKeys);
				keys[i] = true;
				return res;
			}
		}
		return "-1";
	}
}
