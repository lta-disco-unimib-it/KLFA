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

/**
 * The unrestricted grammar is a grammar that can have any production
 * added to it, save for the initial production, which must be
 * restricted since the first production specifies the initial
 * variable in JFLAP.
 *
 * @author Thomas Finley
 */

public class UnrestrictedGrammar extends Grammar {
    /**
     * Every production is all right except those with lambda in the
     * left hand side of the production.
     * @param production the production to check
     * @throws IllegalArgumentException if the production is lambda on
     * the left hand side
     */
    public void checkProduction(Production production) {
	if (production.getLHS().length() == 0) {
	    throw new IllegalArgumentException
		("The left hand side cannot be empty.");
	}
    }
    
    /**
     * This adds a production to the grammar.
     * @param production the production to add
     * @throws IllegalArgumentException if the first production added
     * is unrestricted on the left hand side
     */
    public void addProduction(Production production) {
	if (myProductions.size() == 0 &&
	    !ProductionChecker.isRestrictedOnLHS(production))
	    throw new IllegalArgumentException
		("The first production must be restricted.");
	super.addProduction(production);
    }
}
