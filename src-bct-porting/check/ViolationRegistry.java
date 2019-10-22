package check;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class ViolationRegistry {
	
	private static ViolationRegistry instance;
	private HashMap violations = new HashMap();
	
	/**
	 * Return the instance of IOMemoryRegistry for the current thread.
	 * 
	 * @return IOMemoryRegistry instance or the current thread
	 */
	public static synchronized ViolationRegistry getInstance(){
		if ( instance == null ){
			instance = new ViolationRegistry();
		}
		return instance;
	}
	
	public void addViolation( String methodSignature ){
		Integer num = (Integer) violations.get( methodSignature );
		if ( num == null ){
			num = new Integer(0);
		}
		num += 1;
		violations.put(new String(methodSignature),num);
	}

	public boolean containsViolation(String methodSignature) {
		return violations.containsKey(methodSignature);
		
	}

	public void finalize(){
		Iterator mvi = violations.keySet().iterator();
		System.out.println("Violations summary : ");
		while ( mvi.hasNext() ){
			String meth  = (String) mvi.next();
			System.out.println(meth+" : "+violations.get(meth));
		}
		System.out.flush();
	}
	
	
}
