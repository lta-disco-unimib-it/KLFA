/**
 * 
 */
package it.unimib.disco.lta.alfa.eventsDetection;

import it.unimib.disco.lta.alfa.klfa.utils.componentAnalysis.ComponentFile;
import it.unimib.disco.lta.alfa.utils.FileUtils;

import java.io.File;


public class ComponentLogFile extends ComponentFile {

	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ComponentLogFile(File outDir, String component,String filePrefix) {
		super(outDir,component,filePrefix,".log",".trans",FileUtils.lineSeparator,FileUtils.lineSeparator+"|"+FileUtils.lineSeparator);
	}


}