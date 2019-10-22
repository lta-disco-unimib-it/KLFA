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
 
package grammar;

import java.util.Comparator;

/**
 * This is a comparator for productions that sorts productions
 * lexigraphically, with the exception that the start variable is
 * always ordered first on the LHS.
 * 
 * @author Thomas Finley
 */

public class ProductionComparator implements Comparator {
    /**
     * Instantiates a comparator, getting the start variable from a
     * given grammar.
     * @param grammar the grammar
     * @throws IllegalArgumentException if the grammar does not have a
     * start variable
     */
    public ProductionComparator(Grammar grammar) {
	this(grammar.getStartVariable());
    }

    /**
     * Instantiates a comparator, with the start variable is passed in
     * explicitly.
     * @param variable the start variable
     */
    public ProductionComparator(String variable) {
	start = variable;
	if (start == null)
	    throw new IllegalArgumentException("Null start variable!");
    }
    
    /**
     * Compares two productions.
     */
    public int compare(Object o1, Object o2) {
	Production p1 = (Production) o1, p2 = (Production) o2;
	if (start.equals(p1.getLHS())) {
	    if (p1.getLHS().equals(p2.getLHS())) return 0;
	    else return -1;
	}
	if (start.equals(p2.getLHS())) return 1;
	return p1.getLHS().compareTo(p2.getRHS());
    }

    /**
     * Returns the start variable this comparator keys on.
     * @return the start variable
     */
    public String getStartVariable() {
	return start;
    }

    /**
     * Included to ensure compatibility with equals.
     * @return a hash code for this object
     */
    public int hashCode() {
	return getClass().hashCode() ^ start.hashCode();
    }

    /**
     * Two production comparators are equal if they have the same
     * start variable.
     * @param object the object to test for equality
     * @return if the two objects are equal
     */
    public boolean equals(Object object) {
	try {
	    ProductionComparator c = (ProductionComparator) object;
	    return c.start.equals(start);
	} catch (ClassCastException e) {
	    return false;
	}
    }

    /** The start variable. */
    private String start = null;
}
