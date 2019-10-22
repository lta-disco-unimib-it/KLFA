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

public class TcSpecificationAnd implements TcSpecification {

	private TcSpecification rigthSide;
	private TcSpecification leftSide;

	public TcSpecificationAnd(TcSpecification leftSide, TcSpecification rightSide) {
		this.leftSide = leftSide;
		this.rigthSide = rightSide;
	}

	public String toString(){
		return " ( "+leftSide+" AND "+rigthSide+" ) ";
	}
	
	public boolean equals( Object o ){
		if ( o == this )
			return true;
		if ( o instanceof TcSpecificationAnd ){
			TcSpecificationAnd spec = (TcSpecificationAnd) o;
			return ( spec.contains(leftSide) && spec.contains( rigthSide) );
		}
		return false;
			
	}

	private boolean contains(TcSpecification spec) {
		return ( spec.equals( rigthSide) || spec.equals( leftSide) );
	}

	public TcSpecification getLeftSide() {
		return leftSide;
	}

	public TcSpecification getRigthSide() {
		return rigthSide;
	}
}
