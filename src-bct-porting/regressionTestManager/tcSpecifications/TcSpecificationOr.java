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
package regressionTestManager.tcSpecifications;

import java.util.ArrayList;
import java.util.Iterator;

public class TcSpecificationOr implements TcSpecification {
	
	private ArrayList specifications = new ArrayList();
	
	public TcSpecificationOr ( TcSpecification spec ){
		specifications.add(spec);
	}

	public void addElement(TcSpecification tcs) {
		specifications.add( tcs );
	}
	
	public boolean equals ( Object o ){
		
		if ( o == this )
			return true;
		if ( o instanceof TcSpecificationOr ){
			TcSpecificationOr rhs = ( TcSpecificationOr) o;
			if ( rhs.specifications.size() != specifications.size() )
				return false;
			Iterator itr = rhs.specifications.iterator();
			while ( itr.hasNext() ){
				TcSpecification elem = (TcSpecification)itr.next();
				if ( ! specifications.contains( elem )){
					System.out.println("Not contains "+elem);
					return false;
				}
			}
			return true;
		}
		
		return false;
	}
	
	public String toString(){
		String msg = null;
		Iterator it = specifications.iterator();
		while ( it.hasNext() ){
			if ( msg == null )
				msg = it.next().toString();
			else
				msg += " OR "+it.next();
		}
		
		return msg;
	}

	public Iterator getElementsIterator() {
		return specifications.iterator();
	}
	
	
	
}
