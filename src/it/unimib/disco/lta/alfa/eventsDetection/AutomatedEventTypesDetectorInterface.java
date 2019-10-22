package it.unimib.disco.lta.alfa.eventsDetection;

import java.io.File;
import java.util.List;
import java.util.regex.Pattern;

/**
 * This interface was made just to experiment with EMF
 * @model
 * @author Fabrizio Pastore fabrizio.pastore AT gmail.com
 *
 */
public interface AutomatedEventTypesDetectorInterface {

	/**
	 * @model
	 * @return
	 */
	public abstract String getComponentRegex();

	/**
	 * @model
	 * @return
	 */
	public abstract String getDataRegex();

	/**
	 * @model
	 * @return
	 */
	public abstract String getNumbersSeparator();

	/**
	 * @model
	 * @return
	 */
	public abstract int getOffset();

	/**
	 * @model
	 * @return
	 */
	public abstract File getOutDir();

	/**
	 * @model
	 * @return
	 */
	public abstract String getSeparator();

	/**
	 * @model
	 * @return
	 */
	public abstract String getTraceSeparator();

	/**
	 * @model
	 * @return
	 */
	public abstract File getWorkingDir();

	/**
	 * @model
	 * @return
	 */
	public abstract File getPatternsDir();

	/**
	 * @model
	 * @return
	 */
	public abstract boolean isSkipEventPatternsDetection();

	/**
	 * @model
	 * @return
	 */
	public abstract double getSlctSupportPercentage();

	/**
	 * @model
	 * @return
	 */
	public abstract int getTruncate();
	
	/**
	 * @model
	 * @return
	 */
	public abstract boolean isDontSplitComponents();

}