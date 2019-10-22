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

import automata.NondeterminismDetector;
import automata.Transition;

/**
 * The TTM nondeterminism detector object can be used to find all the
 * nondeterministic states in a Turing machine (i.e. all states with
 * transitions that read the same symbols on each tape).
 * 
 * @author Thomas Finley
 */

public class TMNondeterminismDetector extends NondeterminismDetector {
    /** 
     * Creates an instance of a <CODE>TMNondeterminismDetector</CODE>.
     */
    public TMNondeterminismDetector() {
    }

    /**
     * Returns true if the transitions introduce 
     * nondeterminism (e.g. the input to read from tapes one and
     * two portions of the transition labels are identical).
     * @param t1 a transition
     * @param t2 a transition
     * @return true if the transitions introduce nondeterminism
     */
    public boolean areNondeterministic(Transition t1, Transition t2) {
	TMTransition transition1 = (TMTransition) t1;
	TMTransition transition2 = (TMTransition) t2;
	
	for (int i=0; i<transition1.tapes(); i++) {
	    String read1 = transition1.getRead(i);
	    String read2 = transition2.getRead(i);
	    if (!read1.equals(read2)) return false;
	}
	return true;
    }

}
