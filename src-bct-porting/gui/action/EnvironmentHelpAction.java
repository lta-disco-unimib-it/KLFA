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

import java.awt.event.ActionEvent;

/**
 * The <CODE>EnvironmentHelpAction</CODE> is an extension of the
 * <CODE>HelpAction</CODE> that, whenever an action is received,
 * determines what should be displayed based on the currently active
 * pane in the environment.  Basically, it simply calls
 * <CODE>HelpAction.displayHelp</CODE> on
 * <CODE>Environment.getActive</CODE>.
 * 
 * Any components in an environment that wish to have help should
 * register themselves, or preferably their <CODE>Class</CODE> objects
 * (so that it only happens once), with whatever particular webpage
 * they wish to display whenever help is activated.
 * 
 * @author Thomas Finley
 */

public class EnvironmentHelpAction extends HelpAction {
    /**
     * Instantiates an <CODE>EnvironmentHelpAction</CODE>.
     * @param environment the environment that this help action will
     * get the current panel from
     */
    public EnvironmentHelpAction(Environment environment) {
	this.environment = environment;
    }

    /**
     * Displays help according to the current display of the
     * automaton.
     * @param event the action event
     */
    public void actionPerformed(ActionEvent event) {
	displayHelp(environment.getActive());
    }

    /** The environment this help action is for. */
    private Environment environment;
}
