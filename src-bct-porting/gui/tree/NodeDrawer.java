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
 
package gui.tree;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import javax.swing.tree.TreeNode;

/**
 * A <CODE>NodeDrawer</CODE> handles the drawing of nodes in a tree.
 * 
 * @author Thomas Finley
 */

public interface NodeDrawer {
    /**
     * Draws a passed in node with the "center" at location (0,0).
     * The color of the graphics passed must be the "main color" of
     * the node drawn.
     * @param g the graphics object to draw upon
     * @param node the node to draw
     */
    public void draw(Graphics2D g, TreeNode node);

    /**
     * Returns the maximum size a node can take up.  The point (0,0)
     * in the rectangle returned should be where the center of the
     * node should be.
     * @return the maximum space a node can take up when drawn
     * @see #nodeSize(javax.swing.tree.TreeNode)
     */
    public Rectangle2D nodeSize();

    /**
     * Returns the size of a particular node as drawn, given a node.
     * The point (0,0) in the rectangle returned should be where the
     * center of the node should be.
     * @param node the node
     * @return the space this node will take when drawn
     * @see #nodeSize()
     */
    public Rectangle2D nodeSize(TreeNode node);

    /**
     * Returns if a given point is "on" a given node, assuming that
     * the node is in the same position as the <CODE>draw</CODE>
     * function would draw it.
     * @param node the node to test for containment of a point
     * @param x the x coordinate
     * @param y the y coordinate
     * @return <CODE>true</CODE> if the point (x,y) is on the node,
     * <CODE>false</CODE> otherwise
     * @see #draw
     */
    public boolean onNode(TreeNode node, double x, double y);
}
