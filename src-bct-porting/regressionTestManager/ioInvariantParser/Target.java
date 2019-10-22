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

import java.util.ArrayList;
import java.util.HashMap;

import conf.EnvironmentalSetter;

import sjm.utensil.PubliclyCloneable;

public class Target implements PubliclyCloneable {

  private ArrayList queue = new ArrayList();
  private String programPointName;
  
  public Target(String programPointName) {
    this.programPointName = programPointName;
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
  public Object get(Object key) {
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
	EvaluationRuntimeErrors.log("pushing "+o);  
    queue.add(o);
    EvaluationRuntimeErrors.log("pushed ");
  }
  
  
  public Object clone() {
    try {
      Target t = (Target)super.clone();
      t.queue = (ArrayList)queue.clone();
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

  public String getProgramPointName() {
	  return programPointName;
  }



}
