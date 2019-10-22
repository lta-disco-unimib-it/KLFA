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
package it.unimib.disco.lta.alfa.frequentItemsetsAnalysis;

import java.util.List;
import java.util.Set;

public class Itemset<T> {
	

	private Set<T> items;
	private int frequence;

	public Itemset(Set<T> items,int frequence){
		this.items = items;
		this.frequence = frequence;
	}

	public Set<T> getItems() {
		return items;
	}

	public int getFrequence() {
		return frequence;
	}
}
