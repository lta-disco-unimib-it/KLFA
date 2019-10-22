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
