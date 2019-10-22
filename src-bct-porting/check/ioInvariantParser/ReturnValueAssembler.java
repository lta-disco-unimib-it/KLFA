package check.ioInvariantParser;

import sjm.parse.Assembler;
import sjm.parse.Assembly;

public class ReturnValueAssembler extends Assembler {

  public void workOn(Assembly a) {
    Target target = null; 
    Object returnValue = null;
    try{
    	target = (Target)a.getTarget();
    	returnValue = target.getReturnValue();
    	/*
    	if(returnValue instanceof Boolean)
    		if(((Boolean)returnValue).booleanValue())
    			returnValue = new Double(1);
    		else
    			returnValue = new Double(0); 
    			*/
    	target.push(returnValue);
    }
    catch (Exception e) {
		EvaluationRuntimeErrors.evaluationError();
		//target.push(null);
		return;
	}
  }
}
