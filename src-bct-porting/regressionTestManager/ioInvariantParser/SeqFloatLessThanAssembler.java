package regressionTestManager.ioInvariantParser;

import java.lang.reflect.Array;

import sjm.parse.Assembler;
import sjm.parse.Assembly;

public class SeqFloatLessThanAssembler extends Assembler {

  private Boolean evaluate(Object array,Number scalar) {
    int MAX = Array.getLength(array);
    for(int i=0;i<MAX;i++) {
      Number element = (Number)Array.get(array,i);

      if(element.doubleValue() >= scalar.doubleValue())
        return Boolean.FALSE;
    }
    return Boolean.TRUE;
  }
  public void workOn(Assembly a) {
    Target target = (Target)a.getTarget();
    
    //Object scalar = target.pop();
    Object scalar = null;
    try{
    	scalar = target.pop();
    }catch(ArrayIndexOutOfBoundsException e) {
      	EvaluationRuntimeErrors.emptyStack();
      	return;
    }
    
    //Object array = target.pop();
    Object array = null;
    try{
    	array = target.pop();
    }catch(ArrayIndexOutOfBoundsException e) {
      	EvaluationRuntimeErrors.emptyStack();
      	return;
    }
    try{
    	EvaluationRuntimeErrors.log(this.getClass()+" Not implemented");
//    	if(scalar instanceof Number)
//    		target.push(evaluate(array,(Number)scalar));
    }
    catch (Exception e) {
		EvaluationRuntimeErrors.evaluationError();
		//target.push(Boolean.FALSE);
		return;
	}
  }
}
