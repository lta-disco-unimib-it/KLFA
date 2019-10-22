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
package securityManager;

import java.security.Permission;

/**
 * This class contain a security manager taht allow BCT to access al private methods attributes.
 * This SM does not allow to change the current security manager or to create a new one.
 * 
 * TODO: study the effects of not allowing to create a new SM whan an application to this
 * 
 * TODO: more strict security permissions, this SM behave as no SM exists (except from the case of creation nd substitution
 * of SM.   
 * 
 * @author Fabrizio Pastore
 *
 */
public class BctSecurityManager extends SecurityManager {
	public  void checkPermission(Permission perm){
		if ( perm instanceof java.lang.RuntimePermission ){
			if ( perm.getName().equals("setSecurityManager") )
				throw new SecurityException("BctSecurityManager does not allow to change security manager itself");
			if ( perm.getName().equals("createSecurityManager") )
				throw new SecurityException("BctSecurityManager does not allow to create a new security manager");
		}
	}
}
