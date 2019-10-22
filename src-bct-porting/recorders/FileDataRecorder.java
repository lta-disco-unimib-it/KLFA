package recorders;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import traceReaders.raw.FileIoTrace;
import traceReaders.raw.FileIoTraceRepository;
import traceReaders.raw.FileReaderException;


import conf.ConfigurationSettings;
import flattener.core.Handler;
import flattener.core.StimuliRecorder;

/**
 * Recorder that saves all informations on file.
 * These informations are saved in the directory indicated by the RecorderSettings passed to the init method.
 * Models are then saved under 01-logs: 01-logs/ioInvariantLogs 01-log/interactionInvariantLogs.
 * InteractionsLog are savend in a file named "Logs For Thread #TID.txt" where #TID is the thread id.
 * IoLog are saved with the name of the method that belong to. 
 * 
 * TODO: change the naming of IoLog file name, we need a shorter name.
 * 
 * @author Fabrizio Pastore fabrizio.pastore AT gmail.com
 *
 */
public class FileDataRecorder implements DataRecorder {
	private File ioLogDir;
	private File interactionLogDir;
	private FileIoTraceRepository repository;
	
	private static interface State{
		public static final String ENTER = ":::ENTER";
		public static final String EXIT = ":::EXIT1";
	}
	

	public interface Options {
		final static String loggingDataDir = "loggingDataDir";
	}
	
	
	public FileDataRecorder ( ){
	}
	

	/*
	 * Initialization method, the passed recorderSettings must contain a "loggingDataDir" field.
	 * 
	 * @see recorders.DataRecorder#init(conf.RecorderSettings)
	 */
	public void init( ConfigurationSettings opts ){
		String dataDir = opts.getProperty(Options.loggingDataDir);
		setLogDir(dataDir);
		repository = new FileIoTraceRepository(ioLogDir);
	}
	
	
	private void recordMetaInfo( Writer writer, String metaInfo ) throws IOException{
		
		String message =  metaInfo+"\n#\n";
					
		writer.write(message);
		
	}
	
	private synchronized void recordIoMetaInfo( String state, String methodSignature, String metaInfo ) throws RecorderException{
		//File file = new File ( ioLogDir , methodSignature+".meta" );
		
		
		try {
			FileIoTrace trace = repository.getRawTrace(methodSignature);
			Writer writer = new FileWriter( trace.getMetaTraceFile(), true );
			recordMetaInfo( writer , metaInfo);
			writer.close();
		} catch (IOException e) {
			
		} catch (FileReaderException e) {
			throw new RecorderException(e.getMessage());
		}
	}
	
	private void recordInteractionMetaInfo( String methodSignature, String metaInfo, long threadId ) throws RecorderException{
		File file = new File ( interactionLogDir, "Logs For Thread " + threadId + ".meta" );

		try {
			Writer writer = new FileWriter( file, true );
			recordMetaInfo(writer, metaInfo);
			writer.close();
		} catch (IOException e) {
			throw new RecorderException(e.getMessage());
		}

	}
	
	public void setLogDir( String dataDir ){
		ioLogDir = new File(dataDir+File.separator+"ioInvariantLogs");
		interactionLogDir = new File(dataDir+File.separator+"interactionInvariantLogs");
		
		ioLogDir.mkdirs();
		interactionLogDir.mkdir();
		
	}
	
	public void recordIoEnter( String methodSignature, Handler[] parametersHandlers) throws RecorderException {
		recordIo( State.ENTER, methodSignature, parametersHandlers, null, false );
	}


	public void recordIoExit(String methodSignature, Handler[] parametersHandlers) throws RecorderException {
		recordIo( State.EXIT, methodSignature, parametersHandlers, null, false );
	}

	public void recordIoExit(String methodSignature, Handler[] parametersHandlers, Handler returnValueHandler) throws RecorderException {
		recordIo( State.EXIT, methodSignature, parametersHandlers, returnValueHandler, true );
	}

	public void recordIoEnterMeta( String methodSignature, Handler[] parametersHandlers, String metaInfo ) throws RecorderException {
		recordIo( State.ENTER, methodSignature, parametersHandlers, null, false );
		recordIoMetaInfo( State.ENTER, methodSignature, metaInfo);
	}


	public void recordIoExitMeta(String methodSignature, Handler[] parametersHandlers, String metaInfo) throws RecorderException {
		recordIo( State.EXIT, methodSignature, parametersHandlers, null, false );
		recordIoMetaInfo( State.EXIT, methodSignature, metaInfo );
	}

	public void recordIoExitMeta(String methodSignature, Handler[] parametersHandlers, Handler returnValueHandler, String metaInfo ) throws RecorderException {
		recordIo( State.EXIT, methodSignature, parametersHandlers, returnValueHandler, true );
		recordIoMetaInfo( State.EXIT, methodSignature, metaInfo );
	}
	
	private synchronized void recordIo( String state, String methodSignature, Handler[] parametersHandlers, Handler returnValueHandler, boolean writeReturn) throws RecorderException {
		try {
			FileIoTrace trace = repository.getRawTrace(methodSignature);

			StimuliRecorder stimuliRecorder = FlattenerAssemblerFactory.getAssembler().getStimuliRecorder();
			Writer writer;


			writer = new FileWriter( trace.getTraceFile(), true);

			writer.write("\n"+methodSignature+state+"\n");


			stimuliRecorder.setWriter(writer);
			
			for ( int i = 0; i < parametersHandlers.length; ++i ){
				stimuliRecorder.record( parametersHandlers[i].getData() );
			}
			
			if ( writeReturn )
				stimuliRecorder.record( returnValueHandler.getData() );
			
			writer.close();
			
		} catch (IOException e) {
			e.printStackTrace();
			throw new RecorderException("Error writing trace for "+methodSignature+ ": \n"+e.getMessage());
			
		} catch (Exception e) {
			
			throw new RecorderException("Error writing trace for "+methodSignature+ ": \n"+e.getMessage());
		}

	
		
	}

	public void recordInteractionEnter(String methodSignature, long threadId ) throws RecorderException {
		recordInteraction( methodSignature+"B#", threadId);
	}

	public void recordInteractionExit(String methodSignature, long threadId ) throws RecorderException {
		recordInteraction( methodSignature+"E#", threadId);
	}

	public void recordInteractionEnterMeta(String methodSignature, long threadId, String metaInfo ) throws RecorderException {
		recordInteraction( methodSignature+"B#", threadId);
		recordInteractionMetaInfo( methodSignature, metaInfo, threadId);
	}

	public void recordInteractionExitMeta(String methodSignature, long threadId, String metaInfo ) throws RecorderException {
		recordInteraction( methodSignature+"E#", threadId);
		recordInteractionMetaInfo( methodSignature, metaInfo, threadId);
	}
	
	private synchronized void recordInteraction(String methodSignatureState, long threadId ) throws RecorderException {
		String file = interactionLogDir+File.separator+"Logs For Thread " + threadId + ".int";
		
		try {
			//open an output stream in append mode
			FileOutputStream fos = new FileOutputStream( file, true );
			fos.write(methodSignatureState.getBytes());			
	        fos.close();
		} catch (FileNotFoundException e) {
			throw new RecorderException("Error writing "+file+ ": \n"+e.getMessage());
		} catch (IOException e) {
			throw new RecorderException("Error writing "+file+ ": \n"+e.getMessage());
		}
		
	}

	public void recordIoInteractionEnter(String methodSignature, Handler[] parameters, long threadId) throws RecorderException {
		recordInteraction( methodSignature+"B#", threadId);
		recordIo( State.ENTER, methodSignature, parameters, null, false ); 
	}

	public void recordIoInteractionExit(String methodSignature, Handler[] parameters, long threadId) throws RecorderException {
		recordInteraction( methodSignature+"E#", threadId);
		recordIo( State.EXIT, methodSignature, parameters, null, false );	
	}

	public void recordIoInteractionExit(String methodSignature, Handler[] parameters, Handler returnValue, long threadId) throws RecorderException {
		recordInteraction( methodSignature+"E#", threadId);
		recordIo( State.EXIT, methodSignature, parameters, returnValue, true );
	}


	public File getInteractionLogDir() {
		return interactionLogDir;
	}


	public File getIoLogDir() {
		return ioLogDir;
	}


	public void recordIoInteractionEnterMeta(String methodSignature, Handler[] parameters, long threadId, String metaInfo) throws RecorderException {
		recordInteractionEnterMeta( methodSignature, threadId, metaInfo);
		recordIoEnterMeta( methodSignature, parameters,  metaInfo );
	}


	public void recordIoInteractionExitMeta(String methodSignature, Handler[] parameters, long threadId, String metaInfo) throws RecorderException {
		recordInteractionExitMeta( methodSignature, threadId, metaInfo);
		recordIoExitMeta( methodSignature, parameters,  metaInfo );
	}


	public void recordIoInteractionExitMeta(String methodSignature, Handler[] parameters, Handler returnValue, long threadId, String metaInfo) throws RecorderException {
		recordInteractionExitMeta( methodSignature, threadId, metaInfo);
		recordIoExitMeta( methodSignature, parameters,  returnValue, metaInfo );
	}
}
