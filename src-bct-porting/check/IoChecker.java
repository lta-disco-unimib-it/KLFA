package check;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;

import modelsFetchers.CollectionIoModelIterator;
import modelsFetchers.IoModelIterator;
import modelsFetchers.ModelsFetcherException;
import modelsFetchers.IoExpressionCollection;
import modelsFetchers.ModelsFetcher;
import modelsFetchers.ModelsFetcherFactoy;

import recorders.RecorderException;
import recorders.RecorderFactory;
import recorders.ViolationsRecorder;
import recorders.ViolationsRecorder.IoViolationType;

import check.AspectFlowChecker;
import check.ViolationRegistry;
import check.ioInvariantParser.EvaluationRuntimeErrors;
import check.ioInvariantParser.IOMemoryRegistry;
import check.ioInvariantParser.InvariantParseException;
import check.ioInvariantParser.IoInvariantParser;
import flattener.core.Handler;
import flattener.core.StimuliRecorder;
import flattener.factories.DaikonComponentsFactory;
import flattener.flatteners.ObjectFlattener;

public class IoChecker {	
	
	private static Hashtable<String,IoModelIterator> ioModelsEnter = new Hashtable<String, IoModelIterator>();
	private static Hashtable<String,IoModelIterator> ioModelsExit = new Hashtable<String, IoModelIterator>();
	
	private interface MethodCallType {
		
	}
	
	private static class EnterMethod implements MethodCallType{
		
	}
	
	private static class ExitMethod implements MethodCallType {
		
	}	
	
	protected static final MethodCallType ENTER = new EnterMethod();
	protected static final MethodCallType EXIT = new ExitMethod();
	
	public static void checkEnter( String methodSignature, Object[] parameters ){
		
		IOMemoryRegistry.getInstance().upLevel();
		
		IoModelIterator it;

		try {
			if ( ioModelsEnter.containsKey(methodSignature) ){
				it = ioModelsEnter.get(methodSignature);
				it.reset();
			} else {
				ModelsFetcher mf = ModelsFetcherFactoy.getModelsFetcher();
				if ( mf.ioModelEnterExist(methodSignature) ){
					it = mf.getIoModelIteratorEnter(methodSignature);
					ioModelsEnter.put(methodSignature, it);
				}else {
					it = new CollectionIoModelIterator(new ArrayList<String>(0) );
					ioModelsEnter.put(methodSignature, it );
				}
			}

			while ( it.hasNext() )
				___processExpression( (String)it.next(), parameters, null, methodSignature, ENTER );

		} catch (ModelsFetcherException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
				
		
	}
	
	public static void checkExit( String methodSignature, Object[] parameters ){

		IoModelIterator it;

		try {
			if ( ioModelsExit.containsKey(methodSignature) ){
				it = ioModelsExit.get(methodSignature);
				it.reset();
			} else {
				ModelsFetcher mf = ModelsFetcherFactoy.getModelsFetcher();
				if ( mf.ioModelExitExist(methodSignature) ){
					it = mf.getIoModelIteratorExit(methodSignature);
					ioModelsExit.put(methodSignature, it);
				}else {
					it = new CollectionIoModelIterator(new ArrayList<String>(0) );
					ioModelsExit.put(methodSignature, it );
				}
			}

			while ( it.hasNext() )
				___processExpression( (String)it.next(), parameters, null, methodSignature, EXIT );

		} catch (ModelsFetcherException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		IOMemoryRegistry.getInstance().downLevel();
	}

	public static void checkExit( String methodSignature, Object[] parameters, Object returnValue ){
		
		IoModelIterator it;
		
		try {
			if ( ioModelsExit.containsKey(methodSignature) ){
				it = ioModelsExit.get(methodSignature);
				it.reset();
			} else {
				ModelsFetcher mf = ModelsFetcherFactoy.getModelsFetcher();
				if ( mf.ioModelExitExist(methodSignature) ){
					it = mf.getIoModelIteratorExit(methodSignature);
					ioModelsExit.put(methodSignature, it);
				}else {
					it = new CollectionIoModelIterator(new ArrayList<String>(0) );
					ioModelsExit.put(methodSignature, it );
				}
			}

			while ( it.hasNext() )
				___processExpression( (String)it.next(), parameters, returnValue, methodSignature, EXIT );

		} catch (ModelsFetcherException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}


	private static void ___processExpression(String expression,
			Object[] argumentValues, Object returnValue, String signature, MethodCallType callType ) {
		//EvaluationRuntimeErrors.log("Processing : "+expression);

		try {
			
			boolean result = IoInvariantParser.evaluateExpression(expression,
					argumentValues, returnValue);
			if (!result) {
				ViolationsRecorder violationRecorder = RecorderFactory.getViolationRecorder();
				
				//EvaluationRuntimeErrors.log("VIOLATION "+signature+" "+expression);
			
				
				
				try {
					if ( callType.equals(ENTER))
						violationRecorder.recordIoViolationEnter( signature, expression, result, argumentValues, returnValue, Thread.currentThread().getStackTrace() );
					else {
//						get the original values
						HashMap origValues = IOMemoryRegistry.getInstance().getCurrentMethodsMap();
						violationRecorder.recordIoViolationExit( signature, expression, result, argumentValues, returnValue, Thread.currentThread().getStackTrace(), origValues );
					}

				} catch (RecorderException e) {
					e.printStackTrace();
				}

			}
		} catch (InvariantParseException ipe) {
			//System.err.println("Error parsing expression " + expression+" "+ipe.getMessage());
		} catch ( RuntimeException e ){
			//if ( ! e.getMessage().equals("ClassToIgnore"))
			//	throw e;
		}
	}
	
}
