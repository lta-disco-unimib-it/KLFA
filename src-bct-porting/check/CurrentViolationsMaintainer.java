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
import java.util.HashSet;

import automata.fsa.FiniteStateAutomaton;

/**
 * This class maintains for every thread the list of the FSA that are currently violated.
 * Only FSA of the method currently executed are mantained here.
 * 
 * @author Fabrizio Pastore fabrizio.pastore AT gmail.com
 *
 */
public class CurrentViolationsMaintainer {
	private static HashMap<Long,CurrentViolationsMaintainer> threads = null;
	private HashSet<FiniteStateAutomaton> violatedFSA = new HashSet<FiniteStateAutomaton>();
	
	private CurrentViolationsMaintainer(){
		
	}
	
	
	public synchronized static CurrentViolationsMaintainer getInstance(){
		if ( threads == null ){
			threads = new HashMap<Long, CurrentViolationsMaintainer>();
		}
		
		Long thId = Thread.currentThread().getId();
		CurrentViolationsMaintainer instance = threads.get( thId );
		if ( instance == null ){
			instance = new CurrentViolationsMaintainer();
			threads.put(thId, instance);
		}
		
		return instance;
	}
	
	/**
	 * Returns true if during the current monitored method execution the FSA of the method
	 * was already violated.
	 * 
	 * @param fsa
	 * @return
	 */
	public boolean isFSAViolated( FiniteStateAutomaton fsa ){
		return violatedFSA.contains(fsa);
	}
	
	/**
	 * Tell the maintainer that this method execution is finised. The mainteiner will remove the FSA from the registry. 
	 * 
	 * @param fsa
	 */
	public void executionFinished( FiniteStateAutomaton fsa ){
		violatedFSA.remove(fsa);
	}
	
	/**
	 * The fsa is violated during current execution.
	 * @param fsa
	 */
	public void fsaViolated( FiniteStateAutomaton fsa ){
		violatedFSA.add(fsa);
	}
}
