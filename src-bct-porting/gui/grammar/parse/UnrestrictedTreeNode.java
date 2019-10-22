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
 
package gui.grammar.parse;

import javax.swing.tree.DefaultMutableTreeNode;

class UnrestrictedTreeNode extends DefaultMutableTreeNode {
    /**
     * Creates a new unrestricted tree node.
     * @param text the label for this unrestricted tree node
     */
    public UnrestrictedTreeNode(String text) {
	super(text);
	this.text = text;
    }

    /** 
     * Returns the length of this node, which is the length of the
     * text.
     */
    public int length() {
	return text.length();
    }

    /** 
     * Returns the text.
     * @return the text
     */
    public String getText() {
	return text;
    }

    /**
     * Returns a string representation of the node.
     * @return a string representation of the node
     */
    public String toString() {
	return super.toString();
	//return "("+text+", "+weight+")";
    }

    /** The text! */
    private String text;
    /** The weight. */
    public double weight = 1.0;
    /** The highest row. */
    public int highest = 0;
    /** The lowest row. */
    public int lowest = 0;
}
