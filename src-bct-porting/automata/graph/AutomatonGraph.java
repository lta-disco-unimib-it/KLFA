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
