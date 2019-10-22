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
import java.lang.reflect.Modifier;

public class FieldModifiersCondition extends FieldCondition {
	protected int modifierMask = 0;
	
	protected FieldModifiersCondition(boolean accept, int modifier) {
		super(accept);
		modifierMask = modifier;
	}
	
	protected FieldModifiersCondition(boolean accept, int modifiers[]) {
		super(accept);
		for ( int i = 0; i < modifiers.length; ++i )
			modifierMask = modifierMask & modifiers[i];
		
	}

	@Override
	protected boolean match(Field field ) {
		return  ( field.getModifiers() & modifierMask ) != 0;
	}

}
