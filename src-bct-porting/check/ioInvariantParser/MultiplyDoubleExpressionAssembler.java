
package check.ioInvariantParser;

import sjm.parse.Assembler;
import sjm.parse.Assembly;

public class MultiplyDoubleExpressionAssembler extends Assembler{

  public void workOn(Assembly a) {
    Target target = (Target)a.getTarget();
    /*Number z = (Number)target.pop();
    Number y = (Number)target.pop();
    Number x = (Number)target.pop();*/
    Number z = null;
    Number y = null;
    Number x = null;
    try{
    	z = (Number)target.pop();
        y = (Number)target.pop();
        x = (Number)target.pop();
    }catch(ArrayIndexOutOfBoundsException e) {
      	EvaluationRuntimeErrors.emptyStack();
      	return;
    }
    try{
    	target.push( Boolean.valueOf(y.longValue()*z.longValue() == x.longValue()));
    }catch (Exception e) {
		EvaluationRuntimeErrors.evaluationError();
		//target.push(Boolean.FALSE);
		return;
	}
  }
}
