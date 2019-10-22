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
