package recorders;

import conf.EnvironmentalSetter;

public class FlattenerAssemblerFactory {
	private static FlattenerAssembler instance;
	
	public static FlattenerAssembler getAssembler(){
		Class flattenerType = EnvironmentalSetter.getFlattenerType();
		if ( flattenerType == flattener.flatteners.BreadthObjectFlattener.class ){
			if ( instance == null )
				instance = new BreadthFlattenerAssembler();
		} else {
			if ( instance == null )
				instance = new ObjectFlattenerAssembler();
		}
		return instance;
	}
}
