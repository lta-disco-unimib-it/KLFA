package regressionTestManager.ioInvariantParser;

import sjm.parse.Assembler;
import sjm.parse.Assembly;

public class LinearBinaryFloatAssembler extends Assembler {

  public void workOn(Assembly ass) {
    Target target = (Target)ass.getTarget();
    
    Number result = null;
    Number c = null;
    Number y = null;
    Number b = null;
    Number x = null;
    Number a = null;
    try{
    	c = (Number)target.pop();
        y = (Number)target.pop();
        b = (Number)target.pop();
        x = (Number)target.pop();
        a = (Number)target.pop();
    }catch(ArrayIndexOutOfBoundsException e) {
      	EvaluationRuntimeErrors.emptyStack();
      	return;
    }
    try{  
    	EvaluationRuntimeErrors.log("Linera Binary Float Not Implemented");
    	//target.push(new Boolean(a.floatValue()*x.floatValue()+b.floatValue()*y.floatValue()+c.floatValue()==result.floatValue()));
    }catch (Exception e) {
		EvaluationRuntimeErrors.evaluationError();
		//target.push(null);
		return;
	}
  }
}