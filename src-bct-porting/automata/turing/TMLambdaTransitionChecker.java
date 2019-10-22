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
 
package automata.turing;

import automata.LambdaTransitionChecker;
import automata.Transition;

/**
 * The tm lambda transition checker can be used to check if a one-tape
 * Turing machine's transition is a lambda transition
 *
 * @author Ryan Cavalcante
 */

public class TMLambdaTransitionChecker extends LambdaTransitionChecker {
    /**
     * Returns true if <CODE>transition</CODE> is a lambda transition
     * @param transition the transition
     * @return true if <CODE>transition</CODE> is a lambda transition
     */
    public boolean isLambdaTransition(Transition transition) {
	return false;
    }

}
