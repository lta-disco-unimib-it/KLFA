/*
 * Created on 13-lug-2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package grammarInference.Log;

/**
 * 
 * @author Leonardo Mariani
 *
 * The general interface of a logger
 */
public interface Logger {
	public static int debuginfoLevel = 4;
	public static int infoLevel = 3;
	public static int eventLevel = 2;
	public static int unexpectedinfoLevel = 1;
	public static int criticalLevel = 0;
	

	/**
	 * this method is used to log a general info about the actual 
	 * state of the running program
	 * 
	 * @param info the description of the information
	 */
	public void logInfo(String info);

	public int getVerboseLevel();

	public void setVerboseLevel(int verboseLevel);

	/**
	 * this method is used to log an event that has taken place
	 * into the system
	 * 
	 * @param event a string describing the event
	 */
	public void logEvent(String event);

	/**
	 * this method is used to log that an unexpected event has
	 * taken place
	 * 
	 * @param event a string describing the event
	 */
	public void logUnexpectedEvent(String event);

	/**
	 * this method is used to log that a critical event has
	 * taken place
	 * 
	 * @param event a string describing the event
	 */
	public void logCriticalEvent(String event);

	/**
	 * this method is used to log an information that can be useful only if
	 * one want to check log with a very fine granularity
	 * 
	 * @param event a string describing the event
	 */
	public void logDebugInfo(String event);
	
	/**
	 * this method releases the resources acquired by the specific 
	 * Logger
	 */
	public void close();
	
	public void printDoubleStringArray(String [][] ar);

	

}
