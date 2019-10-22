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

public class MaximumDoubleExpressionAssembler extends Assembler{

  public void workOn(Assembly a) {
    Target target = (Target)a.getTarget();
    /*Double z = (Double)target.pop();
    Double y = (Double)target.pop();
    Double x = (Double)target.pop();*/
    Double z = null;
    Double y = null;
    Double x = null;
    try{
    	z = (Double)target.pop();
        y = (Double)target.pop();
        x = (Double)target.pop();
    }catch(ArrayIndexOutOfBoundsException e) {
      	EvaluationRuntimeErrors.emptyStack();
      	return;
    }
    try{
    	target.push( Boolean.valueOf(Math.max(y.longValue() , z.longValue()) == x.longValue()));
    }
    catch (Exception e) {
		EvaluationRuntimeErrors.evaluationError();
		//target.push(null);
		return;
	}
  }
}
