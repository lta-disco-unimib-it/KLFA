/*******************************************************************************
 *    Copyright 2019 Fabrizio Pastore, Leonardo Mariani
 *   
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 *******************************************************************************/
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