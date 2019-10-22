package check.ioInvariantParser;

import java.lang.reflect.Array;

import sjm.parse.Assembler;
import sjm.parse.Assembly;

public class SuperSetFloatAssembler extends Assembler {
  
  private Boolean isSubSet( Object x , Object y) {
    int subMax = Array.getLength(x);
    int MAX = Array.getLength(y) - subMax +1;
    for(int i=0;i<MAX;i++) {
      Number n1 = (Number)Array.get(x,0);
      Number n2 = (Number)Array.get(y,i);
      if(n1.floatValue()==n2.floatValue()) {
        boolean found = true;
        for(int j = 1 ; j<subMax;j++) {
          Number nn1 = (Number)Array.get(x,j);
          Number nn2 = (Number)Array.get(y,i+j);
          if(nn1.floatValue()!=nn2.floatValue()) {
            found = false;
            break;
          }
        }
        if(found)
          return Boolean.TRUE;
      }
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
    	if(x.getClass().isArray() && y.getClass().isArray())
    		target.push(isSubSet(y,x));
    }
    catch (Exception e) {
		EvaluationRuntimeErrors.evaluationError();
		//target.push(Boolean.FALSE);
		return;
	}
  }
}

