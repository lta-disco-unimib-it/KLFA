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
 
package gui.editor;

import gui.viewer.AutomatonDrawer;
import gui.viewer.AutomatonPane;

import java.awt.event.MouseEvent;

import javax.swing.Icon;
import javax.swing.KeyStroke;

import automata.State;
import automata.Transition;

/**
 * A tool that handles the deletion of states and transitions.
 * 
 * @author Thomas Finley
 */

public class DeleteTool extends Tool {
    /**
     * Instantiates a new delete tool.
     */
    public DeleteTool(AutomatonPane view, AutomatonDrawer drawer) {
	super(view, drawer);
    }

    /**
     * Gets the tool tip for this tool.
     * @return the tool tip for this tool
     */
    public String getToolTip() {
	return "Deleter";
    }

    /**
     * Returns the tool icon.
     * @return the delete tool icon
     */
    protected Icon getIcon() {
	java.net.URL url = getClass().getResource("/ICON/delete.gif");
	return new javax.swing.ImageIcon(url);
    }

    /**
     * Returns the key stroke to switch to this tool, the D key.
     * @return the key stroke to switch to this tool
     */
    public KeyStroke getKey() {
	return KeyStroke.getKeyStroke('d');
    }

    /**
     * When the user clicks, we delete either the state or, if no
     * state, the transition found at this point.  If there's nothing
     * at this point, nothing happens.
     * @param event the mouse event
     */
    public void mouseClicked(MouseEvent event) {
	State state = getDrawer().stateAtPoint(event.getPoint());
	if (state != null) {
	    getAutomaton().removeState(state);
	    getView().repaint();
	    return;
	}
	Transition trans = getDrawer().transitionAtPoint(event.getPoint());
	if (trans != null) {
	    getAutomaton().removeTransition(trans);
	    getView().repaint();
	}
    }
}
