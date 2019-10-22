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
