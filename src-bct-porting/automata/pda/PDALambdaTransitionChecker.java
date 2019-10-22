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
 
package automata.pda;

import automata.LambdaTransitionChecker;
import automata.Transition;

/**
 * The pda lambda transition checker can be used to determine if a 
 * pushdown automaton's transition is a lambda transition
 *
 * @author Ryan Cavalcante
 */

public class PDALambdaTransitionChecker extends LambdaTransitionChecker {
    /**
     * Creates a <CODE>PDALambdaTransitionChecker</CODE>
     */
    public PDALambdaTransitionChecker() {
	super();
    }

    /**
     * Returns true if <CODE>transition</CODE> is a lambda transition
     * (i.e. all three of its fields are the lambda string).
     * @param transition the transition
     * @return true if <CODE>transition</CODE> is a lambda transition
     * (i.e. all three of its fields are the lambda string).
     */
    public boolean isLambdaTransition(Transition transition) {
	PDATransition trans = (PDATransition) transition;
	String input = trans.getInputToRead();
	String toPop = trans.getStringToPop();
	String toPush = trans.getStringToPush();
	if(input.equals(LAMBDA) && toPop.equals(LAMBDA) 
	   && toPush.equals(LAMBDA)) return true;
	return false;
    }

}
