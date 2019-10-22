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
