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

import gui.environment.RegularEnvironment;
import gui.environment.tag.CriticalTag;
import gui.regular.ConvertToAutomatonPane;

import java.awt.event.ActionEvent;

import javax.swing.JOptionPane;

/**
 * This class initiates the conversion of a regular expression to a
 * nondeterministic finite state automaton.
 * 
 * @author Thomas Finley
 */

public class REToFSAAction extends RegularAction {
    /**
     * Instantiates a <CODE>REToFSAAction</CODE>.
     * @param environment the environment which is home to the
     * regular expression to convert
     */
    public REToFSAAction(RegularEnvironment environment) {
	super("Convert to NFA", null, environment);
    }

    /**
     * This begins the process of converting a regular expression to
     * an NFA.
     * @param event the event to process
     */
    public void actionPerformed(ActionEvent event) {
	//JFrame frame = Universe.frameForEnvironment(environment);
	try {
	    getExpression().asCheckedString();
	} catch (UnsupportedOperationException e) {
	    JOptionPane.showMessageDialog
		(getEnvironment(), e.getMessage(),
		 "Illegal Expression", JOptionPane.ERROR_MESSAGE);
	    return;
	}
	ConvertToAutomatonPane pane =
	    new ConvertToAutomatonPane(getEnvironment());
	getEnvironment().add(pane, "Convert RE to NFA", new CriticalTag() {});
	getEnvironment().setActive(pane);
    }
}

