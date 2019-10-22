package tools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class DerivedInvariantsRepository {

	private class DerivedInvariantRepositoryException extends Exception {
		
	}
	
	private HashMap<String,ArrayList<String>> preconditions = new HashMap<String,ArrayList<String>>(); 
	private HashMap<String,ArrayList<String>> postconditions = new HashMap<String,ArrayList<String>>();
	
	public boolean hasPreconditions(String methodName) {
		//System.out.println( "HAS PRE"+methodName+"   "+preconditions.containsKey(methodName) );
		return preconditions.containsKey(methodName);
	}

	public boolean hasPostconditions(String methodName) {
		return postconditions.containsKey(methodName);
	}

	public Iterator<String> getPreconditions(String methodName) throws DerivedInvariantRepositoryException {
			
		ArrayList<String> list = preconditions.get( methodName );
		if ( list == null ){
			throw new DerivedInvariantRepositoryException();
		}
		return list.iterator();
	}
	
	public Iterator<String> getPostconditions(String methodName) throws DerivedInvariantRepositoryException {
		
		ArrayList<String> list = postconditions.get( methodName );
		if ( list == null ){
			throw new DerivedInvariantRepositoryException();
		}
		return list.iterator();
	}
	
	/**
	 * Add a precondition for the given method
	 * 
	 * @param methodName
	 * @param invariant
	 */
	public void addPrecondition( String methodName, String invariant ){
		//System.out.println( "ADD PRE"+methodName+"   "+invariant );
		addinvariant( preconditions, methodName, invariant );
	}

	/**
	 * Add a postcondition fo thegiven method
	 * 
	 * @param methodName
	 * @param invariant
	 */
	public void addPostcondition( String methodName, String invariant ){
		//System.out.println( "ADD POST"+methodName+"   "+invariant );
		addinvariant( postconditions, methodName, invariant );
	}
	
	
	private void addinvariant(HashMap<String, ArrayList<String>> conditions, String methodName, String invariant) {
		ArrayList<String> invariants;
		
		if ( ! conditions.containsKey(methodName) ){
			invariants = new ArrayList<String>(); 
			conditions.put(methodName, invariants);
		} else {
			invariants = conditions.get(methodName);
		}
		
		//add only if there is not another like this
		if ( ! invariants.contains(invariant) )
			invariants.add( invariant );
	}

}
