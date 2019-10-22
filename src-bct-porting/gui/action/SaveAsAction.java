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
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.Serializable;

import javax.swing.JFileChooser;
import javax.swing.KeyStroke;

/**
 * The <CODE>SaveAsAction</CODE> is an action to save a serializable
 * object contained in an environment to file always using a dialog
 * box.
 * 
 * @author Thomas Finley
 */

public class SaveAsAction extends RestrictedAction {
    /**
     * Instantiates a new <CODE>SaveAction</CODE>.
     * @param environment the environment that holds the serializable
     * object
     */
    public SaveAsAction(Environment environment) {
	super("Save As...", null);
	putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke
		 (KeyEvent.VK_S, MAIN_MENU_MASK+InputEvent.SHIFT_MASK));
	this.environment = environment;
	this.fileChooser = Universe.CHOOSER;
    }

    /**
     * If a save was attempted, call the methods that handle the
     * saving of the serializable object to a file.
     * @param event the action event
     */
    public void actionPerformed(ActionEvent event) {
	Universe.frameForEnvironment(environment).save(true);
    }

    /**
     * This action is restricted to those objects that are
     * serializable.
     * @param object the object to check for serializable-ness
     * @return <CODE>true</CODE> if the object is an instance of a
     * serializable object, <CODE>false</CODE> otherwise
     */
    public static boolean isApplicable(Object object) {
	return object instanceof Serializable;
    }

    /** The environment that this save action gets it's object from. */
    protected Environment environment;
    /** The file chooser. */
    private JFileChooser fileChooser; 
}
