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
 
package gui.regular;

import gui.editor.Tool;
import gui.viewer.AutomatonDrawer;
import gui.viewer.AutomatonPane;

import java.awt.event.MouseEvent;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.KeyStroke;

import automata.Transition;

/**
 * A tool that handles the conversion of multiple transitions to one
 * transition for the FSA to regular expression conversion.  This
 * simply calls the {@link FSAToREController#transitionCollapse}
 * method.
 * 
 * @see gui.regular.FSAToREController#transitionCreate
 * 
 * @author Thomas Finley
 */

public class CollapseTool extends Tool {
    /**
     * Instantiates a new transition tool.
     * @param view the view where the automaton is drawn
     * @param drawer the object that draws the automaton
     * @param creator the transition creator for the type of automata
     * we are editing
     */
    public CollapseTool(AutomatonPane view, AutomatonDrawer drawer,
			FSAToREController controller) {
	super(view, drawer);
	this.controller = controller;
    }

    /**
     * Gets the tool tip for this tool.
     * @return the tool tip for this tool
     */
    public String getToolTip() {
	return "Transition Collapser";
    }

    /**
     * Returns the tool icon.
     * @return the state tool icon
     */
    protected Icon getIcon() {
	java.net.URL url = getClass().getResource("/ICON/collapse.gif");
	return new ImageIcon(url);
    }

    /**
     * Returns the keystroke to switch to this tool, C.
     * @return the keystroke for this tool
     */
    public KeyStroke getKey() {
	return KeyStroke.getKeyStroke('c');
    }

    /**
     * When we press the mouse, the convert controller should be told that
     * transitions are done.
     * @param event the mouse event
     */
    public void mousePressed(MouseEvent event) {
	Transition t = getDrawer().transitionAtPoint(event.getPoint());
	if (t != null) {
	    controller.transitionCollapse(t.getFromState(), t.getToState());
	}
    }

    /** The regular conversion controller. */
    private FSAToREController controller;
}
