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

/**
 * This class generates {@link grammar.parse.LLParseTable}s for
 * grammars.
 * 
 * @author Thomas Finley
 */

public class LLParseTableGenerator {
    /**
     * Can't instantiate this bad boy sparky.
     */
    private LLParseTableGenerator() { }

    /**
     * Generates a parse table for a particular grammar.
     * @param grammar the grammar for which a complete parse table
     * should be generated
     */
    public static LLParseTable generate(Grammar grammar) {
	LLParseTable table = new LLParseTable(grammar);
	Map first = Operations.first(grammar),
	    follow = Operations.follow(grammar);
	Production[] productions = grammar.getProductions();
	for (int i=0; i<productions.length; i++) {
	    String alpha = productions[i].getRHS();
	    String A = productions[i].getLHS();
	    Set firsts = Operations.first(first, alpha);
	    Iterator it = firsts.iterator();
	    while (it.hasNext()) {
		String a = (String) it.next();
		if (!a.equals("")) table.addEntry(A, a, alpha);
	    }
	    if (!firsts.contains("")) continue;
	    Set follows = (Set) follow.get(A);
	    it = follows.iterator();
	    while (it.hasNext())
		table.addEntry(A, (String) it.next(), alpha);
	}
	return table;
    }
}
