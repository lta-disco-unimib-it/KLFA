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