package check.ioInvariantParser;

import java.lang.reflect.Array;

import sjm.parse.Assembler;
import sjm.parse.Assembly;

public class SizeAssembler extends Assembler {

  public void workOn(Assembly a) {
    Target target = (Target)a.getTarget();
    //Object array = target.pop();
    Object array = null;
    try{
    	array = target.pop();
    }catch(ArrayIndexOutOfBoundsException e) {
      	EvaluationRuntimeErrors.emptyStack();
      	return;
    }
    try{
    	target.push(new Double(Array.getLength(array)));
    }
    catch (Exception e) {
		EvaluationRuntimeErrors.evaluationError();
		//target.push(null);
		return;
	}
  }
}
