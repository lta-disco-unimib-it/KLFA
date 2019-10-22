package probes;

import java.io.File;

import recorders.LoggingActionRecorder;
import util.TCExecutionRegistry;
import util.TCExecutionRegistryException;



import flattener.utilities.LogWriter;

public class TcInteractionLoggerProbe {

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
		
		String signature = ClassFormatter.getSignature(cName,theMethodName,methodS);
		//String signature = ClassFormatter.getSignature(methodS, argsPassed, cName, theMethodName );
		
		String currentTC;
		try {
			currentTC = TCExecutionRegistry.getInstance().getCurrentTest();
		} catch (TCExecutionRegistryException e) {
			currentTC = "";
			e.printStackTrace();
		}
		
		LoggingActionRecorder.logInteractionEnterMeta(signature, Thread.currentThread().getId(),currentTC);

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
		
		String signature = ClassFormatter.getSignature(cName,theMethodName,methodS);		
		//String signature = ClassFormatter.getSignature(methodS, argsPassed, cName, theMethodName );
		LoggingActionRecorder.logInteractionExit(signature, Thread.currentThread().getId());
	}

	
}
