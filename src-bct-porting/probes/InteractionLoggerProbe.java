package probes;

import java.io.File;

import check.AspectFlowChecker;

import recorders.LoggingActionRecorder;



import flattener.utilities.LogWriter;

public class InteractionLoggerProbe {

	/**
	 * 
	 * @param cName			class Name
	 * @param theMethodName method Name
	 * @param methodS		method SIgnature
	 * @param argsPassed	arguments
	 */
	public static void intLogEnter(
			String /*className*/ cName,
			String /*methodName*/ theMethodName,
			String /*methodSig*/ methodS,
			Object[] /*args*/ argsPassed ){
		if ( AspectFlowChecker.isInsideAnAspect() )
			return;
		AspectFlowChecker.setInsideAnAspect( Boolean.TRUE );
		String signature = ClassFormatter.getSignature(cName,theMethodName,methodS);

		LoggingActionRecorder.logInteractionEnter(signature, Thread.currentThread().getId());
		AspectFlowChecker.setInsideAnAspect( Boolean.FALSE );
	}

	/**
	 * 
	 * @param cName			class Name
	 * @param theMethodName method Name
	 * @param methodS		method SIgnature
	 * @param argsPassed	arguments
	 */
	public static void intLogExit(
			String /*className*/ cName,
			String /*methodName*/ theMethodName,
			String /*methodSig*/ methodS,
			Object[] /*args*/ argsPassed ){
		if ( AspectFlowChecker.isInsideAnAspect() )
			return;
		AspectFlowChecker.setInsideAnAspect( Boolean.TRUE );
		String signature = ClassFormatter.getSignature(cName,theMethodName,methodS);		
		
		LoggingActionRecorder.logInteractionExit(signature, Thread.currentThread().getId());
		AspectFlowChecker.setInsideAnAspect( Boolean.FALSE );
	}

	
}
