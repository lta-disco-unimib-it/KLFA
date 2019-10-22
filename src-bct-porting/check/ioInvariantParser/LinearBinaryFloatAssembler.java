package check.ioInvariantParser;

import sjm.parse.Assembler;
import sjm.parse.Assembly;

public class LinearBinaryFloatAssembler extends Assembler {

  public void workOn(Assembly ass) {
    Target target = (Target)ass.getTarget();
   
    try{
    	Number result = (Number)target.pop();
    	Number c = (Number)target.pop();
    	Number y = (Number)target.pop();
    	Number b = (Number)target.pop();
    	Number x = (Number)target.pop();
    	Number a = (Number)target.pop();
    	
    	if ( c == null || y == null || b == null ||	x == null || a == null ){
    		EvaluationRuntimeErrors.evaluationError();
    		return;
    	}
    	
    	target.push( Boolean.valueOf(a.floatValue()*x.floatValue()+b.floatValue()*y.floatValue()+c.floatValue()==result.floatValue()));
    	
    }catch(ArrayIndexOutOfBoundsException e) {
    	EvaluationRuntimeErrors.emptyStack();
    	return;
    }catch (Exception e) {
    	EvaluationRuntimeErrors.evaluationError();
    	return;
    }

  }
}