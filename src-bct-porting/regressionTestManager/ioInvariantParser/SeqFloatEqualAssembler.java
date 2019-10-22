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

public class SeqFloatEqualAssembler extends Assembler {

  private Boolean evaluate(Object array,Number scalar) {
    int MAX = Array.getLength(array);
    for(int i=0;i<MAX;i++) {
      Number element = (Number)Array.get(array,i);

      if(element.doubleValue() != scalar.doubleValue())
        return Boolean.FALSE;
    }
    return Boolean.TRUE;
  }
  public void workOn(Assembly a) {
    Target target = (Target)a.getTarget();
    
    //Object scalar = target.pop();
    Object scalar = null;
    try{
    	scalar = target.pop();
    }catch(ArrayIndexOutOfBoundsException e) {
      	EvaluationRuntimeErrors.emptyStack();
      	return;
    }
    
    //Object array = target.pop();
    Object array = null;
    try{
    	array = target.pop();
    }catch(ArrayIndexOutOfBoundsException e) {
      	EvaluationRuntimeErrors.emptyStack();
      	return;
    }
    try{
    	EvaluationRuntimeErrors.log("SeqFloatEqual Not implemented");
//    	if(scalar instanceof Number)
//    		target.push(evaluate(array,(Number)scalar));
    }
    catch (Exception e) {
		EvaluationRuntimeErrors.evaluationError();
		//target.push(Boolean.FALSE);
		return;
	}
  }
}
