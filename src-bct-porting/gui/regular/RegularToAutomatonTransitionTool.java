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

import gui.editor.TransitionTool;
import gui.viewer.AutomatonDrawer;
import gui.viewer.AutomatonPane;

import java.awt.event.MouseEvent;

import automata.State;

/**
 * A tool that handles the creation of transitions for the regular
 * expression to FSA conversion.  This simply calls the appropriate
 * <CODE>REToFSAController</CODE> method.
 * 
 * @see gui.regular.REToFSAController#transitionCreate
 * 
 * @author Thomas Finley
 */

public class RegularToAutomatonTransitionTool extends TransitionTool {
    /**
     * Instantiates a new transition tool.
     * @param view the view where the automaton is drawn
     * @param drawer the object that draws the automaton
     * @param controller the RE to FSA controller object
     */
    public RegularToAutomatonTransitionTool
	(AutomatonPane view, AutomatonDrawer drawer,
	 REToFSAController controller) {
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
	if (state != null) {
	    controller.transitionCreate(first, state);
	}
	first = null;
	getView().repaint();
    }

    /** The regular conversion controller. */
    private REToFSAController controller;
}
