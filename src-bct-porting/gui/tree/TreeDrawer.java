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
