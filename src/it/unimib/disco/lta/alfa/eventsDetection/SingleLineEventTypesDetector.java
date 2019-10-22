package it.unimib.disco.lta.alfa.eventsDetection;

/**
 * This interface describe objects being able of processing raw log file lines and transforming them into
 * csv lines acceptable by KLFA.
 * Pay attention, if the log file record events in more than one lines the interface EventTypesDetector must be used.
 * @author andrea.mattavelli
 *
 */
public interface SingleLineEventTypesDetector {

	/**
	 * This method takes a raw log line in input and produces a csv line that can be accepted by KLFA.
	 *  
	 * @param line
	 * @return
	 */
	public String processLine (String line);
	
}
