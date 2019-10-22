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

import gui.SuperMouseAdapter;
import gui.viewer.AutomatonPane;

import java.awt.event.MouseEvent;

/**
 * This is a simple mouse adapter that simply forwards all mouse
 * actions along to another adapter set by the <CODE>setAdapter</CODE>
 * method.
 * 
 * @author Thomas Finley
 */

public class ToolAdapter extends SuperMouseAdapter {
    /**
     * Instantiates a tool adapter.
     * @param pane the automaton pane this tool adapter is listening
     * to
     */
    public ToolAdapter(AutomatonPane pane) {
	this.pane = pane;
    }

    /**
     * Sets a new adapter to be the adapter.
     * @param adapter the new adapter
     */
    public void setAdapter(SuperMouseAdapter adapter) {
	this.adapter = adapter;
    }

    /**
     * Invoked when a mouse button is clicked on a component.
     * @param event the MouseEvent to process
     */
    public void mouseClicked(MouseEvent event) {
	adapter.mouseClicked(event);
    }

    /**
     * Invoked when the mouse enters a component.
     * @param event the MouseEvent to process
     */
    public void mouseEntered(MouseEvent event) {
	adapter.mouseEntered(event);
    }

    /**
     * Invoked when the mouse exits a component.
     * @param event the MouseEvent to process
     */
    public void mouseExited(MouseEvent event) {
	adapter.mouseExited(event);
    }

    /**
     * Invoked when a mouse button is held down on a component.
     * @param event the MouseEvent to process
     */
    public void mousePressed(MouseEvent event) {
	adapter.mousePressed(event);
    }

    /**
     * Invoked when a mouse button is released on a component.
     * @param event the MouseEvent to process
     */
    public void mouseReleased(MouseEvent event) {
	adapter.mouseReleased(event);
    }

    /**
     * Invoked when a mouse is dragged over this component with a button
     * down.
     * @param event the MouseEvent to process
     */
    public void mouseDragged(MouseEvent event) {
	adapter.mouseDragged(event);
    }

    /**
     * Invoked when a mouse is moved over this component with no buttons
     * down.
     * @param event the MouseEvent to process
     */
    public void mouseMoved(MouseEvent event) {
	adapter.mouseMoved(event);
    }

    /** The current adapter */
    private SuperMouseAdapter adapter = new SuperMouseAdapter() {};
    /** The automaton pane that this tool adapter is part of. */
    private AutomatonPane pane = null;
}
