package util;

import java.util.HashMap;

/**
 * 
 * Registry that maintain trace of the currently running test cases
 *  
 * @author Fabrizio Pastore fabrizio.pastore AT gmail.com
 *
 */
public class TCExecutionRegistry {
	
	private static TCExecutionRegistry instance;
	private HashMap testTimes = new HashMap();
	
	private String currentTest;
	private long currentTestStartTime;
	
	private TCExecutionRegistry(){
		
	};
	
	public static synchronized TCExecutionRegistry getInstance(){
		if ( instance == null ){
			instance = new TCExecutionRegistry();
		}
		return instance;
	}
	
	/**
	 * Signalates the Registry that we are inside the passed TestCase
	 * @param signature
	 * @throws TCExecutionRegistryException 
	 */
	public void tcEnter(String signature) throws TCExecutionRegistryException {
		if ( signature == null )
			throw new TCExecutionRegistryException("null value not permitted as TestCase name");
		if ( currentTest != null )
			throw new TCExecutionRegistryException("Already inside test "+currentTest+", now running "+signature);
//		System.out.println("TEST ENTER "+signature);
		currentTest = signature;
		currentTestStartTime = System.currentTimeMillis();
	}

	/**
	 * Signalates the registry that we have exited the passed test case
	 * 
	 * @param signature
	 * @throws TCExecutionRegistryException 
	 */
	public void tcExit(String signature) throws TCExecutionRegistryException {
		if ( signature == null )
			throw new TCExecutionRegistryException("null value not permitted as TestCase name");
		if ( currentTest == null )
			throw new TCExecutionRegistryException("Not inside a TestCase");
		if ( !currentTest.equals(signature))
			throw new TCExecutionRegistryException("Current test not equals to exiting test");
		testTimes.put(currentTest, new Long(System.currentTimeMillis()-currentTestStartTime));
		currentTest = null;
	}

	/**
	 * Return the name of the test case we are currently in
	 * 
	 * @return
	 */
	public String getCurrentTest() throws TCExecutionRegistryException {
		if ( currentTest == null )
			throw new TCExecutionRegistryException("No TestCase execution");
		return currentTest;
	}
	
	
}
