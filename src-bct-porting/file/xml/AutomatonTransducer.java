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

import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import automata.Automaton;
import automata.State;
import automata.Transition;
import automata.graph.AutomatonGraph;
import automata.graph.GEMLayoutAlgorithm;
import automata.graph.LayoutAlgorithm;
import file.ParseException;

/**
 * This is an abstract implementation of a transducer that has methods
 * common to all automaton transducers.
 * 
 * @author Thomas Finley
 */

public abstract class AutomatonTransducer extends AbstractTransducer {
    /**
     * Returns an empty automaton of the correct type.  This method is
     * used by {@link #fromDOM}.
     * @param document the DOM document that is being read
     * @return an empty automaton
     */
    protected abstract Automaton createEmptyAutomaton(Document document);

    /**
     * Reads the states from the document and adds them to the
     * automaton.  Note that in the event of error, the automaton may
     * have been changed up until the point where the error was
     * encountered.
     * @param document the DOM document to read states from
     * @param automaton the automaton to add states to
     * @param locatedStates if not <CODE>null</CODE>, this set will be
     * filled with those states that have their X and Y coordinates
     * specified in the DOM and do not need to be laid out
     * @return a map from state identifiers to the specific state
     * @throws ParseException in the case of non-numeric, negative, or
     * duplicate IDs
     * @see #readTransitions
     */
    protected Map readStates(Document document, Automaton automaton,
			     Set locatedStates) {
	Map i2s = new java.util.HashMap();
	NodeList stateNodes =
	    document.getDocumentElement().getElementsByTagName(STATE_NAME);
	// Map state IDs to states, in an attempt to add in numeric
	// things first.  A specialized Comparator is helpful here.
	Map i2sn = new java.util.TreeMap(new Comparator() {
		public int compare(Object o1, Object o2) {
		    if (o1 instanceof Integer && !(o2 instanceof Integer))
			return -1;
		    if (o1 instanceof Integer)
			return ((Integer)o1).intValue()-
			    ((Integer)o2).intValue();
		    if (o2 instanceof Integer)
			return 1;
		    return ((Comparable)o1).compareTo(o2);
		} });
	// Create the map of ids to state nodes.
	for (int i=0; i<stateNodes.getLength(); i++) {
	    Node stateNode = stateNodes.item(i);
	    if (stateNode.getNodeType() != Node.ELEMENT_NODE) continue;
	    // Extract the ID attribute.
	    String idString = ((Element) stateNode)
		.getAttribute(STATE_ID_NAME);
	    if (idString == null)
		throw new ParseException
		    ("State without id attribute encountered!");
	    Object id = parseID(idString);
	    // Check for duplicates.
	    if (i2sn.put(id, stateNode) != null)
		throw new ParseException("The state ID "+id+" appears twice!");
	}
	// Go through the map, and turn the state nodes into states.
	Iterator it = i2sn.keySet().iterator();
	while (it.hasNext()) {
	    Object id = it.next();
	    Element stateNode = (Element) i2sn.get(id);
	    // Get the fields of this state.
	    Map e2t = elementsToText(stateNode);
	    // Create the state.
	    java.awt.Point p = new java.awt.Point();
	    boolean hasLocation = true;
	    // Try to get the X coord.
	    double x=0, y=0;
	    try {
		x=Double.parseDouble(e2t.get(STATE_X_COORD_NAME).toString());
	    } catch (NullPointerException e) {
		hasLocation = false;
	    } catch (NumberFormatException e) {
		throw new ParseException
		    ("The x coordinate "+e2t.get(STATE_X_COORD_NAME)+
		     " could not be read for state "+id+".");
	    }
	    // Try to get the Y coord.
	    try {
		y=Double.parseDouble(e2t.get(STATE_Y_COORD_NAME).toString());
	    } catch (NullPointerException e) {
		hasLocation = false;
	    } catch (NumberFormatException e) {
		throw new ParseException
		    ("The y coordinate "+e2t.get(STATE_Y_COORD_NAME)+
		     " could not be read for state "+id+".");
	    }
	    p.setLocation(x,y);
	    // Create the state.
	    State state = automaton.createState(p);
	    if (hasLocation && locatedStates != null)
		locatedStates.add(state);
	    i2s.put(id, state);
	    // Add various attributes.
	    if (e2t.containsKey(STATE_NAME_NAME))
		state.setName((String)e2t.get(STATE_NAME_NAME));
	    if (e2t.containsKey(STATE_LABEL_NAME))
		state.setLabel((String)e2t.get(STATE_LABEL_NAME));
	    if (e2t.containsKey(STATE_FINAL_NAME))
		automaton.addFinalState(state);
	    if (e2t.containsKey(STATE_INITIAL_NAME))
		automaton.setInitialState(state);
	}
	return i2s;
    }

    /**
     * Used by the {@link #readTransitions} method.  This should be
     * overridden by subclasses.
     * @param from the from state
     * @param to the to state
     * @param node the DOM node corresponding to the transition
     * @param e2t elements to text from {@link #elementsToText}
     * @return the new transition
     * @see #readTransitions
     */
    protected abstract Transition createTransition
	(State from, State to, Node node, Map e2t);

    /**
     * Reads the transitions from the document and adds them to the
     * automaton.  Note that in the event of error, the automaton may
     * have been changed up until the point where the error was
     * encountered.
     * @param document the DOM document to read transitions from
     * @param automaton the automaton to add transitions to
     * @param id2state the map of ID objects to a state
     * @throws ParseException in the case of absent from/to states
     * @see #createTransition
     * @see #readStates
     */
    protected void readTransitions
	(Document document, Automaton automaton, Map id2state) {
	NodeList tNodes = document.getDocumentElement()
	    .getElementsByTagName(TRANSITION_NAME);
	// Create the transitions.
	for (int i=0; i<tNodes.getLength(); i++) {
	    Node tNode = tNodes.item(i);
	    // Get the subelements of this transition.
	    Map e2t = elementsToText(tNode);
	    // Get the from state.
	    String fromName = (String) e2t.get(TRANSITION_FROM_NAME);
	    if (fromName == null)
		throw new ParseException("A transition has no from state!");
	    Object id = parseID(fromName);
	    State from = (State) id2state.get(id);
	    if (from == null)
		throw new ParseException("A transition is defined from "+
				   "non-existant state "+id+"!");
	    // Get the to state.
	    String toName = (String) e2t.get(TRANSITION_TO_NAME);
	    if (toName == null)
		throw new ParseException("A transition has no to state!");
	    id = parseID(toName);
	    State to = (State) id2state.get(id);
	    if (to == null)
		throw new ParseException("A transition is defined to "+
					 "non-existant state "+id+"!");
	    // Now, make the transition.
	    Transition transition = createTransition(from, to, tNode, e2t);
	    automaton.addTransition(transition);
	}
    }

    /**
     * Used to map a string means to encode a state ID to some unique
     * identifier object.
     * @param string a string that encodes a state ID
     * @return an object that is unique for this state
     * @throws ParseException if the state ID is not in a supported
     * format
     */
    protected static Object parseID(String string) {
	try {
	    int num = Integer.parseInt(string);
	    return new Integer(num);
	} catch (NumberFormatException e) {
	    return string;
	}
    }

    /**
     * Perform graph layout on the automaton if necessary.
     * @param automaton the automaton to lay out
     * @param locStates the states that have the x and y tags in the
     * DOM representation and should be kept as "isonodes" in the
     * layout algorithm
     */
    private void performLayout(Automaton automaton, Set locStates) {
	// Apply the graph layout algorithm to those states that
	// appeared without the <x> and <y> tags.
	if (locStates.size() == automaton.getStates().length) return;
	AutomatonGraph graph = new AutomatonGraph(automaton);
	LayoutAlgorithm layout = new GEMLayoutAlgorithm();
	for (int i=0; i<3; i++) // Do it a few times...
	    layout.layout(graph, locStates);
	if (locStates.size() < 2) {
	    // Make sure things don't get too large or too small in
	    // the event that sufficient reference for scaling is not
	    // present.
	    graph.moveWithinFrame(new java.awt.Rectangle(20, 20, 425, 260));
	}
	graph.moveAutomatonStates();
    }

    /**
     * Given a document, this will return the corresponding automaton
     * encoded in the DOM document.
     * @param document the DOM document to convert
     * @return the {@link automata.fsa.FiniteStateAutomaton} instance
     */
    public java.io.Serializable fromDOM(Document document) {
	Automaton a = createEmptyAutomaton(document);
	Set nonlocStates = new java.util.HashSet();
	// Read the states and transitions.
	readTransitions(document, a, readStates(document, a, nonlocStates));
	// Do the layout if necessary.
	performLayout(a, nonlocStates);
	return a;
    }

    /**
     * Produces a DOM element that encodes a given state.
     * @param document the document to create the state in
     * @param state the state to encode
     * @return the newly created element that encodes the state
     * @see #createTransitionElement
     * @see #toDOM
     */
    protected Element createStateElement(Document document, State state) {
	// Start the creation of the state tag.
	Element se = createElement(document, STATE_NAME, null, null);
	se.setAttribute(STATE_ID_NAME, ""+state.getID());
	// Encode position.
	se.appendChild(createElement(document, STATE_X_COORD_NAME, null,
				     ""+state.getPoint().getX()));
	se.appendChild(createElement(document, STATE_Y_COORD_NAME, null,
				     ""+state.getPoint().getY()));
	// Encode label, if set.
	if (state.getLabel() != null)
	    se.appendChild(createElement(document, STATE_LABEL_NAME, null,
					 state.getLabel()));
	// Encode the name, if set.
	if (!state.getName().equals("q"+state.getID()))
	    se.appendChild(createElement(document, STATE_NAME_NAME, null,
					 state.getName()));
	// Encode whether the state is initial.
	Automaton a = state.getAutomaton();
	if (a.getInitialState() == state)
	    se.appendChild(createElement(document, STATE_INITIAL_NAME,
					 null, null));
	// Encode whether the state is final.
	if (a.isFinalState(state))
	    se.appendChild(createElement(document, STATE_FINAL_NAME,
					 null, null));
	// Return the completed state encoding element.
	return se;
    }

    /**
     * Produces a DOM element that encodes a given transition.  This
     * implementation creates a transition element with only the
     * "from" and "to" elements inserted.  The intention is that
     * subclasses will use this to get the minimal transition element,
     * and fill in whatever else is required themselves.
     * @param document the document to create the state in
     * @param transition the transition to encode
     * @return the newly created element that encodes the state
     * @see #createStateElement
     * @see #toDOM
     */
    protected Element createTransitionElement
	(Document document, Transition transition) {
	// Start the creation of the transition.
	Element te = createElement(document, TRANSITION_NAME, null, null);
	// Encode the from state.
	te.appendChild(createElement(document, TRANSITION_FROM_NAME, null,
				     ""+transition.getFromState().getID()));
	// Encode the to state.
	te.appendChild(createElement(document, TRANSITION_TO_NAME, null,
				     ""+transition.getToState().getID()));
	// Return the completed transition encoding element.
	return te;
    }

    /**
     * Given a JFLAP automaton, this will return the corresponding DOM
     * encoding of the structure.
     * @param structure the JFLAP automaton to encode
     * @return a DOM document instance
     */
    public Document toDOM(java.io.Serializable structure) {
	Automaton automaton = (Automaton) structure;
	Document doc = newEmptyDocument();
	Element se = doc.getDocumentElement();
	// Add the states as subelements of the structure element.
	State[] states = automaton.getStates();
	if (states.length > 0)
	    se.appendChild(createComment(doc, COMMENT_STATES));
	for (int i=0; i<states.length; i++)
	    se.appendChild(createStateElement(doc, states[i]));
	// Add the transitions as subelements of the structure element.
	Transition[] transitions = automaton.getTransitions();
	if (transitions.length > 0)
	    se.appendChild(createComment(doc, COMMENT_TRANSITIONS));
	for (int i=0; i<transitions.length; i++)
	    se.appendChild(createTransitionElement(doc, transitions[i]));
	// Return the completed document.
	return doc;
    }

    /** The tag name for individual state elements. */
    public static final String STATE_NAME = "state";
    /** The attribute name for the state ID. */
    public static final String STATE_ID_NAME = "id";
    /** The tag name for the X coordinate. */
    public static final String STATE_X_COORD_NAME = "x";
    /** The tag name for the Y coordinate. */
    public static final String STATE_Y_COORD_NAME = "y";
    /** The tag name for the optional label of the state. */
    public static final String STATE_LABEL_NAME = "label";
    /** The tag name for the optional special name of the state. */
    public static final String STATE_NAME_NAME = "name";
    /** The tag name for the final state. */
    public static final String STATE_FINAL_NAME = "final";
    /** The tag name for the optional special name of the state. */
    public static final String STATE_INITIAL_NAME = "initial";

    /** The tag name for the individual transition elements. */
    public static final String TRANSITION_NAME = "transition";
    /** The tag name for the from state ID. */
    public static final String TRANSITION_FROM_NAME = "from";
    /** The tag name for the to state ID. */
    public static final String TRANSITION_TO_NAME = "to";

    /** The comment for the list of states. */
    private static final String COMMENT_STATES = "The list of states.";
    /** The comment for the list of transitions. */
    private static final String COMMENT_TRANSITIONS =
	"The list of transitions.";
}
