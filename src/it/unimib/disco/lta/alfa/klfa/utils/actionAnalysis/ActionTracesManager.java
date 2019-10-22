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
import it.unimib.disco.lta.alfa.klfa.utils.componentAnalysis.ComponentTracesFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class ActionTracesManager extends DistinctTracesManager {

	private List<String> actions;
	private String curAction = null;
	
	public ActionTracesManager(File outDir,String prefix, String global, List<String> actions,boolean splitBevaioralSequences) {
		super(outDir,prefix,global,splitBevaioralSequences);
		this.actions = actions;
	}


	
	
	@Override
	protected KeyData getKeyElement(String component, String token) throws IOException {
		boolean actionChanged = false;
		for ( String action : actions ){
			if ( token.matches(action) ){
				if ( curAction != null ){
					ComponentTracesFile traceFile = getKeyElementTrace(curAction);
					traceFile.close();
				}
				curAction = token;
				actionChanged = true;
				break;
			}
		}
		return new KeyData(actionChanged,curAction);
	}

	public String getCurAction() {
		return curAction;
	}

	public void setCurAction(String curAction) {
		this.curAction = curAction;
	}

}
