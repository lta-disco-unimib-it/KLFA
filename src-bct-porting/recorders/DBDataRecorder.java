package recorders;

import java.io.IOException;


import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import conf.ConfigurationSettings;

import flattener.core.Handler;
import flattener.core.StimuliRecorder;
import flattener.factories.DaikonComponentsFactory;
import flattener.writers.DBWriter;
import flattener.writers.DBWriterMeta;


import database.BeginEndExecMethod;
import database.Thread;
import database.DataLayerException;
import database.Method;


/**
 * Recorder that saves all informations on db.
 * 
 *
 */
public class DBDataRecorder implements DataRecorder {
	
	public DBDataRecorder() {
	}

	/*
	 * Initialization method, the passed recorderSettings must contain this fields: "databaseURI", "databaseUser", "databasePassword".
	 * 
	 * @see recorders.DataRecorder#init(conf.RecorderSettings)
	 */
	
	public void init(ConfigurationSettings opts){
	}
	
	public void recordIoEnter( String methodSignature, Handler[] parametersHandlers) throws RecorderException {
		recordIo( ":::ENTER", methodSignature, parametersHandlers, null, false );
	}

	public void recordIoExit(String methodSignature, Handler[] parametersHandlers) throws RecorderException {
		recordIo( ":::EXIT1", methodSignature, parametersHandlers, null, false );
	}

	public void recordIoExit(String methodSignature, Handler[] parametersHandlers, Handler returnValueHandler) throws RecorderException {
		recordIo( ":::EXIT1", methodSignature, parametersHandlers, returnValueHandler, true );
	}

	public void recordIo( String state, String methodSignature, Handler[] parametersHandlers, Handler returnValueHandler, boolean writeReturn) throws RecorderException {
		//DaikonComponentsFactory daikonFactory = new DaikonComponentsFactory();
		//StimuliRecorder stimuliRecorder = daikonFactory.getStimuliRecorder();
		StimuliRecorder stimuliRecorder = FlattenerAssemblerFactory.getAssembler().getStimuliRecorder();
		DBWriter writer;
		String dbTable = "Registration"; //field passed to DBWriter in order to specifies the db table that must be written
		try {
			writer = new DBWriter("\n"+methodSignature+state+"\n", dbTable);
			stimuliRecorder.setWriter(writer);
			
			for ( int i = 0; i < parametersHandlers.length; ++i ){
				stimuliRecorder.record( parametersHandlers[i].getData() );
			}
			
			if ( writeReturn )
				stimuliRecorder.record( returnValueHandler.getData() );

			writer.close();
			
		} catch (IOException e) {
			throw new RecorderException(e.getMessage());
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new RecorderException(e.getMessage());
		}
		
	}
	
	public void recordInteractionEnter(String methodSignature, long threadId ) throws RecorderException {
		recordInteraction(methodSignature, "B#", threadId);
	}

	public void recordInteractionExit(String methodSignature, long threadId ) throws RecorderException {
		recordInteraction(methodSignature, "E#", threadId);
	}

	private void recordInteraction(String methodSignature, String methodState, long threadId ) throws RecorderException {
	    //System.out.println("Recording "+methodSignature+" "+methodState+" "+threadId);
		try {
	    	Method.insert(methodSignature);
	    } catch (DataLayerException e) {
	    	e.printStackTrace();
	    }
		try {
			BeginEndExecMethod.insert(methodSignature, methodState, threadId);
		} catch (DataLayerException e) {
			e.printStackTrace();
		}
		try {
			Thread.insert(threadId);
		} catch (DataLayerException e) {
			e.printStackTrace();
		}
	}

	public synchronized void recordIoInteractionEnter(String methodSignature, Handler[] parametersHandlers, long threadId) throws RecorderException {
		
		recordInteraction(methodSignature, "B#", threadId);
		recordIo( ":::ENTER", methodSignature, parametersHandlers, null, false );
	}

	public synchronized void recordIoInteractionExit(String methodSignature, Handler[] parametersHandlers, long threadId) throws RecorderException {
		
		recordInteraction(methodSignature, "E#", threadId);
		recordIo( ":::EXIT1", methodSignature, parametersHandlers, null, false );		
	}

	public synchronized void recordIoInteractionExit(String methodSignature, Handler[] parametersHandlers, Handler returnValueHandler, long threadId) throws RecorderException {
		
		recordInteraction(methodSignature, "E#", threadId);
		recordIo( ":::EXIT1", methodSignature, parametersHandlers, returnValueHandler, true );
	}

	public void recordInteractionEnterMeta(String methodSignature, long threadId, String metaInfo) throws RecorderException {
		throw new RecorderException("Not implemented for DBDataRecorder");
	}

	public void recordInteractionExitMeta(String methodSignature, long threadId, String metaInfo) throws RecorderException {
		throw new RecorderException("Not implemented for DBDataRecorder");
	}

	public void recordIoEnterMeta(String methodSignature, Handler[] parameters, String metaInfo) throws RecorderException {
		throw new RecorderException("Not implemented for DBDataRecorder");
	}

	public void recordIoExitMeta(String methodSignature, Handler[] parameters, String metaInfo) throws RecorderException {
		throw new RecorderException("Not implemented for DBDataRecorder");
	}

	public void recordIoExitMeta(String methodSignature, Handler[] parameters, Handler returnValue, String metaInfo) throws RecorderException {
		throw new RecorderException("Not implemented for DBDataRecorder");
	}

	public void recordIoInteractionEnterMeta(String methodSignature, Handler[] parametersHandlers, long threadId, String metaInfo) throws RecorderException {
		recordInteraction(methodSignature, "B#", threadId);
		recordIoMeta( ":::ENTER", methodSignature, parametersHandlers, null, false, metaInfo );
	}

	public void recordIoInteractionExitMeta(String methodSignature, Handler[] parametersHandlers, long threadId, String metaInfo) throws RecorderException {
		recordInteraction(methodSignature, "E#", threadId);
		recordIoMeta( ":::EXIT1", methodSignature, parametersHandlers, null, false, metaInfo );
	}

	public void recordIoInteractionExitMeta(String methodSignature, Handler[] parametersHandlers, Handler returnValueHandler, long threadId, String metaInfo) throws RecorderException {
		recordInteraction(methodSignature, "E#", threadId);
		recordIoMeta( ":::EXIT1", methodSignature, parametersHandlers, returnValueHandler, true, metaInfo );
	}

	private void recordIoMeta(String state, String methodSignature, Handler[] parametersHandlers, Handler returnValueHandler, boolean writeReturn, String metaInfo) throws RecorderException {
		StimuliRecorder stimuliRecorder = FlattenerAssemblerFactory.getAssembler().getStimuliRecorder();
		DBWriter writer;
		String dbTable = "Registration"; //field passed to DBWriter in order to specifies the db table that must be written
		try {
			writer = new DBWriterMeta("\n"+methodSignature+state+"\n", dbTable, metaInfo);
			stimuliRecorder.setWriter(writer);
			
			for ( int i = 0; i < parametersHandlers.length; ++i ){
				stimuliRecorder.record( parametersHandlers[i].getData() );
			}
			
			if ( writeReturn )
				stimuliRecorder.record( returnValueHandler.getData() );

			writer.close();
			
		} catch (IOException e) {
			throw new RecorderException(e.getMessage());
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new RecorderException(e.getMessage());
		}
	}
}

