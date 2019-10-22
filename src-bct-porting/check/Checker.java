package check;

public class Checker {

	public static synchronized void checkInteractionEnter(String signature, long threadId) {
		InteractionInvariantHandler.processCallEnter(threadId, signature);
	}

	public static synchronized void checkInteractionExit(String signature, long threadId) {
		InteractionInvariantHandler.processCallExit(threadId, signature);
	}

	public static synchronized void checkIoEnter(String methodSignature, Object[] parameters) {
		IoChecker.checkEnter(methodSignature, parameters);
	}

	public static synchronized void checkIoExit(String methodSignature, Object[] parameters) {
		IoChecker.checkExit(methodSignature, parameters);	
	}

	public static synchronized void checkIoExit(String methodSignature, Object[] parameters, Object returnValue) {
		IoChecker.checkExit(methodSignature, parameters, returnValue);	
	}
	

}
