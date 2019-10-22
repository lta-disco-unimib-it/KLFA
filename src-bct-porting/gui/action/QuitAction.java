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
import gui.environment.Universe;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.KeyStroke;

/**
 * This action handles quitting.
 * 
 * @author Thomas Finley
 */

public class QuitAction extends RestrictedAction {
    /**
     * Instantiates a new <CODE>QuitAction</CODE>.
     */
    public QuitAction() {
	super("Quit", null);
	putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke
		 (KeyEvent.VK_Q, MAIN_MENU_MASK));
    }

    /**
     * This begins the process of quitting.  If this method returns,
     * you know it did not succeed.  Quitting may not succeed if there
     * is an unsaved document and the user elects to cancel the
     * process.
     */
    public static void beginQuit() {
	EnvironmentFrame[] frames = Universe.frames();
	for (int i=0; i<frames.length; i++)
	    if (!frames[i].close()) return;
	System.exit(0);
    }

    /**
     * In repsonding to events, it cycles through all registered
     * windows in the <CODE>gui.environment.Universe</CODE> and closes
     * them all, or at least until the user does something that stops
     * a close, at which point the quit terminates.
     */
    public void actionPerformed(ActionEvent e) {
	beginQuit();
    }
}
