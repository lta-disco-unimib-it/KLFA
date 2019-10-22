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

import java.util.Map;

import javax.swing.tree.TreeModel;

/**
 * A <CODE>NodePlacer</CODE> object is used to assign locations to
 * nodes in a tree.
 * 
 * @author Thomas Finley
 */

public interface NodePlacer {
    /**
     * Given a <CODE>TreeModel</CODE> that contains
     * <CODE>TreeNode</CODE> objects, this method returns a map from
     * <CODE>TreeNode</CODE> objects to <CODE>Dimension2D</CODE>
     * points.  The points should be in the domain ([0,1],[0,1]),
     * where (0,0) is the upper left corner and (1,0) the upper right.
     * A node placer may optionally not place an entry for each node
     * if a particular node should not be drawn.
     * @param tree the tree model
     * @param drawer the object that draws the nodes in the tree
     * @return a map from the nodes of the tree to points where those
     * nodes should be drawn
     */
    public Map placeNodes(TreeModel tree, NodeDrawer drawer);
}
