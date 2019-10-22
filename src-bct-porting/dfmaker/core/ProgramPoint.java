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
package dfmaker.core;

public class ProgramPoint {
	public static int ENTRY_POINT = 0;
	public static int EXIT_POINT = 1;
	public static int OBJECT_POINT = 1;
	
	private int programPointType;
	private String programPointValue = null;
	
	/**
	 * @param programPointType
	 */
	ProgramPoint(int programPointType, String programPointValue) {
		this.programPointType = programPointType;
		this.programPointValue = programPointValue; 
	}
	
	/**
	 * @return Program point type.
	 */
	public int getProgramPointType() {
		return this.programPointType;
	}
	
	/**
	 * @return Program point value
	 */
	public String getProgramPointValue() {
		return this.programPointValue;
	}

}
