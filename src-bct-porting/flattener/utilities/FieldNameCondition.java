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
