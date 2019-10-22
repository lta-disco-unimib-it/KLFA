package it.unimib.disco.lta.alfa.eventsDetection;

import java.io.File;
import java.util.List;


/**
 * Interface for CsvGenerators i.e. classes that take raw log files (in which every line contains a single event) and generate a csv file in the format accepted by KLFA
 * @author Fabrizio Pastore fabrizio.pastore AT gmail.com
 *
 */
public interface EventTypesDetector {

	public void process(List<File> inputs, File destTrace) throws EventTypesDetectorException;

	public void process(File inputs, File destTrace) throws EventTypesDetectorException;
	
	
}