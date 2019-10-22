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
 
package gui.grammar.parse;

import gui.editor.TransitionTool;
import gui.viewer.AutomatonDrawer;
import gui.viewer.AutomatonPane;

import java.awt.event.MouseEvent;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import automata.State;

/**
 * This is a specialized transition tool that handles the movement of
 * gotos from one set to another set.
 * 
 * @author Thomas Finley
 */

public class GotoTransitionTool extends TransitionTool {
    /**
     * Instantiates a new <CODE>TransitionExpanderTool</CODE>.
     * @param view the view where the automaton is drawn
     * @param drawer the object that draws the automaton
     * @param controller the NFA to DFA controller object
     */
    public GotoTransitionTool(AutomatonPane view, AutomatonDrawer drawer,
			      LRParseDerivationController controller) {
	super(view, drawer);
	this.controller = controller;
    }

    /**
     * When we release the mouse, a transition from the start state to
     * this released state must be formed.
     * @param event the mouse event
     */
    public void mouseReleased(MouseEvent event) {
	// Did we even start at a state?
	if (first == null) return;
	State state = getDrawer().stateAtPoint(event.getPoint());
	controller.gotoGroup(first, event.getPoint(), state);
	first = null;
	getView().repaint();
    }

    /**
     * Returns the tool icon.
     * @return the group expander tool icon
     */
    protected Icon getIcon() {
	java.net.URL url = getClass().getResource("/ICON/expand_group.gif");
	return new ImageIcon(url);
    }

    /**
     * Gets the tool tip for this tool.
     * @return the tool tip for this tool
     */
    public String getToolTip() {
	return "Goto Set on Symbol";
    }

    /** The conversion to DFA controller. */
    private LRParseDerivationController controller;
}
