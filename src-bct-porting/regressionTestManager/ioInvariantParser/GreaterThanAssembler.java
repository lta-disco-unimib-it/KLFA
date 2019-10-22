package regressionTestManager.ioInvariantParser;

import java.lang.reflect.Array;

import regressionTestManager.tcSpecifications.TcSpecificationBlock;
import regressionTestManager.tcSpecifications.TcSpecificationEquals;
import regressionTestManager.tcSpecifications.TcSpecificationGreaterThan;
import regressionTestManager.tcSpecifications.TcSpecificationPlusOne;
import sjm.parse.Assembler;
import sjm.parse.Assembly;

public class GreaterThanAssembler extends Assembler {

    
  public void workOn(Assembly a) {
	Target target = null;
	Object parameter2 = null;
	
	try{
	  target = (Target)a.getTarget();
      parameter2 = (Object)target.pop();  
    }catch(ArrayIndexOutOfBoundsException e) {
    	EvaluationRuntimeErrors.emptyStack();
    	return;
    }

    //Object parameter1 = (Object)target.pop();
    Object parameter1 = null;
    try{
    	parameter1 = (Object)target.pop(); 
      }catch(ArrayIndexOutOfBoundsException e) {
      	EvaluationRuntimeErrors.emptyStack();
      	return;
      }
    try{
    	TcSpecificationBlock block = new TcSpecificationBlock();
    	if ( parameter2 instanceof Number ){
    		//FIXME: how to handle double?
    		//add p1 > p2 + 1 
    		block.addElement(
    				new TcSpecificationGreaterThan( (Variable)parameter1,((Number)parameter2).doubleValue()+1 )
    				);
    		//add p1 = p2 + 1
    		block.addElement(
    				new TcSpecificationEquals( (Variable)parameter1,((Number)parameter2).doubleValue()+1 )
    				);
    	
    	} else {
    		block.addElement(
    				new TcSpecificationGreaterThan( (Variable)parameter1, 
    						new TcSpecificationPlusOne((Variable)parameter2 ) )
    				);
    		block.addElement(
    				new TcSpecificationEquals( (Variable)parameter1, 
    						new TcSpecificationPlusOne((Variable)parameter2 ) )
    				);
    	
    	}
    	target.push(block);
    }catch (Exception e) {
		EvaluationRuntimeErrors.evaluationError();
		//target.push(Boolean.FALSE);
		return;
	}
  }
}
