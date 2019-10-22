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
 * automaton aside from the moving about of states.
 * 
 * @author Thomas Finley
 */

public class ArrowNontransitionTool extends ArrowTool {
    /**
     * Instantiates a new <CODE>ArrowNontransitionTool</CODE>.
     * @param view the view the automaton is drawn in
     * @param drawer the automaton drawer
     */
    public ArrowNontransitionTool(AutomatonPane view, AutomatonDrawer drawer) {
	super(view, drawer);
    }
    
    /**
     * On a mouse click, this simply returns, 
     * @param event the mouse event
     */
    public void mouseClicked(MouseEvent event) {
	return;
	/*if (event.getClickCount() == 2) return;
	  super.mouseClicked(event);*/
    }
    
    protected boolean shouldShowStatePopup() {
	return false;
    }
}
