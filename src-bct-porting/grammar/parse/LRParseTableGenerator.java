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
