package it.unimib.disco.lta.alfa.eventsDetection.slct;

import java.io.File;
import java.util.List;
import java.util.regex.Pattern;

public interface Slct {

	/**
	 * Process a log file and extract event types in form of regular expression using slct
	 * 
	 * @param fileToProcess
	 * @return
	 * @throws SlctRunnerException
	 */
	public abstract List<Pattern> getRules(File fileToProcess)
			throws SlctRunnerException;

	public abstract double getSupportPercentage();

	public abstract void setSupportPercentage(double supportPercentage);

	public abstract double getSupportDecreaseFactor();

	public abstract void setSupportDecreaseFactor(double supportDecreaseFactor);

}