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
package it.unimib.disco.lta.alfa.klfa.utils.actionAnalysis;

import it.unimib.disco.lta.alfa.klfa.DistinctTracesManager;
import it.unimib.disco.lta.alfa.klfa.TraceManagerProvider;

import java.io.File;
import java.util.Map;

public class ActionLineTraceManagerProvider implements TraceManagerProvider {

	private Map<Integer,String> actionsLines;
	private boolean splitBahavioralSequences;

	public ActionLineTraceManagerProvider(Map<Integer, String> actionsLines, boolean splitBahavioralSequences ) {
		this.actionsLines = actionsLines;
		this.splitBahavioralSequences = splitBahavioralSequences;
	}

	public DistinctTracesManager getTraceManager(File outputDir, String prefix,
			String name) {
		return new ActionLineTraceManager(outputDir,prefix,"GLOBAL",actionsLines,splitBahavioralSequences);
	}

}
