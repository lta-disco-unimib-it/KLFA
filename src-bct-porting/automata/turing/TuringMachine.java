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
