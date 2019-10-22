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

package file.xml;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * This prettier converts DOMs to a prettier form.  When creating a
 * new raw DOM, all the tags run together.  However, this is not very
 * pretty.  To correct this, text tags are inserted every so often to
 * create at most one element per line, where everything is properly
 * indented, just as one would see in an XML document formated by
 * hand.
 * 
 * @author Thomas Finley
 */

public class DOMPrettier {
    /**
     * Recursive private helper method that inserts indenting text
     * nodes.
     * @param dom the DOM document
     * @param indent the indent string sofar
     * @param node the node that we are recursing on
     * @return if the last node encountered was a text node
     */
    private static boolean makePretty(Document dom, String indent, Node node) {
	if (node.getNodeType() == Node.TEXT_NODE) return true;
	try {
	    node.getParentNode().insertBefore
		(dom.createTextNode(indent), node);
	} catch (DOMException e) {
	    // This occurs when the parent is the document, in which
	    // case we don't want to insert the indentation anyway, so
	    // this is perfectly fine.
	}
	NodeList list = node.getChildNodes();
	// Not good to insert nodes while accessing the list.  :)
	Node[] nodes = new Node[list.getLength()];
	for (int i=0; i<list.getLength(); i++)
	    nodes[i] = list.item(i);
	
	boolean lastChild = true; // If no children, don't want text inside.
	String newIndent = indent+INDENT;
	for (int i=0; i<nodes.length; i++)
	    lastChild = makePretty(dom, newIndent, nodes[i]);
	if (!lastChild)
	    node.appendChild(dom.createTextNode(indent));
	return false;
    }

    /**
     * Pretty-fies a DOM by inserting whitespace text nodes at
     * appropriate places.  This modifies the DOM itself.
     * @param dom the DOM document to make pretty
     */
    public static void makePretty(Document dom) {
	String newline = System.getProperty("line.separator");
	makePretty(dom, newline, dom.getDocumentElement());
    }

    /** The changing indentation string.  Whenever a new level of
     * indent is reached, this is prepended. */
    public static final String INDENT = "\t";
}
