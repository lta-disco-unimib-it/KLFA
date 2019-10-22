package regressionTestManager.ioInvariantParser;

import java.lang.reflect.Array;

import sjm.parse.Assembler;
import sjm.parse.Assembly;

public class SubSequenceFloatAssembler extends Assembler {
  
  private Boolean isSubSequence( Object x , Object y) {
    int MAX = Array.getLength(y);
    int subMax = Array.getLength(x);
    int subSequenceIndex = 0;
    for(int i=0;i<MAX;i++) {
      Number n1 = (Number)Array.get(x,subSequenceIndex);
      Number n2 = (Number)Array.get(y,i);
      if(n1.floatValue()==n2.floatValue())
        subSequenceIndex++;
      if(subSequenceIndex==subMax)
        return Boolean.TRUE;
    }
    return Boolean.FALSE;
  }    
  public void workOn(Assembly a) {
    Target target = (Target)a.getTarget();
    //Object y = (Object)target.pop();  
    Object y = null;
    try{
    	y = (Object)target.pop(); 
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
    try{
    	EvaluationRuntimeErrors.log(this.getClass()+" Not implemented");
//    	if(x.getClass().isArray() && y.getClass().isArray())
//    		target.push(isSubSequence(x,y));
    }
    catch (Exception e) {
		EvaluationRuntimeErrors.evaluationError();
		//target.push(Boolean.FALSE);
		return;
	}
  }
}