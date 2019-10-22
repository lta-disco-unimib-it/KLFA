package sjm.utensil;

import java.io.FileReader;
import java.io.IOException;
import java.io.StringWriter;
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
 * This class has a static method that returns a file's characters 
 * as a single String.
 * 
 * @author Steven J. Metsker
 * 
 * @version 1.0
 */

public class FileString {

/**
 * Returns a string that represents the contents of a file.
 *
 * @param    fileName    the name of the file to read
 *
 * @return   string    the contents of a file as a String
 *
 * @exception   IOException   if the file is not found, or if there is
 *                            any problem reading the file
 */
public static String stringFromFileNamed(String fileName) 
	throws java.io.IOException {
		
	final int BUFLEN = 1024;
	char buf[] = new char[BUFLEN];
	
	FileReader in = new FileReader(fileName);
	StringWriter out = new StringWriter();
	
	try {
		while (true) {
			int len = in.read(buf, 0, BUFLEN);
			if (len == -1) {
				break;
			}	
			out.write(buf, 0, len);
		}	
	}
	finally {
		out.close();
		in.close();
	}	
	return out.toString();
}
}
