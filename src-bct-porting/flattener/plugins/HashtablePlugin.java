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
package flattener.plugins;

import java.util.Enumeration;
import java.util.Hashtable;

import flattener.core.Plugin;


/**
 * @author Davide Lorenzoli
 */
public class HashtablePlugin implements Plugin {

	/**
	 * @see flattener.core.Plugin#smash(java.lang.Object)
	 */
	public Object[] smash(Object object) {
		Hashtable ht = (Hashtable) object;
		Object array[] = new Object[ht.size()];
			
		Enumeration keys = ht.keys();
		int i = 0;		
		while (keys.hasMoreElements()) {
			array[i++] = ht.get(keys.nextElement());
		}	
		return array;
	}

    /**
     * @see flattener.plugins.Plugin#getTargetClass()
     */
    public Class getTargetClass() {        
        return Hashtable.class;
    }
}
