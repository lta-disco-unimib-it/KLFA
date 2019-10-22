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
 
package grammar.parse;

import grammar.Grammar;
import grammar.Production;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import automata.State;
import automata.Transition;
import automata.fsa.FSATransition;
import automata.fsa.FiniteStateAutomaton;

/**
 * This class generates {@link grammar.parse.LRParseTable}s.  The
 * intention is that this shall be used once the item goto graph is
 * generated.
 * 
 * @author Thomas Finley
 */

public abstract class LRParseTableGenerator {
    /**
     * Generates an LR parse table.
     * @param grammar the augmented grammar
     * @param gotoGraph the FSA that represents the completed goto graph
     * @param stateToItems the mapping of states to items
     * @param itemsToState the mapping of items to states
     * @param followSets the mapping of variables to follow sets
     */
    public static LRParseTable generate
	(Grammar grammar, FiniteStateAutomaton gotoGraph,
	 Map stateToItems, Map itemsToState, Map followSets) {
	LRParseTable pt = new LRParseTable(grammar, gotoGraph) {
		public boolean isCellEditable(int row,int column) {
		    return false;
		}
	    };
	Transition[] ts = gotoGraph.getTransitions();
	Production[] ps = grammar.getProductions();
	for (int i=0; i<ts.length; i++) {
	    FSATransition t = (FSATransition) ts[i];
	    if (grammar.isVariable(t.getLabel())) {
		// Is a move.
		pt.appendValueAt(""+t.getToState().getID(),
				 t.getFromState().getID(), t.getLabel());
	    } else {
		// Is a shift.
		pt.appendValueAt("s"+t.getToState().getID(),
				 t.getFromState().getID(), t.getLabel());
	    }
	}
	// Find the acceptance and reduction.
	State[] finals = gotoGraph.getFinalStates();
	for (int i=0; i<finals.length; i++) {
	    Set items = (Set) stateToItems.get(finals[i]);
	    Iterator it = items.iterator();
	    while (it.hasNext()) {
		Production p = (Production) it.next();
		if (p.getLHS().length() == 2) {
		    // This is the S' production.
		    if (p.getRHS().length() == 2 &&
			p.getRHS().charAt(1) == '_')
			pt.appendValueAt("acc", finals[i].getID(), "$");
		    continue;
		}
		if (p.getRHS().endsWith("_")) {
		    Production p2 = new Production
			(p.getLHS(), p.getRHS().substring
			 (0,p.getRHS().length()-1));
		    int j=0;
		    while (!p2.equals(ps[j])) j++;
		    Set follow = (Set) followSets.get(p.getLHS());
		    Iterator fit = follow.iterator();
		    while (fit.hasNext()) {
			String followSymbol = (String) fit.next();
			pt.appendValueAt("r"+j, finals[i].getID(),
					 followSymbol);
		    }
		}
	    }
	}
	return pt;
    }
}
