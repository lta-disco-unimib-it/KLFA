/*******************************************************************************
 *    Copyright 2019 Fabrizio Pastore, Leonardo Mariani
 *   
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 *******************************************************************************/
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
