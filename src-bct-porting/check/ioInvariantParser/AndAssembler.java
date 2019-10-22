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

public class AndAssembler extends Assembler {

	
	public void workOn(Assembly a) {
		Target target = null;
		Boolean leftSide = null;
		Boolean rightSide = null;
		//System.out.println("AndAssembler");
		try{
			target = (Target)a.getTarget();
			
			//if left side of and was not true expression on right side can be not evaluated
			if ( AndStateRegistry.getInstance().getLastQueueSize() == target.queueSize() 
					&& ( ! AndStateRegistry.getInstance().isLeftResultTrue() ) ){
				leftSide = (Boolean) target.pop();
				rightSide = Boolean.FALSE;
			} else {
				rightSide = (Boolean)target.pop();  
				leftSide = (Boolean)target.pop();  
			}
			
		}catch(ArrayIndexOutOfBoundsException e) {
			//System.out.println("AndAssembler "+target.toString());
			EvaluationRuntimeErrors.emptyStack();
			return;
		}finally{
			AndStateRegistry.getInstance().reset();
		}
		
		if( leftSide.equals(Boolean.TRUE) && rightSide.equals(Boolean.TRUE) )
			target.push(new Boolean(true));
		else
			target.push(new Boolean(false));
	}
	


}
