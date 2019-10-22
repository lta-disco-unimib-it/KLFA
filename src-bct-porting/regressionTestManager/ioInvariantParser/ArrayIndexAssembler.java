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
import java.util.Map;

import sjm.parse.Assembler;
import sjm.parse.Assembly;
import sjm.parse.tokens.Token;

public class ArrayIndexAssembler extends Assembler {

  public void workOn(Assembly a) {
	  Target target = (Target)a.getTarget();
	  try {   
      
      //Token arrayIndexToken = (Token)a.pop();
      Token arrayIndexToken = null;
      try{
    	  arrayIndexToken = (Token)a.pop();
        }catch(ArrayIndexOutOfBoundsException e) {
        	EvaluationRuntimeErrors.emptyStack();
        	return;
        }
      int arrayIndex = (int)arrayIndexToken.nval();
      
      Object targetArray = null;
      try{
          targetArray = target.pop();
        }catch(ArrayIndexOutOfBoundsException e) {
        	EvaluationRuntimeErrors.emptyStack();
        	return;
        }
      //if (target.isEmpty()) return;
      
      
      if(targetArray instanceof Map) {    
        Map map = (Map)targetArray;
        targetArray = map.values().toArray();
      }    
     
      Object value = Array.get(targetArray,arrayIndex);
      if(value instanceof Boolean)
        if(((Boolean)value).booleanValue())
          value = new Double(1);
        else
          value = new Double(0);      
      target.push(value);
    }
 /*   catch(Exception e) {
      e.printStackTrace();
    }*/
  catch (IllegalArgumentException e) {
 //System.out.println("QUI");
 //e.printStackTrace();
  	EvaluationRuntimeErrors.emptyStack();
		//target.push(null);
		return;
	}
  catch (Exception e){
  	 	  EvaluationRuntimeErrors.emptyStack();
	  return;
  }
  }
}
