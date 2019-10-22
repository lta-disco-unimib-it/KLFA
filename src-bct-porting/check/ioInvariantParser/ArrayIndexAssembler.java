package check.ioInvariantParser;

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
