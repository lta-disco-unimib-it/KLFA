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
import gui.environment.Universe;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.KeyStroke;

/**
 * The <CODE>SaveAction</CODE> is an action to save a serializable
 * object contained in an environment to a file.
 * 
 * @author Thomas Finley
 */

public class SaveAction extends SaveAsAction {
    /**
     * Instantiates a new <CODE>SaveAction</CODE>.
     * @param environment the environment that holds the serializable
     */
    public SaveAction(Environment environment) {
	super(environment);
	putValue(NAME, "Save");
	putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke
		 (KeyEvent.VK_S, MAIN_MENU_MASK));
	this.environment = environment;
    }

    /**
     * If a save was attempted, call the methods that handle the
     * saving of the serializable object to a file.
     * @param event the action event
     */
    public void actionPerformed(ActionEvent event) {
	Universe.frameForEnvironment(environment).save(false);
    }

    /** The environment this action will handle saving for. */
    private Environment environment;
}
