package check.ioInvariantParser;

import sjm.parse.Assembler;
import sjm.parse.Assembly;

public class RemoveValueAssembler extends Assembler {

  public void workOn(Assembly a) {    
    Target target = (Target)a.getTarget();
    //target.pop();
    try{
    	target.pop();
    }catch(ArrayIndexOutOfBoundsException e) {
      	EvaluationRuntimeErrors.emptyStack();
      	return;
    }
    try{
    	String key = a.consumed("");
    	// trim the key
    	key = key.substring(7,key.length()-1);
    	//boolean result = target.contains(key);
    	target.remove(key);
    	//FIXME: push result
    	target.push(true);
    	
    }
    catch (Exception e) {
		EvaluationRuntimeErrors.evaluationError();
		//target.push(Boolean.FALSE);
		return;
	}
  }
}
