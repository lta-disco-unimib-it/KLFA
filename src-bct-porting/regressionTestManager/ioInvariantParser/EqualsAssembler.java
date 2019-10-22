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