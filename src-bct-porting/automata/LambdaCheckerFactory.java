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
 
package automata;

/**
 * This lambda checker factory returns a lambda transition checker for
 * the type of automaton passed in.
 *
 * @author Ryan Cavalcante
 */

public class LambdaCheckerFactory {
    /**
     * Returns the lambda transition checker for the type of automaton
     * that <CODE>automaton</CODE> is.
     * @param automaton the automaton to get the checker for
     * @return the lambda transition checker for the type of automaton
     * that <CODE>automaton</CODE> is or <CODE>null</CODE> if there is no
     * lambda transition checker for this type of automaton
     */
    public static LambdaTransitionChecker getLambdaChecker
	(Automaton automaton) {
	if (automaton instanceof automata.fsa.FiniteStateAutomaton)
	    return new automata.fsa.FSALambdaTransitionChecker();
	else if (automaton instanceof automata.pda.PushdownAutomaton)
	    return new automata.pda.PDALambdaTransitionChecker();
	else if (automaton instanceof automata.turing.TuringMachine)
	    return new automata.turing.TMLambdaTransitionChecker();
	return null;
    }

}
