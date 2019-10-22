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


public class MatchingRuleExclude extends MatchingRule {

	public MatchingRuleExclude(String packageExpr, String classExpr, String methodExpr) {
		super(packageExpr, classExpr, methodExpr);
	}

	@Override
	public boolean acceptMethod( String packageName, String className, String methodName ){
		return false;
	}

	@Override
	public boolean acceptClass(String packageName, String className) {
		return false;
	}

	@Override
	public boolean rejectClass(String packageName, String className) {
		return ! matchClass(packageName, className );
	}

	@Override
	public boolean rejectMethod(String packageName, String className, String methodName) {
		return ! matchMethod( packageName, className, methodName );
	}
}
