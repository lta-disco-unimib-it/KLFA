package check.ioInvariantParser;

import java.lang.reflect.Array;

import sjm.parse.Assembler;
import sjm.parse.Assembly;

public class EqualsAssembler extends Assembler {
  static final double DFBias = 0.001;
  
  private Boolean evaluateNumbers( Number n1 , Number n2) {
	  //System.out.println(n1.doubleValue()+" "+n2.doubleValue());
    return Boolean.valueOf(Math.abs(n1.doubleValue() - n2.doubleValue())<DFBias);
  }
  private Boolean evaluateStrings( String s1 , String s2) {
    return Boolean.valueOf(s1.compareTo(s2)==0);
  }  
  private Boolean evaluateCharacters( Character c1 , Character c2) {
    return Boolean.valueOf(c1.compareTo(c2)==0);
  }    
  private Boolean evaluateArrays( Object a1 , Object a2) {
    int MAX = Array.getLength(a1);
    try {
    for(int i=0;i<MAX;i++) {
      Comparable c1 = (Comparable)Array.get(a1,i);
      Comparable c2 = (Comparable)Array.get(a2,i);
      if(c1.compareTo(c2)!=0)
        return Boolean.FALSE;
    	}
    } catch (ClassCastException cce) {
    	//TODO: is this correct?
		return Boolean.TRUE;
	}
    return Boolean.TRUE;
  }    
  public void workOn(Assembly a) {
    Target target = (Target)a.getTarget();
   	//System.out.println("EqualsAssembler "+target.toString());
   	//System.out.flush();
    /*Object parameter2 = (Object)target.pop();
    if(target.isEmpty())
      return;
    Object parameter1 = (Object)target.pop();*/
    Object parameter2 = null;
    try{
    	parameter2 = (Object)target.pop();
    }catch(ArrayIndexOutOfBoundsException e) {
      	EvaluationRuntimeErrors.emptyStack();
      	return;
    }
    Object parameter1 = null;
    try{
    	parameter1 = (Object)target.pop();
    }catch(ArrayIndexOutOfBoundsException e) {
      	EvaluationRuntimeErrors.emptyStack();
      	return;
    }
    try{
    	//System.out.println("EqualsAssembler working on "+parameter1+" "+parameter2);
    	
    	if(parameter1 == null && parameter2==null)
            target.push( Boolean.TRUE );
        if(parameter1 == null || parameter2==null)
          target.push(new Boolean(parameter1==parameter2));
        else if ( parameter1 instanceof Boolean && parameter2 instanceof Boolean )
        	target.push( evaluateBoolean((Boolean)parameter1,(Boolean)parameter2));
        else if ( parameter1 instanceof Boolean && parameter2 instanceof Number ){
        	//System.out.println("num2 ");
        	target.push( evaluateBoolean((Boolean)parameter1,new Boolean(((Number)parameter2).doubleValue() > 0)));
        }
        else if ( parameter2 instanceof Boolean && parameter1 instanceof Number ){
        	//System.out.println("num1 ");
        	target.push( evaluateBoolean((Boolean)parameter2,new Boolean(((Number)parameter1).doubleValue() > 0)));
        }
        else if ( parameter1 instanceof Number && parameter2 instanceof Number )
          target.push(evaluateNumbers((Number)parameter1,(Number)parameter2));
        else if (parameter1 instanceof String && parameter2 instanceof String)
          target.push(evaluateStrings((String)parameter1,(String)parameter2));
        else if (parameter1 instanceof Character && parameter2 instanceof Character)
          target.push(evaluateCharacters((Character)parameter1,(Character)parameter2));
        else if ((parameter1 instanceof Character && parameter2 instanceof String ))
          target.push(evaluateStrings(""+(Character)parameter1,(String)parameter2));
        else if ((parameter2 instanceof Character && parameter1 instanceof String ))
          target.push(evaluateStrings(""+(Character)parameter2,(String)parameter1));
        else if(parameter1.getClass().isArray() && parameter2.getClass().isArray())
          target.push(evaluateArrays(parameter1,parameter2));
		else if ( parameter1 instanceof NonExistentVariable )
			target.push(new Boolean(parameter1.equals(parameter2)));
		else if ( parameter2 instanceof NonExistentVariable )
			target.push(new Boolean(parameter2.equals(parameter1)));
    }
    catch (Exception e) {
		//System.out.println("EA "+target.toString());
		EvaluationRuntimeErrors.evaluationError();
		target.push(Boolean.FALSE);
		return;
    }
    //System.out.println("EA OK");
	//System.out.flush();
  }
  
  private Object evaluateBoolean(Boolean boolean1, Boolean boolean2) {	
	return ( new Boolean(boolean1.equals(boolean2)));
  }
}