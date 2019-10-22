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

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import conf.EnvironmentalSetter;

import flattener.utilities.FieldFilter;
import flattener.utilities.FieldFilterVoid;
import flattener.utilities.FieldsRetriever;

import sjm.parse.Assembler;
import sjm.parse.Assembly;
import sjm.parse.tokens.Token;

public class FieldLiteralAssembler extends Assembler  {
  private boolean fieldInherited;
  private static FlattenerCoordinator flattenerCoordinator =  FlattenerCoordinator.getInstance();
  private static FieldsRetriever retriever = flattenerCoordinator.getFieldsRetriever();
  
public void workOn(Assembly a) {
    Target target = (Target)a.getTarget();
    try {
      //Token memberNameToken = (Token)a.pop();
      Token memberNameToken = null;
      try{
    	  memberNameToken = (Token)a.pop();
      }catch(ArrayIndexOutOfBoundsException e) {
        	EvaluationRuntimeErrors.emptyStack();
        	return;
      }
      
      String memberName = memberNameToken.sval();
      //EvaluationRuntimeErrors.log("FieldLiteralAssembler "+memberName);
      
      /*
      Object targetObject = target.pop();
      */
      Object targetObject = null;
      try{
    	  targetObject = target.pop();
      }catch(ArrayIndexOutOfBoundsException e) {
        	EvaluationRuntimeErrors.emptyStack();
        	return;
      }
      //EvaluationRuntimeErrors.log(targetObject.toString());
      //EvaluationRuntimeErrors.log(targetObject.getClass().toString());
      if ( targetObject == null ){
    	  target.block();
    	  target.push(new NonExistentVariable());
    	  EvaluationRuntimeErrors.nullObjectWithAttribute(memberName);
    	  return;
      }
      
      if ( targetObject instanceof NonExistentVariable ){
    	  target.push(targetObject);
    	  return;
      }
      
      Field field = null;
      
      //primitive types are reported only through methods
      //if we are here is because the parser evaluates all possibilities
      if ( ! flattenerCoordinator.isPrimitiveType( targetObject ) )
    	  field = retriever.getField(targetObject.getClass(), memberName);
      
      
      //System.out.println(field);
      //if field is null field does not exists for targetObject class
      if ( field != null ){
    	  field.setAccessible(true);
    	  Object value = field.get(targetObject);
    	  //EvaluationRuntimeErrors.log("Value for "+field.getName()+" : "+value);
    	  target.push(value);
      } else {
    	  //EvaluationRuntimeErrors.log("Not declared : "+memberName);
    	  target.block();
    	  target.push(new NonExistentVariable());
      }
    	  
      
      /*
       * A Boolean cannot be transformed, because of use of inspector also for Wrapper objects
       * 
      if(value instanceof Boolean)
        if(((Boolean)value).booleanValue())
          value = new Double(1);
        else
          value = new Double(0);
      */
      //System.out.println("FieldLiteralAssembler "+targetObject.getClass()+"."+f.getName()+" "+value);
      
      //System.out.println("FieldLiteralAssembler "+targetObject.getClass()+"."+f.getName()+" "+value);
    } catch (Exception e) {
    	//System.out.println("FieldLiteralAssembler "+e.getMessage());
		//System.out.println("FieldLiteralAssembler "+target.toString());
    	EvaluationRuntimeErrors.evaluationError();
		//target.push(null);
		return;
    }
    
  }
}
