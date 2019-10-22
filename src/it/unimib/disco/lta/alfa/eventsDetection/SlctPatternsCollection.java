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
package it.unimib.disco.lta.alfa.eventsDetection;

import it.unimib.disco.lta.alfa.eventsDetection.slct.SlctPattern;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SlctPatternsCollection implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private HashMap<String,List<SlctPattern>> patterns = new HashMap<String, List<SlctPattern>>();
	private List<SlctPattern> allPatterns = new ArrayList<SlctPattern>();
	
	public List<SlctPattern> getPatternsForComponent(Object key) {
		return patterns.get(key);
	}

	public List<SlctPattern> putPatternsForComponent(String key, List<SlctPattern> value) {
		//TODO: throw exc if component already defined
		allPatterns.addAll(value);
		return patterns.put(key, value);
		
	}

	public List<SlctPattern> getAllPatterns() {
		return allPatterns;
	}
}
