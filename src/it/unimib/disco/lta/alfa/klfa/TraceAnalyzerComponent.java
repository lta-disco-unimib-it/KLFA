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


import it.unimib.disco.lta.alfa.klfa.utils.componentAnalysis.ComponentTracesManager;

import java.io.File;


/**
 * This class read a csv trace file and builds separated FSA for different components. Components are selected on the basis of their name.
 * Trace files must be in th eform COMPONENT,Symbol.
 * 
 * @author Fabrizio Pastore fabrizio.pastore AT gmail.com
 *
 */
public class TraceAnalyzerComponent extends TraceAnalyzer {

	public static class TraceManagerProviderComponent implements TraceManagerProvider{

		private boolean splitBehavioralSequences;

		public TraceManagerProviderComponent(boolean splitBehavioralSequences) {
			this.splitBehavioralSequences = splitBehavioralSequences;
		}

		public DistinctTracesManager getTraceManager(File outputDir,String prefix, String name) {
			return new ComponentTracesManager(outputDir,prefix,name,splitBehavioralSequences);
		}
	}
	
	public TraceAnalyzerComponent(boolean splitBehavioralSequences) {
		super(new TraceManagerProviderComponent(splitBehavioralSequences));
	}







}
