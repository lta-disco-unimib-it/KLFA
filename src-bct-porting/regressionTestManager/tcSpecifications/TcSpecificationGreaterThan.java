package regressionTestManager.tcSpecifications;

import regressionTestManager.ioInvariantParser.Variable;


public class TcSpecificationGreaterThan extends TcSpecificationSingle{

	public TcSpecificationGreaterThan( Variable variableName, Object value) {
		super( variableName, value);
	}
	
	public String toString(){
		return variable+" > "+value;
	}

}
