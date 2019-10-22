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

import java.util.Iterator;

import sjm.parse.Assembler;
import sjm.parse.Assembly;

public class OrigAssembler extends Assembler {
  // non funzionante
  public void workOn(Assembly a) {
    Target target = (Target)a.getTarget();
    String key = a.consumed("");
    //target.pop();
    try{
    	target.pop();
    }catch(ArrayIndexOutOfBoundsException e) {
      	EvaluationRuntimeErrors.emptyStack();
      	return;
    }
    try{
    	// trim the key
    	//System.out.println("##OrigAssembler.workOn expr : "+key);
    	key = key.substring(key.lastIndexOf("orig("),key.length());
    	key = key.substring(5,key.length()-1);

    	if ( target.contains(key) ){
    		Object value = target.getOrig(key);
    	//System.out.println("##OrigAssembler.workOn value for key "+key+": "+value+"\n\n\n");
    		target.push(value);
    	} else {
    		target.block();
    		target.push( new NonExistentVariable() );
    	}
    	
  }catch (Exception e) {
	  	//System.out.println("##OrigAssembler exc:");
		EvaluationRuntimeErrors.evaluationError();
		//target.push(Boolean.FALSE);
		return;
	}
  }
}
