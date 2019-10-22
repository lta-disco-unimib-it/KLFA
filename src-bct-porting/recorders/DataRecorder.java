package recorders;

import conf.ConfigurationSettings;
import flattener.core.Handler;

/**
 * Interface that all data recorders must implement.
 * If a recorder does not support a method it must implement it releasing an exception.
 * 
 * @author Fabrizio Pastore fabrizio.pastore AT gmail.com
 *
 */
public interface DataRecorder {
	
	public void recordIoEnter( String methodSignature, Handler[] parameters ) throws RecorderException;
	
	public void recordIoExit( String methodSignature, Handler[] parameters ) throws RecorderException;
	
	public void recordIoExit( String methodSignature, Handler[] parameters, Handler returnValue ) throws RecorderException;
	
	public void recordInteractionEnter( String methodSignature, long threadId ) throws RecorderException;
	
	public void recordInteractionExit( String methodSignature, long threadId  ) throws RecorderException;

	public void init( ConfigurationSettings opts );
	
	public void recordIoInteractionEnter(String methodSignature, Handler[] parameters, long threadId) throws RecorderException;

	public void recordIoInteractionExit(String methodSignature, Handler[] parameters, long threadId) throws RecorderException;

	public void recordIoInteractionExit(String methodSignature, Handler[] parameters, Handler returnValue, long threadId) throws RecorderException;

	/* META DATA */
	
	public void recordIoEnterMeta( String methodSignature, Handler[] parameters, String metaInfo ) throws RecorderException;
	
	public void recordIoExitMeta( String methodSignature, Handler[] parameters, String metaInfo ) throws RecorderException;
	
	public void recordIoExitMeta( String methodSignature, Handler[] parameters, Handler returnValue, String metaInfo ) throws RecorderException;
	
	public void recordInteractionEnterMeta( String methodSignature, long threadId, String metaInfo ) throws RecorderException;
	
	public void recordInteractionExitMeta( String methodSignature, long threadId, String metaInfo  ) throws RecorderException;
	
	public void recordIoInteractionEnterMeta(String methodSignature, Handler[] parameters, long threadId, String metaInfo) throws RecorderException;

	public void recordIoInteractionExitMeta(String methodSignature, Handler[] parameters, long threadId, String metaInfo) throws RecorderException;

	public void recordIoInteractionExitMeta(String methodSignature, Handler[] parameters, Handler returnValue, long threadId, String metaInfo) throws RecorderException;

}
