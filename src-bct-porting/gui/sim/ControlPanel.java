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
 
package gui.sim;

import gui.TooltipAction;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JToolBar;

/**
 * This is a control panel with buttons for invoking methods on a
 * configuration controller.
 * 
 * @author Thomas Finley
 */

public class ControlPanel extends JToolBar {
    /**
     * Instantiates a new <CODE>ControlPanel</CODE> for the given
     * configuration controller.
     * @param controller the configuration controller object
     */
    public ControlPanel(ConfigurationController controller) {
	this.controller = controller;
	initView();
    }

    /**
     * Returns the configuration controller object this panel controls.
     * @return the configuration controller object this panel controls
     */
    public ConfigurationController getController() {
	return controller;
    }

    /**
     * A simple helper function that initializes the gui.
     */
    protected void initView() {
	this.add(new TooltipAction("Step", "Moves existing valid "+
				   "configurations to the next "+
				   "configurations.") {
		public void actionPerformed(ActionEvent e)
		{controller.step();}
	    });

	this.add(new TooltipAction("Reset", "Resets the simulation to "+
				   "start conditions.") {
		public void actionPerformed(ActionEvent e)
		{controller.reset();}
	    });

	this.add(new AbstractAction("Freeze") {
		public void actionPerformed(ActionEvent e)
		{controller.freeze();}
	    });

	this.add(new AbstractAction("Thaw") {
		public void actionPerformed(ActionEvent e)
		{controller.thaw();}
	    });

	this.add(new AbstractAction("Trace") {
		public void actionPerformed(ActionEvent e)
		{controller.trace();}
	    });

	this.add(new AbstractAction("Remove") {
		public void actionPerformed(ActionEvent e)
		{controller.remove();}
	    });
    }

    /** The configuration controller object. */
    private ConfigurationController controller;
}
