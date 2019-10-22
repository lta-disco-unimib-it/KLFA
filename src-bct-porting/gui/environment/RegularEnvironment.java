/*******************************************************************************
 *    Copyright 2019 Fabrizio Pastore, Leonardo Mariani
 *   
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 *******************************************************************************/
 
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
