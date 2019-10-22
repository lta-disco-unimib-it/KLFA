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
 * The unrestricted grammar is a grammar that can have any production
 * added to it, save for the initial production, which must be
 * restricted since the first production specifies the initial
 * variable in JFLAP.
 *
 * @author Thomas Finley
 */

public class UnrestrictedGrammar extends Grammar {
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
    
    /**
     * This adds a production to the grammar.
     * @param production the production to add
     * @throws IllegalArgumentException if the first production added
     * is unrestricted on the left hand side
     */
    public void addProduction(Production production) {
	if (myProductions.size() == 0 &&
	    !ProductionChecker.isRestrictedOnLHS(production))
	    throw new IllegalArgumentException
		("The first production must be restricted.");
	super.addProduction(production);
    }
}
