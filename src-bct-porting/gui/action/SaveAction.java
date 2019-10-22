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
 
package gui.action;

import gui.environment.Environment;
import gui.environment.Universe;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.KeyStroke;

/**
 * The <CODE>SaveAction</CODE> is an action to save a serializable
 * object contained in an environment to a file.
 * 
 * @author Thomas Finley
 */

public class SaveAction extends SaveAsAction {
    /**
     * Instantiates a new <CODE>SaveAction</CODE>.
     * @param environment the environment that holds the serializable
     */
    public SaveAction(Environment environment) {
	super(environment);
	putValue(NAME, "Save");
	putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke
		 (KeyEvent.VK_S, MAIN_MENU_MASK));
	this.environment = environment;
    }

    /**
     * If a save was attempted, call the methods that handle the
     * saving of the serializable object to a file.
     * @param event the action event
     */
    public void actionPerformed(ActionEvent event) {
	Universe.frameForEnvironment(environment).save(false);
    }

    /** The environment this action will handle saving for. */
    private Environment environment;
}
