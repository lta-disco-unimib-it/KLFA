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

public class MinimumDoubleExpressionAssembler extends Assembler{

	public void workOn(Assembly a) {
		Target target = (Target)a.getTarget();
		try{
			Double z = (Double)target.pop();
			Double y = (Double)target.pop();
			Double x = (Double)target.pop();

			if ( z != null && y != null && x != null )
				target.push( Boolean.valueOf(Math.min(y.longValue() , z.longValue()) == x.longValue()));
			else {
				EvaluationRuntimeErrors.evaluationError();
				return;
			}
		}	catch(ArrayIndexOutOfBoundsException e) {
			EvaluationRuntimeErrors.emptyStack();
			return;
		}	catch (Exception e) {
			EvaluationRuntimeErrors.evaluationError();

			return;
		}
	}
}
