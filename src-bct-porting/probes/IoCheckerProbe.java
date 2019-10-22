package probes;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintStream;

import recorders.RecorderException;
import recorders.RecorderFactory;
import recorders.ViolationsRecorder;
import recorders.ViolationsRecorder.IoViolationType;

import check.AspectFlowChecker;
import check.Checker;
import check.IoChecker;
import check.ViolationRegistry;
import check.ioInvariantParser.IOMemoryRegistry;
import check.ioInvariantParser.InvariantParseException;
import check.ioInvariantParser.IoInvariantParser;
import flattener.core.Handler;
import flattener.core.StimuliRecorder;
import flattener.factories.DaikonComponentsFactory;
import flattener.flatteners.ObjectFlattener;

public class IoCheckerProbe {	
	
	public static void checkEnter(
			String /*className*/ cName,
			String /*methodName*/ theMethodName,
			String /*methodSig*/ methodS,
			Object[] /*args*/ argsPassed
	){
		
		if ( AspectFlowChecker.isInsideAnAspect() )
			return;
		AspectFlowChecker.setInsideAnAspect(true);
		String signature = ClassFormatter.getSignature(cName,theMethodName,methodS);
//		String signature = ClassFormatter.getSignature(methodS, argsPassed, cName, theMethodName );
		
		Checker.checkIoEnter(signature, argsPassed);
	
		AspectFlowChecker.setInsideAnAspect(false);
		
	}

	public static void checkExit(
			String /*className*/ cName,
			String /*methodName*/ theMethodName,
			String /*methodSig*/ methodS,
			Object[] /*args*/ argsPassed,
			Object 	ret
	){
		if ( AspectFlowChecker.isInsideAnAspect() )
			return;
		AspectFlowChecker.setInsideAnAspect(true);

		String signature = ClassFormatter.getSignature(cName,theMethodName,methodS);
		//String signature = ClassFormatter.getSignature(methodS, argsPassed, cName, theMethodName );

		if ( methodS.endsWith("V") )
			Checker.checkIoExit(signature, argsPassed );
		else
			Checker.checkIoExit( signature, argsPassed, ret );
		
		
		
		AspectFlowChecker.setInsideAnAspect(false);
	}
	
}
