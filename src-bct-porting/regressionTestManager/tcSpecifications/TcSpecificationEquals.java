package regressionTestManager.tcSpecifications;

import regressionTestManager.ioInvariantParser.Variable;


public class TcSpecificationEquals extends TcSpecificationSingle implements TcSpecification {

	public TcSpecificationEquals( Variable variable, Object value) {
		super( variable, value);
	}

	public String toString(){
		return variable+" == "+value;
	}
	
}
