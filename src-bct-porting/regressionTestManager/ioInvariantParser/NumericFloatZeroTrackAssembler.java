package regressionTestManager.ioInvariantParser;

import sjm.parse.Assembler;
import sjm.parse.Assembly;

public class NumericFloatZeroTrackAssembler extends Assembler {

  public void workOn(Assembly a) {
    Target target = (Target)a.getTarget();
    /*Number parameter2 = (Number)target.pop();  
    Number parameter1 = (Number)target.pop();*/
    Number parameter2 = null;  
    Number parameter1 = null;
    try{
    	parameter2 = (Number)target.pop();  
        parameter1 = (Number)target.pop();
    }catch(ArrayIndexOutOfBoundsException e) {
      	EvaluationRuntimeErrors.emptyStack();
      	return;
    }
    try{
    	EvaluationRuntimeErrors.log("NumericFloatZeroTrack Not implemented");
    }catch (Exception e) {
		EvaluationRuntimeErrors.evaluationError();
		//target.push(Boolean.FALSE);
		return;
	}
  }
}
