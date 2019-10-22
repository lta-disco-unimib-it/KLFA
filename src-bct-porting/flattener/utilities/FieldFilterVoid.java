package flattener.utilities;

import java.lang.reflect.Field;

public class FieldFilterVoid extends FieldFilter {
	public boolean accept( Field field, Object object ){
		return true;
	}
}
