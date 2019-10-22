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

import sjm.parse.Assembler;
import sjm.parse.Assembly;

public class PairwiseLinearBinaryFloatAssembler extends Assembler {

  private static Boolean evaluatePairwiseLinearBinaryFloat(Object y,Number a,Object x,Number b) {
    int MAX = Array.getLength(y);
    for(int i=0;i<MAX;i++) {
      Number yn = (Number)Array.get(y,i);
      Number xn = (Number)Array.get(x,i);
      if(yn.floatValue()!=a.floatValue()*xn.floatValue()+b.floatValue())
        return Boolean.FALSE;
    }
    return Boolean.TRUE;
  } 
  public void workOn(Assembly ass) {
    Target target = (Target)ass.getTarget();
    //Number b = (Number)target.pop();  
    Number b = null;
    try{
    	b = (Number)target.pop();
    }catch(ArrayIndexOutOfBoundsException e) {
      	EvaluationRuntimeErrors.emptyStack();
      	return;
    }
    
    //Object x = (Object)target.pop();
    Object x = null;
    try{
    	x = (Object)target.pop();
    }catch(ArrayIndexOutOfBoundsException e) {
      	EvaluationRuntimeErrors.emptyStack();
      	return;
    }
    Number a = (Number)target.pop();
    //Object y = (Object)target.pop();
    Object y = null;
    try{
    	y = (Object)target.pop();
    }catch(ArrayIndexOutOfBoundsException e) {
      	EvaluationRuntimeErrors.emptyStack();
      	return;
    }
    try{
    	EvaluationRuntimeErrors.log("PairWise Not implemented");
    	//target.push(evaluatePairwiseLinearBinaryFloat(y,a,x,b));
    }catch (Exception e) {
		EvaluationRuntimeErrors.evaluationError();
		//target.push(Boolean.FALSE);
		return;
	}
  }
}