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
 
package grammar.reg;

import grammar.Production;
import grammar.ProductionChecker;

/**
 * This <CODE>RightLinearGrammar</CODE> is a regular grammar with the
 * additional restriction that the grammar cannot be a left linear
 * grammar.
 * 
 * @author Thomas Finley
 */

public class RightLinearGrammar extends RegularGrammar {
    /**
     * The production checker makes sure that the production added is
     * a proper right linear production.
     * @param production the production to check
     * @throws IllegalArgumentException if the production is not a
     * right linear production
     */
    public void checkProduction(Production production) {
	if (!ProductionChecker.isRightLinear(production))
	    throw new IllegalArgumentException
		("The production is not right linear.");
    }

}
