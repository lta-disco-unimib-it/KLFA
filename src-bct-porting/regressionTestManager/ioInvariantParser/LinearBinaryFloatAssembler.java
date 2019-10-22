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

import sjm.parse.Assembler;
import sjm.parse.Assembly;

public class LinearBinaryFloatAssembler extends Assembler {

  public void workOn(Assembly ass) {
    Target target = (Target)ass.getTarget();
    
    Number result = null;
    Number c = null;
    Number y = null;
    Number b = null;
    Number x = null;
    Number a = null;
    try{
    	c = (Number)target.pop();
        y = (Number)target.pop();
        b = (Number)target.pop();
        x = (Number)target.pop();
        a = (Number)target.pop();
    }catch(ArrayIndexOutOfBoundsException e) {
      	EvaluationRuntimeErrors.emptyStack();
      	return;
    }
    try{  
    	EvaluationRuntimeErrors.log("Linera Binary Float Not Implemented");
    	//target.push(new Boolean(a.floatValue()*x.floatValue()+b.floatValue()*y.floatValue()+c.floatValue()==result.floatValue()));
    }catch (Exception e) {
		EvaluationRuntimeErrors.evaluationError();
		//target.push(null);
		return;
	}
  }
}