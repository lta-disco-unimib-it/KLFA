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

import gui.environment.EnvironmentFrame;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.KeyStroke;

/**
 * The <CODE>CloseWindowAction</CODE> invokes the close method on
 * the <CODE>EnvironmentFrame</CODE> to which they belong.
 * 
 * @author Thomas Finley
 */

public class CloseWindowAction extends RestrictedAction {
    /**
     * Instantiates a <CODE>CloseWindowAction</CODE>.
     * @param frame the <CODE>EnvironmentFrame</CODE> to dismiss when
     * an action is registered
     */
    public CloseWindowAction(EnvironmentFrame frame) {
	super("Close", null);
	putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke
		 (KeyEvent.VK_W, MAIN_MENU_MASK));
	this.frame = frame;
    }

    /**
     * Handles the closing of the window.
     */
    public void actionPerformed(ActionEvent event) {
	frame.close();
    }
    
    /** The environment frame to call the close method on. */
    EnvironmentFrame frame;
}
