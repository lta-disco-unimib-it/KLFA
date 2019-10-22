package probes;

import recorders.LoggingActionRecorder;


public abstract class IoLoggerProbe {
	
	public static void ioLogEnter(String /*className*/ cName,
			String /*methodName*/ theMethodName,
			String /*methodSig*/ methodS,
			Object[] /*args*/ argsPassed ){
		

//		You need the following lines only if you use Lorenzoli ObjectFlattener
//		if ( AspectFlowChecker.isInsideAnAspect() )
//			return;
//		AspectFlowChecker.setInsideAnAspect( Boolean.TRUE );
//		
		String signature = ClassFormatter.getSignature(cName,theMethodName,methodS);
		//System.out.println("IOS "+signature);

		//System.out.println( Thread.currentThread().getId()+"#IO-ENTER : "+signature);
		
		LoggingActionRecorder.logIoEnter(signature, argsPassed);

//You need the following lines only if you use Lorenzoli ObjectFlattener
//		AspectFlowChecker.setInsideAnAspect( Boolean.FALSE );
	}
	

	public static void ioLogExit(String cName, String theMethodName, String methodS, Object[] argsPassed, Object ret){
//		You need the following lines only if you use Lorenzoli ObjectFlattener
//
//		if ( AspectFlowChecker.isInsideAnAspect() )
//			return;
//		AspectFlowChecker.setInsideAnAspect( Boolean.TRUE );
//
		String signature = ClassFormatter.getSignature(cName,theMethodName,methodS);

		//System.out.println( Thread.currentThread().getId()+"#IO-EXIT : "+signature);
		
		if ( methodS.endsWith("V") ){						//void method
			LoggingActionRecorder.logIoExit(signature, argsPassed);
		} else {											//non void method
			LoggingActionRecorder.logIoExit(signature, argsPassed, ret);
		}

//      You need the following lines only if you use Lorenzoli ObjectFlattener
//		
//		//System.out.println("IOE "+signature);
//		AspectFlowChecker.setInsideAnAspect( Boolean.FALSE );

	}
}
