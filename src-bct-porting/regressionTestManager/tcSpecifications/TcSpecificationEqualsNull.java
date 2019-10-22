package regressionTestManager.tcSpecifications;

import regressionTestManager.ioInvariantParser.Variable;

public class TcSpecificationEqualsNull implements TcSpecification {

	private Variable variable;
	
	public TcSpecificationEqualsNull(Variable variable) {
		this.variable = variable;
	}

	public boolean equals( Object o ){
		if ( o == this )
			return true;
		if ( o instanceof TcSpecificationEqualsNull )
			return ((TcSpecificationEqualsNull)o).variable.equals(variable);
		return false;
	}
	
	public String toString(){
		return variable + " == null";
	}

	public Variable getVariable() {
		return variable;
	}
}
