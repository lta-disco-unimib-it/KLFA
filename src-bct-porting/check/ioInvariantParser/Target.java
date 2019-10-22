package check.ioInvariantParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import conf.EnvironmentalSetter;

import sjm.utensil.PubliclyCloneable;

public class Target implements PubliclyCloneable {

  private ArrayList queue = new ArrayList();
  
  //public static HashMap memory = new HashMap();

  private Object[] parameters = new Object[0];

  private Object returnValue;

  private boolean blocked;
  
  public Target(Object[] parameters,Object returnValue) {
    this.parameters = parameters;
    this.returnValue = returnValue;
  }

  public Object[] getParameters() {
    return parameters;
  }

  public Object getReturnValue() {
    return returnValue;
  }

  public boolean isEmpty() {
    return queue.isEmpty();
  }

  public Object top() {
    return queue.get(queue.size() - 1);
  }

  public Object pop()  {
	//  System.out.println("Queue :\n"+queue.toString());
	//System.out.println("pop queue : "+queue.toString());
	//System.out.flush();
	Object o = queue.get(queue.size() - 1);
    queue.remove(queue.size() - 1);
    return o;
  }

  
  public int queueSize(){
	  return queue.size();
  }
  
  public void put(Object key,Object value) {
	//  System.out.println("#Target.put: key: "+key+" : "+value+" a "+memory.keySet() );
    //memory.put(key,value);
	  
	  IOMemoryRegistry.getInstance().getCurrentMethodsMap().put(key,value);
  }
  
  /**
   * Returns the original value of an inspector.
   * This method is called when evaluating an orig(..) operator during exit from a method.
   * 
   * @param key
   * @return
   */
  public Object getOrig(Object key) {
	  //System.out.println("#Target.get: key: "+key+" "+IOMemoryRegistry.getInstance().getCurrentMethodsMap().get(key) );
	  //System.out.println(memory);
      //return memory.get(key);
	  return IOMemoryRegistry.getInstance().getCurrentMethodsMap().get(key);
  }
  
  public Object remove(Object key) {
	  //System.out.println("#Target.remove: key: "+key );
	  //System.out.println(IOMemoryRegistry.getInstance().getCurrentMethodsMap().containsKey(key) );
	  //return memory.remove(key);
	  return IOMemoryRegistry.getInstance().getCurrentMethodsMap().remove(key);
  }
  
  public void push(Object o) {
	  //FIXME: throw an exception?
	  
	  if ( ClassTypeChecker.getInstance().isIgnoredType(o) ){
		  block();
		  
		  return;
		  //throw new RuntimeException("ClassToIgnore");
	  }
    queue.add(o);
	//System.out.println("push queue : "+queue.toString());
	//System.out.flush();
  }
  
  
  public Object clone() {
    try {
      Target t = (Target)super.clone();
      t.queue = (ArrayList)queue.clone();
      t.blocked = blocked;
      return t;
    }
    catch (CloneNotSupportedException e) {
      throw new InternalError();
    }
  }
  
  public boolean contains( Object key ){
	  return IOMemoryRegistry.getInstance().getCurrentMethodsMap().containsKey(key);
  }
  
  
  public String toString(){
	  String res = super.toString();
	  res += "queue: "+queue.toString();
	  return res;
  }
  
  /**
   * Block target queue, form this moment queue will be void an no push can be made.
   * It is used when a NonExistentMethod is called.
   *
   */
  public void block(){
	  
	  blocked = true;
  }
  
  
  public boolean isBlocked(){
	  return blocked;
  }
  
  public Set getOrigKeys(){
	  return IOMemoryRegistry.getInstance().getCurrentMethodsMap().keySet();
  }
}
