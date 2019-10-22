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
