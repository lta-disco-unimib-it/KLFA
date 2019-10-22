package regressionTestManager.ioInvariantParser;

import java.util.ArrayList;

import sjm.parse.Assembler;
import sjm.parse.Assembly;

public class ArrayCompletedAssembler extends Assembler {

  public void workOn(Assembly a) {
    Target target = (Target)a.getTarget();
    //ArrayList list = (ArrayList)target.pop();
    ArrayList list = null;
    try{
    	list = (ArrayList)target.pop();
    }catch(ArrayIndexOutOfBoundsException e) {
      	EvaluationRuntimeErrors.emptyStack();
      	return;
    }
    Object[] array = null;
    try{
    	array= list.toArray();
    	target.push(array);   
    }
    catch (Exception e) {
    	EvaluationRuntimeErrors.evaluationError();
		//target.push(null);
		return;
    }
  }
}
