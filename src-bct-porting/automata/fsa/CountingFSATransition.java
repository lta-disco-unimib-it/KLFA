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
package automata.fsa;

import java.util.ArrayList;
import java.util.List;

import automata.State;

public class CountingFSATransition extends FSATransition {
	private ArrayList<Integer> symbolIds = new ArrayList<Integer>();
	
	public CountingFSATransition(State from, State to, String label, int symbolPosition) {
		super(from, to, label);
		addVisitingSymbol(symbolPosition);
	}

	public CountingFSATransition(State fromState, State newState, String label, List<Integer> symbols) {
		super(fromState, newState, label);
		symbolIds.addAll(symbols);
	}

	private void addVisitingSymbol(int symbolPosition) {
		symbolIds.add(symbolPosition);
	}

	public ArrayList<Integer> getSymbolIds() {
		return symbolIds;
	}

	
}
