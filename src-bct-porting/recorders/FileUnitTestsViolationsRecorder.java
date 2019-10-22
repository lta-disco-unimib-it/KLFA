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
