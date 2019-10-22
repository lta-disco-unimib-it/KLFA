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

public class SubSetFloatAssembler extends Assembler {
  
  private Boolean isSubSet( Object x , Object y) {
    int subMax = Array.getLength(x);
    int MAX = Array.getLength(y) - subMax +1;
    for(int i=0;i<MAX;i++) {
      Number n1 = (Number)Array.get(x,0);
      Number n2 = (Number)Array.get(y,i);
      if(n1.floatValue()==n2.floatValue()) {
        boolean found = true;
        for(int j = 1 ; j<subMax;j++) {
          Number nn1 = (Number)Array.get(x,j);
          Number nn2 = (Number)Array.get(y,i+j);
          if(nn1.floatValue()!=nn2.floatValue()) {
            found = false;
            break;
          }
        }
        if(found)
          return Boolean.TRUE;
      }
    }
    return Boolean.FALSE;
  }    
  public void workOn(Assembly a) {
    Target target = (Target)a.getTarget();
    //Object y = (Object)target.pop();  
    Object y = null;
    try{
    	y = (Object)target.pop(); 
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
    try{
    	EvaluationRuntimeErrors.log(this.getClass()+" Not implemented");
//    	if(x.getClass().isArray() && y.getClass().isArray())
//    		target.push(isSubSet(x,y));
    }
    catch (Exception e) {
		EvaluationRuntimeErrors.evaluationError();
		//target.push(Boolean.FALSE);
		return;
	}
  }
}
