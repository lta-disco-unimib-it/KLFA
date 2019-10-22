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
package conf;

public class Daikon {

	private static double confidenceLevel;
	private static String operatorFile;
	
	public static double getConfidenceLevel() {
		return confidenceLevel;
	}
	public static void setConfidenceLevel(double confidenceLevel) {
		Daikon.confidenceLevel = confidenceLevel;
	}
	public static String getOperatorFile() {
		return operatorFile;
	}
	public static void setOperatorFile(String operatorFile) {
		Daikon.operatorFile = operatorFile;
	}
}
