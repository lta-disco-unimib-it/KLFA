package traceReaders.normalized;

import java.io.File;

public class NormalizedInteractionTraceFile implements NormalizedInteractionTrace {
	
	private String 	methodName;
	private File	traceFile;
	
	/**
	 * Constructor
	 * @param methodName	name of the method te trace refers to
	 * @param traceFile		file on wich the normalized trace is saved
	 */
	public NormalizedInteractionTraceFile ( String methodName, File traceFile ){
		this.methodName = methodName;
		this.traceFile = traceFile;
	}
	
	public String getMethodName() {
		return methodName;
	}
	
	public File getTraceFile(){
		return traceFile;
	}

}
