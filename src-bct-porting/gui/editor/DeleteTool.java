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
 
package gui.editor;

import gui.viewer.AutomatonDrawer;
import gui.viewer.AutomatonPane;

import java.awt.event.MouseEvent;

import javax.swing.Icon;
import javax.swing.KeyStroke;

import automata.State;
import automata.Transition;

/**
 * A tool that handles the deletion of states and transitions.
 * 
 * @author Thomas Finley
 */

public class DeleteTool extends Tool {
    /**
     * Instantiates a new delete tool.
     */
    public DeleteTool(AutomatonPane view, AutomatonDrawer drawer) {
	super(view, drawer);
    }

    /**
     * Gets the tool tip for this tool.
     * @return the tool tip for this tool
     */
    public String getToolTip() {
	return "Deleter";
    }

    /**
     * Returns the tool icon.
     * @return the delete tool icon
     */
    protected Icon getIcon() {
	java.net.URL url = getClass().getResource("/ICON/delete.gif");
	return new javax.swing.ImageIcon(url);
    }

    /**
     * Returns the key stroke to switch to this tool, the D key.
     * @return the key stroke to switch to this tool
     */
    public KeyStroke getKey() {
	return KeyStroke.getKeyStroke('d');
    }

    /**
     * When the user clicks, we delete either the state or, if no
     * state, the transition found at this point.  If there's nothing
     * at this point, nothing happens.
     * @param event the mouse event
     */
    public void mouseClicked(MouseEvent event) {
	State state = getDrawer().stateAtPoint(event.getPoint());
	if (state != null) {
	    getAutomaton().removeState(state);
	    getView().repaint();
	    return;
	}
	Transition trans = getDrawer().transitionAtPoint(event.getPoint());
	if (trans != null) {
	    getAutomaton().removeTransition(trans);
	    getView().repaint();
	}
    }
}
