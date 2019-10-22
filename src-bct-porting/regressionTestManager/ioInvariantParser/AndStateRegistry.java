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

/**
 * This class maintain infomrmation on current expression. It tells the user if we are inside right side of an and.
 * FIXME: this class does not handle correctly annidated ands.
 * 
 * @author Fabrizio Pastore [ fabrizio.pastore at gmail dot com ]
 *
 */
public class AndStateRegistry {

	private static AndStateRegistry instance = null;
	private boolean andState = false;
	private boolean leftResult;
	private int 	lastQueueSize = 0;
	private boolean clear = true;
	
	
	
	
	public static synchronized AndStateRegistry getInstance(){
		if ( instance == null ){
			instance = new AndStateRegistry();
		}
		return instance;
	}


	public boolean isAndState() {
		return andState;
	}


	public void setAndState( Boolean lr, int i) {
		this.andState = true;
		
		if ( clear ){
			this.leftResult = lr.booleanValue();
			this.lastQueueSize = i;
			clear = false;
		} else if ( leftResult == true ){
			leftResult = lr.booleanValue();
		}
	}


	public boolean isLeftResultTrue() {
		return leftResult;
	}


	
	public void reset(){
		//System.out.println("ReSetting state ");
		andState = false;
		clear = true;
	}


	public int getLastQueueSize() {
		return lastQueueSize;
	}


}
