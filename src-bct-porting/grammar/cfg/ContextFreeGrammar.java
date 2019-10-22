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
 
package grammar.cfg;

import grammar.Grammar;
import grammar.Production;
import grammar.ProductionChecker;

/**
 * The context free grammar object is a representation of a context free
 * grammar.  This object is a data structure of sorts, maintaining the
 * data pertinent to the definition of a context free grammar.
 *
 * @author Ryan Cavalcante
 */

public class ContextFreeGrammar extends Grammar {
    /**
     * Creates an instance of <CODE>ContextFreeGrammar</CODE>.  The 
     * created instance has no productions, no terminals, no variables, 
     * and specifically no start variable.
     */
    public ContextFreeGrammar() {
	super();
    }

    /**
     * Throws an exception if the production is unrestricted on the
     * left hand side.
     * @param production the production to check
     * @throws IllegalArgumentException if the production is
     * unrestricted on the left hand side
     */
    public void checkProduction(Production production) {
	if (!ProductionChecker.isRestrictedOnLHS(production))
	    throw new IllegalArgumentException
		("The production is unrestricted on the left hand side.");
    }
}
