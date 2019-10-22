/*
 * Created on 13-lug-2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package grammarInference.Log;

import java.io.FileWriter;
import java.io.IOException;

/**
 * 
 * @author Leonardo Mariani
 *
 * The class logs events on a log file.
 */
public class FileLogger implements Logger {
	FileWriter fw = null;
	boolean disableLogging = false;
	int verboseLevel;

	/**
	 * The constructor instantiates a logger that wirte log data on a file
	 * 
	 * @param verboseLevel it sets the verbose level witha value 
	 * between 0 and 3
	 * @param fileName name of hte file that will store logs
	 */
	public FileLogger(int verboseLevel, String fileName) {
		this.verboseLevel = verboseLevel;
		try {
			fw = new FileWriter(fileName);
		} catch (IOException e) {
			disableLogging = true;
			System.out.println(
				"WARNING: impossible to create file "
					+ fileName
					+ " for logging events.");
		}

	}

	/* (non-Javadoc)
	 * @see grammarInference.Log.Logger#logInfo(java.lang.String)
	 */
	public void logInfo(String info) {
		if (disableLogging)
			return;
		if (verboseLevel >= Logger.infoLevel) {
			try {
				fw.write(Logger.infoLevel + ") " + info + "\n");
			} catch (IOException e) {
				disableLogging = true;
				System.out.println("WARNING: impossible continue logging.");
			}
		}
	}

	/* (non-Javadoc)
	 * @see grammarInference.Log.Logger#logEvent(java.lang.String)
	 */
	public void logEvent(String event) {
		if (disableLogging)
			return;
		if (verboseLevel >= Logger.eventLevel) {
			try {
				fw.write(Logger.eventLevel + ") " + event + "\n");
			} catch (IOException e) {
				disableLogging = true;
				System.out.println("WARNING: impossible continue logging.");
			}
		}
	}

	/* (non-Javadoc)
	 * @see grammarInference.Log.Logger#logUnexpectedEvent(java.lang.String)
	 */
	public void logUnexpectedEvent(String event) {
		if (disableLogging)
			return;
		if (verboseLevel >= Logger.unexpectedinfoLevel) {
			try {
				fw.write(Logger.unexpectedinfoLevel + ") " + event + "\n");
			} catch (IOException e) {
				disableLogging = true;
				System.out.println("WARNING: impossible continue logging.");
			}
		}

	}

	/* (non-Javadoc)
	 * @see grammarInference.Log.Logger#logCriticalEvent(java.lang.String)
	 */
	public void logCriticalEvent(String event) {
		if (disableLogging)
			return;
		if (verboseLevel >= Logger.criticalLevel) {
			try {
				fw.write(Logger.criticalLevel + ") " + event + "\n");
			} catch (IOException e) {
				disableLogging = true;
				System.out.println("WARNING: impossible continue logging.");
			}
		}

	}

	public void printDoubleStringArray(String [][] ar) {
		// TO DO
	}
	
	/* (non-Javadoc)
	 * @see grammarInference.Log.Logger#close()
	 */
	public void close() {
		try {
			fw.flush();
			fw.close();
		} catch (IOException e) {
			disableLogging = true;
			System.out.println("WARNING: impossible close logging file.");
		}
	}

	public int getVerboseLevel() {
		return verboseLevel;
	}

	public void setVerboseLevel(int verboseLevel) {
		this.verboseLevel = verboseLevel;
	}

	public void logDebugInfo(String event) {
		if (disableLogging)
			return;
		if (verboseLevel >= Logger.debuginfoLevel) {
			try {
				fw.write(Logger.debuginfoLevel + ") " + event + "\n");
			} catch (IOException e) {
				disableLogging = true;
				System.out.println("WARNING: impossible continue logging.");
			}
		}
		
	}

	
}
