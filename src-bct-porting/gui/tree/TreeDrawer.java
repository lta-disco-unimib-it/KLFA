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
import java.awt.geom.Dimension2D;
import java.awt.geom.Point2D;

import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;

/**
 * The <CODE>TreeDrawer</CODE> object is used to draw a
 * <CODE>TreeModel</CODE> in a given space.
 * 
 * @author Thomas Finley
 */

public interface TreeDrawer {
    /**
     * Draws the tree in the indicated amount of space.
     * @param g the graphics object to draw upon
     * @param size the bounds for the space the tree has to draw
     * itself in in the current graphics, assumed to be a rectangle
     * with a corner at 0,0.
     */
    public void draw(Graphics2D g, Dimension2D size);

    /**
     * Returns the <CODE>TreeModel</CODE> that this
     * <CODE>TreeDrawer</CODE> draws.
     * @return the tree model this drawer draws
     */
    public TreeModel getModel();

    /**
     * This marks the structure as uninitialized, indicating that some
     * state has changed.
     */
    public void invalidate();

    /**
     * This initializes whatever structures need to be reinitialized
     * after there is some change in the tree.
     */
    public void revalidate();

    /**
     * Returns the node at a particular point.
     * @param point the point to check for the presence of a node
     * @param size the size that the tree, if drawn, would be drawn in
     */
    public TreeNode nodeAtPoint(Point2D point, Dimension2D size);

    /**
     * Sets the node placer for this drawer.
     * @param placer the new node placer
     */
    public void setNodePlacer(NodePlacer placer);

    /**
     * Returns the node placer for this drawer.
     * @return the node placer for this drawer
     */
    public NodePlacer getNodePlacer();

    /**
     * Sets the node drawer for this drawer.
     * @param drawer the new node drawer
     */
    public void setNodeDrawer(NodeDrawer drawer);

    /**
     * Returns the node drawer for this drawer
     * @return the node drawer for this drawer
     */
    public NodeDrawer getNodeDrawer();
}
