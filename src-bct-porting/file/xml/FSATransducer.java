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

package file.xml;

import java.util.Map;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import automata.Automaton;
import automata.State;
import automata.Transition;
import automata.fsa.FSATransition;
import automata.fsa.FiniteStateAutomaton;

/**
 * This is the transducer for encoding and decoding
 * {@link automata.fsa.FiniteStateAutomaton} objects.
 * 
 * @author Thomas Finley
 */

public class FSATransducer extends AutomatonTransducer {
    /**
     * Creates and returns an empty FSA.
     * @param document the DOM document that is being read
     * @return an empty FSA
     */
    protected Automaton createEmptyAutomaton(Document document) {
	return new FiniteStateAutomaton();
    }

    /**
     * Creates and returns a transition consistent with this node.
     * @param from the from state
     * @param to the to state
     * @param node the DOM node corresponding to the transition, which
     * should contain a "read" element
     * @param e2t elements to text from {@link #elementsToText}
     * @return the new transition
     */
    protected Transition createTransition
	(State from, State to, Node node, Map e2t) {
	String s = (String) e2t.get(TRANSITION_READ_NAME);
	if (s == null) s = ""; // Allow lambda transition.
	return new FSATransition(from, to, s);
    }

    /**
     * Produces a DOM element that encodes a given transition.  This
     * adds the string to read.
     * @param document the document to create the state in
     * @param transition the transition to encode
     * @return the newly created element that encodes the transition
     * @see file.xml.AutomatonTransducer#createTransitionElement
     */
    protected Element createTransitionElement
	(Document document, Transition transition) {
	Element te = super.createTransitionElement(document, transition);
	FSATransition t = (FSATransition) transition;
	// Add what the label is.
	te.appendChild(createElement(document, TRANSITION_READ_NAME, null,
				     t.getLabel()));
	return te;
    }

    /**
     * Returns the type string for this transducer, "fa".
     * @return the string "fa"
     */
    public String getType() {
	return "fa";
    }

    /** The tag name for the read string transition elements. */
    public static final String TRANSITION_READ_NAME = "read";
}
