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
