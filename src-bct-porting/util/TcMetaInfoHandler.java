package util;

/**
 * This class is a facade for the TCExecutionRegistry to make simple the calls to the registry to get information about the currently running test case
 *   
 * @author Fabrizio Pastore fabrizio.pastore AT gmail.com
 *
 */
public class TcMetaInfoHandler {
	
	/**
	 * Return the name of the currently running test case, if a problem occurrs during the call to the TCExecutionRegistry it returns a blnk line.
	 * 
	 * @return the name of the current test case or a blank line
	 * 
	 */
	public static String getCurrentTestCase() {
		String currentTC;

		try {
			currentTC = TCExecutionRegistry.getInstance().getCurrentTest();
		} catch (TCExecutionRegistryException e ) {
			if ( e.getStackTrace().length > 3 ){
				StackTraceElement sel = e.getStackTrace()[3];
				currentTC = "*"+sel.getClassName()+"."+sel.getMethodName();
			} else {
				currentTC="";
				e.printStackTrace();
			}
			
		}
		return currentTC;
	}
}
