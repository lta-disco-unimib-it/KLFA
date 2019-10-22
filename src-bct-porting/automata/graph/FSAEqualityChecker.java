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
