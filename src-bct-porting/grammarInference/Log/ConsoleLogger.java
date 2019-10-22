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
/*
 * Created on 13-lug-2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package grammarInference.Log;

/**
 * 
 * @author Leonardo Mariani
 *
 * The class prints all logs on the standard output
 */
public class ConsoleLogger implements Logger {
	int verboseLevel;

	public int getVerboseLevel() {
		return verboseLevel;
	}
	public void setVerboseLevel(int verboseLevel) {
		this.verboseLevel = verboseLevel;
	}
	/**
	 * constructor for the logger printing all data on the standard
	 * output
	 * 
	 * @param verboseLevel verbose level, the value must be between
	 * 0 and 3
	 */
	public ConsoleLogger(int verboseLevel) {
		this.verboseLevel = verboseLevel;
	}

	/* (non-Javadoc)
	 * @see grammarInference.Log.Logger#logInfo(java.lang.String)
	 */
	public void logInfo(String info) {
		if (verboseLevel >= Logger.infoLevel) {
			//			System.out.println(Logger.infoLevel + ") " + info + "\n");
			System.out.println(Logger.infoLevel + ") " + info);
		}
	}

	/* (non-Javadoc)
	 * @see grammarInference.Log.Logger#logEvent(java.lang.String)
	 */
	public void logEvent(String event) {
		if (verboseLevel >= Logger.eventLevel) {
			//			System.out.println(Logger.eventLevel + ") " + event + "\n");
			System.out.println(Logger.eventLevel + ") " + event);
		}
	}

	/* (non-Javadoc)
	 * @see grammarInference.Log.Logger#logUnexpectedEvent(java.lang.String)
	 */
	public void logUnexpectedEvent(String event) {
		if (verboseLevel >= Logger.unexpectedinfoLevel) {
			System.out.println(
			//				Logger.unexpectedinfoLevel + ") " + event + "\n");
			Logger.unexpectedinfoLevel + ") " + event);
		}

	}

	/* (non-Javadoc)
	 * @see grammarInference.Log.Logger#logCriticalEvent(java.lang.String)
	 */
	public void logCriticalEvent(String event) {
		if (verboseLevel >= Logger.criticalLevel) {
			//			System.out.println(Logger.criticalLevel + ") " + event + "\n");
			System.out.println(Logger.criticalLevel + ") " + event);
		}
	}

	public void printDoubleStringArray(String [][] ar) {
		if (verboseLevel >= Logger.infoLevel) {
			for (int i=0; i< ar.length; i++) {
				System.out.println("Printing behavior set " + i);
				for (int j=0; j< ar[i].length; j++) {
					System.out.println("Behavior " +j);
					System.out.println(ar[i][j]);
				}
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see grammarInference.Log.Logger#close()
	 */
	public void close() {

	}
	public void logDebugInfo(String event) {
		if (verboseLevel >= Logger.debuginfoLevel) {
			System.out.println(
			//				Logger.unexpectedinfoLevel + ") " + event + "\n");
			Logger.debuginfoLevel + ") " + event);
		}
		
	}

}
