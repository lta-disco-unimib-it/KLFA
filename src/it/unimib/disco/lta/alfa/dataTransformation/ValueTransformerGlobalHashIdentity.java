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

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * This value trabformer work like the ValueTransformerGlobalHash, but two distinct values cannot be associated to the same abstract value (the ker automatically generated)
 * In that case the algorithm associate a new key by adding the appearance order.
 * 
 * 
 * 
 * @author Fabrizio Pastore
 *
 */
public class ValueTransformerGlobalHashIdentity extends ValueTransformerGlobalHash {
	private Set<String> generatedKeys = new HashSet<String>();
	
	public ValueTransformerGlobalHashIdentity(String prefix,
			List<Integer> positions) {
		super(prefix, positions);
	}

	@Override
	protected String calculateKey(String[] line) {
		
		String generatedKey = super.calculateKey(line);
		
		int i = 1;
		String key = generatedKey;
		while ( generatedKeys.contains(key) ){
			key=generatedKey+"_"+i;
		}
		generatedKeys.add(key);
		return key;
	}

}
