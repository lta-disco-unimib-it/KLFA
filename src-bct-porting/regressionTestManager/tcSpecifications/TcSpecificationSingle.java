package regressionTestManager.tcSpecifications;

import regressionTestManager.ioInvariantParser.Variable;


public class TcSpecificationSingle implements TcSpecification {
	
	protected Variable variable;
	protected Object value;
	
	public TcSpecificationSingle( Variable variable, Object value ){
		this.variable = variable;
		this.value = value;
	}
	
	public boolean equals( Object o ){
		if ( this == o )
			return true;
		
		if ( o instanceof TcSpecificationSingle ){
			TcSpecificationSingle rhs = (TcSpecificationSingle) o;
			return ( variable.equals(rhs.variable) &&
					value.equals( rhs.value ) );
		}
		
		return false;
	}

	public Object getRightSide() {
		return value;
	}

	public Variable getVariable() {
		return variable;
	}
	
}
