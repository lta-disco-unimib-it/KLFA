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
import automata.pda.PDATransition;
import automata.pda.PushdownAutomaton;
import file.ParseException;

/**
 * This is the transducer for encoding and decoding
 * {@link automata.pda.PushdownAutomaton} objects.
 * 
 * @author Thomas Finley
 */

public class PDATransducer extends AutomatonTransducer {
    /**
     * Creates and returns an empty PDA.
     * @param document the DOM document that is being read
     * @return an empty PDA
     */
    protected Automaton createEmptyAutomaton(Document document) {
	return new PushdownAutomaton();
    }

    /**
     * Creates and returns a transition consistent with this node.
     * @param from the from state
     * @param to the to state
     * @param node the DOM node corresponding to the transition, which
     * should contain a "read" element, a "pop" element, and a "push"
     * elements
     * @param e2t elements to text from {@link #elementsToText}
     * @return the new transition
     */
    protected Transition createTransition
	(State from, State to, Node node, Map e2t) {
	String read = (String) e2t.get(TRANSITION_READ_NAME);
	String pop = (String) e2t.get(TRANSITION_POP_NAME);
	String push = (String) e2t.get(TRANSITION_PUSH_NAME);
	if (read == null) read = ""; // Allow lambda transition.
	if (pop == null) pop = ""; // Allow lambda transition.
	if (push == null) push = ""; // Allow lambda transition.
	try {
	    return new PDATransition(from, to, read, pop, push);
	} catch (IllegalArgumentException e) {
	    throw new ParseException(e.getMessage());
	}
    }

    /**
     * Produces a DOM element that encodes a given transition.  This
     * adds the strings to read, pop, and push.
     * @param document the document to create the state in
     * @param transition the transition to encode
     * @return the newly created element that encodes the transition
     * @see file.xml.AutomatonTransducer#createTransitionElement
     */
    protected Element createTransitionElement
	(Document document, Transition transition) {
	Element te = super.createTransitionElement(document, transition);
	PDATransition t = (PDATransition) transition;
	// Add the characterizing strings for this transition.
	te.appendChild(createElement(document, TRANSITION_READ_NAME, null,
				     t.getInputToRead()));
	te.appendChild(createElement(document, TRANSITION_POP_NAME, null,
				     t.getStringToPop()));
	te.appendChild(createElement(document, TRANSITION_PUSH_NAME, null,
				     t.getStringToPush()));
	return te;
    }

    /**
     * Returns the type string for this transducer, "pda".
     * @return the string "pda"
     */
    public String getType() {
	return "pda";
    }

    /** The tag name for the read string transition elements. */
    public static final String TRANSITION_READ_NAME = "read";
    /** The tag name for the pop string transition elements. */
    public static final String TRANSITION_POP_NAME = "pop";
    /** The tag name for the push string transition elements. */
    public static final String TRANSITION_PUSH_NAME = "push";
}
