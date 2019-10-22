package regressionTestManager.ioInvariantParser;

import sjm.parse.Assembler;
import sjm.parse.Assembly;

public class ReturnValueAssembler extends Assembler {

  public void workOn(Assembly a) {
    Target target = null; 
    Object returnValue = null;
    try{
    	target = (Target)a.getTarget();
    	
    	target.push(new Variable(target.getProgramPointName(),"returnValue"));
    }
    catch (Exception e) {
		EvaluationRuntimeErrors.evaluationError();
		//target.push(null);
		return;
	}
  }
}
