package flattener.utilities;

import java.lang.reflect.Field;

public abstract class FieldCondition {

	private boolean accept;
	
	protected FieldCondition( boolean accept){
		this.accept = accept;
	}
	
	/**
	 * Return if a field is acceptable:
	 * 
	 * @param field
	 * @param object
	 * @return
	 */
	public boolean applies(Field field) {
		return ( ( match(field) ) );
	}

	protected abstract boolean match(Field field);

	public boolean isAccept() {
		return accept;
	}
	
	
}
