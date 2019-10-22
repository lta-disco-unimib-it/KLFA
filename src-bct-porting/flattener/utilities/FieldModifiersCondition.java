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
