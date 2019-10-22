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
 
package automata.graph;

import java.awt.Point;
import java.awt.geom.Point2D;

import automata.Automaton;
import automata.State;
import automata.Transition;

/**
 * This extension of the graph makes it easier for a graph to be built
 * from an automaton.  The vertex objects used are the states
 * themselves.  The analogy to an edge is, naturally, transitions.
 * This class of graph has a method to, once graph vertices are moved
 * around, to synchronize the locations of the automaton states to the
 * positions of the graph nodes, thus making graph layout algorithms
 * simpler to apply.
 * 
 * @author Thomas Finley
 */

public class AutomatonGraph extends Graph {
    /**
     * Constructures a graph using an automaton.
     * @param automaton the automaton to build the graph from
     */
    public AutomatonGraph(Automaton automaton) {
	super();
	State[] states = automaton.getStates();
	Transition[] transitions = automaton.getTransitions();
	for (int i=0; i<states.length; i++)
	    addVertex(states[i], states[i].getPoint());
	for (int i=0; i<transitions.length; i++)
	    addEdge(transitions[i].getFromState(), transitions[i].getToState());
    }

    /**
     * Moves the states of the underlying automaton to synchronize
     * with the positions of the corresponding vertices in the graph.
     */
    public void moveAutomatonStates() {
	Object[] vertices = vertices();
	for (int i=0; i<vertices.length; i++) {
	    State state = (State) vertices[i];
	    Point2D point = pointForVertex(state);
	    state.setPoint(new Point((int)point.getX(), (int)point.getY()));
	}
    }
}
