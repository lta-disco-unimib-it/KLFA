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

import grammar.lsystem.LSystem;
import gui.environment.LSystemEnvironment;
import gui.environment.tag.CriticalTag;
import gui.lsystem.DisplayPane;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

/**
 * This action creates a new L-system renderer.
 * 
 * @author Thomas Finley
 */

public class LSystemDisplay extends LSystemAction {
    /**
     * Instantiates a new <CODE>BruteParseAction</CODE>.
     * @param environment the grammar environment
     */
    public LSystemDisplay(LSystemEnvironment environment) {
	super(environment, "Render System", null);
	putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke
		 (KeyEvent.VK_D, MAIN_MENU_MASK));
    }

    /**
     * Performs the action.
     */
    public void actionPerformed(ActionEvent e) {
	LSystem lsystem = getEnvironment().getLSystem();
	
	if (lsystem.getAxiom().size() == 0) {
	    JOptionPane.showMessageDialog
		(getEnvironment(),
		 "The axiom must have one or more symbols.",
		 "Nonempty Axiom Required", JOptionPane.ERROR_MESSAGE);
	    return;
	}

	try {
	    DisplayPane pane = new DisplayPane(lsystem);
	    getEnvironment().add(pane, "L-S Render", new CriticalTag() {});
	    getEnvironment().setActive(pane);
	} catch (NoClassDefFoundError ex) {
	    JOptionPane.showMessageDialog
		(getEnvironment(),
		 "Sorry, but this uses features requiring Java 1.4 or later!",
		 "JVM too primitive", JOptionPane.ERROR_MESSAGE);
	    return;
	}
    }

}
