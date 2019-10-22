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
package recorders;

import flattener.core.Flattener;
import flattener.core.Handler;
import flattener.core.StimuliRecorder;
import flattener.factories.DaikonComponentsFactory;
import flattener.flatteners.ObjectFlattener;
import flattener.handlers.DaikonHandler;

public class ObjectFlattenerAssembler implements FlattenerAssembler {
	private DaikonComponentsFactory daikonFactory = new DaikonComponentsFactory();
	
	public Flattener getFlattener(String rootName) {
		Handler handler = daikonFactory.getHandler(rootName);
		ObjectFlattener flattener = new ObjectFlattener();
		flattener.setDataHandler(handler);
		return flattener;
	}

	public StimuliRecorder getStimuliRecorder() {
		return daikonFactory.getStimuliRecorder();
	}

}
