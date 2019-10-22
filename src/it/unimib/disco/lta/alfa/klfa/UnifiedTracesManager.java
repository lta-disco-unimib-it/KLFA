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
package it.unimib.disco.lta.alfa.klfa;

import it.unimib.disco.lta.alfa.klfa.utils.componentAnalysis.ComponentTracesFile;

import java.io.File;
import java.io.IOException;

public class UnifiedTracesManager extends DistinctTracesManager {

	public UnifiedTracesManager(File outDir,String prefix, String globalTraceName, boolean splitBehavioralSequences ) {
		super(outDir,prefix, globalTraceName, splitBehavioralSequences);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected KeyData getKeyElement(String component, String token)
			throws IOException {
		return new KeyData(false,component);
	}
	
	@Override
	public void addToken(String component,String token,int line) throws IOException{
	
		String keyElement = getKeyElement(component,token).componentName;
		
		if ( keyElement == null ){
			return;
		}
		
		
		ComponentTracesFile componentTrace = getKeyElementTrace(global);
		componentTrace.addToken(token,line);
		
	}
}
