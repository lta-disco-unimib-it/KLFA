package check.ioInvariantParser;

import java.util.Iterator;

import sjm.parse.Assembler;
import sjm.parse.Assembly;

public class OrigAssembler extends Assembler {
  // non funzionante
  public void workOn(Assembly a) {
    Target target = (Target)a.getTarget();
    String key = a.consumed("");
    //target.pop();
    try{
    	target.pop();
    }catch(ArrayIndexOutOfBoundsException e) {
      	EvaluationRuntimeErrors.emptyStack();
      	return;
    }
    try{
    	// trim the key
    	//System.out.println("##OrigAssembler.workOn expr : "+key);
    	key = key.substring(key.lastIndexOf("orig("),key.length());
    	key = key.substring(5,key.length()-1);

    	if ( target.contains(key) ){
    		Object value = target.getOrig(key);
    	//System.out.println("##OrigAssembler.workOn value for key "+key+": "+value+"\n\n\n");
    		target.push(value);
    	} else {
    		target.block();
    		target.push( new NonExistentVariable() );
    	}
    	
  }catch (Exception e) {
	  	//System.out.println("##OrigAssembler exc:");
		EvaluationRuntimeErrors.evaluationError();
		//target.push(Boolean.FALSE);
		return;
	}
  }
}
