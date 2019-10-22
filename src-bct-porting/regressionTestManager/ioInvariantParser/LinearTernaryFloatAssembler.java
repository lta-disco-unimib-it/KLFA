package regressionTestManager.ioInvariantParser;

import sjm.parse.Assembler;
import sjm.parse.Assembly;

public class LinearTernaryFloatAssembler extends Assembler {

  public void workOn(Assembly ass) {
    /*Target target = (Target)ass.getTarget();
    Number result = (Number)target.pop();  
    Number d = (Number)target.pop();
    Number z = (Number)target.pop();
    Number c = (Number)target.pop();    
    Number y = (Number)target.pop();
    Number b = (Number)target.pop();
    Number x = (Number)target.pop();
    Number a = (Number)target.pop();*/
    
	Target target = null;  
    Number result = null;  
    Number d = null;
    Number z = null;
    Number c = null;    
    Number y = null;
    Number b = null;
    Number x = null;
    Number a = null;
    try{
    	target = (Target)ass.getTarget();
        result = (Number)target.pop();  
        d = (Number)target.pop();
        z = (Number)target.pop();
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
    	EvaluationRuntimeErrors.log("Linera Ternary Float Not Implemented");
//    	target.push(new Boolean(a.floatValue()*x.floatValue()+b.floatValue()*y.floatValue()+c.floatValue()*z.floatValue()+d.floatValue()==result.floatValue()));
    }catch (Exception e) {
		EvaluationRuntimeErrors.evaluationError();
		//target.push(null);
		return;
	}
  }
}