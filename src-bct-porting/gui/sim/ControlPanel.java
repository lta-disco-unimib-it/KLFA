/*******************************************************************************
 *    Copyright 2019 Fabrizio Pastore, Leonardo Mariani
 *   
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 *******************************************************************************/
 
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
