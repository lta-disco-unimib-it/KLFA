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
