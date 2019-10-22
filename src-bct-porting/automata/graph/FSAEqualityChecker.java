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

import automata.fsa.FiniteStateAutomaton;
import automata.fsa.Minimizer;
import automata.fsa.NFAToDFA;

/**
 * This determines if two FSAs accept the same language.
 * 
 * @author Thomas Finley
 */

public class FSAEqualityChecker {
    /**
     * Checks if two FSAs accept the same language.
     * @param fsa1 the first finite state automaton
     * @param fsa2 the second finite state automaton
     * @return <CODE>true</CODE> if <CODE>fsa1</CODE> and
     * <CODE>fsa2</CODE> accept the same language, <CODE>false</CODE>
     * if they they do not
     */
    public boolean equals(FiniteStateAutomaton fsa1,
			  FiniteStateAutomaton fsa2) {
	// Clone for safety.
	fsa1 = (FiniteStateAutomaton) fsa1.clone();
	fsa2 = (FiniteStateAutomaton) fsa2.clone();

	// Make sure they're DFAs.
	fsa1 = nfaConverter.convertToDFA(fsa1);
	fsa2 = nfaConverter.convertToDFA(fsa2);
	// Minimize the DFAs.
	minimizer.initializeMinimizer();
	fsa1 = (FiniteStateAutomaton)minimizer.getMinimizeableAutomaton(fsa1);
	javax.swing.tree.DefaultTreeModel tree =
	    minimizer.getDistinguishableGroupsTree(fsa1);
	fsa1 = minimizer.getMinimumDfa(fsa1, tree);

	minimizer.initializeMinimizer();
	fsa2 = (FiniteStateAutomaton)minimizer.getMinimizeableAutomaton(fsa2);
	tree = minimizer.getDistinguishableGroupsTree(fsa2);
	fsa2 = minimizer.getMinimumDfa(fsa2, tree);

	// Check the minimized DFAs to see if they are the same.
	return checker.equals(fsa1, fsa2);
    }

    /** The equality checker. */
    private static DFAEqualityChecker checker = new DFAEqualityChecker();
    /** The converter for an NFA to a DFA. */
    private static NFAToDFA nfaConverter = new NFAToDFA();
    /** That which minimizes a DFA. */
    private static Minimizer minimizer = new Minimizer();
}
