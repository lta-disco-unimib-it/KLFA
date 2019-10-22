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
package flattener.handlers;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import flattener.core.Handler;


public class RawHandler implements Handler {
	protected HashMap<String,Object> nodes = new HashMap<String,Object>();
	//private HashMap	 references = new HashMap();
	private String root;
	
	public RawHandler(String rootName) {
		root = rootName;
	}

	/**
	 * 
	 * @param name
	 * @param object
	 */
	public void addNode( String name, Object object ){
		String value = getValue ( object );
		nodes.put(root+name, value);
	}

	private String getValue(Object object) {
		if ( object == null )
			return "null";
		
		if ( object.getClass() == Boolean.class ){
			if ( (Boolean)object )
				return "1";
			return"0";
		}
		
		if ( object.getClass() == String.class )
			return "\""+object+"\"";
		
		if ( object.getClass() == Character.class )
			return "\""+object+"\"";
		
		return object.toString();
	}

	public void addNodeRef(String referencingName, String referencedName) {
		nodes.put(root+referencingName, "@"+root+referencedName);
	}

	public void add(Object object) {
		throw new NotImplementedException();
	}

	public Object getData() {
		return this;
	}

	public void goDown(Method method) {
		throw new NotImplementedException();
	}

	public void goDown(Field field) {
		throw new NotImplementedException();
	}

	public void goDownArray() {
		throw new NotImplementedException();
	}

	public void goDownArray(int i) {
		throw new NotImplementedException();
	}

	public void goUp() {
		throw new NotImplementedException();
	}

	public void goUpArray() {
		throw new NotImplementedException();
	}

	public Iterator getNodeNamesIt() {
		return nodes.keySet().iterator();
		
	}

	public String getNodeValue( String nodeName ){
		return (String) nodes.get(nodeName);
	}

	public String getRootName() {
		return root;
	}
	
	public int getNodesNumber(){
		return nodes.size();
	}

	public void addNotNull(String inspectorFullName) {
		nodes.put(root+inspectorFullName, "!NULL");
	}
	
	
}
