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
package dfmaker.core;

import dfmaker.core.VarTypeResolver.Types;

/**
 * Field of a daikon superstructure.
 * 
 * @author Fabrizio Pastore [ fabrizio.pastore at gmail dot com ]
 *
 */
public class SuperstructureField {
	private String varName;		
	private VarTypeResolver.Types varType;
	private boolean fakeHash;
	private boolean array;
	private boolean referenced = false;
	private boolean nonsensicable;
	
	/**
	 * Constructor, create a superstructure field with the given values.
	 * Fakehash indicates that in normalization phase variables that are not present 
	 * in the point beeing normalized must be set to a random hashcode value and not set to nonsensical.
	 * 
	 * 
	 * @param varName 	name of the variable
	 * @param type		representation type
	 * @param fakeHash	wether variable is a fakehash or not
	 * @param isArray	wether variable is an array
	 */
	public SuperstructureField(String varName, Types type, boolean fakeHash, boolean isArray ) {
		this.varName = varName;
		this.varType = type;
		this.fakeHash = fakeHash;
		this.array = isArray;
	}

	public SuperstructureField(SuperstructureField field) {
		this.varName = field.varName;
		this.varType = field.varType;
		this.fakeHash = field.fakeHash;
		this.array = field.array;
		referenced  = field.referenced;
	}

	public boolean isFakeHash() {
		return fakeHash;
	}

	public void setFakeHash(boolean fakeHash) {
		this.fakeHash = fakeHash;
	}

	public String getVarName() {
		return varName;
	}

	public void setVarName(String varName) {
		this.varName = varName;
	}

	public VarTypeResolver.Types getVarType() {
		return varType;
	}

	public void setVarType(VarTypeResolver.Types varType) {
		this.varType = varType;
	}

	public boolean isArray() {
		return array;
	}

	public void setArray(boolean array) {
		this.array = array;
	}

	public boolean isReferenced() {
		return referenced;
	}

	public void setReferenced(boolean referenced) {
		this.referenced = referenced;
	}

	public void setNonsensicable() {
		nonsensicable = true;
	}
	
	public boolean isNonsensicable(){
		return nonsensicable;
	}

}
