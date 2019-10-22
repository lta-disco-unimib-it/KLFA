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
 
package gui.deterministic;

import gui.editor.TransitionTool;
import gui.viewer.AutomatonDrawer;
import gui.viewer.AutomatonPane;

import java.awt.event.MouseEvent;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import automata.State;

/**
 * This is a specialized transition tool that handles the expansion of
 * a group on a terminal.  The user clicks on a state, and drags
 * possibly to another state if he wants the default in the input
 * dialog to be the destination state's set of NFA states, or to empty
 * space.  The user is then prompted for the terminal to expand on,
 * and then asked to specify those states which are to appear.
 * 
 * @author Thomas Finley
 */

public class TransitionExpanderTool extends TransitionTool {
    /**
     * Instantiates a new <CODE>TransitionExpanderTool</CODE>.
     * @param view the view where the automaton is drawn
     * @param drawer the object that draws the automaton
     * @param controller the NFA to DFA controller object
     */
    public TransitionExpanderTool(AutomatonPane view, AutomatonDrawer drawer,
				  ConversionController controller) {
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
	controller.expandState(first, event.getPoint(), state);
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
	return "Expand Group on Terminal";
    }

    /** The conversion to DFA controller. */
    private ConversionController controller;
}
