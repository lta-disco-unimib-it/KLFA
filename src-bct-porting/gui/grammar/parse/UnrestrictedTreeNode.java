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
