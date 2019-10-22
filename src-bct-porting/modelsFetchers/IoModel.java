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
package modelsFetchers;

import java.util.ArrayList;
import java.util.Iterator;

public class IoModel {
	private ArrayList preconditions = new ArrayList();
	private ArrayList postconditions = new ArrayList();	
	
	public void addPreconditions(ArrayList preconditions) {
		this.preconditions.addAll(preconditions);
	}

	public void addPrecondition(String line) {
		preconditions.add(line);
	}

	public void addPostconditions(ArrayList postconditions) {
		this.postconditions.addAll(postconditions);
	}
	
	public void addPostcondition(String line) {
		postconditions.add(line);
	}
	
	public Iterator preconditionsIt(){
		return preconditions.iterator();
	}

	public Iterator postconditionsIt(){
		return postconditions.iterator();
	}
}
