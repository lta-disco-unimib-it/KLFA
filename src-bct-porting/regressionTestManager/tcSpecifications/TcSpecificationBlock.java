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

/**
 * A TcSpecificationBlock is a block of TcSpecifications derived an invariant. 
 * It represent a block of variable-values that represent a true case.
 * Each Assemblers that produce multiple specifications from an invariant has to encapsulate them in a block. 
 * 
 * For example during the evaluation of:
 * 
 * ( A != null ) ==> ( A.b > 3 )
 * 
 * two blocks will be made
 * [ A != null ]
 * [ A.b == 4; A.b > 4 ]
 * 
 * if you see the implication as L ==> R, ( A != null ) represent the truth value for L, ( A.b == 4 ) and ( A.b > 4 ) 
 * are the possible truth value for R. 
 * 
 *  
 * @author Fabrizio Pastore fabrizio.pastore AT gmail.com
 *
 */
public class TcSpecificationBlock implements TcSpecification {
	private ArrayList<TcSpecification> elements = new ArrayList<TcSpecification>();
	
	public void addElement( TcSpecification element ){
		elements.add( element );
	}

	public Iterator<TcSpecification> elementsIterator() {
		return elements.iterator();
	}
}
