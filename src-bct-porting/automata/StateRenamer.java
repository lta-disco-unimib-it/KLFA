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
 
package automata;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * This class contains operations to contain that all states have
 * numeric IDs from 0 up.  This will change the ID numbers of the
 * states.
 * 
 * @author Thomas Finley
 */

public class StateRenamer {
    /**
     * Renames the states for an automaton, by changing all the ID
     * numbers so that all the ID numbers go from 0 up without
     * interruption in the numeric sequence.  This will modify the
     * automaton passed in.
     * @param automaton the automaton to change the IDs of the states
     */
    public static void rename(Automaton a) {
	State[] s = a.getStates();
	int maxId = s.length-1;
	Set untaken = new HashSet(), reassign = new HashSet(Arrays.asList(s));
	for (int i=0; i<=maxId; i++)
	    untaken.add(new Integer(i));
	for (int i=0; i<s.length; i++)
	    if (untaken.remove(new Integer(s[i].getID())))
		reassign.remove(s[i]);
	// Now untaken has the untaken IDs, and reassign has the
	// states that need reassigning.
	s = (State[]) reassign.toArray(new State[0]);
	Iterator it = untaken.iterator();
	for (int i=0; i<s.length; i++) {
	    s[i].setID(((Integer) it.next()).intValue());
	}
    }
}
