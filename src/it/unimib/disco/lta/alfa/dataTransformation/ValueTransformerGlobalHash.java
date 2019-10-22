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
import java.util.ArrayList;
import java.util.List;


public class ValueTransformerGlobalHash implements ValueTransformer {
	protected HistoryHashMap<String,String> keys = new HistoryHashMap<String, String>(); 
	private String prefix;
	private List<Integer> positions=new ArrayList<Integer>();
	private boolean changed = false;
	
	public ValueTransformerGlobalHash( String prefix, List<Integer> positions){
		this.prefix = prefix;
		this.positions.addAll(positions);
	}
	
	public String getTransformedValue( String data ) {
		return "";
	}

	public void reset() {
		keys = new HistoryHashMap<String, String>();
	}

	public String getTransformedValue(String value, String[] line) {
		if ( keys.containsKey(value) ){
			changed = false;
			return keys.get(value);
		} else {
			changed = true;
			String key = calculateKey(line);
			keys.put( value, key );
			return key;
		}
		
	}

	protected String calculateKey(String[] line) {
		String key = "";
		for ( int position : positions ){
			key+=line[position];
		}
		for ( String s : line ){
			System.out.print(s+" ");
		}
		System.out.println(" ");
		System.out.println("!!KEY "+key);
		return prefix+key.hashCode();
	}

	public void back() throws UnsupportedOperationException {
		if ( changed ){
			keys.revertToOldState();
			changed = false;
		}
	}
	
	public String toString() {
		return keys.toString();
	}

}
