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
 
package automata.turing;

import automata.Automaton;
import automata.Transition;

/**
 * This subclass of <CODE>Automaton</CODE> is specifically for a
 * definition of a Turing machine, possibly with multiple tapes.
 * 
 * @author Thomas Finley
 */

public class TuringMachine extends Automaton {
    /**
     * Creates a 1-tape Turing machine with no states and no
     * transitions.
     */
    public TuringMachine() {
	this(1);
    }

    /**
     * Creates a Turing machine with a variable number of tapes, no
     * states, and no transitions.
     * @param tapes the number of tapes for the Turing machine
     */
    public TuringMachine(int tapes) {
	super();
	this.tapes = tapes;
    }
    
    /**
     * Returns the class of <CODE>Transition</CODE> this automaton
     * must accept.
     * @return the <CODE>Class</CODE> object for
     * <CODE>automata.tm.TMTransition</CODE>
     */
    protected Class getTransitionClass() {
	return automata.turing.TMTransition.class;
    }

    /**
     * Adds a transition to this Turing machine.
     * @param transition the transition to add
     * @throws IllegalArgumentException if this transition requires a
     * different number of tapes than required by other Turing
     * machines
     */
    public void addTransition(Transition t) {
	try {
	    int ttapes = ((TMTransition) t).tapes();
	    if (tapes == 0) tapes = ttapes;
	    if (ttapes != tapes)
		throw new IllegalArgumentException
		    ("Transition has "+ttapes+" tapes while TM has "+tapes);
	    super.addTransition(t);
	} catch (ClassCastException e) {
	    
	}
    }

    /**
     * Returns the number of tapes this Turing machine uses.
     * @return the number of tapes this Turing machine uses
     */
    public int tapes() {
	return tapes;
    }

    /** The number of tapes.  It's public for some hacky reasons
     * related to serialization. */
    public int tapes;
}
