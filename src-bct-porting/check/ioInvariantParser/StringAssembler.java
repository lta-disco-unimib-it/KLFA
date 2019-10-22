package check.ioInvariantParser;

import sjm.parse.Assembler;
import sjm.parse.Assembly;
import sjm.parse.tokens.Token;

public class StringAssembler extends Assembler {

  public void workOn(Assembly a) {
    Target target = (Target)a.getTarget();
    //Token tokenNumber = (Token)a.pop();
    Token tokenNumber = null;
    try{
    	tokenNumber = (Token)a.pop();
    }catch(ArrayIndexOutOfBoundsException e) {
      	EvaluationRuntimeErrors.emptyStack();
      	return;
    }
    try{
    	String s = tokenNumber.sval();  
    	s = s.substring(1,s.length()-1);       
    	
    	//un-escape operations
    	s = s.replace("\\r","\r")
    	.replace("\\\"","\"")
    	.replace("\\\\","\\")
    	.replace("\\n","\n");
    	
    	target.push(s);
    }
    catch (Exception e) {
		EvaluationRuntimeErrors.evaluationError();
		//target.push(null);
		return;
	}
  }
}
