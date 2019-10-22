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
package flattener.utilities;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class FieldFilter {
	ArrayList<FieldCondition> conditions = null;
	
	FieldFilter(){
		conditions = new ArrayList<FieldCondition>(0);
	}
	
	FieldFilter ( ArrayList<FieldCondition> conditions ){
		this.conditions = new ArrayList<FieldCondition>( conditions );
	}
	
	public boolean accept( Field field ){
		for ( FieldCondition condition : conditions ){
			if ( condition.applies(field) && condition.isAccept() )
				return true;
			if ( condition.applies(field) && ! condition.isAccept() )
				return false;
		}
		
		//TODO: add checks for superclasses
		
		return true;
	}

	public void addCondition(FieldCondition condition) {
		conditions.add(condition);
	}
	
	public int getRulesNumber(){
		return conditions.size();
	}
}
