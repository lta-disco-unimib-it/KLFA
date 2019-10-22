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

import util.TCExecutionRegistry;
import util.TCExecutionRegistryException;

/**
 * Record violations adding informations relative to the current JUnit test case.
 * Junit tests must be instrumented with the aspect/probe that record in the TCExecutionRegistry the name of the currently running test
 * 
 * @author Fabrizio Pastore fabrizio.pastore AT gmail.com
 *
 */
public class FileUnitTestsViolationsRecorder extends FileTCViolationsRecorder {

	@Override
	protected String getTestName() throws ViolationRecorderException {
		try {
			String testName = TCExecutionRegistry.getInstance().getCurrentTest();
			return testName;
		} catch (TCExecutionRegistryException e) {
			throw new ViolationRecorderException(e.getMessage());
		}
		
	}

}
