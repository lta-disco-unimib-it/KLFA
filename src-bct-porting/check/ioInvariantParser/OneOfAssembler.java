package check.ioInvariantParser;

import java.lang.reflect.Array;

import sjm.parse.Assembler;
import sjm.parse.Assembly;

public class OneOfAssembler extends Assembler {
  
  private Boolean oneOfArray( Object x , Object y) {
    int arrayMax = Array.getLength(x);
    for(int i=0;i<arrayMax;i++) {
      Object o = Array.get(x,i);
      if(!oneOf(o,y).booleanValue())
        return Boolean.FALSE;
    }
    return Boolean.TRUE;
  } 
  private Boolean oneOf(Object x,Object y) {
	  if((x instanceof Boolean)) {
		  int bVal = 0;
		  if ( (Boolean) x )
			  bVal = 1;
		  return oneOfNumber(bVal,y);
	  }
	  if((x instanceof Number)) {
    	return oneOfNumber((Number)x,y);
    }
    else if (x instanceof String) {
    	return oneOfString((String)x,y);
    }
	 return new Boolean(false);
  }
  
  private Boolean oneOfString( String x , Object y) {
    int valueListMax = Array.getLength(y);
    boolean found = false;
    for(int i=0;i<valueListMax;i++) {
      String o = (String)Array.get(y,i);
      if(o.equals(x)) {
        found = true;
        break;
      }
    }
    return new Boolean(found);
  }    
  private Boolean oneOfNumber( Number x , Object y) {
    int valueListMax = Array.getLength(y);
    //System.out.println("One of number : "+x);
    boolean found = false;
    for(int i=0;i<valueListMax;i++) {    
      Number n = (Number)Array.get(y,i);
      if(n.floatValue() == x.floatValue()) {
        found = true;
        break;
      }
    }
    return new Boolean(found);
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
      target.push(oneOfArray(x,y));
    else if(y.getClass().isArray())
    	//System.out.println("One of:"+x+" "+y);
    	//System.out.println( target );
      target.push(oneOf(x,y));
    }
    catch (Exception e) {
		EvaluationRuntimeErrors.evaluationError();
		//target.push(Boolean.FALSE);
		return;
	}
  }
}
