package check.ioInvariantParser;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;

import sjm.parse.Assembler;
import sjm.parse.Assembly;
import sjm.parse.tokens.Token;

public class MethodLiteralAssembler extends Assembler  {
	private final static HashMap<String,Class> specialMethods = new HashMap<String,Class>();
	
	static{
		specialMethods.put("toString",String.class);
		specialMethods.put("intValue",Integer.class);
		specialMethods.put("booleanValue",Boolean.class);
		specialMethods.put("doubleValue",Double.class);
		specialMethods.put("byteValue",Byte.class);
		specialMethods.put("floatValue",Float.class);
		specialMethods.put("longValue",Long.class);
		specialMethods.put("shortValue",Short.class);
	}
	
  public void workOn(Assembly a) {
    Target target = (Target)a.getTarget();
    //Token methodNameToken = (Token)a.pop();
    Token methodNameToken = null;
    try{
    	methodNameToken = (Token)a.pop();
    }catch(ArrayIndexOutOfBoundsException e) {
      	EvaluationRuntimeErrors.emptyStack();
      	return;
    }
    
    
    
    String methodName = methodNameToken.sval();
    
    //System.out.println("Method literal:"+methodName);
    //Object targetObject = target.pop();
    Object targetObject = null;
    try{
    	targetObject = target.pop();
    }catch(ArrayIndexOutOfBoundsException e) {
      	EvaluationRuntimeErrors.emptyStack();
      	return;
    }
    
    if ( ! applicable(targetObject,methodName )){
    	target.block();
    	return;
    }
    
    Method m = null;
    Object value = null;
    if (targetObject==null) {
    	target.push(new NonExistentVariable());
    	target.block();
    	EvaluationRuntimeErrors.nullObjectWithMethodToBeInvoked( methodName );
    	return;
    }
    
    
    if ( targetObject instanceof NonExistentVariable ){
    	target.push(targetObject);
    	return;
    }
    

    //FIXME: this is not a very good solution
    //This iss done for Boolean values
    //the problem is that Boolean are read as Double, but double don't have a booleanValue() mathod
    if ( targetObject instanceof Double && methodName.equals("booleanValue")){
    	//System.out.println("Boolean value:"+targetObject);
   		target.push( targetObject );
   		return;
    }
    
    
    
    try {
      m = targetObject.getClass().getMethod(methodName, new Class[0]);      
      value = m.invoke(targetObject, new Object[0]);      
    }
    catch (IllegalAccessException e) {			
      Class[] interfaces = targetObject.getClass().getInterfaces();
      for(int i = 0;i<interfaces.length;i++)
        try {
          m = interfaces[i].getMethod(methodName, new Class[0]);
          value = m.invoke(targetObject,new Object[0]);
          break;
        }
        catch(Exception ex) {
        	ex.printStackTrace();
        }
    } catch (java.lang.NoSuchMethodException e) {
    	
    	//FIXME: hack-way to stop execution in case of interface changed
    	target.block();
    	
    	
      	EvaluationRuntimeErrors.noSuchMethod( targetObject.getClass().toString()+"."+methodName );
      	return;
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    //System.out.println("(PREPUSH)Method literal:"+targetObject.getClass()+"."+methodName+" "+value);
    
    try{
    	/*
    	if(value instanceof Boolean)
    		if(((Boolean)value).booleanValue())
    			value = new Double(1);
    		else
    			value = new Double(0);
    	*/
    	target.push(value);
        //System.out.println("(POSTPUSH)Method literal:"+targetObject.getClass()+"."+methodName+" "+value);
    }

    catch (Exception e) {
		EvaluationRuntimeErrors.evaluationError();
		//target.push(null);
		return;
	}
  }

  /**
   * Check if a method can be calledon an object.
   * This is done to reduce false positives because in certain cases we can have invariants that call primitive inspectors 
   * (like intValue or toString) that can be present also in Objects that are not instances of primitive types and this can generate 
   * false positives. This is a consequence of the presence of nonsensical values in traces used for model generation.
   * 
   * @param targetObject
   * @param methodName
   * @return
   */
  private boolean applicable(Object targetObject, String methodName) {
	if ( targetObject == null )
		return true;
	
	Class targetClass = targetObject.getClass();
	
	Iterator<String> specialClassIt = specialMethods.keySet().iterator();
	
	//check if the method called is a special one
	while ( specialClassIt.hasNext() ){
		String key = specialClassIt.next();
		if ( key.equals(methodName) ){
			if ( specialMethods.get(key).equals(targetClass) ){
				return true;
			} else{
				return false;
			}
		}
	}
	
	return true;
  }


}