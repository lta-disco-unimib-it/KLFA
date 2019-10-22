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

/**
 * This class is a filter that permits to have a condition on the name 
 * of the field and on the runtime class of the object owning the field.
 * 
 *  
 * 
 * @author Fabrizio Pastore - fabrizio.pastore@gmail.com
 *
 */
public class FieldNameCondition extends FieldCondition {
	private String fieldRegexp;
	private String packageRegexp;
	private String classRegexp;

	protected FieldNameCondition(boolean accept, String packageCondition, String classCondition, String fieldCondition) {
		super(accept);
		fieldRegexp = fieldCondition;
		classRegexp = classCondition;
		packageRegexp = packageCondition;
		
	}

	protected boolean match(Field field ) {
		if ( field.getDeclaringClass().getPackage() == null ){
			if ( ! "".matches(packageRegexp) )
				return false;
		}else{
			
			if ( ! field.getDeclaringClass().getPackage().getName().matches(packageRegexp) )
				return false;
		}
		if ( ! field.getDeclaringClass().getSimpleName().matches(classRegexp) ){
			return false;
		}
		return ( field.getName().matches(fieldRegexp) );
		
	}

}
