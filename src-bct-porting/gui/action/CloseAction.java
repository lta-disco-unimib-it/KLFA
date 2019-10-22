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
 
package gui.action;

import gui.environment.Environment;
import gui.environment.tag.PermanentTag;
import gui.environment.tag.Tag;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.KeyStroke;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * The <CODE>CloseAction</CODE> is an action for removing tabs in an
 * environment.  It automatically detects changes in the activation of
 * panes in the environment, and changes its enabledness whether or not
 * a pane in the environment is permanent (i.e. should not be closed).
 * 
 * @author Thomas Finley
 */

public class CloseAction extends RestrictedAction {
    /**
     * Instantiates a <CODE>CloseAction</CODE>.
     * @param environment the environment to handle the closing for
     */
    public CloseAction(Environment environment) {
	super("Dismiss Tab", null);
	this.environment = environment;
	putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke
		 (KeyEvent.VK_ESCAPE, MAIN_MENU_MASK));
	environment.addChangeListener(new ChangeListener() {
		public void stateChanged(ChangeEvent e) { checkEnabled(); }
	    });
	checkEnabled();
    }

    /**
     * Handles the closing on the environment.
     * @param e the action event
     */
    public void actionPerformed(ActionEvent e) {
	environment.remove(environment.getActive());
    }

    /**
     * Checks the environment to see if the currently active object
     * has the <CODE>PermanentTag</CODE> associated with it, and if it
     * does, disables this action; otherwise it makes it activate.
     */
    private void checkEnabled() {
	Tag tag = environment.getTag(environment.getActive());
	setEnabled(!(tag instanceof PermanentTag));
    }

    /** The environment to handle the closing of tabs for. */
    private Environment environment;
}
