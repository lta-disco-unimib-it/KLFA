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
package traceReaders.normalized;

import java.io.File;

import tools.TraceRepository;

import conf.EnvironmentalSetter;
import dfmaker.core.Superstructure;

public class NormalizedTraceHandlerFile implements NormalizedTraceHandler {
	private File decls;
	private File dtrace;
	private File interaction;
	
	public NormalizedTraceHandlerFile() throws NormalizedTraceHandlerException{
		
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
		return new NormalizedInteractionTraceHandlerFile( interaction );
	}

	public NormalizedIoTraceHandler getIoTraceHandler( ) {
		return new NormalizedIoTraceHandlerFile( decls, dtrace);
	}



}
