package check.ioInvariantParser;

import java.util.ArrayList;

import sjm.parse.Assembler;
import sjm.parse.Assembly;

public class CreateArrayAssembler extends Assembler {

  public void workOn(Assembly a) {
    Target target = null;
    try{
    target =(Target)a.getTarget();
    target.push(new ArrayList());  
    }
    catch (Exception e){
    	EvaluationRuntimeErrors.evaluationError();
		//target.push(null);
		return;
    }
  }
}
