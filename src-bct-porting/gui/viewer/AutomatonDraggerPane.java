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
 
package gui.viewer;

import gui.editor.ArrowNontransitionTool;
import automata.Automaton;

/**
 * This is the same as an automaton pane, except that it allows the
 * user to drag states around.  This is used particularly in
 * situations where the placement of states may not be to a users
 * liking, i.e. displaying of DFAs with a random placement of some
 * states.
 * 
 * @author Thomas Finley
 */

public class AutomatonDraggerPane extends AutomatonPane {
    /**
     * Instantiates the automaton dragger pane.
     * @param drawer the automaton drawer
     */
    public AutomatonDraggerPane(AutomatonDrawer drawer) {
	super(drawer);
	init();
    }

    /**
     * Instantiates the automaton dragger pane.
     * @param drawer the automaton drawer
     * @param boolean whether or not to adapt the size of the view
     */
    public AutomatonDraggerPane(AutomatonDrawer drawer, boolean adapt) {
	super(drawer, adapt);
	init();
    }

    /**
     * Instantiates the automaton dragger pane.
     * @param automaton the automaton to draw
     */
    public AutomatonDraggerPane(Automaton automaton) {
	super(automaton);
	init();
    }
    
    /**
     * Adds what allows dragging.
     */
    private void init() {
	ArrowNontransitionTool t =
	    new ArrowNontransitionTool(this, getDrawer());
	addMouseListener(t);
	addMouseMotionListener(t);
    }
}
