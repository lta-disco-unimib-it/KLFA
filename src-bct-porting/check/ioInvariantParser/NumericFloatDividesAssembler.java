package check.ioInvariantParser;

import sjm.parse.Assembler;
import sjm.parse.Assembly;

public class NumericFloatDividesAssembler extends Assembler{

  public void workOn(Assembly a) {
    Target target = (Target)a.getTarget();
    /*Number y = (Number)target.pop();
    Number x = (Number)target.pop();*/
    Number y = null;
    Number x = null;
    try{
    	y = (Number)target.pop();
        x = (Number)target.pop();
    }catch(Exception e) {
      	EvaluationRuntimeErrors.emptyStack();
      	return;
    }
    try{
    target.push(new Boolean(x.longValue()%y.longValue() == 0));
    }
    catch (Exception e) {
		EvaluationRuntimeErrors.evaluationError();
		//target.push(Boolean.FALSE);
		return;
	}
  }
}