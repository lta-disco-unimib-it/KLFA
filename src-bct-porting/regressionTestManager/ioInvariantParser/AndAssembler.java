package regressionTestManager.ioInvariantParser;

import regressionTestManager.tcSpecifications.TcSpecification;
import regressionTestManager.tcSpecifications.TcSpecificationAnd;
import sjm.parse.Assembler;
import sjm.parse.Assembly;

public class AndAssembler extends Assembler {

	
	public void workOn(Assembly a) {
		Target target = null;
		TcSpecification leftSide = null;
		TcSpecification rightSide = null;
		
		try{
			target = (Target)a.getTarget();
			
			rightSide = (TcSpecification)target.pop();  
			leftSide = (TcSpecification)target.pop();  
			
		}catch(ArrayIndexOutOfBoundsException e) {
			//System.out.println("AndAssembler "+target.toString());
			EvaluationRuntimeErrors.emptyStack();
			return;
		}
		
		target.push(new TcSpecificationAnd(leftSide,rightSide));
	}
	


}
