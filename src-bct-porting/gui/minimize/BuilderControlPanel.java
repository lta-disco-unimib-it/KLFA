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
