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

import regressionTestManager.tcSpecifications.TcSpecificationEquals;
import sjm.parse.Assembler;
import sjm.parse.Assembly;

public class MemberAssembler extends Assembler {

  private Boolean member(Number scalar, Object array) {
    int MAX = Array.getLength(array);
    for ( int i = 0; i < MAX; i++) {
      Number element = (Number)Array.get(array, i);
      if ( scalar.doubleValue() == element.doubleValue() )
        return Boolean.TRUE;
    }
    return Boolean.FALSE;
  }

  private Boolean member(String scalar, Object array) {
    int MAX = Array.getLength(array);
    for ( int i = 0; i < MAX; i++) {
      String element = (String)Array.get(array, i);
      if ( scalar.equals(element) )
        return Boolean.TRUE;
    }
    return Boolean.FALSE;
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
    //Object scalar = (Object)target.pop();
    Object scalar = null;
    try{
    	scalar = (Object)target.pop();
    }catch(ArrayIndexOutOfBoundsException e) {
      	EvaluationRuntimeErrors.emptyStack();
      	return;
    }
    try{
    	if ( array.getClass().isArray() && array.getClass().getComponentType() == Double.class ){
    		for ( int i = 0; i < ((Double[])array).length; ++i ){
    			target.push(new TcSpecificationEquals(  (Variable) scalar, ((Double[])array)[i] ) );
    		}
    	}
    	else if ( array.getClass().isArray() && array.getClass().getComponentType() == String.class )
    		for ( int i = 0; i < ((String[])array).length; ++i ){
    			target.push(new TcSpecificationEquals(  (Variable) scalar, ((String[])array)[i] ) );
    		}
    	else
    		EvaluationRuntimeErrors.evaluationError();
    }catch (Exception e) {
		EvaluationRuntimeErrors.evaluationError();
		//target.push(Boolean.FALSE);
		return;
	}
  }
}