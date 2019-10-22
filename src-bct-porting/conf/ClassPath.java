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
/*
 *
 * This class stores values for the environmental classpaths
 * 
 */
package conf;

public class ClassPath {
	private static String xmlParserPath;
	private static String daikonPath;
	
	public static String getDaikonPath() {
		return daikonPath;
	}
	public static void setDaikonPath(String daikonPath) {
		ClassPath.daikonPath = daikonPath;
	}
	public static String getXmlParserPath() {
		return xmlParserPath;
	}
	public static void setXmlParserPath(String xmlParserPath) {
		ClassPath.xmlParserPath = xmlParserPath;
	}
}
