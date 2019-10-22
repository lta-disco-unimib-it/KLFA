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
import gui.environment.tag.PermanentTag;
import gui.environment.tag.Tag;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.KeyStroke;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * The <CODE>CloseAction</CODE> is an action for removing tabs in an
 * environment.  It automatically detects changes in the activation of
 * panes in the environment, and changes its enabledness whether or not
 * a pane in the environment is permanent (i.e. should not be closed).
 * 
 * @author Thomas Finley
 */

public class CloseAction extends RestrictedAction {
    /**
     * Instantiates a <CODE>CloseAction</CODE>.
     * @param environment the environment to handle the closing for
     */
    public CloseAction(Environment environment) {
	super("Dismiss Tab", null);
	this.environment = environment;
	putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke
		 (KeyEvent.VK_ESCAPE, MAIN_MENU_MASK));
	environment.addChangeListener(new ChangeListener() {
		public void stateChanged(ChangeEvent e) { checkEnabled(); }
	    });
	checkEnabled();
    }

    /**
     * Handles the closing on the environment.
     * @param e the action event
     */
    public void actionPerformed(ActionEvent e) {
	environment.remove(environment.getActive());
    }

    /**
     * Checks the environment to see if the currently active object
     * has the <CODE>PermanentTag</CODE> associated with it, and if it
     * does, disables this action; otherwise it makes it activate.
     */
    private void checkEnabled() {
	Tag tag = environment.getTag(environment.getActive());
	setEnabled(!(tag instanceof PermanentTag));
    }

    /** The environment to handle the closing of tabs for. */
    private Environment environment;
}
