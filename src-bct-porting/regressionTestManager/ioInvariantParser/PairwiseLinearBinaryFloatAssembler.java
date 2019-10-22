package regressionTestManager.ioInvariantParser;

import java.lang.reflect.Array;

import sjm.parse.Assembler;
import sjm.parse.Assembly;

public class PairwiseLinearBinaryFloatAssembler extends Assembler {

  private static Boolean evaluatePairwiseLinearBinaryFloat(Object y,Number a,Object x,Number b) {
    int MAX = Array.getLength(y);
    for(int i=0;i<MAX;i++) {
      Number yn = (Number)Array.get(y,i);
      Number xn = (Number)Array.get(x,i);
      if(yn.floatValue()!=a.floatValue()*xn.floatValue()+b.floatValue())
        return Boolean.FALSE;
    }
    return Boolean.TRUE;
  } 
  public void workOn(Assembly ass) {
    Target target = (Target)ass.getTarget();
    //Number b = (Number)target.pop();  
    Number b = null;
    try{
    	b = (Number)target.pop();
    }catch(ArrayIndexOutOfBoundsException e) {
      	EvaluationRuntimeErrors.emptyStack();
      	return;
    }
    
    //Object x = (Object)target.pop();
    Object x = null;
    try{
    	x = (Object)target.pop();
    }catch(ArrayIndexOutOfBoundsException e) {
      	EvaluationRuntimeErrors.emptyStack();
      	return;
    }
    Number a = (Number)target.pop();
    //Object y = (Object)target.pop();
    Object y = null;
    try{
    	y = (Object)target.pop();
    }catch(ArrayIndexOutOfBoundsException e) {
      	EvaluationRuntimeErrors.emptyStack();
      	return;
    }
    try{
    	EvaluationRuntimeErrors.log("PairWise Not implemented");
    	//target.push(evaluatePairwiseLinearBinaryFloat(y,a,x,b));
    }catch (Exception e) {
		EvaluationRuntimeErrors.evaluationError();
		//target.push(Boolean.FALSE);
		return;
	}
  }
}