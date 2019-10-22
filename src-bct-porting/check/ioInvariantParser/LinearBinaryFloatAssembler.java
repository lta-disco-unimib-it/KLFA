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
package check.ioInvariantParser;

import sjm.parse.Assembler;
import sjm.parse.Assembly;

public class LinearBinaryFloatAssembler extends Assembler {

  public void workOn(Assembly ass) {
    Target target = (Target)ass.getTarget();
   
    try{
    	Number result = (Number)target.pop();
    	Number c = (Number)target.pop();
    	Number y = (Number)target.pop();
    	Number b = (Number)target.pop();
    	Number x = (Number)target.pop();
    	Number a = (Number)target.pop();
    	
    	if ( c == null || y == null || b == null ||	x == null || a == null ){
    		EvaluationRuntimeErrors.evaluationError();
    		return;
    	}
    	
    	target.push( Boolean.valueOf(a.floatValue()*x.floatValue()+b.floatValue()*y.floatValue()+c.floatValue()==result.floatValue()));
    	
    }catch(ArrayIndexOutOfBoundsException e) {
    	EvaluationRuntimeErrors.emptyStack();
    	return;
    }catch (Exception e) {
    	EvaluationRuntimeErrors.evaluationError();
    	return;
    }

  }
}