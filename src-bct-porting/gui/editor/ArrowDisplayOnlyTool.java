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
    
/**
 * This extension of the arrow tool does not allow the editing of an
 * automaton, even for moving states, but does allow for the changing
 * of certain view options (displaying of labels and things of that
 * nature).
 * 
 * @author Thomas Finley
 */

public class ArrowDisplayOnlyTool extends ArrowNontransitionTool {
    /**
     * Instantiates a new <CODE>ArrowDisplayOnlyTool</CODE>.
     * @param view the view the automaton is drawn in
     * @param drawer the automaton drawer
     */
    public ArrowDisplayOnlyTool(AutomatonPane view, AutomatonDrawer drawer) {
	super(view, drawer);
    }

    /**
     * We don't want anything happening when the mouse is dragged.
     * This method simply returns without doing anything.
     * @param event the dragging mouse event
     */
    public void mouseDragged(MouseEvent event) {
	return;
    }
}
