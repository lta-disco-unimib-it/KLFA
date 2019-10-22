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
import javax.swing.ImageIcon;
import javax.swing.KeyStroke;

/**
 * A tool that handles the creation of states.
 * 
 * @author Thomas Finley
 */

public class StateTool extends Tool {
    /**
     * Instantiates a new state tool.
     */
    public StateTool(AutomatonPane view, AutomatonDrawer drawer) {
	super(view, drawer);
    }

    /**
     * Gets the tool tip for this tool.
     * @return the tool tip for this tool
     */
    public String getToolTip() {
	return "State Creator";
    }

    /**
     * Returns the tool icon.
     * @return the state tool icon
     */
    protected Icon getIcon() {
	java.net.URL url = getClass().getResource("/ICON/state.gif");
	return new ImageIcon(url);
    }

    /**
     * When the user clicks, one creates a state.
     * @param event the mouse event
     */
    public void mousePressed(MouseEvent event) {
	state = getAutomaton().createState(event.getPoint());
	getView().repaint();
    }

    /**
     * When the user drags, one moves the created state.
     * @param event the mouse event
     */
    public void mouseDragged(MouseEvent event) {
	state.setPoint(event.getPoint());
	getView().repaint();
    }

    /**
     * Returns the keystroke to switch to this tool, S.
     * @return the keystroke for this tool
     */
    public KeyStroke getKey() {
	return KeyStroke.getKeyStroke('s');
    }

    /** The state that was created. */
    automata.State state = null;
}
