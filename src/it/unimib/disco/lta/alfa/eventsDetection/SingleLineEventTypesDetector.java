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
