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
 
package automata.vdg;

import automata.State;
import automata.Transition;

/**
 * A <CODE>VDGTransition</CODE> is a <CODE>Transition</CODE> object 
 * used by Variable Dependecy Graphs (VDGs).  They have no labels.
 *
 * @author Ryan Cavalcante
 */

public class VDGTransition extends Transition {
    /**
     * Instantiates a new <CODE>VDGTransition</CODE> object.
     * @param from the state this transition comes from.
     * @param to the state this transition goes to.
     */
    public VDGTransition(State from, State to) {
	super(from,to);
    }

    /**
     * Produces a copy of this transition with new from and to states.
     * @param from the new from state
     * @param to the new to state
     * @return a copy of this transition with the new states
     */
    public Transition copy(State from, State to) {
	return new VDGTransition(from,to);
    }
    
    /**
     * Returns a string representation of this object.  This is the
     * same as the string representation for a regular transition
     * object.
     * @see automata.Transition#toString
     * @return a string representation of this object
     */
    public String toString() {
	return super.toString();
    }
}
