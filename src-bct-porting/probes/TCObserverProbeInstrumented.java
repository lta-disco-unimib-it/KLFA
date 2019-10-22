package probes;

import util.TCExecutionRegistry;
import util.TCExecutionRegistryException;

/**
 * 
 * This class is used to keep in memory the name of the executed TestCase when the test process it is possible to instrument directly 
 * the test case with probes to maintain the name of the current test in the test registry.
 * 
 * @author Fabrizio Pastore fabrizio.pastore AT gmail.com
 *
 */
public class TCObserverProbeInstrumented {
	
	public static void enter( String className, String  methodName, String methodSignature ){
		try {
			//System.out.println("ENTER");
			TCExecutionRegistry.getInstance().tcEnter(ClassFormatter.getSignature(className, methodName, methodSignature));
		} catch (TCExecutionRegistryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void exit( String className, String  methodName, String methodSignature ){
		try {
			//System.out.println("EXIT");
			TCExecutionRegistry.getInstance().tcExit(ClassFormatter.getSignature(className, methodName, methodSignature));
		} catch (TCExecutionRegistryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
