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
