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

import java.util.Properties;

/**
 * @author Davide Lorenzoli
 *
 */
public class SimplifyTheoremProverSettings {

	private String temporaryDir;
	private String executableFile;
	
	public SimplifyTheoremProverSettings(Properties p) {
		temporaryDir = p.getProperty("temporaryDir");
		executableFile = p.getProperty("executableFile");
	}

	/**
	 * @return the executableFile
	 */
	public String getExecutableFile() {
		return executableFile;
	}

	/**
	 * @return the temporaryDir
	 */
	public String getTemporaryDir() {
		return temporaryDir;
	}	
}
