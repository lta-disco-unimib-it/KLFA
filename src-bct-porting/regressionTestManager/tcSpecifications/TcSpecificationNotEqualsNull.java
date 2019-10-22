package regressionTestManager.tcSpecifications;

import regressionTestManager.ioInvariantParser.Variable;

public class TcSpecificationNotEqualsNull implements TcSpecification {

	protected Variable variable;
	
	public TcSpecificationNotEqualsNull(Variable variable) {
		this.variable = variable;
	}

	public boolean equals( Object o ){
		if ( o == this )
			return true;
		if ( o instanceof TcSpecificationNotEqualsNull )
			return ((TcSpecificationNotEqualsNull)o).variable.equals(variable);
		return false;
	}
	
	public String toString(){
		return variable+" != null";
	}

	public Variable getVariable() {
		return variable;
	}
}
