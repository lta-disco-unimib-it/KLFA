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
package it.unimib.disco.lta.alfa.utils;

import java.io.*;
import java.nio.channels.*;

public class FileUtils{
	
	public static final String lineSeparator = System.getProperty ( "line.separator" );
	
    public static void copyFile(File in, File out) 
        throws IOException 
    {
        FileChannel inChannel = new
            FileInputStream(in).getChannel();
        FileChannel outChannel = new
            FileOutputStream(out).getChannel();
        try {
            inChannel.transferTo(0, inChannel.size(),
                    outChannel);
        } 
        catch (IOException e) {
            throw e;
        }
        finally {
            if (inChannel != null) inChannel.close();
            if (outChannel != null) outChannel.close();
        }
    }
    
 

	public static int getNumberOfLines(File outliersFile) throws IOException {
		FileReader r = new FileReader(outliersFile);
		LineNumberReader lnr = new LineNumberReader(r);
		int bufSize = 256;
		char[] buf = new char[bufSize];
		
			while ( ( lnr.read( buf, 0, bufSize) ) > 0 ){
				
			}
		
		return lnr.getLineNumber();
	}
   

}
