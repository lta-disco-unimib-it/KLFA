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
 
package gui.minimize;

import gui.tree.DefaultNodeDrawer;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.Arrays;
import java.util.HashMap;

import javax.swing.tree.TreeNode;

import automata.State;
import automata.fsa.MinimizeTreeNode;

/**
 * The <CODE>MinimizeNodeDrawer</CODE> is used to draw
 * <CODE>MinimizeTreeNode</CODE> objects.
 * 
 * @see automata.fsa.MinimizeTreeNode
 * 
 * @author Thomas Finley
 */

public class MinimizeNodeDrawer extends DefaultNodeDrawer {
    /**
     * This draws a node.  The fill color is the color of the graphics
     * object before this method was called.
     * @param g the graphics object to draw the node on
     * @param node the node to draw
     */
    public void draw(Graphics2D g, TreeNode node) {
	g.fill(NODE_SHAPE);
	Color c = g.getColor();
	g.setColor(Color.black);
	g.draw(NODE_SHAPE);

	MinimizeTreeNode m = (MinimizeTreeNode) node;
	g.setColor(c);
	drawBox(g, getStateString(m), new Point2D.Float(0.0f,0.0f));
	g.setColor(c);
	drawBox(g, m.getTerminal(), new Point2D.Float(0.0f,20.0f));
	g.setColor(c);
	String label = getLabel(node);
	if (label == null) return;
	drawBox(g, label, new Point2D.Float(0.0f,-20.0f));
	g.setColor(c);
    }

    /**
     * Returns the state list of the node as a string.
     * @param node the node to get the state list for
     * @return a string of the states IDs of the node
     */
    private static String getStateString(MinimizeTreeNode node) {
	State[] states = (State[]) node.getUserObject();
	int[] ids = new int[states.length];
	for (int i=0; i<ids.length; i++) ids[i] = states[i].getID();
	Arrays.sort(ids);
	StringBuffer buffer = new StringBuffer();
	for (int i=0; i<ids.length; i++) {
	    if (i!=0) buffer.append(", ");
	    buffer.append(ids[i]);
	}
	return buffer.toString();
    }

    /**
     * Sets a label to appear above a node for a particular node.
     * @param node the node to set the label for
     * @param label the label to set for the node, or
     * <CODE>null</CODE> if there should be no label for this node
     */
    public void setLabel(TreeNode node, String label) {
	if (label == null) labels.remove(node);
	else labels.put(node, label);
    }

    /**
     * Returns the label for a particular node.
     * @param node the node to get the label for
     * @return the label for a particular node, or <CODE>null</CODE>
     * if there is no label for this node
     */
    public String getLabel(TreeNode node) {
	return (String) labels.get(node);
    }

    /**
     * Draws a string in a box centered at the given coordinates.
     * @param g the graphics object to draw upon
     * @param string the string to draw
     * @param point the 
     */
    private void drawBox(Graphics2D g, String string, Point2D point) {
	if (string.equals("")) return;

	Rectangle2D rect = g.getFontMetrics().getStringBounds(string, g);
	double x = point.getX() - rect.getWidth() / 2.0;
	double y = point.getY() - rect.getY() - rect.getHeight() / 2.0;
	
	g.fillRect((int)x-2, (int)(y+rect.getY())-2, (int)rect.getWidth()+4,
		   (int)rect.getHeight()+4);
	g.setColor(Color.black);
	g.drawRect((int)x-2, (int)(y+rect.getY())-2, (int)rect.getWidth()+4,
		   (int)rect.getHeight()+4);
	g.drawString(string, (float)x, (float)y);
	//g.drawRect();
    }

    /** The map of nodes to labels. */
    private HashMap labels = new HashMap();
}
