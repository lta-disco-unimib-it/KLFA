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
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.Serializable;

import javax.swing.JFileChooser;
import javax.swing.KeyStroke;

/**
 * The <CODE>SaveAsAction</CODE> is an action to save a serializable
 * object contained in an environment to file always using a dialog
 * box.
 * 
 * @author Thomas Finley
 */

public class SaveAsAction extends RestrictedAction {
    /**
     * Instantiates a new <CODE>SaveAction</CODE>.
     * @param environment the environment that holds the serializable
     * object
     */
    public SaveAsAction(Environment environment) {
	super("Save As...", null);
	putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke
		 (KeyEvent.VK_S, MAIN_MENU_MASK+InputEvent.SHIFT_MASK));
	this.environment = environment;
	this.fileChooser = Universe.CHOOSER;
    }

    /**
     * If a save was attempted, call the methods that handle the
     * saving of the serializable object to a file.
     * @param event the action event
     */
    public void actionPerformed(ActionEvent event) {
	Universe.frameForEnvironment(environment).save(true);
    }

    /**
     * This action is restricted to those objects that are
     * serializable.
     * @param object the object to check for serializable-ness
     * @return <CODE>true</CODE> if the object is an instance of a
     * serializable object, <CODE>false</CODE> otherwise
     */
    public static boolean isApplicable(Object object) {
	return object instanceof Serializable;
    }

    /** The environment that this save action gets it's object from. */
    protected Environment environment;
    /** The file chooser. */
    private JFileChooser fileChooser; 
}
