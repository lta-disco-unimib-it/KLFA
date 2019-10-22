package check.ioInvariantParser;

import sjm.parse.Assembler;
import sjm.parse.Assembly;

public class StoreValueAssembler extends Assembler {

  public void workOn(Assembly a) {    
    Target target = (Target)a.getTarget();
    //Object value = target.pop();
    Object value = null;
    try{
    	value = target.pop();
    }catch(ArrayIndexOutOfBoundsException e) {
      	EvaluationRuntimeErrors.emptyStack();
      	return;
    }
    try{
    	String key = a.consumed("");
    	// trim the key
    	key = key.substring(6,key.length()-1);
    	//System.out.println("#StoreValueAssembler "+key+" : "+value);
    	target.put(key,value);
    	target.push(Boolean.TRUE);
    }
    catch (Exception e) {
		EvaluationRuntimeErrors.evaluationError();
		//target.push(Boolean.FALSE);
		return;
	}
  }
}