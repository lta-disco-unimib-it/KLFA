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
package util;

import java.io.File;

public class FileUtil {
	
	public static String getFileExtension ( File file ){
		return getFileExtension(file.getAbsolutePath());	
	}
	
	public static String getFileExtension ( String path ){
		int dotPosition = path.lastIndexOf('.');
		return path.substring(dotPosition+1, path.length());	
	}
	
	static public boolean deleteDirectory(File path) {
		if( path.exists() ) {
			File[] files = path.listFiles();
			for(int i=0; i<files.length; i++) {
				if(files[i].isDirectory()) {
					if ( ! deleteDirectory(files[i]) )
						System.out.println("cannotDelete "+files[i]);
					else
						System.out.println("deleted "+files[i]);
				}
				else {
					if ( ! files[i].delete() )
						System.out.println("cannotDelete "+files[i]);
					else
						System.out.println("deleted "+files[i]);
				}
			}
		}
		return( path.delete() );
	}
}
