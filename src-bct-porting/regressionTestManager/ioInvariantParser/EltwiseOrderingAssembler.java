package regressionTestManager.ioInvariantParser;

import java.lang.reflect.Array;

import sjm.parse.Assembler;
import sjm.parse.Assembly;
import sjm.parse.tokens.Token;

public class EltwiseOrderingAssembler extends Assembler {
  private Boolean greaterEqual( Object a1) {
    int MAX = Array.getLength(a1);
    double previous = Double.NEGATIVE_INFINITY;
    for(int i=0;i<MAX;i++) {
      Number n = (Number)Array.get(a1,i);
      if(n.doubleValue()>previous)
        return Boolean.FALSE;
      previous = n.doubleValue();
    }
    return Boolean.TRUE;
  }
  private Boolean greater(Object a1) {
    int MAX = Array.getLength(a1);
    double previous = Double.NEGATIVE_INFINITY;
    for(int i=0;i<MAX;i++) {
      Number n = (Number)Array.get(a1,i);
      if(n.doubleValue()>=previous)
        return Boolean.FALSE;
      previous = n.doubleValue();
    }
    return Boolean.TRUE;
  } 
  private Boolean less(Object a1) {
    int MAX = Array.getLength(a1);
    double previous = Double.POSITIVE_INFINITY;
    for(int i=0;i<MAX;i++) {
      Number n = (Number)Array.get(a1,i);
      if(n.doubleValue()<=previous)
        return Boolean.FALSE;
      previous = n.doubleValue();
    }
    return Boolean.TRUE;
  }   
  private Boolean lessEqual(Object a1) {
    int MAX = Array.getLength(a1);
    double previous = Double.POSITIVE_INFINITY;
    for(int i=0;i<MAX;i++) {
      Number n = (Number)Array.get(a1,i);
      if(n.doubleValue()<previous)
        return Boolean.FALSE;
      previous = n.doubleValue();
    }
    return Boolean.TRUE;
  }     
  public void workOn(Assembly a) {
    Target target = (Target)a.getTarget();
    /*
    Object parameter1 = (Object)target.pop();
    */
    Object parameter1 = null;
    try{
    	parameter1 = target.pop();
    }catch(ArrayIndexOutOfBoundsException e) {
      	EvaluationRuntimeErrors.emptyStack();
      	return;
    }
    
    //Token t = (Token)a.pop();
    Token t = null;
    try{
    	t = (Token)a.pop();
    }catch(ArrayIndexOutOfBoundsException e) {
      	EvaluationRuntimeErrors.emptyStack();
      	return;
    }
    
    EvaluationRuntimeErrors.log("Eltwise not implemented");
    return;
    
//    String operator = null;
//    try{
//    	operator = t.sval();
//    	if(operator.equals("\">=\""))
//    	    target.push(greaterEqual(parameter1));
//    		else if(operator.equals("\">\""))
//    	      target.push(greater(parameter1));
//    	    else if(operator.equals("\"<\""))
//    	      target.push(less(parameter1));
//    	    else if(operator.equals("\"<=\""))
//    	      target.push(lessEqual(parameter1));
//    }catch (Exception e) {
//		EvaluationRuntimeErrors.evaluationError();
//		//target.push(Boolean.FALSE);
//		return;
//    }      
  }
}

