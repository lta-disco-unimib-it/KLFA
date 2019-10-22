/*******************************************************************************
 *    Copyright 2019 Fabrizio Pastore, Leonardo Mariani
 *   
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 *******************************************************************************/
 
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
