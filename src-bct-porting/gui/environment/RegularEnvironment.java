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
 
package gui.environment;

import regular.ExpressionChangeEvent;
import regular.ExpressionChangeListener;
import regular.RegularExpression;

/**
 * This is the environment for a regular expression.
 * 
 * @author Thomas Finley
 */

public class RegularEnvironment extends Environment {
    /**
     * Instantiates an <CODE>RegularEnvironment</CODE> for the given
     * regular expression.
     * @param expression the regular expression
     */
    public RegularEnvironment(RegularExpression expression) {
	super(expression);
	expression.addExpressionListener(new Listener());
    }

    /**
     * Returns the regular expression that this environment manages.
     * @return the regular expression that this environment manages
     */
    public RegularExpression getExpression() {
	return (RegularExpression) super.getObject();
    }

    /**
     * The expression change listener for a regular expression detects
     * if there are changes in the environment, and if so, sets the
     * dirty bit for the file.
     */
    private class Listener implements ExpressionChangeListener {
	public void expressionChanged(ExpressionChangeEvent e) {
	    setDirty();
	}
    }

    /**
     * Returns if this environment dirty.  An environment is called
     * dirty if the object it holds has been modified since the last
     * save.
     * @return <CODE>true</CODE> if the environment is dirty,
     * <CODE>false</CODE> otherwise
     */
    public boolean isDirty() {
	getExpression().asString(); // Force resolution of reference.
	return super.isDirty();
    }
}
