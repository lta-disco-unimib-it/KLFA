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
package check.ioInvariantParser;

import flattener.flatteners.BreadthObjectFlattener;
import flattener.utilities.FieldFilter;
import flattener.utilities.FieldsRetriever;
import recorders.BreadthFlattenerAssembler;

public class FlattenerCoordinator {
	
	private BreadthObjectFlattener flattener;
	private static FlattenerCoordinator instance;
	
	private FlattenerCoordinator(){
		flattener = new BreadthObjectFlattener(null);
	}
	
	public synchronized static FlattenerCoordinator getInstance(){
		if ( instance == null )
			instance = new FlattenerCoordinator();
		return instance;
	}

	public boolean isSmashAggregations() {
		
		return flattener.isSmashAggregations();
	}

	public boolean isPrimitiveArray(Object object) {
		
		return flattener.isPrimitiveArray(object);
	}

	public boolean isIgnoredType(Object object) {
		
		return flattener.isIgnoredType(object);
	}
	
	public boolean isPrimitiveType(Object object) {
		
		return flattener.isPrimitiveType(object);
	}

	public FieldsRetriever getFieldsRetriever() {
		return flattener.getFieldsRetriever();
	}
}
