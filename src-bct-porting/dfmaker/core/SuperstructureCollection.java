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
package dfmaker.core;

import java.util.Collection;
import java.util.HashMap;

public class SuperstructureCollection {
	private HashMap superstructures = new HashMap(2);
	
	public void put ( Superstructure superstructure ){
		superstructures.put( superstructure.getProgramPointName(), superstructure );
	}
	
	public Superstructure get ( String programPointName ){
		return (Superstructure) superstructures.get(programPointName);
	}

	public Collection values() {
		return superstructures.values();
	}
}
