package check.ioInvariantParser;

import java.util.ArrayList;

import sjm.parse.Assembler;
import sjm.parse.Assembly;

public class AddArrayElementAssembler extends Assembler {

  public void workOn(Assembly a) {
    Target target = (Target)a.getTarget();
    Object element = null;
    
    try{
        element = target.pop();
    }catch(ArrayIndexOutOfBoundsException e) {
      	EvaluationRuntimeErrors.emptyStack();
      	return;
    }
    //ArrayList list = (ArrayList)target.pop();
    ArrayList list = null;
    try{
    	list = (ArrayList)target.pop();
    }catch(ArrayIndexOutOfBoundsException e) {
      	EvaluationRuntimeErrors.emptyStack();
      	return;
    }
    try {
    list.add(element);
    target.push(list);
    }catch (Exception e) {
		EvaluationRuntimeErrors.evaluationError();
		//target.push(null);
		return;
	}
  }
}