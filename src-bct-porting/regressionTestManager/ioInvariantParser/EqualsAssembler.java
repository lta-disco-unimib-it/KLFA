package regressionTestManager.ioInvariantParser;

import java.lang.reflect.Array;

import regressionTestManager.tcSpecifications.TcSpecification;
import regressionTestManager.tcSpecifications.TcSpecificationEquals;
import regressionTestManager.tcSpecifications.TcSpecificationEqualsNull;
import sjm.parse.Assembler;
import sjm.parse.Assembly;

public class EqualsAssembler extends Assembler {
  static final double DFBias = 0.001;
  
     
  public void workOn(Assembly a) {
    Target target = (Target)a.getTarget();
   	
    Object parameter2 = null;
    try{
    	parameter2 = (Object)target.pop();
    }catch(ArrayIndexOutOfBoundsException e) {
      	EvaluationRuntimeErrors.emptyStack();
      	return;
    }
    Object parameter1 = null;
    try{
    	parameter1 = (Object)target.pop();
    }catch(ArrayIndexOutOfBoundsException e) {
      	EvaluationRuntimeErrors.emptyStack();
      	return;
    }
    try{
    	
    	
    	TcSpecification spec;
    	
    	if ( parameter2 == null )
    		spec = new TcSpecificationEqualsNull(   (Variable)parameter1 );
    	else
    		spec = new TcSpecificationEquals(   (Variable)parameter1, parameter2);
    	
    	target.push( spec );
    	
    	
    }
    catch (Exception e) {
		//System.out.println("EA "+target.toString());
		EvaluationRuntimeErrors.evaluationError();
		target.push(Boolean.FALSE);
		return;
    }
    
  }
  
}