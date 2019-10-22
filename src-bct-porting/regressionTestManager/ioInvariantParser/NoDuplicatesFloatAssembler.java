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

public class NoDuplicatesFloatAssembler extends Assembler{

  private Boolean arrayContainsNoDuplicates(Object array) {
    int MAX = Array.getLength(array);
    for(int i=0;i<MAX-1;i++) {
      Number n = (Number)Array.get(array,i);
      for(int j=i+1;j<MAX;j++) {
        Number element = (Number)Array.get(array,j);
        if(element.floatValue()==n.floatValue())
          return Boolean.FALSE;
      }
    }
    return Boolean.TRUE;
  }    
  public void workOn(Assembly a) {
    Target target = (Target)a.getTarget();
    //Object array = (Object)target.pop();
    Object array = null;
    try{
    	array = (Object)target.pop();
    }catch(ArrayIndexOutOfBoundsException e) {
      	EvaluationRuntimeErrors.emptyStack();
      	return;
    }
    try{
    	EvaluationRuntimeErrors.log("No duplicates expression not implemented");
    	//target.push(arrayContainsNoDuplicates(array));
    }
    catch (Exception e) {
    	EvaluationRuntimeErrors.evaluationError();
    	//target.push(Boolean.FALSE);
    	return;
    }	
  }
}
