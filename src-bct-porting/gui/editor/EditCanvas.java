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

import java.awt.Graphics;

/**
 * An <CODE>EditCanvas</CODE> is an extension of
 * <CODE>AutomatonPane</CODE> more suitable for use as a place where
 * automatons may be edited.
 * 
 * @author Thomas Finley
 */

public class EditCanvas extends AutomatonPane {
    /**
     * Instantiates a new <CODE>EditCanvas</CODE>.
     * @param drawer the automaton drawer
     */
    public EditCanvas(AutomatonDrawer drawer) {
	this(drawer, false);
    }

    /**
     * Instantiates a new <CODE>EditCanvas</CODE>.
     * @param drawer the automaton drawer
     * @param fit <CODE>true</CODE> if the automaton should change its
     * size to fit in the automaton; this can be very annoying
     */
    public EditCanvas(AutomatonDrawer drawer, boolean fit) {
	super(drawer, fit);
    }

    /**
     * Sets the toolbar for this edit canvas.
     * @param toolbar the toolbar for this edit canvas
     */
    public void setToolBar(ToolBar toolbar) {
	this.toolbar = toolbar;
    }

    /**
     * Paints the component.  In addition to what the automaton pane
     * does, this also calls the current tool's draw method.
     * @param g the graphics object to draw upon
     */
    public void paintComponent(Graphics g) {
	super.paintComponent(g);
	toolbar.drawTool(g);
    }

    /** The toolbar that is used for this edit canvas. */
    private ToolBar toolbar;
}
