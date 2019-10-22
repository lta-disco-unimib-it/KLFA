package check.ioInvariantParser;

import java.lang.reflect.Array;

import sjm.parse.Assembler;
import sjm.parse.Assembly;

public class GreaterThanAssembler extends Assembler {

  private Boolean evaluateNumbers( Number n1 , Number n2) {
    return Boolean.valueOf(n1.doubleValue() > n2.doubleValue());
  }
  private Boolean evaluateStrings( String s1 , String s2) {
    return Boolean.valueOf(s1.compareTo(s2)>0);
  }  
  private Boolean evaluateCharacters( Character c1 , Character c2) {
    return Boolean.valueOf(c1.compareTo(c2)>0);
  }    
  private Boolean evaluateArrays( Object a1 , Object a2) {
    int MAX = Array.getLength(a1);
    try {
    for(int i=0;i<MAX;i++) {
      Comparable c1 = (Comparable)Array.get(a1,i);     
      Comparable c2 = (Comparable)Array.get(a2,i);
      if(c1.compareTo(c2)<=0)
        return Boolean.FALSE;
    	}
    }catch (ClassCastException cce){
		return Boolean.TRUE;
	}
    return Boolean.TRUE;
  }    
  public void workOn(Assembly a) {
	Target target = null;
	Object parameter2 = null;
	
	try{
	  target = (Target)a.getTarget();
      parameter2 = (Object)target.pop();  
    }catch(ArrayIndexOutOfBoundsException e) {
    	EvaluationRuntimeErrors.emptyStack();
    	return;
    }

    //Object parameter1 = (Object)target.pop();
    Object parameter1 = null;
    try{
    	parameter1 = (Object)target.pop(); 
      }catch(ArrayIndexOutOfBoundsException e) {
      	EvaluationRuntimeErrors.emptyStack();
      	return;
      }
    try{
    if ( parameter1 instanceof Number && parameter2 instanceof Number )
      target.push(evaluateNumbers((Number)parameter1,(Number)parameter2));
    else if (parameter1 instanceof String && parameter2 instanceof String)
      target.push(evaluateStrings((String)parameter1,(String)parameter2));
    else if (parameter1 instanceof Character && parameter2 instanceof Character)
      target.push(evaluateCharacters((Character)parameter1,(Character)parameter2));
    else if(parameter1.getClass().isArray() && parameter2.getClass().isArray())
      target.push(evaluateArrays(parameter1,parameter2));
    }catch (Exception e) {
		EvaluationRuntimeErrors.evaluationError();
		//target.push(Boolean.FALSE);
		return;
	}
  }
}
