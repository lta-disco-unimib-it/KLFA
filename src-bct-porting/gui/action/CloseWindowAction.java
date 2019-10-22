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

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.KeyStroke;

/**
 * The <CODE>CloseWindowAction</CODE> invokes the close method on
 * the <CODE>EnvironmentFrame</CODE> to which they belong.
 * 
 * @author Thomas Finley
 */

public class CloseWindowAction extends RestrictedAction {
    /**
     * Instantiates a <CODE>CloseWindowAction</CODE>.
     * @param frame the <CODE>EnvironmentFrame</CODE> to dismiss when
     * an action is registered
     */
    public CloseWindowAction(EnvironmentFrame frame) {
	super("Close", null);
	putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke
		 (KeyEvent.VK_W, MAIN_MENU_MASK));
	this.frame = frame;
    }

    /**
     * Handles the closing of the window.
     */
    public void actionPerformed(ActionEvent event) {
	frame.close();
    }
    
    /** The environment frame to call the close method on. */
    EnvironmentFrame frame;
}
