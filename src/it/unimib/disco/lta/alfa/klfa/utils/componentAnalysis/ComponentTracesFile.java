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