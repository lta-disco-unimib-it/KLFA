package regressionTestManager.ioInvariantParser;

import sjm.parse.Assembler;
import sjm.parse.Assembly;

public class RemoveValueAssembler extends Assembler {

  public void workOn(Assembly a) {    
    Target target = (Target)a.getTarget();
    //target.pop();
    try{
    	target.pop();
    }catch(ArrayIndexOutOfBoundsException e) {
      	EvaluationRuntimeErrors.emptyStack();
      	return;
    }
    try{
    	EvaluationRuntimeErrors.log("Remove not implemented");
    	
    }
    catch (Exception e) {
		EvaluationRuntimeErrors.evaluationError();
		//target.push(Boolean.FALSE);
		return;
	}
  }
}
