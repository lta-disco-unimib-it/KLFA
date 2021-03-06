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
package it.unimib.disco.lta.alfa.dataTransformation;


/**
 * This ValueTransformer replaces each value with
 * a number that indicates the number of values that have been
 * observed from its last occurrence.
 * If the passed value was never seen before "0" is returned.
 *
 * For example if applied to these symbols:
 * 
 * A A B C D A D A C
 * 
 * it will produce a sequence:
 * 
 * 0 1 0 0 0 4 2 2 5 
 * 
 * A A B B C C D A D A C
 * 
 * will produce a sequence:
 * 
 * 0 1 0 1 0 1 0 6 2 2 5 
 * 
 * @author Fabrizio Pastore fabrizio.pastore AT gmail.com
 *
 */
public class ValueTransformerRelativeToAccess implements ValueTransformer {
	private HistoryHashMap<String,Long> keys = new HistoryHashMap<String,Long>(); 
	private String prefix;
	private long current = 0;
	
	public ValueTransformerRelativeToAccess( String prefix ){
		this.prefix = prefix;
	}
	
	public String getTransformedValue(String data) {
		++current;
		if ( keys.containsKey(data) ){
			long index = keys.get(data);
			keys.put(data,current);
			long relative = (current-index);
			return prefix+relative;
		}
		keys.put(data,current);
		return prefix+"0";
	}

	public void reset() {
		current = 0;
		keys=new HistoryHashMap<String, Long>();
	}
	
	public String getTransformedValue(String value, String[] line) {
		return getTransformedValue(value);
	}

	public void back() throws UnsupportedOperationException {
		current--;
		keys.revertToOldState();
	}
	
	public String toString() {
		return keys.toString();
	}

}
