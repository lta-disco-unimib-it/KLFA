/*******************************************************************************
 *    Copyright 2019 Fabrizio Pastore, Leonardo Mariani
 *   
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 *******************************************************************************/
package recorders;

import java.lang.reflect.InvocationTargetException;
import flattener.core.Flattener;
import flattener.core.Handler;

/**
 * This class is a Facade for the logging functionalities (io,Interactions, mixed).
 * 
 * Retrieve the correct Flattener to flatten parameters and call the user-specified DataRecorder to record data (io, interaction or both).
 * 
 * 
 * @author Fabrizio Pastore fabrizio.pastore AT gmail.com
 *
 */
public class LoggingActionRecorder {


	public static void logIoEnter(  String methodSignature, Object[] parameters ) {        
		Handler[] parametersHandlers = flattenParameters(parameters);
		
		DataRecorder recorder = RecorderFactory.getLoggingRecorder();
		
		try {
			recorder.recordIoEnter(methodSignature,parametersHandlers);
		} catch ( RecorderException e) {
			e.printStackTrace();
		}
	}

	public static void logIoExit(  String methodSignature, Object[] parameters ) {        
		Handler[] parametersHandlers = flattenParameters(parameters);
		
		DataRecorder recorder = RecorderFactory.getLoggingRecorder();
		try {
			recorder.recordIoExit(methodSignature,parametersHandlers);
		} catch ( RecorderException e) {
			e.printStackTrace();
		}
	}

	public static void logIoExit(  String methodSignature, Object[] parameters, Object returnValue ) {        
		Handler[] parametersHandlers = flattenParameters(parameters);
		
		//flatten return value
		Handler	  returnValueHandler = flattenParameter("returnValue", returnValue);

		DataRecorder recorder = RecorderFactory.getLoggingRecorder();

		try {
			recorder.recordIoExit(methodSignature,parametersHandlers, returnValueHandler);
		} catch ( RecorderException e) {
			e.printStackTrace();
		}

	}

	private	static Handler[]	flattenParameters( Object[] parameters ){
		Handler[] parametersHandlers = new Handler[parameters.length];

		for (int i=0; i<parameters.length; i++) {
			parametersHandlers[i] = flattenParameter("parameter["+i+"]", parameters[i]);
		}

		return parametersHandlers;
	}

	private static Handler flattenParameter( String rootName, Object parameter ){
		//ObjectFlattener of = new ObjectFlattener();					

		// Create the factory
				
		Flattener of = FlattenerAssemblerFactory.getAssembler().getFlattener(rootName);

		//smash the first parameter

		try {
			of.doSmash(parameter);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return of.getDataHandler();

	}

	public static void logInteractionEnter( String methodSignature, long threadId ){
		DataRecorder recorder = RecorderFactory.getLoggingRecorder();
		
		try {
			recorder.recordInteractionEnter( methodSignature, threadId );
		} catch (RecorderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void logInteractionExit( String methodSignature, long threadId ){
		DataRecorder recorder = RecorderFactory.getLoggingRecorder();
		
		try {
			recorder.recordInteractionExit( methodSignature, threadId );
		} catch (RecorderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void logIoInteractionEnter(String methodSignature, Object[] parameters, long threadId) {
		Handler[] parametersHandlers = flattenParameters(parameters);
		//System.out.println("ENTER "+methodSignature);
		DataRecorder recorder = RecorderFactory.getLoggingRecorder();
		try {
			recorder.recordIoInteractionEnter(methodSignature, parametersHandlers, threadId);
		} catch (RecorderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}

	public static void logIoInteractionExit(String methodSignature, Object[] parameters, long threadId) {
		Handler[] parametersHandlers = flattenParameters(parameters);
		
		DataRecorder recorder = RecorderFactory.getLoggingRecorder();
		try {
			recorder.recordIoInteractionExit(methodSignature,parametersHandlers, threadId);
		} catch (RecorderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}

	public static void logIoInteractionExit(String methodSignature, Object[] parameters, Object returnValue, long threadId) {
		Handler[] parametersHandlers = flattenParameters(parameters);
		
		//flatten return value
		Handler	  returnValueHandler = flattenParameter("returnValue", returnValue);

		DataRecorder recorder = RecorderFactory.getLoggingRecorder();

		try {
			recorder.recordIoInteractionExit(methodSignature,parametersHandlers, returnValueHandler, threadId);
		} catch (RecorderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void logIoInteractionEnterMeta(String methodSignature, Object[] parameters, long threadId,String metaInfo) {
		Handler[] parametersHandlers = flattenParameters(parameters);
		
		DataRecorder recorder = RecorderFactory.getLoggingRecorder();
		try {
			recorder.recordIoInteractionEnterMeta(methodSignature, parametersHandlers, threadId, metaInfo);
		} catch (RecorderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}

	public static void logIoInteractionExitMeta(String methodSignature, Object[] parameters, long threadId, String metaInfo) {
		Handler[] parametersHandlers = flattenParameters(parameters);
		
		DataRecorder recorder = RecorderFactory.getLoggingRecorder();
		try {
			recorder.recordIoInteractionExitMeta(methodSignature,parametersHandlers, threadId, metaInfo);
		} catch (RecorderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}

	public static void logIoInteractionExitMeta(String methodSignature, Object[] parameters, Object returnValue, long threadId, String metaInfo) {
		Handler[] parametersHandlers = flattenParameters(parameters);
		
		//flatten return value
		Handler	  returnValueHandler = flattenParameter("returnValue", returnValue);

		DataRecorder recorder = RecorderFactory.getLoggingRecorder();

		try {
			recorder.recordIoInteractionExitMeta(methodSignature,parametersHandlers, returnValueHandler, threadId, metaInfo);
		} catch (RecorderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void logIoEnterMeta(String methodSignature, Object[] parameters, String metaInfo) {
		Handler[] parametersHandlers = flattenParameters(parameters);
		
		DataRecorder recorder = RecorderFactory.getLoggingRecorder();
		
		try {
			recorder.recordIoEnterMeta(methodSignature,parametersHandlers,metaInfo);
		} catch (RecorderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void logIoExitMeta(  String methodSignature, Object[] parameters, String metaInfo ) {        
		Handler[] parametersHandlers = flattenParameters(parameters);
		
		DataRecorder recorder = RecorderFactory.getLoggingRecorder();
		try {
			recorder.recordIoExitMeta(methodSignature,parametersHandlers,metaInfo);
		} catch (RecorderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void logIoExitMeta(  String methodSignature, Object[] parameters, Object returnValue, String metaInfo ) {        
		Handler[] parametersHandlers = flattenParameters(parameters);
		
		//flatten return value
		Handler	  returnValueHandler = flattenParameter("returnValue", returnValue);

		DataRecorder recorder = RecorderFactory.getLoggingRecorder();

		try {
			recorder.recordIoExitMeta(methodSignature,parametersHandlers, returnValueHandler, metaInfo);
		} catch (RecorderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public static void logInteractionEnterMeta( String methodSignature, long threadId, String metaInfo ){
		DataRecorder recorder = RecorderFactory.getLoggingRecorder();
		
		try {
			recorder.recordInteractionEnterMeta( methodSignature, threadId, metaInfo );
		} catch (RecorderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void logInteractionExitMeta( String methodSignature, long threadId, String metaInfo ){
		DataRecorder recorder = RecorderFactory.getLoggingRecorder();
		
		try {
			recorder.recordInteractionExitMeta( methodSignature, threadId, metaInfo );
		} catch (RecorderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
