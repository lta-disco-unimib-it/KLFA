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

import gui.editor.StateTool;
import gui.viewer.AutomatonDrawer;
import gui.viewer.AutomatonPane;

import java.awt.event.MouseEvent;

import automata.State;

/**
 * A tool that handles the creation of the final state for the FSA to
 * regular expression conversion.
 * 
 * @see gui.regular.FSAToREController
 * 
 * @author Thomas Finley
 */

public class RegularStateTool extends StateTool {
    /**
     * Instantiates a new regular state tool.
     * @param view the view that the automaton is drawn in
     * @param drawer the automaton drawer for the view
     * @param controller the controller object we report to
     */
    public RegularStateTool(AutomatonPane view, AutomatonDrawer drawer,
			    FSAToREController controller) {
	super(view, drawer);
	this.controller = controller;
    }

    /**
     * When the user clicks, one creates a state.
     * @param event the mouse event
     */
    public void mousePressed(MouseEvent event) {
	if ((state = controller.stateCreate(event.getPoint()))==null) return;
	getView().repaint();
    }

    /**
     * When the user drags, one moves the created state.
     * @param event the mouse event
     */
    public void mouseDragged(MouseEvent event) {
	if (state == null) return;
	state.setPoint(event.getPoint());
	getView().repaint();
    }

    /** The state that was created. */
    private State state = null;
    /** The controller object. */
    private FSAToREController controller;
}
