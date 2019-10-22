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
