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
import java.util.HashMap;

/**
 * This ValueTransformer follow the rule of the ValueTransformerRelativeToInstantiation but it has a limit on the history of values,
 * values seen limit+1 times ago or more are threated like new values.
 * 
 *  Eg.
 *  
 *  If called on this sequence (and limit is set to 4)
 * 
 * A A B C D A D A C E F G D G D F A B C F C F B 
 * 
 * it will produce a sequence:
 * 
 * 0 1 0 0 0 4 1 4 2 0 0 0 4 1 4 2 0 0 0 4 1 4 2
 * 
 * The value obtained by the ValueTransformerRelativeToInstantiation are similar apart from the last sequence
 * A B C F C F B, that in RelativeToInstantiation becomes 7 6 5 1 5 1 6 but in these case the values A B C are set
 * to 0 since they are major than 4.
 * 
 * @author Fabrizio Pastore fabrizio.pastore AT gmail.com
 *
 */
public class ValueTransformerRelativeToInstantiationAndLimited extends ValueTransformerRelativeToInstantiation {

	private int level;
	private int limit;
	private HashMap<String,Integer> dataLevels = new HashMap<String, Integer>();

	public ValueTransformerRelativeToInstantiationAndLimited(String prefix, int limit) {
		super(prefix);
		this.limit = limit;
	}

	public String getTransformedValue(String data) {
		//Check if the existing data key is valid
		if ( dataLevels.containsKey( data ) ){
			Integer dataLevel = dataLevels.get(data);
			if ( level - dataLevel > limit ){
				reset(data);
			}
		}
		dataLevels.put(data, level );
		return super.getTransformedValue(data);
		
	}
	
	public void newLevel(){
		++level;
	}
}
