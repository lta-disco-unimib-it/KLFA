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

import java.io.File;

public class ObjectFlattener {
    private static String objectFlattenerPropertiesFileName = "flattener"+File.separator+"conf"+File.separator+"objectFlattener.conf";
    private static String inspectorRecognizersListFileName = "flattener"+File.separator+"conf"+File.separator+"inspectorRecognizers.list";
    private static String classesToIgnoreListFileName = "flattener"+File.separator+"conf"+File.separator+"classesToIgnore.list";
    private static String pluginsListFileName = "flattener"+File.separator+"conf"+File.separator+"plugins.list";
    private static String fieldsFiltersFileName = "flattener"+File.separator+"conf"+File.separator+"fieldsFilter.conf";
    
	public static String getClassesToIgnoreListFileName() {
		return classesToIgnoreListFileName;
	}
	public static void setClassesToIgnoreListFileName(
			String classesToIgnoreListFileName) {
		ObjectFlattener.classesToIgnoreListFileName = classesToIgnoreListFileName;
	}
	public static String getInspectorRecognizersListFileName() {
		return inspectorRecognizersListFileName;
	}
	public static void setInspectorRecognizersListFileName(
			String inspectorRecognizersListFileName) {
		ObjectFlattener.inspectorRecognizersListFileName = inspectorRecognizersListFileName;
	}
	public static String getObjectFlattenerPropertiesFileName() {
		return objectFlattenerPropertiesFileName;
	}
	public static void setObjectFlattenerPropertiesFileName(
			String objectFlattenerPropertiesFileName) {
		ObjectFlattener.objectFlattenerPropertiesFileName = objectFlattenerPropertiesFileName;
	}
	public static String getPluginsListFileName() {
		return pluginsListFileName;
	}
	public static void setPluginsListFileName(String pluginsListFileName) {
		ObjectFlattener.pluginsListFileName = pluginsListFileName;
	}
	public static String getFieldsFilterFileName() {
		return fieldsFiltersFileName;
	}
	public static void setFieldsFilterFileName(String fieldsFiltersFileName) {
		ObjectFlattener.fieldsFiltersFileName = fieldsFiltersFileName;
	}
	
}
