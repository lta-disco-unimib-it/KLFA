/* -- JFLAP 4.0 --
 *
 * Copyright information:
 *
 * Susan H. Rodger, Thomas Finley
 * Computer Science Department
 * Duke University
 * April 24, 2003
 * Supported by National Science Foundation DUE-9752583.
 *
 * Copyright (c) 2003
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms are permitted
 * provided that the above copyright notice and this paragraph are
 * duplicated in all such forms and that any documentation,
 * advertising materials, and other materials related to such
 * distribution and use acknowledge that the software was developed
 * by the author.  The name of the author may not be used to
 * endorse or promote products derived from this software without
 * specific prior written permission.
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND WITHOUT ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, WITHOUT LIMITATION, THE IMPLIED
 * WARRANTIES OF MERCHANTIBILITY AND FITNESS FOR A PARTICULAR PURPOSE.
 */
 
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
