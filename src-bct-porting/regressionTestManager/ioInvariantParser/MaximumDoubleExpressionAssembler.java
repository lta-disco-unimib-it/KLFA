package regressionTestManager.ioInvariantParser;

import sjm.parse.Assembler;
import sjm.parse.Assembly;

public class MaximumDoubleExpressionAssembler extends Assembler{

  public void workOn(Assembly a) {
    Target target = (Target)a.getTarget();
    /*Double z = (Double)target.pop();
    Double y = (Double)target.pop();
    Double x = (Double)target.pop();*/
    Double z = null;
    Double y = null;
    Double x = null;
    try{
    	z = (Double)target.pop();
        y = (Double)target.pop();
        x = (Double)target.pop();
    }catch(ArrayIndexOutOfBoundsException e) {
      	EvaluationRuntimeErrors.emptyStack();
      	return;
    }
    try{
    	EvaluationRuntimeErrors.log("maximumDouble Not Implemented");
    	//target.push(new Boolean(Math.max(y.longValue() , z.longValue()) == x.longValue()));
    }
    catch (Exception e) {
		EvaluationRuntimeErrors.evaluationError();
		//target.push(null);
		return;
	}
  }
}
