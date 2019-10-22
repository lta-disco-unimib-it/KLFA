package check.ioInvariantParser;

import sjm.parse.Assembler;
import sjm.parse.Assembly;
import sjm.parse.tokens.Token;

public class NumberAssembler extends Assembler {

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
//System.out.println(a);
//System.out.println(tokenNumber.nval());    
    Double number = null;
    try{
    	number = new Double(tokenNumber.nval());
    	//System.out.println(number);      
    	target.push(number);
    }
    catch (Exception e) {
		EvaluationRuntimeErrors.evaluationError();
		//target.push(null);
		return;
	}
  }
}
