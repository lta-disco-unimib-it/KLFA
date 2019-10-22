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
package it.unimib.disco.lta.alfa.testUtils;

import java.io.File;

import util.FileUtil;


public class ArtifactsProvider {
	
	

	public static File getFile(String string) {
		return new File("tests/"+string);
	}

	public static File getNonExistentFile(String string) {
		File f = getFile(string);
		
		checkNonExistent(f);
		
		
		return f;
	}

	private static void checkNonExistent(File f) {
		if ( f.exists() ){
			if ( f.isDirectory() ){
				FileUtil.deleteRecursively(f);
			} else {
				f.delete();
			}
		}
	}

	public static File getBugFile(String string) {
		return new File("tests/bugs/artifacts/"+string);
	}
	
	public static File getUnitTestFile(String string) {
		return new File("tests/unit/main/artifacts/"+string);
	}
	
	public static File getSystemTestFile(String string) {
		return new File("tests/system/"+string);
	}
	
	public static File getNonexistentUnitTestFile(String string) {
		File file = getUnitTestFile(string);
		checkNonExistent(file);
		return file;
	}
	
}
