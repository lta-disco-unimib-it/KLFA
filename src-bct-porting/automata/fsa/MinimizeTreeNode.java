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
 
package automata.fsa;

import javax.swing.tree.DefaultMutableTreeNode;

import automata.State;

/**
 * The Minimize Tree Node object is merely a default mutable tree node
 * with an additional field of data.  This additional field is a String.
 * You can set and get this field.  Other than that addition, Minimize
 * Tree Nodes function identically to Default Mutable Tree Nodes.  You
 * can insert them into DefaultTreeModels, etc.  This particular node
 * is used in the tree of distinguishable groups created during the 
 * DFA minimize operation.
 * 
 * @author Ryan Cavalcante
 */

public class MinimizeTreeNode extends DefaultMutableTreeNode {
    /**
     * Creates a new <CODE>MinimizeTreeNode</CODE> with 
     * <CODE>userObject</CODE> as its user object and the
     * empty string as its terminal.
     * @param userObject the node's user object
     */
    public MinimizeTreeNode(Object userObject) {
	super(userObject);
	myTerminal = "";
    }

    /**
     * Creates a new <CODE>MinimizeTreeNode</CODE> with
     * <CODE>userObject</CODE> as its user object and
     * <CODE>terminal</CODE> as its terminal.
     * @param userObject the node's user object
     * @param terminal the node's terminal
     */
    public MinimizeTreeNode(Object userObject, String terminal) {
	super(userObject);
	myTerminal = terminal;
    }

    /**
     * Sets the node's terminal field to <CODE>terminal</CODE>.
     * @param terminal the node's terminal
     */
    public void setTerminal(String terminal) {
	myTerminal = terminal;
    }

    /**
     * Returns the node's terminal field
     * @return the node's terminal field
     */
    public String getTerminal() {
	return myTerminal;
    }

    /**
     * Returns the states on this node.
     * @return the array of states for this group
     */
    public State[] getStates() {
	return (State[]) getUserObject();
    }

    /** The node's terminal field. */
    protected String myTerminal = "";

}
