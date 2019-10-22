/*******************************************************************************
 *    Copyright 2019 Fabrizio Pastore, Leonardo Mariani
 *   
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 *******************************************************************************/
package regressionTestManager.ioInvariantParser;

import java.lang.reflect.Array;

import probes.TCObserverProbeInstrumented;

import regressionTestManager.tcSpecifications.TcSpecificationBlock;
import regressionTestManager.tcSpecifications.TcSpecificationEquals;
import regressionTestManager.tcSpecifications.TcSpecificationGreaterThan;
import regressionTestManager.tcSpecifications.TcSpecificationPlusOne;
import sjm.parse.Assembler;
import sjm.parse.Assembly;

public class GreaterOrEqualThanAssembler extends Assembler  {

  public void workOn(Assembly a) {
    Target target = (Target)a.getTarget();
    /*
    Object parameter2 = (Object)target.pop(); 
    */
    Object parameter2 = null;
    try{
    	parameter2 = (Object)target.pop();
    }catch(ArrayIndexOutOfBoundsException e) {
      	EvaluationRuntimeErrors.emptyStack();
      	return;
    }
    /*
    if(target.isEmpty())
        return;    
    Object parameter1 = (Object)target.pop();
    */
    Object parameter1 = null;
    try{
    	parameter1 = (Object)target.pop();
    }catch(ArrayIndexOutOfBoundsException e) {
      	EvaluationRuntimeErrors.emptyStack();
      	return;
    }
    TcSpecificationBlock block = new TcSpecificationBlock();
    try{
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
    		//add p1 = p2
    		block.addElement(
    				new TcSpecificationEquals( (Variable)parameter1,((Number)parameter2) )
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
    		block.addElement(
    				new TcSpecificationEquals( (Variable)parameter1, (Variable)parameter2 )
    				);
    	}
    	target.push( block );
    }catch (Exception e) {
		EvaluationRuntimeErrors.evaluationError();
		//target.push(Boolean.FALSE);
		return;
	}
  }
}