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
