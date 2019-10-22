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
import sjm.parse.tokens.Token;

public class MethodLiteralAssembler extends Assembler  {
	public void workOn(Assembly a) {
		Target target = (Target)a.getTarget();
		//Token methodNameToken = (Token)a.pop();
		Token methodNameToken = null;
		try{
			methodNameToken = (Token)a.pop();
		}catch(ArrayIndexOutOfBoundsException e) {
			EvaluationRuntimeErrors.emptyStack();
			return;
		}



		String methodName = methodNameToken.sval();

		//System.out.println("Method literal:"+methodName);
		//Object targetObject = target.pop();
		Variable targetObject = null;
		try{
			targetObject = (Variable) target.pop();
		}catch(ArrayIndexOutOfBoundsException e) {
			EvaluationRuntimeErrors.emptyStack();
			return;
		}

		target.push(new Variable( target.getProgramPointName(), targetObject.getName()+"."+methodName+"()"));
	}

}