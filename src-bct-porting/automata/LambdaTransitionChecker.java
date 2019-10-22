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
 * The lambda transition checker can be used to determine if a given 
 * transition is a lambda transition.
 *
 * @author Ryan Cavalcante
 */

public abstract class LambdaTransitionChecker {
    /**
     * Creates a <CODE>LambdaTransitionChecker</CODE>.
     */
    public LambdaTransitionChecker() {

    }

    /**
     * Returns true if <CODE>transition</CODE> is a lambda transition.
     * @param transition the transition.
     * @return true if <CODE>transition</CODE> is a lambda transition.
     */
    public abstract boolean isLambdaTransition(Transition transition);

    /** The lambda string. */
    protected String LAMBDA = "";
    /** The stay string. */
    protected String STAY = "S";

}
