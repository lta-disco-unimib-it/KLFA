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
 * If the distance is greater than the given threshold returns "0".
 *
 * For example if applied to these symbols (limit = 4):
 * 
 * A A B C D D A C
 * 
 * it will produce a sequence:
 * 
 * 0 1 0 0 0 1 0 4  
 * 
 * A A B B C C D A D A C
 * 
 * will produce a sequence:
 * 
 * 0 1 0 1 0 1 0 0 2 2 0 
 * 
 * @author Fabrizio Pastore fabrizio.pastore AT gmail.com
 *
 */
public class ValueTransformerRelativeToAccessReset implements ValueTransformer {
	private HistoryHashMap<String,Long> keys = new HistoryHashMap<String,Long>(); 
	private String prefix;
	private long current = 0;
	private int limit;
	
	public ValueTransformerRelativeToAccessReset( String prefix, int limit ){
		this.prefix = prefix;
		this.limit = limit;
	}
	
	public String getTransformedValue(String data) {
		//System.out.println(data);
		++current;
		if ( keys.containsKey(data) ){
			long index = keys.get(data);
			keys.put(data,current);
			long relative = -1*(index-current);
			if ( relative > limit ){
				return prefix+"0";
			}	
			return prefix+relative;
		}
		keys.put(data,current);
		return prefix+"0";
	}

	public void reset() {
		current = 0;
		keys = new HistoryHashMap<String, Long>();
	}
	
	public String getTransformedValue(String value, String[] line) {
		return getTransformedValue(value);
	}

	public void back() throws UnsupportedOperationException {
		keys.revertToOldState();
	}

}
