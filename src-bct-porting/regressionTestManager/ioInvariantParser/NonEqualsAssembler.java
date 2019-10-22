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

import java.lang.reflect.Array;

import regressionTestManager.tcSpecifications.TcSpecificationEquals;
import regressionTestManager.tcSpecifications.TcSpecificationGreaterThan;
import regressionTestManager.tcSpecifications.TcSpecificationLessThan;
import regressionTestManager.tcSpecifications.TcSpecificationNot;
import regressionTestManager.tcSpecifications.TcSpecificationNotEqualsNull;
import regressionTestManager.tcSpecifications.TcSpecificationPlusOne;
import sjm.parse.Assembler;
import sjm.parse.Assembly;

/**
 * Implements non equals check.
 * Rember that a NonExistentVariable is always considered different from any other variable so
 * a != b returns true if at least one of the two is a NonExistentVariable.
 * 
 * @author Fabrizio Pastore [ fabrizio.pastore at gmail dot com ]
 *
 */
public class NonEqualsAssembler extends Assembler {

	private Boolean evaluateArrays(Object a1, Object a2) {
		int MAX = Array.getLength(a1);
		try {
			for (int i = 0; i < MAX; i++) {
				Comparable c1 = (Comparable) Array.get(a1, i);
				Comparable c2 = (Comparable) Array.get(a2, i);
				if (c1.compareTo(c2) != 0)
					return Boolean.TRUE;
			}
		} catch (ClassCastException cce) {
			return Boolean.FALSE;
		}
		return Boolean.FALSE;
	}

	public void workOn(Assembly a) {
		Target target = (Target) a.getTarget();
		/*
		 * Object parameter2 = (Object)target.pop(); if(target.isEmpty())
		 * return;
		 */
		Object parameter2 = null;
		try {
			parameter2 = (Object) target.pop();
		} catch (ArrayIndexOutOfBoundsException e) {
			EvaluationRuntimeErrors.emptyStack();
			return;
		}
		//Object parameter1 = (Object)target.pop();
		Object parameter1 = null;
		try {
			parameter1 = (Object) target.pop();
		} catch (ArrayIndexOutOfBoundsException e) {
			EvaluationRuntimeErrors.emptyStack();
			return;
		}
		try {
			if ( parameter2 == null ) {
				target.push( new TcSpecificationNotEqualsNull( (Variable)parameter1 ) );
			} else if ( parameter2 instanceof Number ){
				//FIXME: how to handle double?
				//add p1 > p2 + 1 
				target.push(
						new TcSpecificationEquals( (Variable)parameter1,((Number)parameter2).doubleValue()+1 )
				);
				//add p1 = p2 + 1
				target.push(
						new TcSpecificationEquals( (Variable)parameter1,((Number)parameter2).doubleValue()-1 )
				);
				target.push(
						new TcSpecificationGreaterThan( (Variable)parameter1,((Number)parameter2).doubleValue()+1 )
				);
				//add p1 = p2 + 1
				target.push(
						new TcSpecificationLessThan( (Variable)parameter1,((Number)parameter2).doubleValue()-1 )
				);

			} else {
				//special case : two string parameters 
				if ( parameter2.toString().endsWith("toString()") && parameter1.toString().endsWith("toString()") ){
					target.push(
							new TcSpecificationNot( new TcSpecificationEquals((Variable)parameter2,(Variable)parameter1))
							);
				} else {
				
				//y < x
				target.push(
						new TcSpecificationLessThan( (Variable)parameter2, (Variable)parameter1 )
				);
				
				//y = x + 1
				target.push(
						new TcSpecificationEquals( (Variable)parameter2, 
								new TcSpecificationPlusOne( (Variable)parameter1 ) )
				);
				//x = y + 1
				target.push(
						new TcSpecificationEquals( (Variable)parameter1, 
								new TcSpecificationPlusOne((Variable)parameter2 ) )
				);
				//x < y
				target.push(
						new TcSpecificationLessThan( (Variable)parameter1, (Variable)parameter2 ) 
				);
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
			//System.out.println("NEA "+target.toString());
			EvaluationRuntimeErrors.evaluationError();
			//target.push(Boolean.FALSE);
			return;
		}
		//System.out.println("NEA OK");
		//System.out.flush();
	}
}