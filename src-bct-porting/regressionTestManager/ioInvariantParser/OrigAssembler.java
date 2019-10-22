package regressionTestManager.ioInvariantParser;

import java.util.Iterator;

import sjm.parse.Assembler;
import sjm.parse.Assembly;

public class OrigAssembler extends Assembler {
  // non funzionante
  public void workOn(Assembly a) {
    Target target = (Target)a.getTarget();
    
    
    try{
    	//We have to refer to an enter variable
    	Variable var = (Variable) target.pop();
    	var.setProgramPoint ( var.getProgramPoint().replace(":::EXIT1", ":::ENTER") );
    	target.push(var);
    	
    }catch(ArrayIndexOutOfBoundsException e) {
      	EvaluationRuntimeErrors.emptyStack();
      	return;
    }
    
  }
}
