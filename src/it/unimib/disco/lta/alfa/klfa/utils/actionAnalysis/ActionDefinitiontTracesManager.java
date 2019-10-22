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
import it.unimib.disco.lta.alfa.klfa.DistinctTracesManager.KeyData;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class ActionDefinitiontTracesManager extends DistinctTracesManager {

	private List<ActionDefinition> actionDefinitions;
	private String columnSeparator;
	private String curAction;

	public ActionDefinitiontTracesManager(File outDir, String prefix,
			String globalTraceName, boolean splitBehavioralSequences, List<ActionDefinition> actionDefinitions, String columnSeparator ) {
		super(outDir, prefix, globalTraceName, splitBehavioralSequences);
		this.actionDefinitions = actionDefinitions;
		this.columnSeparator = columnSeparator;
	}

	@Override
	protected KeyData getKeyElement(String component, String token)
			throws IOException {
		String[] cols = token.split(columnSeparator);
		boolean changed =false;
		for ( ActionDefinition action : actionDefinitions ){
			if ( action.isActionEvent(cols) ){
				curAction = action.getName();
				changed = true;
			}
		}
		return new KeyData(changed,curAction);
	}

}
