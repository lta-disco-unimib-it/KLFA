package traceReaders.normalized;

import conf.EnvironmentalSetter;
import conf.InvariantGeneratorSettings;
import dfmaker.core.Superstructure;

public class TraceHandlerFactory {
	private static NormalizedTraceHandler nth = null;
	
	private static NormalizedTraceHandler getNormalizedTraceHandler() throws InstantiationException, IllegalAccessException{
		if ( nth == null ){
			
			InvariantGeneratorSettings iGS = EnvironmentalSetter.getInvariantGeneratorSettings();
			System.out.println("iGS "+iGS.getNormalizedTraceHandlerType());
			Class nthc = iGS.getNormalizedTraceHandlerType();
			nth = (NormalizedTraceHandler) nthc.newInstance();
			
		}
		return nth; 
	}
	
	public static NormalizedIoTraceHandler getNormalizedIoTraceHandler() throws NormalizedTraceHandlerException {
		
		NormalizedTraceHandler nth;
		try {
			nth = (NormalizedTraceHandler) getNormalizedTraceHandler();
			return nth.getIoTraceHandler();
		} catch ( Exception e) {
			throw new NormalizedTraceHandlerException("Cannot initialize NormalizedIoTraceHandler "+e.getMessage());
		} 

	}

	public static NormalizedInteractionTraceHandler getNormalizedInteractionTraceHandler() throws NormalizedTraceHandlerException {
		NormalizedTraceHandler nth;
		
		try {
			nth = (NormalizedTraceHandler) getNormalizedTraceHandler();
		
			return nth.getInteractionTraceHandler();
			
		} catch ( Exception e) {
			throw new NormalizedTraceHandlerException("Cannot initialize NormalizedInteractionTraceHandler");
		}
	}

}
