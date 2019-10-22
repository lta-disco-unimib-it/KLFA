package check.ioInvariantParser;

import sjm.parse.Assembler;
import sjm.parse.Assembly;
import sjm.parse.tokens.Token;

public class ParameterAssembler extends Assembler {

  public void workOn(Assembly a) {   
    Target target = (Target)a.getTarget();
    //Token tokenIndex = (Token)a.pop();
    Token tokenIndex = null;
    try{
    	tokenIndex = (Token)a.pop();
    }catch(ArrayIndexOutOfBoundsException e) {
      	EvaluationRuntimeErrors.emptyStack();
      	return;
    }
    try{
    	int index = (int)tokenIndex.nval();
    	Object[] parameters = target.getParameters();  
    	Object value = parameters[index];
    	
    	/*
    	 Boolean cannot be chaged
    	 
    	if(value instanceof Boolean)
    		if(((Boolean)value).booleanValue())
    			value = new Double(1);
    		else
    			value = new Double(0);
    	*/
    	target.push(value);
    }
    catch (Exception e) {
    	e.printStackTrace();
		EvaluationRuntimeErrors.evaluationError();
		//target.push(Boolean.FALSE);
		return;
	}
  }
}