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
 
package gui.minimize;

import gui.TooltipAction;

import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JToolBar;

/**
 * This control panel is a set of GUI elements that, when invoked,
 * call methods on the <CODE>BuilderController</CODE> object.
 * 
 * @author Thomas Finley
 */

class BuilderControlPanel extends JToolBar {
    /**
     * Instantiates a new <CODE>BuilderControlPanel</CODE>.
     * @param controller the builder controller to call methods of
     */
    public BuilderControlPanel(BuilderController controller) {
	//super(new GridLayout(1,0));
	this.controller = controller;
	initView(this, controller);
    }

    /**
     * Initializes the GUI elements in the indicated toolbar.
     * @param toolbar the tool bar
     * @param controller the controller for the building of the automaton
     */
    public static void initView(JToolBar toolbar,
				final BuilderController controller) {
	toolbar.add(new JButton(new TooltipAction
			("Hint", "Adds one transition.") {
		public void actionPerformed(ActionEvent e) {
		    controller.hint();
		}
	    }));

	toolbar.add(new JButton(new TooltipAction
			("Complete", "Adds all transitions.") {
		public void actionPerformed(ActionEvent e) {
		    controller.complete();
		}
	    }));

	toolbar.add(new JButton(new TooltipAction
			("Done?", "Checks if the automaton is done.") {
		public void actionPerformed(ActionEvent e) {
		    controller.done();
		}
	    }));
    }

    /** The builder controller to call methods of. */
    private BuilderController controller;
}
