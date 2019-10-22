package flattener.utilities;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author Davide Lorenzoli
 *
 */
public class LogWriter {
	private static boolean APPEND = true;
	private static boolean NOT_APPEND = false;		
	
	public static void log(String logFilePath, String logFileName, String log) {		
	    logFilePath = logFilePath.endsWith("/") ? logFilePath : logFilePath + "/";
		
		log(logFilePath + logFileName, log);
	}
	
	public static void log(String logFile, String log) {								
		FileOutputStream fOut;
		try {
		    if (log != null) {
		        fOut = new FileOutputStream(logFile, APPEND);			
		        fOut.write(log.getBytes());			
		        fOut.close();
		    }
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}	
}
