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
 * An unbound grammar has no restrictions whatsoever in the way of
 * what productions can be added to it.  Since we may no longer depend
 * on the first production being restricted, the start variable is
 * assumed to be S until the grammar is told otherwise.
 *
 * @author Thomas Finley
 */

public class UnboundGrammar extends Grammar {
    /**
     * Creates a new grammar.
     */
    public UnboundGrammar() {
	setStartVariable("S");
    }

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
}
