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
 
package gui.environment;

import java.io.File;
import java.util.EventObject;

/**
 * This is an event that registers with a listener that an environment
 * has changed its file.
 * 
 * @see gui.environment.FileChangeListener
 * @see gui.environment.Environment
 * @see gui.environment.Environment#setFile
 * @see gui.environment.Environment#getFile
 * 
 * @author Thomas Finley
 */

public class FileChangeEvent extends EventObject {
    /**
     * Instantiates a new <CODE>FileChangeEvent</CODE>.
     * @param environment the environment that threw this event
     * @param oldFile the previous file that was the file of the
     * <CODE>Environment</CODE>
     */
    public FileChangeEvent(Environment environment, File oldFile) {
	super(environment);
	this.oldFile = oldFile;
    }

    /**
     * Returns the native file for the environment before the change.
     * @return the native file for the environment before the change
     */
    public File getOldFile() {
	return oldFile;
    }

    /** The old file that was the native file for the environment. */
    private File oldFile;
}
