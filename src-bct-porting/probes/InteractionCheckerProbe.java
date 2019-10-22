package probes;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintStream;

import check.AspectFlowChecker;
import check.Checker;
import check.InteractionInvariantHandler;
import check.ViolationRegistry;
import check.ioInvariantParser.IOMemoryRegistry;
import check.ioInvariantParser.InvariantParseException;
import check.ioInvariantParser.IoInvariantParser;
import flattener.core.Handler;
import flattener.core.StimuliRecorder;
import flattener.factories.DaikonComponentsFactory;
import flattener.flatteners.ObjectFlattener;

public class InteractionCheckerProbe {
	
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
		//String signature = ClassFormatter.getSignature(methodS, argsPassed, cName, theMethodName );
		
		Checker.checkInteractionEnter( signature, Thread.currentThread().getId() );

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
		
		Checker.checkInteractionExit( signature, Thread.currentThread().getId() );
		
		AspectFlowChecker.setInsideAnAspect(false);
	}
	
}
