package regressionTestManager.ioInvariantParser;

import java.util.HashMap;

import flattener.flatteners.ObjectFlattener;

/**
 * This class is used to not inspect classes ignored in logging phase.
 * This class,implemented as a singleton, maintain an instance of ObjectFlattener and uses it
 * to check if an object can be inspected or not.
 * 
 * This class is useful because can exists invariants on interfaces 
 * implemented by objects that have to be ignored and a call on a method of this object can have side effects on the program
 * so the object must be ignored also in checking phase.
 * 
 * @author Fabrizio Pastore [ fabrizio.pastore at gmail dot com ]
 *
 */
public class ClassTypeChecker {
	private static ClassTypeChecker instance = null;
	private	ObjectFlattener flattener = null;
	
	public ClassTypeChecker () {
		flattener = new ObjectFlattener();
		
	}
	
	
	public static synchronized  ClassTypeChecker getInstance(){
		if ( instance == null ){
			instance = new ClassTypeChecker();
		}
		return instance;
	}
	
	public boolean isIgnoredType( Object object ){
		//if do not smash aggregations, don't check them
		if ( ! flattener.isSmashAggregations() ){
			if ( flattener.isPrimitiveArray( object))
				return true;
			if ( object.getClass().isArray() )
				return true;
			if ( object.getClass().getName().equals("java.util.HashMap") )
				return true;
		}
		
		if ( object != null )
			return false;
		
		return flattener.isIgnoredType(object);
		
	}
}
