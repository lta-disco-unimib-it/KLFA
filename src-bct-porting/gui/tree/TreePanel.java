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

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Point2D;

import javax.swing.JComponent;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;

/**
 * The <CODE>TreePanel</CODE> is a graphical component that draws a
 * tree using a <CODE>TreeDrawer</CODE> object.
 * 
 * @see javax.swing.tree.TreeModel
 * 
 * @author Thomas Finley
 */

public class TreePanel extends JComponent {
    /**
     * Instantiates a <CODE>TreePanel</CODE> to draws the specified
     * tree with a default <CODE>TreeDrawer</CODE> object.
     * @param tree the tree to draw
     */
    public TreePanel(TreeModel tree) {
	treeDrawer = new DefaultTreeDrawer(tree);
    }

    /**
     * Instantiates a <CODE>TreePanel</CODE> to draws a tree with a
     * given <CODE>TreeDrawer</CODE>.
     * @param drawer the tree drawer to draw a tree with
     */
    public TreePanel(TreeDrawer drawer) {
	treeDrawer = drawer;
    }

    /**
     * Returns the <CODE>TreeDrawer</CODE> for this treepanel.
     * @return the <CODE>TreeDrawer</CODE> for this treepanel
     */
    public TreeDrawer getTreeDrawer() {
	return treeDrawer;
    }

    /**
     * Sets a new <CODE>TreeDrawer</CODE> for this treepanel.
     * @param drawer the new treedrawer
     */
    public void setTreeDrawer(TreeDrawer drawer) {
	treeDrawer = drawer;
	repaint();
    }

    /**
     * Paints the component.
     * @param g the graphics object to draw on
     */
    public void paintComponent(Graphics gr) {
	Graphics2D g = (Graphics2D) gr;
	super.paintComponent(g);
	g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
			   RenderingHints.VALUE_ANTIALIAS_ON);
	g.setColor(Color.white);
	Dimension d=getSize();
	g.fillRect(0,0,d.width,d.height);
	g.setColor(Color.black);
	treeDrawer.draw((Graphics2D)g, d);
    }

    /**
     * Prints the component.
     * @param g the graphics interface for the printer device
     */
    /*public void printComponent(Grpahics g) {
	
    }*/

    /**
     * Returns the node at a particular point.
     * @param point the point to check for nodeness
     * @return the treenode at a particular point, or
     * <CODE>null</CODE> if there is no treenode at that point
     */
    public TreeNode nodeAtPoint(Point2D point) {
	return treeDrawer.nodeAtPoint(point, getSize());
    }

    /** The tree drawing object. */
    private TreeDrawer treeDrawer;
}
