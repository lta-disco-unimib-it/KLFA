package regressionTestManager.ioInvariantParser;

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
    	Double value = (Double)tokenIndex.value();
    	target.push(new Variable(target.getProgramPointName(),"parameter["+value.intValue()+"]"));
    }
    catch (Exception e) {
		EvaluationRuntimeErrors.evaluationError();
		//target.push(Boolean.FALSE);
		return;
	}
  }
}