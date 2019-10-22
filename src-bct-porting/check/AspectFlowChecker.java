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


/**
 * This class is used to check if an aspect is currently running.
 * 
 * @author Fabrizio Pastore [ fabrizio.pastore at gmail dot com ]
 *
 */
public class AspectFlowChecker {
	public static final HashMap<Long,Boolean> threads = new HashMap<Long,Boolean>();
	
	public static synchronized boolean isInsideAnAspect(){
		Boolean state = (Boolean) threads.get( new Long(Thread.currentThread().getId()));
		if ( state == null )
			return false;
		return state;
	}
	
	public static synchronized void setInsideAnAspect( Boolean state){
		threads.put( new Long(Thread.currentThread().getId()), state);
	}
	
}
