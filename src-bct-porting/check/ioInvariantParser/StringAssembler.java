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
import sjm.parse.tokens.Token;

public class StringAssembler extends Assembler {

  public void workOn(Assembly a) {
    Target target = (Target)a.getTarget();
    //Token tokenNumber = (Token)a.pop();
    Token tokenNumber = null;
    try{
    	tokenNumber = (Token)a.pop();
    }catch(ArrayIndexOutOfBoundsException e) {
      	EvaluationRuntimeErrors.emptyStack();
      	return;
    }
    try{
    	String s = tokenNumber.sval();  
    	s = s.substring(1,s.length()-1);       
    	
    	//un-escape operations
    	s = s.replace("\\r","\r")
    	.replace("\\\"","\"")
    	.replace("\\\\","\\")
    	.replace("\\n","\n");
    	
    	target.push(s);
    }
    catch (Exception e) {
		EvaluationRuntimeErrors.evaluationError();
		//target.push(null);
		return;
	}
  }
}
