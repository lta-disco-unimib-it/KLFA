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
