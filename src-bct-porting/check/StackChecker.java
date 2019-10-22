package check;

public class StackChecker {
	public static boolean isLogging(){
		StackTraceElement ste[] = Thread.currentThread().getStackTrace();
	    for ( int i = 4; i < ste.length; i++) {
	      if ( ste[i].getClassName().startsWith("log.") ) {
	        return true;
	      }
	    }
	    return false;
	}
	
	public static boolean isChecking(){
		StackTraceElement ste[] = Thread.currentThread().getStackTrace();
	    for ( int i = 4; i < ste.length; i++) {
	    	//System.out.println(ste[i]);
	      if ( ste[i].getClassName().startsWith("check.") ) {
	        return true;
	      }
	    }
	    return false;
	}
}
