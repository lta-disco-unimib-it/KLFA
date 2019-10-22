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

import java.awt.Rectangle;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import automata.State;
import automata.Transition;

/**
 * This variant of automaton pane is meant to draw the automaton only
 * with a <CODE>SelectionDrawer</CODE> that restricts the display to
 * what is selected (plus a little extra for padding).
 * 
 * @author Thomas Finley
 */

public class ZoomPane extends AutomatonPane {
    /**
     * Instantiates a <CODE>ZoomPane</CODE> for a given
     * <CODE>SelectionDrawer</CODE>.
     * @param drawer the selection drawer
     */
    public ZoomPane(SelectionDrawer drawer) {
	super(drawer);
	drawer.addChangeListener(new ChangeListener() {
		public void stateChanged(ChangeEvent e) {
		    transform = null;
		}
	    });
    }

    /**
     * Returns the bounds for the section of the automaton that should
     * be drawn.  In the case of this object this will be restricted
     * to those objects which are restricted.  If no objects are
     * selected, then the default superclass bounds are returned
     * @return the bounds for this zoom pane
     */
    protected Rectangle getAutomatonBounds() {
	SelectionDrawer d = (SelectionDrawer) drawer;
	State[] s = d.getSelected();
	Transition[] t = d.getSelectedTransitions();
	// What if nothing is selected?
	if (s.length + t.length == 0) return super.getAutomatonBounds();

	Rectangle rect = null;
	if (s.length != 0) {
	    rect = d.getBounds(s[0]);
	    for (int i=1; i<s.length; i++)
		rect.add(d.getBounds(s[i]));
	} else
	    rect = d.getBounds(t[0]);
	for (int i=0; i<t.length; i++)
	    rect.add(d.getBounds(t[i]));
	return rect;
    }
}
