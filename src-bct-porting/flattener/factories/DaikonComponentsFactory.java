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
/*
 * Created on 20-mag-2005
 */
package flattener.factories;

import flattener.core.Factory;
import flattener.core.Handler;
import flattener.core.StimuliRecorder;
import flattener.handlers.DaikonHandler;
import flattener.stimuliRecorders.DaikonStimuliRecorder;

/**
 * @author Davide Lorenzoli
 */
public class DaikonComponentsFactory implements Factory {

	/**
	 * @param rootNodeName
	 * @return
	 */
	public Handler getHandler(String rootNodeName) {
		return new DaikonHandler(rootNodeName);
	}
	
	/**
	 * @see flattener.core.Factory#getHandler()
	 */
	public Handler getHandler() {		
		return new DaikonHandler();
	}
	
	/**
	 * @see flattener.core.Factory#getStimuliRecorder()
	 */
	public StimuliRecorder getStimuliRecorder() {
		return new DaikonStimuliRecorder();
	}
}
