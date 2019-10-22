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
 
package regular;

import java.util.EventObject;

/**
 * This event should be distributed when a regular expression object
 * changes.
 * 
 * @author Thomas Finley
 */

public class ExpressionChangeEvent extends EventObject {
    /**
     * Instantiates a change event.
     * @param expression the expression object that was changed
     * @param old the string representing the old regular expression
     */
    public ExpressionChangeEvent(RegularExpression expression, String old) {
	super(expression);
	this.expression = expression;
	this.old = old;
    }

    /**
     * Returns the regular expression that was changed.
     * @return the regular expression that was changed
     */
    public RegularExpression getExpression() {
	return expression;
    }

    /**
     * Returns the old string representation of the expression.
     * @return the old string representation of the expression
     */
    public String getOld() {
	return old;
    }

    /** The changed RE. */
    private RegularExpression expression;
    /** The old string representation of the RE. */
    private String old;
}
