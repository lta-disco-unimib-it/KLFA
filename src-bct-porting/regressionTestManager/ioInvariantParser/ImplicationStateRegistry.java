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

public class ImplicationStateRegistry {
	
	private boolean implication = false;
	private boolean leftSideTrue = false;
	private int lastQueueSize = 0;
	
	private static ImplicationStateRegistry instance;

	public static synchronized ImplicationStateRegistry getInstance(){
		if ( instance == null ){
			instance = new ImplicationStateRegistry();
		}
		return instance;
	}

	public boolean isImplication() {
		return implication;
	}

	public void setImplication(boolean implication) {
		//System.out.println("implication state "+implication);
		this.implication = implication;
	}

	public boolean isLeftSideTrue() {
		return leftSideTrue;
	}

	public void setLeftSideTrue(boolean leftSideTrue) {
		//System.out.println("implication left side "+leftSideTrue);
		this.leftSideTrue = leftSideTrue;
	}

	public int getLastQueueSize() {
		return lastQueueSize;
	}

	public void setLastQueueSize(int lastQueueSize) {
		this.lastQueueSize = lastQueueSize;
	}
	
	public void reset(){
		//System.out.println("reset implication");
		implication = false;
		leftSideTrue = false;
		lastQueueSize = 0;
	}
}
