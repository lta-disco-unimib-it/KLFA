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

import gui.AboutBox;

import java.awt.event.ActionEvent;

/**
 * This action will display a small about box that lists the tool
 * version number, and other version.
 * 
 * @author Thomas Finley
 */

public class AboutAction extends RestrictedAction {
    /**
     * Instantiates a new <CODE>AboutAction</CODE>.
     */
    public AboutAction() {
	super("About...", null);
    }
    
    /**
     * Shows the about box.
     */
    public void actionPerformed(ActionEvent e) {
	BOX.displayBox();
    }

    private static final AboutBox BOX = new AboutBox();
}
