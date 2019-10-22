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
import java.awt.Point;
import java.awt.Stroke;
import java.awt.event.MouseEvent;

import javax.swing.Icon;
import javax.swing.KeyStroke;

import automata.State;

/**
 * A tool that handles the creation of transitions.
 * 
 * @author Thomas Finley
 */

public class TransitionTool extends Tool {
    /**
     * Instantiates a new transition tool.
     * @param view the view where the automaton is drawn
     * @param drawer the object that draws the automaton
     * @param creator the transition creator for the type of automata
     * we are editing
     */
    public TransitionTool(AutomatonPane view, AutomatonDrawer drawer,
			  TransitionCreator creator) {
	super(view, drawer);
	this.creator = creator;
    }

    /**
     * Instantiates a new transition tool.  The transition creator is
     * obtained from whatever is returned from the transition creator
     * factory.
     * @see gui.editor.TransitionCreator#creatorForAutomaton
     */
    public TransitionTool(AutomatonPane view, AutomatonDrawer drawer) {
	super(view, drawer);
	this.creator =
	    TransitionCreator.creatorForAutomaton(getAutomaton(), getView());
    }

    /**
     * Gets the tool tip for this tool.
     * @return the tool tip for this tool
     */
    public String getToolTip() {
	return "Transition Creator";
    }

    /**
     * Returns the tool icon.
     * @return the transition tool icon
     */
    protected Icon getIcon() {
	java.net.URL url = getClass().getResource("/ICON/transition.gif");
	return new javax.swing.ImageIcon(url);
    }

    /**
     * When the user presses the mouse, we detect if there is a state
     * here.  If there is, then this may be the start of a transition.
     * @param event the mouse event
     */
    public void mousePressed(MouseEvent event) {
	first = getDrawer().stateAtPoint(event.getPoint());
	if (first == null) return;
	hover = first.getPoint();
    }

    /**
     * When the mouse is dragged someplace, updates the "hover" point
     * so the line from the state to the mouse can be drawn.
     * @param event the mouse event
     */
    public void mouseDragged(MouseEvent event) {
	if (first == null) return;
	hover = event.getPoint();
	getView().repaint();
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
	    creator.createTransition(first, state);
	    /*if (t != null)
	      getAutomaton().addTransition(t);*/
	}
	first = null;
	getView().repaint();
    }

    /**
     * Draws the line from the first clicked state to the drag point,
     * if indeed we are even in a drag.
     * @param g the graphics object to draw upon
     */
    public void draw(Graphics g) {
	if (first == null) return;
	java.awt.Graphics2D g2 = (java.awt.Graphics2D) g;
	Stroke s = g2.getStroke();
	g2.setStroke(STROKE);
	g2.setColor(COLOR);
	g2.drawLine(first.getPoint().x, first.getPoint().y, hover.x, hover.y);
	g2.setStroke(s);
    }

    /**
     * Returns the keystroke to switch to this tool, the T key.
     * @return the keystroke to switch to this tool
     */
    public KeyStroke getKey() {
	return KeyStroke.getKeyStroke('t');
    }

    /** The first clicked state. */
    protected State first;
    /** The point over which we are hovering. */
    protected Point hover;
    /** The transition creator. */
    protected TransitionCreator creator;

    /** The stroke object that draws the lines. */
    private static Stroke STROKE = new java.awt.BasicStroke(2.4f);
    /** The color for the line. */
    private static java.awt.Color COLOR = new java.awt.Color(.5f,.5f,.5f,.5f);
}
