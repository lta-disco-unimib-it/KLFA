/* -- JFLAP 4.0 --
 *
 * Copyright information:
 *
 * Susan H. Rodger, Thomas Finley
 * Computer Science Department
 * Duke University
 * April 24, 2003
 * Supported by National Science Foundation DUE-9752583.
 *
 * Copyright (c) 2003
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms are permitted
 * provided that the above copyright notice and this paragraph are
 * duplicated in all such forms and that any documentation,
 * advertising materials, and other materials related to such
 * distribution and use acknowledge that the software was developed
 * by the author.  The name of the author may not be used to
 * endorse or promote products derived from this software without
 * specific prior written permission.
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND WITHOUT ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, WITHOUT LIMITATION, THE IMPLIED
 * WARRANTIES OF MERCHANTIBILITY AND FITNESS FOR A PARTICULAR PURPOSE.
 */
 
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
