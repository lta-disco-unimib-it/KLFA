package check.ioInvariantParser;

import java.lang.reflect.Array;

import sjm.parse.Assembler;
import sjm.parse.Assembly;

public class NoDuplicatesFloatAssembler extends Assembler{

  private Boolean arrayContainsNoDuplicates(Object array) {
    int MAX = Array.getLength(array);
    for(int i=0;i<MAX-1;i++) {
      Number n = (Number)Array.get(array,i);
      for(int j=i+1;j<MAX;j++) {
        Number element = (Number)Array.get(array,j);
        if(element.floatValue()==n.floatValue())
          return Boolean.FALSE;
      }
    }
    return Boolean.TRUE;
  }    
  public void workOn(Assembly a) {
    Target target = (Target)a.getTarget();
    //Object array = (Object)target.pop();
    Object array = null;
    try{
    	array = (Object)target.pop();
    }catch(ArrayIndexOutOfBoundsException e) {
      	EvaluationRuntimeErrors.emptyStack();
      	return;
    }
    try{
    	target.push(arrayContainsNoDuplicates(array));
    }
    catch (Exception e) {
    	EvaluationRuntimeErrors.evaluationError();
    	//target.push(Boolean.FALSE);
    	return;
    }	
  }
}
