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
package check;

import java.util.ArrayList;

import check.ioInvariantParser.EvaluationRuntimeErrors;

public class Stack {

  private ArrayList stack = new ArrayList();

  public boolean isEmpty() {
    return stack.isEmpty();    
  }
  
  public int getStackSize() {
	  return stack.size();
  }
  
  public void push(Object o) {
    stack.add(0,o);
  }
  public Object pop() {
	  try{
	  	Object o = stack.remove(0);
	  	stack.trimToSize();
	  	return o;
		  
	  } catch (IndexOutOfBoundsException e) {
		  EvaluationRuntimeErrors.emptyStack(); 
	  }
	  return null;
}
 
  public String toString() {
    return stack.toString();
  }
}
