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
/**
 * 
 */
package it.unimib.disco.lta.alfa.klfa.utils.componentAnalysis;

import java.io.File;

/**
 * This class represent Component execution traces
 * 
 * @author Fabrizio Pastore fabrizio.pastore AT gmail.com
 *
 */
public class ComponentTracesFile extends ComponentFile {

	
	/**
	 * Create a trace file object for a component trace
	 * 
	 * @param outDir directory where to create the file
	 * @param component	name of the component
	 * @param filePrefix prefix for the file
	 */
	public ComponentTracesFile(File outDir, String component,String filePrefix) {
		super(outDir,component,filePrefix,".trace",".trans","#","|");
	}

	


}