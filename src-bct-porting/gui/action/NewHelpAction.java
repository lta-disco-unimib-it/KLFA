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

import java.awt.event.ActionEvent;

/**
 * The <CODE>NewHelpAction</CODE> is an extension of the
 * <CODE>HelpAction</CODE> that, whenever an action is received, puts
 * up the help code for the {@link gui.action.NewAction}.
 * 
 * @author Thomas Finley
 */

public class NewHelpAction extends HelpAction {
    /**
     * Instantiates an <CODE>EnvironmentHelpAction</CODE>.
     * @param environment the environment that this help action will
     * get the current panel from
     */
    public NewHelpAction() {
	
    }

    /**
     * Displays help according to the current display of the
     * automaton.
     * @param event the action event
     */
    public void actionPerformed(ActionEvent event) {
	displayHelp(NewAction.class);
    }
}
