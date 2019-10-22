package it.unimib.disco.lta.alfa.eventsDetection;

import it.unimib.disco.lta.alfa.eventsDetection.slct.GenericLogToComponentSlctPreprocessor.ComponentProcessingSequence;
import it.unimib.disco.lta.alfa.logging.Logger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class ComponentTracesMerger {

	public void merge(File destTrace, ComponentProcessingSequence cps, ComponentsTracesManager ctm) throws IOException, ComponentsTracesManagerException {
		Logger.info("Generating the global csv file");
		
		String nextId;
		
		
		
			BufferedWriter w = new BufferedWriter( new FileWriter(destTrace) );
			int c = 0;
			boolean addTraceSeparator = false;
			while ( ( nextId = cps.nextId() ) != null ){
				++c;
				
				
				if ( addTraceSeparator ){
					w.write("|");
					w.newLine();
					addTraceSeparator = false;
				}
				
				if ( nextId.equals("|") ){
					addTraceSeparator = true;
				} else {
					System.out.println(c+": "+nextId);
					String line = ctm.getSymbol(nextId);
					w.write(line);
					w.newLine();
				}
				
			}
			w.close();
			Logger.info("Global csv file generated");
		

	}

}
