package regressionTestManager.ioInvariantParser;

import sjm.parse.Assembler;
import sjm.parse.Assembly;

public class NumericFloatSquareExpressionAssembler extends Assembler{

  public void workOn(Assembly a) {
    Target target = (Target)a.getTarget();
    /*Number y = (Number)target.pop();
    Number x = (Number)target.pop();*/
    Number y = null;
    Number x = null;
    try{
    	y = (Number)target.pop();
        x = (Number)target.pop();
    }catch(ArrayIndexOutOfBoundsException e) {
      	EvaluationRuntimeErrors.emptyStack();
      	return;
    }
    try{
    	EvaluationRuntimeErrors.log("NumericFloatDIvide Not implemented");
    	//target.push(new Boolean(Math.pow(y.longValue(),2) == x.longValue()));
    }
    catch (Exception e) {
		EvaluationRuntimeErrors.evaluationError();
		//target.push(Boolean.FALSE);
		return;
	}
  }
}
