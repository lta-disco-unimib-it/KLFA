package dfmaker.core;

import java.util.Comparator;

public class VariableComparator implements Comparator<Variable> {

	public int compare(Variable o1, Variable o2) {
		return o1.getName().compareTo(o2.getName());
	}

}
