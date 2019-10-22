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
import java.util.Iterator;

import regressionTestManager.tcSpecifications.TcSpecification;
import regressionTestManager.tcSpecifications.TcSpecificationAnd;
import regressionTestManager.tcSpecifications.TcSpecificationBlock;
import regressionTestManager.tcSpecifications.TcSpecificationNot;
import regressionTestManager.tcSpecifications.TcSpecificationOr;
import sjm.parse.Assembler;
import sjm.parse.Assembly;

/**
 * Assembler for the implication production.
 * This is executed after all production is read and so there are 
 * left side result and right side result on the stack.
 * 
 * @author Fabrizio Pastore [ fabrizio.pastore at gmail dot com ]
 *
 */
public class ImplicationAssembler extends Assembler {

	
	public void workOn(Assembly a) {
		Target target = null;
		TcSpecification leftSide = null;
		TcSpecification rightSide = null;
		//System.out.println("ImplicationAssembler ");
		try{
			target = (Target)a.getTarget();
			
			//FIXME: add check for non existent variable
			
			//if left side was FALSE and
			//there is no right side due to access problems, evaluation is still correct
				rightSide = (TcSpecification)target.pop();  
				leftSide = (TcSpecification)target.pop();  
			
		}catch(ArrayIndexOutOfBoundsException e) {
			//System.out.println("ImplicationAssembler "+target.toString());
			EvaluationRuntimeErrors.emptyStack();
			return;
		}
		
		
		ArrayList leftElements = new ArrayList();
		if ( leftSide instanceof TcSpecificationBlock ){
			Iterator it = ((TcSpecificationBlock)leftSide).elementsIterator();
			while ( it.hasNext() ){
				leftElements.add( it.next() );
			}
		} else {
			leftElements.add( leftSide );
		}
		
		ArrayList rightElements = new ArrayList();
		if ( rightSide instanceof TcSpecificationBlock ){
			Iterator it = ((TcSpecificationBlock)rightSide).elementsIterator();
			while ( it.hasNext() ){
				rightElements.add( it.next() );
			}
		} else {
			rightElements.add( rightSide );
		}
		
		//!A and B
		//to cover !A and B we need at least that one combination is covered
		//so we do the combinations of all !A and B values and put all these in alternation
		Iterator itl = leftElements.iterator();
		
		
		TcSpecificationOr orBlock = null;
		while ( itl.hasNext() ){
			TcSpecification left = (TcSpecification) itl.next();
			Iterator itr= rightElements.iterator();
			while ( itr.hasNext() ){
				TcSpecificationAnd tcs = new TcSpecificationAnd( new TcSpecificationNot ( left ) ,(TcSpecification) itr.next());
				if ( orBlock == null){
					orBlock = new TcSpecificationOr( tcs );
				} else {
					orBlock.addElement( tcs );
				}
			}
		}
		target.push( orBlock );
		
		//!A and !B
		
		
		itl = leftElements.iterator();
		
		orBlock = null;
		while ( itl.hasNext() ){
			TcSpecification left = (TcSpecification) itl.next();
			Iterator itr= rightElements.iterator();
			while ( itr.hasNext() ){
				TcSpecificationAnd tcs = new TcSpecificationAnd( new TcSpecificationNot ( left ) , new TcSpecificationNot ((TcSpecification) itr.next()));
				if ( orBlock == null){
					orBlock = new TcSpecificationOr( tcs );
				} else {
					orBlock.addElement( tcs );
				}
			}
		}
		target.push( orBlock );
		
		
		//A
		
		itl = leftElements.iterator();
		
		orBlock = null;
		while ( itl.hasNext() ){
			TcSpecification tcs = (TcSpecification) itl.next();
			if ( orBlock == null){
				orBlock = new TcSpecificationOr( tcs );
			} else {
				orBlock.addElement( tcs );
			}
		}
		target.push( orBlock );
		
	}



}
