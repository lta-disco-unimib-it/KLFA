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
package util.componentsDeclaration;

import java.util.ArrayList;
import java.util.List;


public class Component {
	
	protected List<MatchingRule> rules = new ArrayList<MatchingRule>();
	private String name;
	
	public Component( String name, List<MatchingRule> rules ){
		this( name );
		this.rules.addAll(rules);
	}

	public Component( String name ) {
		this.name = name;
	}
	
	public void addRule(MatchingRule rule) {
		rules.add(rule);
	}
	
	public boolean acceptClass( String packageName, String className ){
		System.out.println("Component.acceptClass : "+packageName+" "+className);
		for ( MatchingRule rule : rules ){
			if ( rule.acceptClass(packageName, className) ){
				return true;
			} else if ( rule.rejectClass(packageName, className) ) {
				return false;
			}
		}
		return false;
	}

	public String getName() {
		return name;
	}

	public List<MatchingRule> getRules() {
		return rules;
	}

	public boolean acceptFunction(String packageName, String functionName) {
		return acceptMethod( packageName, "", functionName );
	}

	public boolean acceptMethod(String packageName, String className, String methodName) {
		for ( MatchingRule rule : rules ){
			if ( rule.acceptMethod(packageName, className, methodName) ){
				return true;
			} else if ( rule.rejectMethod(packageName, className, methodName ) ) {
				return false;
			}
		}
		return false;
	}
}
