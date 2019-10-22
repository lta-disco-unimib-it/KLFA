package regressionTestManager.tcSpecifications;

import regressionTestManager.ioInvariantParser.Variable;


public class TcSpecificationLessThan extends TcSpecificationSingle {

	public TcSpecificationLessThan( Variable variable, Object value) {
		super( variable, value);
	}

	public String toString(){
		return variable+" < "+value;
	}
}
