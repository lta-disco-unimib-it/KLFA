package regressionTestManager.tcSpecifications;

import regressionTestManager.ioInvariantParser.Variable;


public class TcSpecificationPlusOne implements TcSpecification {

	private Variable variable;


	public TcSpecificationPlusOne(Variable variable ) {
		this.variable = variable;
	}
	
	public boolean equals ( Object o ){
		if ( o == this ){
			return true;
		}
		if ( o instanceof TcSpecificationPlusOne ){
			TcSpecificationPlusOne rhs = (TcSpecificationPlusOne) o;
			return variable.equals(variable);
		}
		return false;
	}
	
	public String toString(){
		return variable.toString()+" + 1";
	}

	public Variable getVariable() {
		return variable;
	}
}
