package traceReaders.normalized;

import java.io.File;

/**
 * This class wraps a normalized interaction trace.
 * The behaviour of this class is managed by the NormalizedInteractionTraceMapper.
 *  
 * @author Fabrizio Pastore fabrizio.pastore AT gmail.com
 *
 */
public interface NormalizedInteractionTrace {
	
	/**
	 * Returns the name of the method this trace refers to
	 * 
	 * @return	the name of the method
	 */
	public String getMethodName();
	
	/**
	 * Returns the File on which the trace is stored. Since InferenceEngine work on files every normalizedTrace implementor
	 * must implement a method that generate a that file and return its file descriptor.
	 * 
	 * @return the file
	 */
	public File getTraceFile();

}
