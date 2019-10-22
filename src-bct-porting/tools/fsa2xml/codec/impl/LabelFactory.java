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
package tools.fsa2xml.codec.impl;

import java.util.HashMap;

public class LabelFactory {
	private HashMap<String,String> label = new HashMap<String, String>();
	
	/**
	 * Returns a label equals to the provided one.
	 * This method guarantees that all the labels returned which are equals are also the same object.
	 * @param description
	 * @return
	 */
	public String getLabel(String description) {
		String desc = label.get(description);
		if ( desc == null ){
			label.put(description, description);
			desc = description;
		}
		return desc;
	}

}
