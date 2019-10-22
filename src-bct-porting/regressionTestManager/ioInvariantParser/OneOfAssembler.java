package regressionTestManager.ioInvariantParser;

import java.lang.reflect.Array;

import conf.EnvironmentalSetter;

import regressionTestManager.tcSpecifications.TcSpecification;
import regressionTestManager.tcSpecifications.TcSpecificationBlock;
import regressionTestManager.tcSpecifications.TcSpecificationEquals;
import sjm.parse.Assembler;
import sjm.parse.Assembly;

public class OneOfAssembler extends Assembler {
  
  private TcSpecificationBlock oneOf ( Variable x , Object[] y) {
	  if ( x.isArray() )
	  	  return oneOfArray(x,y);
	  return oneOfScalar(x,y);
  }
  
  

  private TcSpecificationBlock oneOfArray(Variable x, Object[] y) {
	  TcSpecificationBlock block = new TcSpecificationBlock();
	  for(int i = 0; i < y.length; ++i ){
		  block.addElement(new TcSpecificationArrayContains(x,y[i]));
	  }
	  return block;
	
  }



private TcSpecificationBlock oneOfScalar ( Variable x , Object[] y) {
	  TcSpecificationBlock block = new TcSpecificationBlock();
	  for(int i = 0; i < y.length; ++i ){
		  block.addElement(new TcSpecificationEquals(x,y[i]));
	  }
	  return block;
  }
  
  
  public void workOn(Assembly a) {
    Target target = (Target)a.getTarget();
    
  
    try{
    	Object y = (Object)target.pop();
    	Variable x = (Variable)target.pop();
    	
    	
    	if( y.getClass().isArray())
    		 target.push(oneOf(x,(Object[])y));
    	else 
    		EvaluationRuntimeErrors.log("OneOfAssembler: second element must be an array!");
    	    
    }catch(ArrayIndexOutOfBoundsException e) {
      	EvaluationRuntimeErrors.emptyStack();
      	return;
    } catch (Exception e) {
		EvaluationRuntimeErrors.evaluationError();
		return;
	}
  }



}
