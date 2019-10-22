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
package database;

import java.sql.SQLException;

/**
 * Exception Thrown when an error occurs during 
 * data manipulation or extraction
 * 
 * @author Trucco Enrico
 *
 */
public class DataLayerException extends Exception {

	private static final long serialVersionUID = -1930025238946607542L;

	public DataLayerException(String arg0) {
		super(arg0);
	}

	public DataLayerException(String message, Throwable e) {
		super(message,e);
	}

}
