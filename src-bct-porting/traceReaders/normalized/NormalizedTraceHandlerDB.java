package traceReaders.normalized;

import java.io.File;

import conf.EnvironmentalSetter;

public class NormalizedTraceHandlerDB implements NormalizedTraceHandler {

	private File decls;
	private File dtrace;
	private File interaction;
	
	public NormalizedTraceHandlerDB() throws NormalizedTraceHandlerException{	
	
		String declsDirName = EnvironmentalSetter.getInvariantGeneratorSettings().getProperty("normalizedTraceHandlerFile.declsDir");
		if ( declsDirName == null )
			throw new NormalizedTraceHandlerException("Property normalizedTraceHandlerFile.declsDir not set in InvariantGeneratorSettings file");
		
		decls = new File( declsDirName );
		
		String dtraceDirName = EnvironmentalSetter.getInvariantGeneratorSettings().getProperty("normalizedTraceHandlerFile.dtraceDir"); 
		if ( dtraceDirName == null )
			throw new NormalizedTraceHandlerException("Property normalizedTraceHandlerFile.dtraceDir not set in InvariantGeneratorSettings file");
		
		dtrace = new File ( dtraceDirName );
		
		String interactionDirName = EnvironmentalSetter.getInvariantGeneratorSettings().getProperty("normalizedTraceHandlerFile.interactionDir");
		if ( interactionDirName == null )
			throw new NormalizedTraceHandlerException("Property normalizedTraceHandlerFile.interactionDir not set in InvariantGeneratorSettings file");
		
		interaction = new File ( interactionDirName );
		
		decls.mkdirs();
		
		if ( ! decls.exists() )
			throw new NormalizedTraceHandlerException("cannot create "+decls);
		
		dtrace.mkdirs();
		if ( ! decls.exists() )
			throw new NormalizedTraceHandlerException("cannot create "+dtrace);
	
		interaction.mkdirs();
		if ( ! interaction.exists() )
			throw new NormalizedTraceHandlerException("cannot create "+interaction);
	}
	
	
	public NormalizedInteractionTraceHandler getInteractionTraceHandler() {
		return new NormalizedInteractionTraceHandlerDB(interaction);
	}

	public NormalizedIoTraceHandler getIoTraceHandler( ) {
		return new NormalizedIoTraceHandlerDB(decls, dtrace);
	}
}