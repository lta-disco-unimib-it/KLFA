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

import gui.environment.EnvironmentFrame;
import gui.environment.Universe;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.KeyStroke;

/**
 * This action handles quitting.
 * 
 * @author Thomas Finley
 */

public class QuitAction extends RestrictedAction {
    /**
     * Instantiates a new <CODE>QuitAction</CODE>.
     */
    public QuitAction() {
	super("Quit", null);
	putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke
		 (KeyEvent.VK_Q, MAIN_MENU_MASK));
    }

    /**
     * This begins the process of quitting.  If this method returns,
     * you know it did not succeed.  Quitting may not succeed if there
     * is an unsaved document and the user elects to cancel the
     * process.
     */
    public static void beginQuit() {
	EnvironmentFrame[] frames = Universe.frames();
	for (int i=0; i<frames.length; i++)
	    if (!frames[i].close()) return;
	System.exit(0);
    }

    /**
     * In repsonding to events, it cycles through all registered
     * windows in the <CODE>gui.environment.Universe</CODE> and closes
     * them all, or at least until the user does something that stops
     * a close, at which point the quit terminates.
     */
    public void actionPerformed(ActionEvent e) {
	beginQuit();
    }
}
