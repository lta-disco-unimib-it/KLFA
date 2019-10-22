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

import gui.editor.Tool;
import gui.viewer.AutomatonDrawer;
import gui.viewer.AutomatonPane;

import java.awt.event.MouseEvent;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.KeyStroke;

import automata.State;

/**
 * A tool that handles the removal of states.  This calls the {@link
 * FSAToREController#stateCollapse} method on the clicked state.
 * 
 * @see gui.regular.FSAToREController#transitionCreate
 * 
 * @author Thomas Finley
 */

public class StateCollapseTool extends Tool {
    /**
     * Instantiates a new transition tool.
     * @param view the view where the automaton is drawn
     * @param drawer the object that draws the automaton
     * @param creator the transition creator for the type of automata
     * we are editing
     */
    public StateCollapseTool(AutomatonPane view, AutomatonDrawer drawer,
			     FSAToREController controller) {
	super(view, drawer);
	this.controller = controller;
    }

    /**
     * Gets the tool tip for this tool.
     * @return the tool tip for this tool
     */
    public String getToolTip() {
	return "State Collapser";
    }

    /**
     * Returns the tool icon.
     * @return the state tool icon
     */
    protected Icon getIcon() {
	java.net.URL url = getClass().getResource("/ICON/state_collapse.gif");
	return new ImageIcon(url);
    }

    /**
     * Returns the keystroke to switch to this tool, C.
     * @return the keystroke for this tool
     */
    public KeyStroke getKey() {
	return KeyStroke.getKeyStroke('o');
    }

    /**
     * When we press the mouse, the convert controller should be told that
     * transitions are done.
     * @param event the mouse event
     */
    public void mousePressed(MouseEvent event) {
	State s = getDrawer().stateAtPoint(event.getPoint());
	if (s != null) {
	    controller.stateCollapse(s);
	}
    }

    /** The regular conversion controller. */
    private FSAToREController controller;
}
