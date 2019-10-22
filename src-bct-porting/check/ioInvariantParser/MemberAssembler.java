package check.ioInvariantParser;

import java.lang.reflect.Array;

import sjm.parse.Assembler;
import sjm.parse.Assembly;

public class MemberAssembler extends Assembler {

  private Boolean member(Number scalar, Object array) {
    int MAX = Array.getLength(array);
    for ( int i = 0; i < MAX; i++) {
      Number element = (Number)Array.get(array, i);
      if ( scalar.doubleValue() == element.doubleValue() )
        return Boolean.TRUE;
    }
    return Boolean.FALSE;
  }

  private Boolean member(String scalar, Object array) {
    int MAX = Array.getLength(array);
    for ( int i = 0; i < MAX; i++) {
      String element = (String)Array.get(array, i);
      if ( scalar.equals(element) )
        return Boolean.TRUE;
    }
    return Boolean.FALSE;
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
    //Object scalar = (Object)target.pop();
    Object scalar = null;
    try{
    	scalar = (Object)target.pop();
    }catch(ArrayIndexOutOfBoundsException e) {
      	EvaluationRuntimeErrors.emptyStack();
      	return;
    }
    try{
    	if ( scalar instanceof Number && array.getClass().isArray())
    		target.push(member((Number)scalar, array));
    	else
    		if ( scalar instanceof String && array.getClass().isArray())
    			target.push(member((String)scalar, array));
    }catch (Exception e) {
		EvaluationRuntimeErrors.evaluationError();
		//target.push(Boolean.FALSE);
		return;
	}
  }
}