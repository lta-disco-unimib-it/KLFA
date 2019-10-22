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
 
package grammar.lsystem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * Given an L-system, this will expand the L-system, creating the vast
 * lists of symbols generated by successive expansions of the
 * L-system.
 * 
 * @author Thomas Finley
 */

public class Expander {
    /**
     * Instantiates a new expander, with a randomly selected random
     * seed used for resolving ambiguity.
     * @param lsystem the lsystem to make an expander for
     */
    public Expander(LSystem lsystem) {
	this(lsystem, RANDOM.nextLong());
    }

    /**
     * Instantiates a new expander, with a specified random seed.
     * @param lsystem the lsystem to make an expander for
     * @param seed the seed for the randomizer
     */
    public Expander(LSystem lsystem, long seed) {
	stochiastic = new Random(seed);
	this.lsystem = lsystem;
	cachedExpansions.add(lsystem.getAxiom());
	initializeContexts();
    }

    /**
     * Returns the expansion at a given level of recursion.  An input
     * of 0 will return the axiom (i.e., no replacement or recursion
     * has occurred).
     * @param level the level of recursion to sink to
     * @return the list of string symbols
     * @throws IllegalArgumentException if the level is less than 0
     */
    public List expansionForLevel(int level) {
	if (level < 0)
	    throw new IllegalArgumentException
		("Recursion level "+level+" impossible!");
	if (level < cachedExpansions.size())
	    return (List) cachedExpansions.get(level);
	List lastOne = (List) cachedExpansions.get(cachedExpansions.size()-1);
	for (int i=cachedExpansions.size(); i<=level; i++)
	    cachedExpansions.add(lastOne = expand(lastOne));
	return lastOne;
    }

    /**
     * Does the expansion of a given string list thing.
     * @param symbols the list of symbols to expand
     * @return the expansion of the passed in symbols
     */
    private List expand(List symbols) {
	if (contexts == null)
	    return expandNoContext(symbols);
	return expandContext(symbols);
    }

    /**
     * Does the expansion of a given string list thing given that we
     * have no "contexts" to worry about.
     * @param symbols the list of symbols to expand
     * @return the expansion of the passed in symbols
     */
    private List expandNoContext(List symbols) {
	List ne = new ArrayList();
	for (int i=0; i<symbols.size(); i++) {
	    String s = (String) symbols.get(i);
	    List[] replacements = lsystem.getReplacements(s);
	    List replacement = null;
	    switch (replacements.length) {
	    case 0:
		// This cannot be replaced, so we skip to the next symbol.
		ne.add(s);
		continue;
	    case 1:
		// There is only one replacement possibility.
		replacement = replacements[0];
		break;
	    default:
		// If there's more than one possibility, we choose one
		// nearly at random.
		replacement = replacements
		    [stochiastic.nextInt(replacements.length)];
		break;
	    }
	    Iterator it2 = replacement.iterator();
	    while (it2.hasNext()) ne.add(it2.next()); // Add replacements!
	}
	return ne;
    }

    /**
     * Does the expansion of a given string list thing given that we
     * have contexts.  This can be computationally more expensive,
     * though not horribly so.
     * @param symbols the list of symbols to expand
     * @return the expansion of the passed in symbols
     */
    private List expandContext(List symbols) {
	List ne = new ArrayList();
	for (int i=0; i<symbols.size(); i++) {
	    String s = (String) symbols.get(i);
	    ArrayList replacementsList = new ArrayList();
	    for (int j=0; j<contexts.length; j++) {
		List[] l = contexts[j].matches(symbols, i);
		for (int k=0; k<l.length; k++)
		    replacementsList.add(l[k]);
	    }
	    List[] replacements = 
		(List[])replacementsList.toArray(EMPTY_ARRAY);
	    List replacement = null;
	    switch (replacements.length) {
	    case 0:
		// This cannot be replaced, so we skip to the next symbol.
		ne.add(s);
		continue;
	    case 1:
		// There is only one replacement possibility.
		replacement = replacements[0];
		break;
	    default:
		// If there's more than one possibility, we choose one
		// nearly at random.
		replacement = replacements
		    [stochiastic.nextInt(replacements.length)];
		break;
	    }
	    Iterator it2 = replacement.iterator();
	    while (it2.hasNext()) ne.add(it2.next()); // Add replacements!
	}
	return ne;
    }

    /**
     * Initializes the contexts.
     */
    private final void initializeContexts() {
	Iterator symbolIt = lsystem.getSymbolsWithReplacements().iterator();
	Set searchLengths = new HashSet();
	Integer one = new Integer(1); // It'll be quite common...
	ArrayList contextsList = new ArrayList();
	boolean hasContexts = false;
	// Build the contexts.
	while (symbolIt.hasNext()) {
	    String symbol = (String) symbolIt.next();
	    List tokens = LSystem.tokenify(symbol);
	    List[] replacements = lsystem.getReplacements(symbol);
	    int context=0;
	    switch (tokens.size()) {
	    case 0:
		// In case they decide to put spaces in.  Bleh.
		continue;
	    case 1:
		break;
	    default: // More than 1 symbol!
		try {
		    context = Integer.parseInt((String)tokens.get(0));
		    tokens.get(context+1); // Just to check.
		} catch (NumberFormatException e) {
		    // Not a number in the first symbol.
		    continue;
		} catch (IndexOutOfBoundsException e) {
		    // The number is out of bounds.
		    continue;
		}
		hasContexts = true;
		tokens = tokens.subList(1, tokens.size());
	    }
	    contextsList.add(new Context(tokens, context, replacements));
	}
	// Set the array.
	if (hasContexts)
	    contexts = (Context[]) contextsList.toArray(new Context[0]);
    }

    /** This is a class that is used to perform limited matchings of a
     * list. */
    private class Context {
	/**
	 * Instantiates a given context list.
	 * @param tokens the list of tokens
	 * @param center the index of the "center" token
	 * @param results the results of matching
	 */
	public Context(List tokens, int center, List[] results) {
	    this.tokens = tokens;
	    this.center = center;
	    this.results = results;
	}

	/**
	 * Given an input list, checks to see if it partially matches
	 * with the center of the input list and the center of this
	 * list.
	 * @param list the list of tokens we shoudl check this against
	 * @return the resulting replacement lists for the center
	 * token if there was a match, or an empty array otherwise
	 */
	public List[] matches(List list, int centerList) {
	    centerList -= center;
	    try {
		List sub=list.subList(centerList, centerList + tokens.size());
		if (sub.equals(tokens)) return results;
	    } catch (IndexOutOfBoundsException e) { }
	    return EMPTY_ARRAY;
	}

	/**
	 * Returns a string description of this context.
	 * @return a string description of this context
	 */
	public String toString() {
	    StringBuffer sb = new StringBuffer(super.toString());
	    sb.append(" : tokens(");
	    sb.append(tokens);
	    sb.append(") at ");
	    sb.append(center);
	    sb.append(" with ");
	    sb.append(Arrays.asList(results));
	    return sb.toString();
	}

	/** The token list to match. */
	protected List tokens;
	/** The center index. */
	protected int center;
	/** The result of finding a matching. */
	protected List[] results;
    }
    
    /** The L-system we are expanding. */
    private LSystem lsystem;
    /** For stochiastic l-systemness, this will generate random numbers. */
    private Random stochiastic;
    /** The cached expansions.  At index 0 is the axiom. */
    private List cachedExpansions = new ArrayList();
    /** For generating random seeds. */
    private static final Random RANDOM = new Random();

    /** The contexts. */
    private Context[] contexts = null;
    /** An empty list. */
    protected static final List[] EMPTY_ARRAY = new List[0];
}
