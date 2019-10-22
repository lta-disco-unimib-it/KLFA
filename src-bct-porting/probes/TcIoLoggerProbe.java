package probes;

import recorders.LoggingActionRecorder;
import util.TCExecutionRegistry;
import util.TCExecutionRegistryException;
import check.AspectFlowChecker;

public class TcIoLoggerProbe {

	public static void ioLogEnter(String /*className*/ cName,
			String /*methodName*/ theMethodName,
			String /*methodSig*/ methodS,
			Object[] /*args*/ argsPassed ){
		
		
		if ( AspectFlowChecker.isInsideAnAspect() )
			return;
		AspectFlowChecker.setInsideAnAspect( true );
		
		String signature = ClassFormatter.getSignature(cName,theMethodName,methodS);
		//String signature = ClassFormatter.getSignature(methodS,argsPassed,cName,theMethodName);

		String currentTC;
		try {
			currentTC = TCExecutionRegistry.getInstance().getCurrentTest();
			
		} catch (TCExecutionRegistryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			currentTC = "";
			
		}
		LoggingActionRecorder.logIoEnterMeta(signature, argsPassed, currentTC );
		

		AspectFlowChecker.setInsideAnAspect( false );
	}
	

	public static void ioLogExit(String cName, String theMethodName, String methodS, Object[] argsPassed, Object ret){

		if ( AspectFlowChecker.isInsideAnAspect() )
			return;
		AspectFlowChecker.setInsideAnAspect( true );

		String signature = ClassFormatter.getSignature(cName,theMethodName,methodS);
		//String signature = ClassFormatter.getSignature(methodS,argsPassed,cName,theMethodName);

		String currentTC;
		try {
			currentTC = TCExecutionRegistry.getInstance().getCurrentTest();
		} catch (TCExecutionRegistryException e) {
			currentTC = "";
			e.printStackTrace();
		}
		
		if ( methodS.endsWith("V") ){						//void method
			LoggingActionRecorder.logIoExitMeta(signature, argsPassed, currentTC);
		} else {											//non void method
			LoggingActionRecorder.logIoExitMeta(signature, argsPassed, ret, currentTC);
		}	
		AspectFlowChecker.setInsideAnAspect( false );

	}
	
}
