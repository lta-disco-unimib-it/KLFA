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

import gui.editor.Tool;
import gui.viewer.AutomatonDrawer;
import gui.viewer.AutomatonPane;

import java.awt.event.MouseEvent;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.KeyStroke;

import automata.State;

/**
 * This is a tool that expands a state completely.
 * 
 * @author Thomas Finley
 */

public class StateExpanderTool extends Tool {
    /**
     * Instantiates a new state tool.
     */
    public StateExpanderTool(AutomatonPane view, AutomatonDrawer drawer,
			     ConversionController controller) {
	super(view, drawer);
	this.controller = controller;
    }

    /**
     * Gets the tool tip for this tool.
     * @return the tool tip for this tool
     */
    public String getToolTip() {
	return "State Expander";
    }

    /**
     * Returns the tool icon.
     * @return the state tool icon
     */
    protected Icon getIcon() {
	java.net.URL url = getClass().getResource("/ICON/state_expander.gif");
	return new ImageIcon(url);
    }

    /**
     * When the user clicks, one creates a state.
     * @param event the mouse event
     */
    public void mousePressed(MouseEvent event) {
	State state = getDrawer().stateAtPoint(event.getPoint());
	if (state == null) return;
	controller.expandState(state);
    }

    /**
     * Returns the keystroke to switch to this tool, S.
     * @return the keystroke for this tool
     */
    public KeyStroke getKey() {
	return KeyStroke.getKeyStroke('s');
    }
    
    /** The deterministic NFA to DFA controller. */
    private ConversionController controller;
}
