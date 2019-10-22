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
package it.unimib.disco.lta.alfa.dataTransformation.parserRules;


import java.util.List;


public class ParserRuleAction extends ParserRule {


	private List<String> actions;

	public ParserRuleAction(List<String> actions) {
		super(0, 1, ".*", ".*");
		this.actions = actions;
	}
	
	public boolean match ( String lineElements[] ){
	
		String token=lineElements[0];
		for ( String action : actions ){
			if ( token.matches(action) ){
				return true;
			}
		}
		return false;
	}
	


}
