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
package it.unimib.disco.lta.alfa.inferenceEngines;

import automata.State;

/**
 * @author Leonardo Mariani
 *
 * Inner class for returning the data of a behavioral pattern
 */
class BehavioralPatternData {
	public State fromState;
	public State toState;

	public int fromTrace;
	public int toTrace;

	public int lengthTrace;

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new String(
			"Behavioral Pattern from position "
				+ fromTrace
				+ " to position "
				+ toTrace
				+ " of the trace file, corresponding to submachine between state "
				+ fromState
				+ " and state "
				+ toState
				+ ". The behavior length is "
				+ lengthTrace);
	}
}