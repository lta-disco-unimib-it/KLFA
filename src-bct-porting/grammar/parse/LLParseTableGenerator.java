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
