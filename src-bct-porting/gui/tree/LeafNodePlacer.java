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

import java.awt.geom.Point2D;
import java.util.HashMap;
import java.util.Map;

import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;

/**
 * A <CODE>LeafNodePlacer</CODE> places leaves so that they are evenly
 * spaced horizontally.
 * 
 * @author Thomas Finley
 */
   
public class LeafNodePlacer implements NodePlacer {
    /**
     * Given a <CODE>TreeModel</CODE> that contains
     * <CODE>TreeNode</CODE> objects, this method returns a map from
     * all <CODE>TreeNode</CODE> objects to <CODE>Point2D</CODE>
     * points.  This placer works according to a rather simple
     * algorithm that places all nodes at a particular depth in a tree
     * at regular intervals.
     * @param tree the tree model
     * @param drawer the object that draws the nodes in the tree
     * @return a map from the nodes of the tree to points where those
     * nodes should be drawn
     */
    public Map placeNodes(TreeModel tree, NodeDrawer drawer) {
	HashMap nodeToPoint = new HashMap();
	int[] width = Trees.width(tree);
	TreeNode[] leaves = Trees.leaves(tree);
	int depth = Trees.depth(tree);
	setPoints((TreeNode)tree.getRoot(), depth, 0, leaves.length,
		  new int[] {0}, nodeToPoint);
	return nodeToPoint;
    }
    
    /**
     * This will place the nodes.  Oooo.
     * @param node the current node being placed
     * @param depth the depth of the tree
     * @param thisDepth the depth of this node
     * @param leaves the total number of leaves
     * @param sofar the number of leaves placed sofar
     * @param nodeToPoint the mapping of nodes to points
     */
    private void setPoints(TreeNode node, int depth, int thisDepth,
			   int leaves, int[] sofar, Map nodeToPoint) {
	TreeNode[] children = Trees.children(node);
	float y = (float) (thisDepth+1) / (float) (depth+2);
	if (children.length == 0) {
	    // It is a leaf!
	    float x = (float) (sofar[0]+1) / (float) (leaves+1);
	    nodeToPoint.put(node, new Point2D.Float(x,y));
	    sofar[0]++;
	    return;
	}
	// Not a leaf!
	for (int i=0; i<children.length; i++)
	    setPoints(children[i],depth,thisDepth+1,leaves,sofar,nodeToPoint);
	Point2D leftmost = (Point2D) nodeToPoint.get(children[0]);
	Point2D rightmost = (Point2D) nodeToPoint.get
	    (children[children.length-1]);
	float x = (float) ((leftmost.getX()+rightmost.getX())/2.0);
	nodeToPoint.put(node, new Point2D.Float(x,y));
    }
}
