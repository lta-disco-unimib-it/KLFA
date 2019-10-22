package dfmaker.core;

/**
 * This file cointain the logic to understand Daikon trace files.
 * 
 * @author Fabrizio Pastore [ fabrizio.pastore at gmail dot com ]
 *
 */
public class DaikonReader {

	public static boolean isProgramPoint(String line){
        return line.indexOf(":::") != -1 ? true : false;
	}
	
	
	/**
     * @param line
     * @return
     */
    public static boolean isEntryPoint(String line) {
        return line.indexOf(":::ENTER") != -1 ? true : false;
    }
    
    /**
     * @param line
     * @return
     */
    public static boolean isExitPoint(String line) {        
        return line.indexOf(":::EXIT") != -1 ? true : false;
    }
    
    /**
     * @param line
     * @return
     */
    public static boolean isObjectPoint(String line) {
        return line.indexOf(":::OBJECT") != -1 ? true : false;
    }    

}
