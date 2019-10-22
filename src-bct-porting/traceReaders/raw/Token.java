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
package traceReaders.raw;

/**
 * class used to normalized interaction trace
 * 
 *
 */

public class Token {
	
	private String methodSignature = "";
	private int id;
	
	//Token for DB
	public Token (int id, String methodSignature) {
		this.id = id;
		this.methodSignature = methodSignature;
	}
	
	//Token for file
	public Token (String methodSignature) {
		this.methodSignature = methodSignature;
	}
	
	public String getMethodName() {
		return methodSignature;
	}
	
	public int getId() {
		return id;
	}

}
