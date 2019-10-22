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

import regressionTestManager.tcSpecifications.TcSpecification;
import regressionTestManager.tcSpecifications.TcSpecificationAnd;
import sjm.parse.Assembler;
import sjm.parse.Assembly;

public class AndAssembler extends Assembler {

	
	public void workOn(Assembly a) {
		Target target = null;
		TcSpecification leftSide = null;
		TcSpecification rightSide = null;
		
		try{
			target = (Target)a.getTarget();
			
			rightSide = (TcSpecification)target.pop();  
			leftSide = (TcSpecification)target.pop();  
			
		}catch(ArrayIndexOutOfBoundsException e) {
			//System.out.println("AndAssembler "+target.toString());
			EvaluationRuntimeErrors.emptyStack();
			return;
		}
		
		target.push(new TcSpecificationAnd(leftSide,rightSide));
	}
	


}
