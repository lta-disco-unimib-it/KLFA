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
 
package gui.deterministic;

import gui.editor.Tool;
import gui.viewer.AutomatonDrawer;
import gui.viewer.AutomatonPane;

import java.awt.event.MouseEvent;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.KeyStroke;

import automata.State;

/**
 * This is a tool that expands a state completely.
 * 
 * @author Thomas Finley
 */

public class StateExpanderTool extends Tool {
    /**
     * Instantiates a new state tool.
     */
    public StateExpanderTool(AutomatonPane view, AutomatonDrawer drawer,
			     ConversionController controller) {
	super(view, drawer);
	this.controller = controller;
    }

    /**
     * Gets the tool tip for this tool.
     * @return the tool tip for this tool
     */
    public String getToolTip() {
	return "State Expander";
    }

    /**
     * Returns the tool icon.
     * @return the state tool icon
     */
    protected Icon getIcon() {
	java.net.URL url = getClass().getResource("/ICON/state_expander.gif");
	return new ImageIcon(url);
    }

    /**
     * When the user clicks, one creates a state.
     * @param event the mouse event
     */
    public void mousePressed(MouseEvent event) {
	State state = getDrawer().stateAtPoint(event.getPoint());
	if (state == null) return;
	controller.expandState(state);
    }

    /**
     * Returns the keystroke to switch to this tool, S.
     * @return the keystroke for this tool
     */
    public KeyStroke getKey() {
	return KeyStroke.getKeyStroke('s');
    }
    
    /** The deterministic NFA to DFA controller. */
    private ConversionController controller;
}
