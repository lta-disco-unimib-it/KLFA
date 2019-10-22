package traceReaders.normalized;

import java.io.File;

import conf.EnvironmentalSetter;

public class GKTailNormalizedTraceHandlerDB implements NormalizedTraceHandler {

	private File decls;
	private File dtrace;
	
	public GKTailNormalizedTraceHandlerDB() throws NormalizedTraceHandlerException{	
	
		//TODO: check if is a GKTail invariant generator?
		
		String declsDirName = EnvironmentalSetter.getInvariantGeneratorSettings().getProperty("normalizedTraceHandlerFile.declsDir");
		if ( declsDirName == null )
			throw new NormalizedTraceHandlerException("Property normalizedTraceHandlerFile.declsDir not set in InvariantGeneratorSettings file");
		
		decls = new File( declsDirName );
		
		String dtraceDirName = EnvironmentalSetter.getInvariantGeneratorSettings().getProperty("normalizedTraceHandlerFile.dtraceDir"); 
		if ( dtraceDirName == null )
			throw new NormalizedTraceHandlerException("Property normalizedTraceHandlerFile.dtraceDir not set in InvariantGeneratorSettings file");
		
		dtrace = new File ( dtraceDirName );

		decls.mkdirs();
	
		if ( ! decls.exists() )
			throw new NormalizedTraceHandlerException("cannot create " + decls);
		
		dtrace.mkdirs();
		if ( ! decls.exists() )
			throw new NormalizedTraceHandlerException("cannot create " + dtrace);

	}
	
	//FIXME: geterate exception
	public NormalizedInteractionTraceHandler getInteractionTraceHandler() {
		//return new GKTailNormalizedInteractionTraceHandlerDB(interaction);
		return null;
	}

	public NormalizedIoTraceHandler getIoTraceHandler( ) {
		return new GKTailNormalizedIoTraceHandlerDB(decls, dtrace);
	}
}
