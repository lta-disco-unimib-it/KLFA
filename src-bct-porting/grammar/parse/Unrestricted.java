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
import grammar.UnrestrictedGrammar;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * This class is a utility class for determining some facts about
 * unrestricted grammars.  As structures equivalent in power to Turing
 * machines, a brute force parse of an unrestricted grammar may, in
 * some situations, not be recognized.
 * 
 * @author Thomas Finley
 */

public class Unrestricted {
    /**
     * Dang class aint for instantiation!  Get along, lil doggie.
     */
    private Unrestricted() {}

    /**
     * Given a string and a smaller set, this returns the minimum
     * length that the string can derive as indicated by the smaller
     * set.
     * @param string the string to get the "smaller" 
     * @param smaller the "smaller" set, as returned by {@link
     * #smallerSymbols}
     */
    public static int minimumLength(String string, Set smaller) {
	int length=0;
	for (int j=0; j<string.length(); j++)
	    if (!smaller.contains(string.substring(j,j+1)))
		length++;
	return length;
    }

    /**
     * Counts the number of characters in a given string.
     * @param s the string
     * @param c the character
     * @return the number of occurances of the character in the string
     */
    private static int count(String s, char c) {
	int count = 0;
	for (int i=0; i<s.length(); i++) if (s.charAt(i) == c) count++;
	return count;
    }

    /**
     * Returns a set of those symbols in the grammar that can derive
     * some string smaller than it.  For a normal grammar, of course,
     * this would be just those variables with, but for an
     * unrestricted grammar this can include the symbol <I>b</I> and
     * <I>c</I> where <I>babca->aa</I> is a rule.  <I>a</I> is not
     * included because there are <I>a</I> terminals in the result.
     * @param grammar the grammar to find the "small" symbols for
     */
    public static Set smallerSymbols(Grammar grammar) {
	Set smaller = new HashSet();
	Production[] prods = grammar.getProductions();
	boolean added;
	do {
	    added = false;
	    for (int i=0; i<prods.length; i++) {
		String left = prods[i].getLHS();
		String right = prods[i].getRHS();
		int rightLength = minimumLength(right, smaller);
		int leftLength = minimumLength(left, smaller);
		if (leftLength > rightLength) {
		    for (int j=0; j<left.length(); j++) {
			String symbol = left.substring(j,j+1);
			char s = symbol.charAt(0);
			if (smaller.contains(symbol)) continue;
			if (count(left, s) <= count(right, s)) continue;
			smaller.add(symbol);
			added = true;
		    }
		}
	    }
	} while (added);
	return smaller;
    }

    /**
     * Returns if a grammar is truly unrestricted.
     * @param grammar the grammar to test
     * @return if a grammar is unrestricted
     */
    public static boolean isUnrestricted(Grammar grammar) {
	Production[] prods = grammar.getProductions();
	for (int i=0; i<prods.length; i++)
	    if (prods[i].getLHS().length() != 1)
		return true;
	return false;
    }

    /**
     * Given an unrestricted grammar, this will return an unrestricted
     * grammar with fewer productions that accepts the same language.
     * @param grammar the input grammar
     * @return a grammar with productions some subset of the original
     * grammar, or <CODE>null</CODE> in the special case where no
     * production with just the start variable on the LHS exists
     * (i.e. the grammar accepts no language, though if a grammar
     * accepts no language this method is NOT gauranteed to return
     * <CODE>null</CODE>)
     */
    public static UnrestrictedGrammar optimize(Grammar grammar) {
	String startVariable = grammar.getStartVariable();
	Production[] prods = grammar.getProductions();
	// Which symbols in the grammar may possibly lead to just
	// terminals?  First, we just add all those symbols with just
	// terminals on the right hand side.
	Set terminating = new HashSet();
	// Add those variables that lead to success.
	boolean[] added = new boolean[prods.length];
	for (int i=0; i<prods.length; i++) {
	    added[i] = false;
	    if (prods[i].getVariablesOnRHS().length == 0) {
		terminating.addAll(Arrays.asList(prods[i].getSymbols()));
		added[i] = true;
	    }
	}
	// Repeat
	boolean changed;
	do {
	    changed = false;
	    // If a production has only "terminating" variables, add it.
	    for (int i=0; i<prods.length; i++) {
		List l = Arrays.asList(prods[i].getVariablesOnRHS());
		if (!added[i] && terminating.containsAll(l)) {
		    terminating.addAll(Arrays.asList(prods[i].getSymbols()));
		    added[i] = changed = true;
		}
	    }
	} while (changed);
	UnrestrictedGrammar g = new UnrestrictedGrammar();
	g.setStartVariable(grammar.getStartVariable());
	// Need to find a production with just the start var on LHS.
	int i;
	for (i=0; i<prods.length; i++)
	    if (added[i] && prods[i].getLHS().equals(startVariable))
		break;
	if (i==prods.length) return null;
	g.addProduction(prods[i]);
	added[i] = false;
	for (i=0; i<prods.length; i++) if (added[i]) g.addProduction(prods[i]);
	return g;
    }
}
