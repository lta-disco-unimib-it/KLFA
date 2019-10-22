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
