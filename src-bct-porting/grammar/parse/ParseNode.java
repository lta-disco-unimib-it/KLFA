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

import grammar.Production;

import java.util.Arrays;

import javax.swing.tree.DefaultMutableTreeNode;

/**
 * A parse node is used as an aide for brute force parsing.  It
 * contains a derivation field showing a current derivation of a
 * string, the grammar rules that were substitute in to achieve this,
 * and the position of those substitutions.<P>
 * 
 * For example, if the string "aACBb" were to derive "axxCyyb" based
 * on the rules "A->xx" and "B->yy", then the rules used array would
 * hold those two rules, and the substitution array would be {1,3} for
 * the positions in the original string that the substitutions
 * happened in.
 * 
 * @author Thomas Finley
 */

public class ParseNode extends DefaultMutableTreeNode {
    /**
     * Instantiates a new parse node.
     * @param derivation the derivation of this rule
     * @param productions the productions that led to this derivation
     * @param substitutions the positions in the parent string
     * derivation that the productions were substituted in to achieve
     * this derivation
     */
    public ParseNode(String derivation, Production[] productions,
		     int[] substitutions) {
	this.derivation = derivation;
	if (productions.length != substitutions.length)
	    throw new IllegalArgumentException
		("Production and substitution array sizes mismatch!");
	this.productions = productions;
	this.subs = substitutions;
    }

    /**
     * Instantiates a parse node based on an existing node.
     * @param node the parse node to copy
     */
    public ParseNode(ParseNode node) {
	this(node.derivation, node.productions, node.subs);
    }

    /**
     * Returns the derivation string.
     * @return the derivation string
     */
    public String getDerivation() {
	return derivation;
    }

    /**
     * Returns the productions array for this node.  For performance
     * reasons this array could not be copied, and so must not be
     * modified.
     * @return the productions that were substituted in to achieve the
     * derivation
     */
    public Production[] getProductions() {
	return productions;
    }

    /**
     * Returns the substitution positions.  For performance reasons
     * this array could not be copied, and so must not be modified.
     * @return the positions for the substitutions of the productions
     * in the parent derivation that led to this current derivation
     */
    public int[] getSubstitutions() {
	return subs;
    }

    /**
     * Returns a string representation of those object.
     * @return a string representation of those object
     */
    public String toString() {
	StringBuffer sb = new StringBuffer(derivation);
	sb.append(", ");
	sb.append(Arrays.asList(productions) + ", ");
	sb.append('[');
	for (int j=0; j<subs.length; j++) {
	    if (j!=0) sb.append(", ");
	    sb.append(subs[j]);
	}
	sb.append(']');
	return sb.toString();
    }

    /** The current string derivation. */
    private String derivation;
    /** The grammar rules used to achieve this derivation. */
    private Production[] productions;
    /** The positions at which substitutions were attempted. */
    private int[] subs;
}
