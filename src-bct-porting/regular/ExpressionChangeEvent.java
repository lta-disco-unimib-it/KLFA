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
