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

import java.util.Collection;
import java.util.Iterator;

public class CollectionIoModelIterator implements IoModelIterator {

	private Collection<String> models;
	private Iterator<String>	iterator;
	
	public CollectionIoModelIterator( Collection<String> models ){
		this.models = models;
		iterator = models.iterator();
	}
	
	public void reset() {
		iterator = models.iterator();
	}

	public boolean hasNext() {
		return iterator.hasNext();
	}

	public String next() {
		return iterator.next();
	}

	public void remove() {
		throw new UnsupportedOperationException();
	}

}
